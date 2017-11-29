package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.adapter.SectionAdapter;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.MySection;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.DataServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相册
 */
public class AlbumActivity extends BaseActivity {

    private static final String TAG = AlbumActivity.class.getSimpleName();
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
    @BindView(R.id.album_recycle)
    RecyclerView albumRecycle;
    @BindView(R.id.album_swipe)
    SwipeRefreshLayout albumSwipe;

    private List<MySection> mySections;
    private SectionAdapter sectionAdapter;
    private static final int PAGE_SIZE = 5;
    private int anInt = 0;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AlbumActivity.this, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(AlbumActivity.this);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("相册");

        albumSwipe.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        albumSwipe.setColorSchemeResources(R.color.color_0e, R.color.color_0e, R.color.color_0e, R.color.color_0e);
//        albumSwipe.post(new Runnable() {
//            @Override
//            public void run() {
//                albumSwipe.setRefreshing(true);
//            }
//        });
        albumSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        albumRecycle.setLayoutManager(new GridLayoutManager(this, 4));
        mySections = DataServer.getSampleData();

        sectionAdapter = new SectionAdapter(R.layout.item_album, R.layout.section_album, mySections);

        sectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (anInt <= 1) {
                    L.e(TAG, "sectionAdapter   " + anInt);
                    setData(false, mySections);
                    anInt++;
                } else {
                    sectionAdapter.loadMoreEnd(false);
                }
            }
        }, albumRecycle);

        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = mySections.get(position);
                if (mySection.isHeader) {
                    T.showShort(AlbumActivity.this, mySection.header + "  " + position);
                } else
                    T.showShort(AlbumActivity.this, " " + position);
            }
        });

        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                T.showShort(AlbumActivity.this, "onItemChildClick " + position);
            }
        });
        albumRecycle.setAdapter(sectionAdapter);
    }

    private void setData(boolean isRefresh, List<MySection> data) {
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            sectionAdapter.setNewData(data);
        } else {
            if (size > 0) {
                sectionAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            sectionAdapter.loadMoreEnd(isRefresh);
        } else {
            sectionAdapter.loadMoreComplete();
        }
    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
        }
    }
}
