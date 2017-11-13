package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.view.FullScreenDialog;
import com.jaeger.library.StatusBarUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class AddBxActivity extends BaseActivity{

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;
    @BindView(R.id.base_ll)
    LinearLayout baseLl;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.add_by_self)
    LinearLayout addBySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.add_bx_activity);
        ButterKnife.bind(this);
        tvTitle.setVisibility(View.VISIBLE);
        baseLl.setVisibility(View.GONE);
        tvTitle.setText("扫一扫");
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddBxActivity.this, null);
    }

    @OnClick({R.id.img_back,R.id.add_by_self})//, R.id.add_by_self
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.add_by_self:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        final FullScreenDialog dialog = new FullScreenDialog(this);
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.add_bx_complete, null);

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                AddBxActivity.this.finish();
            }
        }, 1500);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);

    }
}
