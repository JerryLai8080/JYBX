package com.bx.jz.jy.jybx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.FourPhotoBean;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 显示冰箱上传图片
 */

public class ShowCameraActivity extends BaseActivity {

    private static final String TAG = ShowCameraActivity.class.getSimpleName();
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
    @BindView(R.id.img_A)
    ImageView imgA;
    @BindView(R.id.rl_A)
    RelativeLayout rlA;
    @BindView(R.id.img_B)
    ImageView imgB;
    @BindView(R.id.rl_B)
    RelativeLayout rlB;
    @BindView(R.id.img_C)
    ImageView imgC;
    @BindView(R.id.rl_C)
    RelativeLayout rlC;
    @BindView(R.id.img_D)
    ImageView imgD;
    @BindView(R.id.rl_D)
    RelativeLayout rlD;
    @BindView(R.id.img_to_album)
    ImageView imgToAlbum;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(ShowCameraActivity.this, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(ShowCameraActivity.this);
        setContentView(R.layout.activity_show_camera);
        ButterKnife.bind(this);

        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("摄像头");
//        getNewPhotos();

        swipeRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        swipeRefresh.setColorSchemeResources(R.color.color_0e, R.color.color_0e, R.color.color_0e, R.color.color_0e);
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                getNewPhotos();
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewPhotos();
            }
        });
    }

    private void getNewPhotos() {

        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.NEWPHOTO, PhotoRequest(), new OkHttpUtils.RequestCallBack<FourPhotoBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(ShowCameraActivity.this, e.getMessage());
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onResponse(FourPhotoBean response) {
                if (response.getCode() == 1) {
                    if (response.getNewmg() != null && response.getNewmg().size() != 0) {
                        for (int i = 0; i < response.getNewmg().size(); i++) {
                            switch (response.getNewmg().get(i).getPlace()) {
                                case 1:
                                    Glide.with(ShowCameraActivity.this).load(response.getNewmg().get(i).getUrl()).into(imgA);
                                    break;
                                case 2:
                                    Glide.with(ShowCameraActivity.this).load(response.getNewmg().get(i).getUrl()).into(imgB);
                                    break;
                                case 3:
                                    Glide.with(ShowCameraActivity.this).load(response.getNewmg().get(i).getUrl()).into(imgC);
                                    break;
                                case 4:
                                    Glide.with(ShowCameraActivity.this).load(response.getNewmg().get(i).getUrl()).into(imgD);
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else {
                        T.showLong(ShowCameraActivity.this, response.getMsg());
                    }
                }
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private Map<String, Object> PhotoRequest() {
        Map<String, Object> object = new HashMap<>();
        object.put("img.pid", 1);
        object.put("img.menuId", 1);
        return object;
    }

    @OnClick({R.id.img_back, R.id.rl_A, R.id.rl_B, R.id.rl_C, R.id.rl_D, R.id.img_to_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.rl_A:
                T.showShort(this, "点了A");
                break;
            case R.id.rl_B:
                T.showShort(this, "点了B");
                break;
            case R.id.rl_C:
                T.showShort(this, "点了C");
                break;
            case R.id.rl_D:
                T.showShort(this, "点了D");
                break;
            case R.id.img_to_album:
                startActivity(new Intent(ShowCameraActivity.this, AlbumActivity.class));
                break;
        }
    }
}