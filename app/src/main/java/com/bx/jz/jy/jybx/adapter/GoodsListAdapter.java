package com.bx.jz.jy.jybx.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class GoodsListAdapter extends BaseItemDraggableAdapter<Ingredients, BaseViewHolder> {

    private Context context;

    public GoodsListAdapter(List<Ingredients> data, Context context) {
        super(R.layout.item_goods_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Ingredients item) {
        Glide.with(context)
                .load(item.getImgUrl())
                .placeholder(R.mipmap.red) //设置占位图
                .error(R.mipmap.red) //设置错误图片
                .into((ImageView) helper.getView(R.id.img_goods));
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
    }
}
