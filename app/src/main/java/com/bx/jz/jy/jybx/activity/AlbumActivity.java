package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.adapter.SectionAdapter;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.Album;
import com.bx.jz.jy.jybx.bean.AlbumBean;
import com.bx.jz.jy.jybx.bean.MySection;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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

    private List<MySection> mySections = new ArrayList<>();
    private SectionAdapter sectionAdapter;
    private static final int PAGE_SIZE = 5;
    private int temp = 1;
    private boolean isRefresh = true;
    private View notDataView;
    private View errorView;
    private int anInt = 0;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AlbumActivity.this, null);
    }

    private void getAlbumList(int page) {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.ALBUM, albumRequest(page), new OkHttpUtils.RequestCallBack<AlbumBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showShort(AlbumActivity.this, e.getMessage());
                L.e(TAG, e.getMessage());
                if (!isRefresh) {
                    sectionAdapter.loadMoreFail();
                }
                sectionAdapter.setEmptyView(errorView);
                if (albumSwipe != null) {
                    albumSwipe.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(AlbumBean response) {
                if(response.getLists().size() == 0){
                    setData(true, null);
                    sectionAdapter.setEmptyView(notDataView);
                }
                mySections = new ArrayList<>();
                for (int i = 0; i < response.getLists().size(); i++) {
                    mySections.add(new MySection(true, response.getLists().get(i).getGroupTime(), false));
                    for (int j = 0; j < response.getLists().get(i).getUrl().size(); j++) {
                        mySections.add(new MySection(new Album(response.getLists().get(i).getUrl().get(j))));
                    }
                }
                temp++;
                L.e(TAG, TAG + "    " + temp);
                setData(isRefresh, mySections);
                if (albumSwipe != null) {
                    albumSwipe.setRefreshing(false);
                }
            }
        });
    }

    private Map<String, Object> albumRequest(int page) {
        Map<String, Object> object = new HashMap<>();
        object.put("img.pid", 1);
        object.put("img.menuId", 1);
        object.put("pageNo", page);
        object.put("pageNum", 5);
        return object;
    }

    @Override
    protected void onResume() {
        super.onResume();
        temp = 1;
        mySections.clear();
        albumSwipe.setRefreshing(true);
        getAlbumList(temp);
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
        albumSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                temp = 1;
                isRefresh = true;
                mySections = new ArrayList<>();
                getAlbumList(temp);
            }
        });

        albumRecycle.setLayoutManager(new GridLayoutManager(this, 4));

        sectionAdapter = new SectionAdapter(R.layout.item_album, R.layout.section_album, mySections, AlbumActivity.this);

        sectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getAlbumList(temp);
            }
        },albumRecycle);

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
        notDataView = getLayoutInflater().inflate(R.layout.empty_list_view, (ViewGroup) albumRecycle.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                mySections.clear();
                albumSwipe.setRefreshing(true);
                getAlbumList(temp);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_list_view, (ViewGroup) albumRecycle.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                mySections.clear();
                albumSwipe.setRefreshing(true);
                getAlbumList(temp);
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
