package com.bx.jz.jy.jybx.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.activity.AddBxActivity;
import com.bx.jz.jy.jybx.activity.ShowCameraActivity;
import com.bx.jz.jy.jybx.base.BaseEntity;
import com.bx.jz.jy.jybx.bean.FridgeInfoBean;
import com.bx.jz.jy.jybx.bean.ImgBean;
import com.bx.jz.jy.jybx.bean.WeatherBean;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.MarqueeText;
import com.bx.jz.jy.jybx.view.MyViewPager;
import com.bx.jz.jy.jybx.view.SettingDialog;
import com.suke.widget.SwitchButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class FragmentOne extends Fragment implements ViewSwitcher.ViewFactory {

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
    TextView llAiMode;
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
    MarqueeText tvErrorCode;
    @BindView(R.id.tv_error_reason)
    TextView tvErrorReason;
    @BindView(R.id.point1)
    ImageView point1;
    @BindView(R.id.point2)
    ImageView point2;
    @BindView(R.id.point3)
    ImageView point3;
    @BindView(R.id.ll_bx_info)
    LinearLayout bxInfo;
    @BindView(R.id.ll_add_bx)
    LinearLayout bxAdd;

    @BindView(R.id.img_page_1)
    ImageSwitcher imgPage1;
    @BindView(R.id.tv_food_name_1)
    TextView tvFoodName1;
    @BindView(R.id.tv_data_1)
    TextView tvData1;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.img_page_2)
    ImageSwitcher imgPage2;
    @BindView(R.id.tv_food_name_2)
    TextView tvFoodName2;
    @BindView(R.id.tv_data_2)
    TextView tvData2;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.img_page_3)
    ImageSwitcher imgPage3;
    @BindView(R.id.tv_food_name_3)
    TextView tvFoodName3;
    @BindView(R.id.tv_data_3)
    TextView tvData3;
    @BindView(R.id.tv_num3)
    TextView tvNum3;
    @BindView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @BindView(R.id.leng_cang_10)
    TextView leng_cang_10;
    @BindView(R.id.bian_wen_10)
    TextView bian_wen_10;
    @BindView(R.id.leng_dong_10)
    TextView leng_dong_10;

    @BindView(R.id.error_ll)
    LinearLayout error_ll;

    @BindView(R.id.bian_wen_degree)
    TextView bian_wen_degree;
    @BindView(R.id.leng_dong_degree)
    TextView leng_dong_degree;
    Unbinder unbinder;

    private List<View> viewList = new ArrayList<>();
    private Timer timer = new Timer();

    private boolean overDue1 = false;//冷藏室过期开关
    private boolean overDue2 = false;//变温室过期开关
    private boolean overDue3 = false;//冷冻室过期开关

    // 图片数组
    private int[] arrayPictures = {R.mipmap.shipu_1, R.mipmap.shipu_2, R.mipmap.shipu_3};
    private String[] arrayFoods = {"米糊", "蛋糕", "法拉卷"};
    private String[] arrayOverDue = {"2天过期", "4天过期", "8天过期"};
    // 要显示的图片在图片数组中的Index
    private int pictureIndex1 = 0;
    private int pictureIndex2 = 0;
    private int pictureIndex3 = 0;

    @Override
    public View makeView() {
        return new ImageView(getActivity());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (pictureIndex1 == arrayPictures.length) {
                        pictureIndex1 = 0;
                    }
                    relativeLayout1.setVisibility(View.VISIBLE);
                    // 设置图片切换的动画
                    imgPage1.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_in_right));
                    imgPage1.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_out_left));
                    // 设置当前要看的图片
                    imgPage1.setImageResource(arrayPictures[pictureIndex1]);

                    tvFoodName1.setText(arrayFoods[pictureIndex1]);
                    tvData1.setText(arrayOverDue[pictureIndex1]);
                    tvNum1.setText(1 + pictureIndex1 + "/" + arrayFoods.length);
                    pictureIndex1++;
                    break;
                case 1:
                    if (pictureIndex2 == arrayPictures.length) {
                        pictureIndex2 = 0;
                    }
                    relativeLayout2.setVisibility(View.VISIBLE);
                    // 设置图片切换的动画
                    imgPage2.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_in_right));
                    imgPage2.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_out_left));
                    // 设置当前要看的图片
                    imgPage2.setImageResource(arrayPictures[pictureIndex2]);

                    tvFoodName2.setText(arrayFoods[pictureIndex2]);
                    tvData2.setText(arrayOverDue[pictureIndex2]);
                    tvNum2.setText(1 + pictureIndex2 + "/" + arrayFoods.length);
                    pictureIndex2++;
                    break;
                case 2:
                    if (pictureIndex3 == arrayPictures.length) {
                        pictureIndex3 = 0;
                    }
                    relativeLayout3.setVisibility(View.VISIBLE);
                    // 设置图片切换的动画
                    imgPage3.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_in_right));
                    imgPage3.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_out_left));
                    // 设置当前要看的图片
                    imgPage3.setImageResource(arrayPictures[pictureIndex3]);

                    tvFoodName3.setText(arrayFoods[pictureIndex3]);
                    tvData3.setText(arrayOverDue[pictureIndex3]);
                    tvNum3.setText(1 + pictureIndex3 + "/" + arrayFoods.length);
                    pictureIndex3++;
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_one_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        startImageView();
        getWeather();
        getGoodList();
        return view;
    }

    private void startImageView() {

        imgPage1.setFactory(this);
        imgPage2.setFactory(this);
        imgPage3.setFactory(this);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (overDue1) {
                    handler.sendEmptyMessage(0);
                }
                if (overDue2) {
                    handler.sendEmptyMessage(1);
                }
                if (overDue3) {
                    handler.sendEmptyMessage(2);
                }
            }
        }, 0, 5000);
    }

    private void initView(ImgBean imgBean) {

        @SuppressLint("InflateParams") View view1 = getLayoutInflater().inflate(R.layout.view_pager_layout, null, false);
        ImageView img1 = view1.findViewById(R.id.img_1);
        ImageView img2 = view1.findViewById(R.id.img_2);
        ImageView img3 = view1.findViewById(R.id.img_3);

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).priority(Priority.HIGH);

        Glide.with(getActivity()).load(imgBean.getBreakfast().get(0)).apply(options).into(img1);
        Glide.with(getActivity()).load(imgBean.getBreakfast().get(1)).apply(options).into(img2);
        Glide.with(getActivity()).load(imgBean.getBreakfast().get(2)).apply(options).into(img3);

        @SuppressLint("InflateParams") View view2 = getLayoutInflater().inflate(R.layout.view_pager_layout, null, false);
        ImageView img4 = view2.findViewById(R.id.img_1);
        ImageView img5 = view2.findViewById(R.id.img_2);
        ImageView img6 = view2.findViewById(R.id.img_3);

        Glide.with(getActivity()).load(imgBean.getLunch().get(0)).apply(options).into(img4);
        Glide.with(getActivity()).load(imgBean.getLunch().get(1)).apply(options).into(img5);
        Glide.with(getActivity()).load(imgBean.getLunch().get(2)).apply(options).into(img6);

        @SuppressLint("InflateParams") View view3 = getLayoutInflater().inflate(R.layout.view_pager_layout, null, false);
        ImageView img7 = view3.findViewById(R.id.img_1);
        ImageView img8 = view3.findViewById(R.id.img_2);
        ImageView img9 = view3.findViewById(R.id.img_3);

        Glide.with(getActivity()).load(imgBean.getDinner().get(0)).apply(options).into(img7);
        Glide.with(getActivity()).load(imgBean.getDinner().get(1)).apply(options).into(img8);
        Glide.with(getActivity()).load(imgBean.getDinner().get(2)).apply(options).into(img9);

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
                        L.e(TAG, String.valueOf(response.getWeatherBean().getWeatherinfo().getTemp1() + "~" + response.getWeatherBean().getWeatherinfo().getTemp2()));
                        tvWeather.setText(String.valueOf(response.getWeatherBean().getWeatherinfo().getTemp1() + "~" + response.getWeatherBean().getWeatherinfo().getTemp2()));
                    }
                }
            }
        });
    }

    private Map<String, Object> WeatherMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("code", ConstantPool.CITYCODE);
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
        timer.cancel();
        timer = null;
    }

    @OnClick({R.id.open_camera, R.id.setting, R.id.ll_ai_mode, R.id.ll_add_bx, R.id.relativeLayout1, R.id.relativeLayout2, R.id.relativeLayout3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_camera:
//                startActivity(new Intent(getActivity(), ShowCameraActivity.class));
                startActivity(new Intent(getActivity(), AddBxActivity.class));
                break;
            case R.id.setting:
                showSettingView();
                break;
            case R.id.ll_ai_mode:
                break;
            case R.id.ll_add_bx:
                startActivity(new Intent(getActivity(), AddBxActivity.class));
                break;
            case R.id.relativeLayout1:
                break;
            case R.id.relativeLayout2:
                break;
            case R.id.relativeLayout3:
                break;
        }
    }

    private void showSettingView() {
        final ViewGroup mContainerView;
        final SettingDialog dialog = new SettingDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.setting_view, null);
        ImageView imageView = layout.findViewById(R.id.setting_back);

        mContainerView = layout.findViewById(R.id.container);

        final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
                R.layout.add_layout_1, mContainerView, false);

        DiscreteSeekBar seekBar1 = newView.findViewById(R.id.seekBar1);
        DiscreteSeekBar seekBar2 = newView.findViewById(R.id.seekBar2);
        DiscreteSeekBar seekBar3 = newView.findViewById(R.id.seekBar3);
        final TextView tvC1 = newView.findViewById(R.id.c_1);
        final TextView tvC2 = newView.findViewById(R.id.c_2);
        final TextView tvC3 = newView.findViewById(R.id.c_3);

        seekBar1.setIndicatorPopupEnabled(false);
        seekBar1.setMin(2);
        seekBar1.setMax(8);
        seekBar1.setProgress(5);
        tvC1.setText(String.valueOf(5 + "°C"));
        seekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar1 ");
                tvC1.setText(String.valueOf(value + "°C"));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        seekBar2.setIndicatorPopupEnabled(false);
        seekBar2.setMin(-18);
        seekBar2.setMax(8);
        seekBar2.setProgress(0);
        tvC2.setText(String.valueOf(0 + "°C"));
        seekBar2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar2 ");
                tvC2.setText(String.valueOf(value + "°C"));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        seekBar3.setIndicatorPopupEnabled(false);
        seekBar3.setMin(-24);
        seekBar3.setMax(-16);
        seekBar3.setProgress(-18);
        tvC3.setText(String.valueOf(-18 + "°C"));
        seekBar3.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar3 ");
                tvC3.setText(String.valueOf(value + "°C"));
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

        final SwitchButton switchbutton1 = layout.findViewById(R.id.switchbutton1);
        final SwitchButton switchbutton2 = layout.findViewById(R.id.switchbutton2);
        final SwitchButton switchbutton3 = layout.findViewById(R.id.switchbutton3);
        final SwitchButton switchbutton4 = layout.findViewById(R.id.switchbutton4);
        final SwitchButton switchbutton5 = layout.findViewById(R.id.switchbutton5);
        final SwitchButton switchbutton6 = layout.findViewById(R.id.switchbutton6);

        switchbutton2.setChecked(true);
        switchbutton6.setChecked(true);

        switchbutton1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    mContainerView.addView(newView, 1);
                    if (switchbutton2.isChecked()) {
                        switchbutton2.setChecked(false);
                    }
                    if (switchbutton3.isChecked()) {
                        switchbutton3.setChecked(false);
                    }
                    if (switchbutton4.isChecked()) {
                        switchbutton4.setChecked(false);
                    }
                    if (switchbutton5.isChecked()) {
                        switchbutton5.setChecked(false);
                    }
                    if (mContainerView.getChildCount() == 0) {
                        layout.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                    }
                } else {
                    mContainerView.removeView(newView);
                }
            }
        });

        switchbutton2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (switchbutton1.isChecked()) {
                        switchbutton1.setChecked(false);
                    }
                    if (switchbutton3.isChecked()) {
                        switchbutton3.setChecked(false);
                    }
                    if (switchbutton4.isChecked()) {
                        switchbutton4.setChecked(false);
                    }
                    if (switchbutton5.isChecked()) {
                        switchbutton5.setChecked(false);
                    }
                }
            }
        });

        switchbutton3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (switchbutton1.isChecked()) {
                        switchbutton1.setChecked(false);
                    }
                    if (switchbutton2.isChecked()) {
                        switchbutton2.setChecked(false);
                    }
                    if (switchbutton4.isChecked()) {
                        switchbutton4.setChecked(false);
                    }
                    if (switchbutton5.isChecked()) {
                        switchbutton5.setChecked(false);
                    }
                }
            }
        });

        switchbutton4.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (switchbutton1.isChecked()) {
                        switchbutton1.setChecked(false);
                    }
                    if (switchbutton2.isChecked()) {
                        switchbutton2.setChecked(false);
                    }
                    if (switchbutton3.isChecked()) {
                        switchbutton3.setChecked(false);
                    }
                    if (switchbutton5.isChecked()) {
                        switchbutton5.setChecked(false);
                    }
                }
            }
        });

        switchbutton5.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (switchbutton1.isChecked()) {
                        switchbutton1.setChecked(false);
                    }
                    if (switchbutton2.isChecked()) {
                        switchbutton2.setChecked(false);
                    }
                    if (switchbutton3.isChecked()) {
                        switchbutton3.setChecked(false);
                    }
                    if (switchbutton4.isChecked()) {
                        switchbutton4.setChecked(false);
                    }
                }
            }
        });

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

    /*
     * 获取冰箱信息 各个舱室温度，冰箱当前模式，是否有报错信息
     */
    private void getFridgeInfo() {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.FridgeInfo, info(), new OkHttpUtils.RequestCallBack<FridgeInfoBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(getActivity(), e.getMessage());
            }

            @Override
            public void onResponse(FridgeInfoBean response) {
                if (null != response && response.getCode() == 1) {

                    if (response.getRefrigerator() != null) {
                        if (response.getRefrigerator().getPattern() != null && !"".equals(response.getRefrigerator().getPattern())) {
                            llAiMode.setText(response.getRefrigerator().getPattern());
                        }
                        lengCangDegree.setText(String.valueOf(response.getRefrigerator().getRefrigerate()));
                        bian_wen_degree.setText(String.valueOf(response.getRefrigerator().getHeterotherm()));
                        leng_dong_degree.setText(String.valueOf(response.getRefrigerator().getFreeze()));
                        if (response.getRefrigerator().getRefrigerate() < 0) {
                            leng_cang_10.setVisibility(View.VISIBLE);
                        } else {
                            leng_cang_10.setVisibility(View.INVISIBLE);
                        }
                        if (response.getRefrigerator().getFreeze() < 0) {
                            leng_dong_10.setVisibility(View.VISIBLE);
                        } else {
                            leng_dong_10.setVisibility(View.INVISIBLE);
                        }
                        if (response.getRefrigerator().getHeterotherm() < 0) {
                            bian_wen_10.setVisibility(View.VISIBLE);
                        } else {
                            bian_wen_10.setVisibility(View.INVISIBLE);
                        }

                        switch (response.getRefrigerator().getAbnormity()) {
                            case 0:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱异常");
                                break;
                            case 1:
                                error_ll.setVisibility(View.GONE);
                                break;
                            case 2:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱冷藏门未关");
                                break;
                            case 3:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱变温门未关");
                                break;
                            case 4:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱冷冻门未关");
                                break;
                        }
                    }
                }
            }
        });
    }

    private Map<String, Object> info() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("refrigerator.refrigeratorid", ConstantPool.FridgeId);
        return objectMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG, "onResume  可见");
        getFridgeInfo();
    }
}
