package com.bx.jz.jy.jybx.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.utils.L;

import java.util.List;


/**
 * Created by Administrator on 2017/8/1.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> list_view;
    private Context context;
    private DeleteView deleteView;

    public interface DeleteView{
        void ChangeList(int position);
    }

    public ViewPagerAdapter(Context context, List<View> list_view) {
        deleteView = (DeleteView) context;
        this.context = context;
        this.list_view = list_view;
    }

    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if(list_view.size() > 0){
            container.addView(list_view.get(position % list_view.size()));
        }
        return list_view.get(position % list_view.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(list_view.size() != 0){
            container.removeView(list_view.get(position % list_view.size()));
        }
    }
}
