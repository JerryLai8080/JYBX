package com.bx.jz.jy.jybx.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.bx.jz.jy.jybx.base.BaseEntity;
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
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    SwipeMenuRecyclerView RecyclerView;
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

    // 创建菜单
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setBackground(R.mipmap.gary)
                    .setWidth(width)
                    .setHeight(height)
                    .setText("编辑")
                    .setTextColor(Color.WHITE);
            rightMenu.addMenuItem(addItem);

            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setBackground(R.mipmap.red)
                    .setWidth(width)
                    .setHeight(height)
                    .setText("删除")
                    .setTextColor(Color.WHITE);
            rightMenu.addMenuItem(deleteItem);
        }
    };

    // 菜单点击监听。
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                switch (menuPosition) {
                    case 0:
                        T.showShort(getActivity(),"点击了 " + adapterPosition + "的编辑");
                        startActivity(new Intent(getActivity(),AddGoodsActivity.class));
                        break;
                    case 1:
                        Ingredients Ingredients = mAdapter.getData().get(adapterPosition);
                        deleteFoods(Ingredients.getIngredientsId(),Ingredients.getUserId(),adapterPosition);
                        break;
                }
            }
        }
    };

    private void deleteFoods(Long ingredientsId, Long userId, final int adapterPosition){
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.DELETEFOODS, deleteRequest(ingredientsId, userId), new OkHttpUtils.RequestCallBack<BaseEntity>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showShort(getActivity(),e.getMessage());
            }

            @Override
            public void onResponse(BaseEntity response) {
                if(response.getCode().equals("1")){
                    mAdapter.notifyItemRemoved(adapterPosition);
                    T.showShort(getActivity(),"删除成功");
                }
            }
        });
    }

    private Map<String, Object> deleteRequest(Long ingredientsId,Long userId) {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("ingredients.refrigeratorId", 1);
        object.put("ingredients.ingredientsId", ingredientsId);
        object.put("ingredients.userId", userId);
        return object;
    }

    private void initView() {
        RecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getActivity()));
        RecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        RecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

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
        errorView = getLayoutInflater().inflate(R.layout.error_list_view, (ViewGroup) RecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ingredientsList.clear();
                SwipeRefreshLayout.setRefreshing(true);
                getGoodList(temp, Order);
            }
        });

        mAdapter = new GoodsListAdapter(ingredientsList, getActivity());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getGoodList(temp, Order);
            }
        });

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);

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
                startActivity(new Intent(getActivity(), SearchActivity.class));
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
                if (SwipeRefreshLayout != null) {
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
                if (SwipeRefreshLayout != null) {
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
