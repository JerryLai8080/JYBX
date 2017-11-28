package com.bx.jz.jy.jybx.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.activity.FoodEncyclopediaActivity;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.T;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class GoodsListAdapter extends BaseQuickAdapter<Ingredients, BaseViewHolder> {

    private Context context;

    public GoodsListAdapter(List<Ingredients> data, Context context) {
        super(R.layout.item_goods_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Ingredients item) {

        ImageView imageView = (ImageView) helper.getView(R.id.img_goods);
        final View pitchOn = helper.getView(R.id.pitch_on_view);
        LinearLayout llOnClick = helper.getView(R.id.ll_onClick);
        Glide.with(context)
                .load(item.getImgUrl())
                .placeholder(R.mipmap.red) //设置占位图
                .error(R.mipmap.red) //设置错误图片
                .into(imageView);
        helper.setText(R.id.tv_goods_name, item.getIngredientsName());
        if (item.getSubordinatePosition() != null) {
            switch (item.getSubordinatePosition()) {//所属位置(1=冷藏，2=变温，3=冷冻)
                case 1:
                    helper.setText(R.id.tv_goods_type, "冷藏");
                    break;
                case 2:
                    helper.setText(R.id.tv_goods_type, "变温");
                    break;
                case 3:
                    helper.setText(R.id.tv_goods_type, "冷冻");
                    break;
            }
        }
        if (item.getShelfLifeRemaining() != null && item.getShelfLifeRemaining() != 0) {
            int day = (int) (item.getShelfLifeRemaining() / 60 / 60 / 1000);
            helper.setText(R.id.tv_goods_date, String.valueOf(day));
        }
        helper.setText(R.id.tv_goods_weight, item.getFoodComponent() + item.getComponentUnit());

        if(!item.isClick()){
            pitchOn.setBackgroundResource(R.color.color_ee);
        }else {
            pitchOn.setBackgroundResource(R.color.color_0e);
        }

        llOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isClick()){
                    pitchOn.setBackgroundResource(R.color.color_ee);
                    item.setClick(false);
                }else {
                    pitchOn.setBackgroundResource(R.color.color_0e);
                    item.setClick(true);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(context,"点击了头像   " + item.getIngredientsName());
                context.startActivity(new Intent(context,FoodEncyclopediaActivity.class));
            }
        });
    }
}
