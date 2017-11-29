package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手动配网
 */

public class AddBySelfActivity extends BaseActivity {

    private static final String TAG = AddBySelfActivity.class.getSimpleName();

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
    @BindView(R.id.tv_open_wifi)
    TextView tvOpenWifi;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.btn_deploy)
    AppCompatButton btnDeploy;

    private boolean eyeStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(AddBySelfActivity.this);
        setContentView(R.layout.add_by_self_activity);
        ButterKnife.bind(this);

        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("连接网络");
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddBySelfActivity.this, null);
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etPwd};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_pwd};
    }

    @OnClick({R.id.img_back, R.id.tv_open_wifi, R.id.img_cancel, R.id.img_eye,R.id.btn_deploy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_open_wifi:
                T.showShort(this, "别急，逻辑还没写好 :)");
                break;
            case R.id.img_cancel:
                T.showShort(this, "别急，逻辑还没写好 :)");
                break;
            case R.id.img_eye:
                if (!eyeStatus) {
                    imgEye.setImageResource(R.mipmap.eye_off);
                    eyeStatus = true;
                } else {
                    imgEye.setImageResource(R.mipmap.eye_on);
                    eyeStatus = false;
                }
                break;
            case R.id.btn_deploy:
                T.showShort(this, "别急，逻辑还没写好 :)");
                break;
        }
    }

}
