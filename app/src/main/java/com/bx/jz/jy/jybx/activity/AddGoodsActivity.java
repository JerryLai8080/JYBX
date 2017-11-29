package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.view.FullScreenDialog;
import com.jaeger.library.StatusBarUtil;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加食材界面
 */

public class AddGoodsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.discreteSeekBar)
    DiscreteSeekBar discreteSeekBar;
    @BindView(R.id.cang)
    TextView cang;
    @BindView(R.id.wen)
    TextView wen;
    @BindView(R.id.dong)
    TextView dong;
    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;
    @BindView(R.id.base_ll)
    LinearLayout baseLl;
    @BindView(R.id.SeekBar)
    DiscreteSeekBar SeekBar;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private int durabilityPeriod = 0;
    private int howMuch = 0;
    private int whichBX = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_add_goods);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("食材编辑");

        discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                durabilityPeriod = value;
                L.e("TAG", durabilityPeriod + "");
                tvDate.setText(String.valueOf(value + "天"));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
            }
        });

        SeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                howMuch = value;
                L.e("TAG", howMuch + "");
                tvNum.setText(String.valueOf(value + "克"));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddGoodsActivity.this, null);
    }

    @OnClick({R.id.cang, R.id.wen, R.id.dong, R.id.img_back, R.id.img_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.cang:
                setO();
                cang.setTextColor(getResources().getColor(R.color.color_0e));
                whichBX = 1;
                break;
            case R.id.wen:
                setO();
                wen.setTextColor(getResources().getColor(R.color.color_0e));
                whichBX = 2;
                break;
            case R.id.dong:
                setO();
                dong.setTextColor(getResources().getColor(R.color.color_0e));
                whichBX = 3;
                break;
            case R.id.img_complete:
                getDialog();
                break;
        }
    }

    private void setO() {
        cang.setTextColor(getResources().getColor(R.color.color_9d));
        wen.setTextColor(getResources().getColor(R.color.color_9d));
        dong.setTextColor(getResources().getColor(R.color.color_9d));
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etName};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_name};
    }

    public void getDialog() {
        final FullScreenDialog dialog = new FullScreenDialog(this);
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_add_edit, null);
        TextView content = layout.findViewById(R.id.content);
        content.setText("添加成功");

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                AddGoodsActivity.this.finish();
            }
        }, 1500);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }
}
