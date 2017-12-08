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
import com.bx.jz.jy.jybx.view.RulerView;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加食材界面
 */

public class EditorsMaterialActivity extends BaseActivity {

    private static final String TAG = EditorsMaterialActivity.class.getSimpleName();

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
    @BindView(R.id.rulerView)
    RulerView rulerView;
    @BindView(R.id.rulerView_day)
    RulerView rulerViewDay;
    @BindView(R.id.tv_ke)
    TextView tvKe;
    @BindView(R.id.tv_ge)
    TextView tvGe;
    @BindView(R.id.tv_he)
    TextView tvHe;
    @BindView(R.id.tv_jin)
    TextView tvJin;
    @BindView(R.id.et_name)
    EditText etName;

    private int whichBX = 0;//冷藏室 1 ， 变温室  2 ， 冷冻室 3

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_editors_material);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("食材编辑");

        rulerViewDay.setUnit("天");
        rulerViewDay.setMaxScale(30);
        rulerViewDay.setMinScale(0);
        rulerViewDay.setFirstScale(15);
        rulerViewDay.setScaleCount(2);
        rulerViewDay.setScaleGap(200);

        rulerView.setUnit("克");
        rulerView.setMaxScale(100);
        rulerView.setMinScale(0);
        rulerView.setFirstScale(50);

        rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {

            }
        });

//        rulerView.computeScrollTo(Float.parseFloat(((EditText) findViewById(R.id.edt)).getText().toString()));

        if (getIntent() != null) {
            whichBX = getIntent().getIntExtra("whichBX", 0);
            L.e(TAG, String.valueOf(whichBX));
        }

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
        StatusBarUtil.setTranslucentForImageViewInFragment(EditorsMaterialActivity.this, null);
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
                EditorsMaterialActivity.this.finish();
            }
        }, 1500);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

    @OnClick({R.id.img_back, R.id.tv_ke, R.id.tv_ge, R.id.tv_he, R.id.tv_jin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_ke:
                setO();
                tvKe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("克");
                break;
            case R.id.tv_ge:
                setO();
                tvGe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("个");
                break;
            case R.id.tv_he:
                setO();
                tvHe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("盒");
                break;
            case R.id.tv_jin:
                setO();
                tvJin.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("斤");
                break;
        }
    }

    private void setO() {
        tvGe.setTextColor(getResources().getColor(R.color.color_df));
        tvKe.setTextColor(getResources().getColor(R.color.color_df));
        tvHe.setTextColor(getResources().getColor(R.color.color_df));
        tvJin.setTextColor(getResources().getColor(R.color.color_df));
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etName};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_name};
    }
}
