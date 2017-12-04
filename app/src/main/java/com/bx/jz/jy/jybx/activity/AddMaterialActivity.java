package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class AddMaterialActivity extends BaseActivity {

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
    @BindView(R.id.lengCang)
    TextView lengCang;
    @BindView(R.id.bianWen)
    TextView bianWen;
    @BindView(R.id.lengDong)
    TextView lengDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("食材添加");
    }

    @OnClick({R.id.img_back, R.id.lengCang, R.id.bianWen, R.id.lengDong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.lengCang:
                toZero();
                lengCang.setTextColor(getResources().getColor(R.color.theme_other));
                break;
            case R.id.bianWen:
                toZero();
                bianWen.setTextColor(getResources().getColor(R.color.theme_other));
                break;
            case R.id.lengDong:
                toZero();
                lengDong.setTextColor(getResources().getColor(R.color.theme_other));
                break;
        }
    }

    private void toZero(){
        lengCang.setTextColor(getResources().getColor(R.color.color_666));
        bianWen.setTextColor(getResources().getColor(R.color.color_666));
        lengDong.setTextColor(getResources().getColor(R.color.color_666));
    }
}
