package com.bx.jz.jy.jybx.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.bean.Album;
import com.bx.jz.jy.jybx.bean.MySection;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<MySection,BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MySection> data , Context context) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        Album album = item.t;
        Glide.with(context).load(album.getImg())
                .placeholder(R.mipmap.m_img1) //设置占位图
                .error(R.mipmap.m_img1) //设置错误图片
         .into((ImageView) helper.getView(R.id.iv));
    }
}
