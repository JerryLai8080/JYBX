package com.bx.jz.jy.jybx.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.activity.AddGoodsActivity;
import com.bx.jz.jy.jybx.activity.SearchActivity;
import com.bx.jz.jy.jybx.adapter.GoodsListAdapter;
import com.bx.jz.jy.jybx.base.BaseListEntity;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.LinearLayoutManagerWrapper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class FragmentTwo extends Fragment {

    private static final String TAG = "FragmentTwo";

    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.durability_period)
    LinearLayout durabilityPeriod;
    @BindView(R.id.all_goods)
    LinearLayout allGoods;
    @BindView(R.id.RecyclerView)
    android.support.v7.widget.RecyclerView RecyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.tv_all_goods)
    TextView tvAllGoods;
    Unbinder unbinder;

    private static final int PAGE_SIZE = 10;
    private List<Ingredients> ingredientsList = new ArrayList<>();
    private GoodsListAdapter mAdapter;

    private String Order = "asc";
    private int temp = 1;
    private boolean isRefresh = true;
    private boolean isDesc = false;
    private View notDataView;
    private View errorView;
    private boolean isAll = false;
    private int subordinatePosition = 0;

    String[] arrayList = {"所有", "冷藏", "变温", "冷冻"};

    @Override
    public void onResume() {
        super.onResume();
        temp = 1;
        ingredientsList.clear();
        SwipeRefreshLayout.setRefreshing(true);
        getGoodList(temp, Order);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_two_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        SwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        // 设置下拉进度的主题颜色
        SwipeRefreshLayout.setColorSchemeResources(R.color.color_0e, R.color.color_0e, R.color.color_0e);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                temp = 1;
                isRefresh = true;
                getGoodList(temp, Order);
            }
        });

        initView();
        return view;
    }

    private void initView() {
        RecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getActivity()));

        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) RecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ingredientsList.clear();
                SwipeRefreshLayout.setRefreshing(true);
                getGoodList(temp, Order);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) RecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ingredientsList.clear();
                SwipeRefreshLayout.setRefreshing(true);
                getGoodList(temp, Order);
            }
        });

//        OnItemDragListener listener = new OnItemDragListener() {
//            @Override
//            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                L.d(TAG, "drag start");
//                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
////                holder.setTextColor(R.id.tv, Color.WHITE);
//            }
//
//            @Override
//            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
//                L.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
//            }
//
//            @Override
//            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
//                L.d(TAG, "drag end");
//                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
////                holder.setTextColor(R.id.tv, Color.BLACK);
//            }
//        };
//        final Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setTextSize(20);
//        paint.setColor(Color.BLACK);
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                L.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                L.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                L.d(TAG, "View Swiped: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(getActivity(), R.color.color_0e));
//                canvas.drawText("Just some text", 0, 40, paint);
                L.d(TAG, "View Moving: " + isCurrentlyActive);
            }
        };

        mAdapter = new GoodsListAdapter(ingredientsList, getActivity());

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getGoodList(temp, Order);
            }
        });

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(RecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START );//| ItemTouchHelper.END
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
//        mAdapter.enableDragItem(mItemTouchHelper);
//        mAdapter.setOnItemDragListener(listener);
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        RecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T.showShort(getActivity(), "点击了" + position);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DecorViewUtils.getDarkDecorView(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.durability_period, R.id.all_goods, R.id.img_search, R.id.img_add})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.durability_period://保质期剩余
                temp = 1;
                isRefresh = true;
                if (!isDesc) {
                    Order = "desc";
                    SwipeRefreshLayout.setRefreshing(true);
                    getGoodList(temp, Order);
                    isDesc = true;

                } else {
                    Order = "asc";
                    SwipeRefreshLayout.setRefreshing(true);
                    getGoodList(temp, Order);
                    isDesc = false;
                }
                break;
            case R.id.all_goods:

                final LayoutInflater dialogInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList) {

                    @NonNull
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                        convertView = dialogInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                        TextView tvSimple = (TextView) convertView.findViewById(android.R.id.text1);
                        String display = this.getItem(position);
                        tvSimple.setText(display);

                        if (tvAllGoods.getText().toString().equals(tvSimple.getText().toString())) {
                            tvSimple.setTextColor(getResources().getColor(R.color.color_0e));
                        }
                        return convertView;
                    }
                };

                DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = 1;
                        isRefresh = true;

                        if (which == 0) {
                            isAll = false;
                            SwipeRefreshLayout.setRefreshing(true);
                            getGoodList(temp, Order);
                        } else {
                            isAll = true;
                            subordinatePosition = which;
                            isRefresh = true;
                            SwipeRefreshLayout.setRefreshing(true);
                            getGoodList(temp, Order);
                        }
                        tvAllGoods.setText(arrayList[which]);
                        tvAllGoods.setTextColor(getResources().getColor(R.color.color_0e));
                    }
                };
                new AlertDialog.Builder(getActivity()).setAdapter(adapter, clickListener).create().show();
                break;
            case R.id.img_search:
                startActivity(new Intent(getActivity(),SearchActivity.class));
                break;
            case R.id.img_add:
                startActivity(new Intent(getActivity(), AddGoodsActivity.class));
                break;
        }
    }

    private void getGoodList(int page, String order) {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.GOODSLIST, GoodsRequest(page, order), new OkHttpUtils.RequestCallBack<BaseListEntity<Ingredients>>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(getActivity(), e.getMessage());
                if (!isRefresh) {
                    mAdapter.loadMoreFail();
                }
                mAdapter.setEmptyView(errorView);
                if(SwipeRefreshLayout != null){
                    SwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(BaseListEntity<Ingredients> response) {
                if (response.getList() == null && response.getList().size() == 0) {
                    mAdapter.setEmptyView(notDataView);
                } else {
                    temp++;
                    setData(isRefresh, response.getList());
                }
                if(SwipeRefreshLayout != null){
                    SwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private Map<String, Object> GoodsRequest(int page, String order) {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("ingredients.refrigeratorId", 1);
        if (isAll) {
            object.put("ingredients.subordinatePosition", subordinatePosition);
        }
        object.put("pageNo", page);
        object.put("pageNum", 10);
        object.put("order", order);//降序：desc；升序：asc
        return object;
    }

    private void setData(boolean isRefresh, List data) {
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

}
