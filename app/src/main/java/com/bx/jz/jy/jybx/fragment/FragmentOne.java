package com.bx.jz.jy.jybx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.bean.ImgBean;
import com.bx.jz.jy.jybx.bean.WeatherBean;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.MyViewPager;
import com.bx.jz.jy.jybx.view.SettingDialog;
import com.suke.widget.SwitchButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class FragmentOne extends Fragment {

    private static final String TAG = "FragmentOne";
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tab_one_viewpager)
    MyViewPager viewPager;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.open_camera)
    ImageView openCamera;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.tab_one_title)
    LinearLayout tabOneTitle;
    @BindView(R.id.ll_ai_mode)
    LinearLayout llAiMode;
    @BindView(R.id.leng_cang)
    TextView lengCang;
    @BindView(R.id.leng_cang_degree)
    TextView lengCangDegree;
    @BindView(R.id.ll_leng_cang)
    RelativeLayout llLengCang;
    @BindView(R.id.bian_wen)
    TextView bianWen;
    @BindView(R.id.ll_bian_wen)
    RelativeLayout llBianWen;
    @BindView(R.id.leng_dong)
    TextView lengDong;
    @BindView(R.id.ll_leng_dong)
    RelativeLayout llLengDong;
    @BindView(R.id.tv_error_time)
    TextView tvErrorTime;
    @BindView(R.id.tv_error_code)
    TextView tvErrorCode;
    @BindView(R.id.tv_error_reason)
    TextView tvErrorReason;
    @BindView(R.id.point1)
    ImageView point1;
    @BindView(R.id.point2)
    ImageView point2;
    @BindView(R.id.point3)
    ImageView point3;


    Unbinder unbinder;

    private List<View> viewList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_one_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        getWeather();
        getGoodList();
        return view;
    }

    private void initView(ImgBean imgBean) {

        View view1 = getLayoutInflater().inflate(R.layout.view_pager_layout, null);
        ImageView img1 = view1.findViewById(R.id.img_1);
        ImageView img2 = view1.findViewById(R.id.img_2);
        ImageView img3 = view1.findViewById(R.id.img_3);


        Glide.with(getActivity()).load(imgBean.getBreakfast().get(0)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img1);
        Glide.with(getActivity()).load(imgBean.getBreakfast().get(1)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img2);
        Glide.with(getActivity()).load(imgBean.getBreakfast().get(2)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img3);

        View view2 = getLayoutInflater().inflate(R.layout.view_pager_layout, null);
        ImageView img4 = view2.findViewById(R.id.img_1);
        ImageView img5 = view2.findViewById(R.id.img_2);
        ImageView img6 = view2.findViewById(R.id.img_3);

        Glide.with(getActivity()).load(imgBean.getLunch().get(0)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img4);
        Glide.with(getActivity()).load(imgBean.getLunch().get(1)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img5);
        Glide.with(getActivity()).load(imgBean.getLunch().get(2)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img6);

        View view3 = getLayoutInflater().inflate(R.layout.view_pager_layout, null);
        ImageView img7 = view3.findViewById(R.id.img_1);
        ImageView img8 = view3.findViewById(R.id.img_2);
        ImageView img9 = view3.findViewById(R.id.img_3);

        Glide.with(getActivity()).load(imgBean.getDinner().get(0)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img7);
        Glide.with(getActivity()).load(imgBean.getDinner().get(1)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img8);
        Glide.with(getActivity()).load(imgBean.getDinner().get(2)).placeholder(R.mipmap.red).error(R.mipmap.red).into(img9);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img1");
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img2");
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img3");
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img4");
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img5");
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img6");
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img7");
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img8");
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getContext(), "img9");
            }
        });


        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position % viewList.size()));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position % viewList.size()));

                return viewList.get(position % viewList.size());
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        point1.setImageResource(R.mipmap.point);
                        point2.setImageResource(R.mipmap.point_no);
                        point3.setImageResource(R.mipmap.point_no);
                        tvRecommend.setText("早餐推荐");
                        break;
                    case 1:
                        point1.setImageResource(R.mipmap.point_no);
                        point2.setImageResource(R.mipmap.point);
                        point3.setImageResource(R.mipmap.point_no);
                        tvRecommend.setText("午餐推荐");
                        break;
                    case 2:
                        point1.setImageResource(R.mipmap.point_no);
                        point2.setImageResource(R.mipmap.point_no);
                        point3.setImageResource(R.mipmap.point);
                        tvRecommend.setText("晚餐推荐");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getWeather() {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.WEATHER, WeatherMap(), new OkHttpUtils.RequestCallBack<WeatherBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(getActivity(), e.getMessage());
            }

            @Override
            public void onResponse(WeatherBean response) {
                if (response != null) {
                    if (response.getCode().equals("1")) {
                        tvWeather.setText(String.valueOf(response.getWeatherBean().getWeatherinfo().getTemp1() + "~" + response.getWeatherBean().getWeatherinfo().getTemp2()));
                    }
                }
            }
        });
    }

    private Map<String, Object> WeatherMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("code", "101210101");
        return object;
    }

    private void getGoodList() {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.GOODSRECOMMEND, map(), new OkHttpUtils.RequestCallBack<ImgBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(getActivity(), e.getMessage());
            }

            @Override
            public void onResponse(ImgBean response) {
                if (response != null) {
                    initView(response);
                }
            }
        });
    }

    private Map<String, Object> map() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("pageNum", 10);
        return object;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.open_camera, R.id.setting, R.id.ll_ai_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_camera:
                break;
            case R.id.setting:
                showSettingView();
                break;
            case R.id.ll_ai_mode:
                break;
        }
    }

    private void showSettingView() {
        final ViewGroup mContainerView;
        final SettingDialog dialog = new SettingDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.setting_view, null);
        ImageView imageView = layout.findViewById(R.id.setting_back);

        mContainerView = (ViewGroup) layout.findViewById(R.id.container);

        final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
                R.layout.add_layout_1, mContainerView, false);

        DiscreteSeekBar seekBar1 = newView.findViewById(R.id.seekBar1);
        DiscreteSeekBar seekBar2 = newView.findViewById(R.id.seekBar2);
        DiscreteSeekBar seekBar3 = newView.findViewById(R.id.seekBar3);

        seekBar1.setProgress(1000);
        seekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar1 ");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        seekBar2.setProgress(500);

        seekBar2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar2 ");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        seekBar3.setProgress(250);

        seekBar3.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar3 ");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        SwitchButton switchbutton1 = layout.findViewById(R.id.switchbutton1);
        SwitchButton switchbutton2 = layout.findViewById(R.id.switchbutton2);
        SwitchButton switchbutton3 = layout.findViewById(R.id.switchbutton3);
        SwitchButton switchbutton4 = layout.findViewById(R.id.switchbutton4);
        SwitchButton switchbutton5 = layout.findViewById(R.id.switchbutton5);
        SwitchButton switchbutton6 = layout.findViewById(R.id.switchbutton6);

        switchbutton2.setChecked(true);
        switchbutton6.setChecked(true);

        switchbutton1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    mContainerView.addView(newView,1);
                    if (mContainerView.getChildCount() == 0) {
                        layout.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                    }
                }else {
                    mContainerView.removeView(newView);
                }
            }
        });

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

}
