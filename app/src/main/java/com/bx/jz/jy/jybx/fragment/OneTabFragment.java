package com.bx.jz.jy.jybx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseFragment;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class OneTabFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_tab,container,false);

        return view;

    }
}
