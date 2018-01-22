package com.bx.jz.jy.jybx.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bx.jz.jy.jybx.R;

import java.util.List;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/22 0022.
 * desc:
 */

public class MyPagerAdapter3 extends PagerAdapter {

    private List<ImageView> imageList;
    private ViewPager viewPager;
    private RequestOptions options = new RequestOptions()
            .centerCrop().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).priority(Priority.HIGH);

    public MyPagerAdapter3(List<ImageView> imageList, ViewPager viewPager) {
        this.imageList = imageList;
        this.viewPager = viewPager;
    }

    /**
     * 返回的int的值, 会作为ViewPager的总长度来使用.
     */
    @Override
    public int getCount() {
        return imageList.size();//Integer.MAX_VALUE伪无限循环
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     * position 就是当前需要加载条目的索引
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 把position对应位置的ImageView添加到ViewPager中
        ImageView iv = imageList.get(position % imageList.size());
        viewPager.addView(iv);
        // 把当前添加ImageView返回回去.
        return iv;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        viewPager.removeView(imageList.get(position % imageList.size()));
    }
}

