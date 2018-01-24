package com.bx.jz.jy.jybx.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import com.bx.jz.jy.jybx.adapter.MyPagerAdapter1;
import com.bx.jz.jy.jybx.adapter.MyPagerAdapter2;
import com.bx.jz.jy.jybx.adapter.MyPagerAdapter3;
import com.bx.jz.jy.jybx.bean.CodeBean;
import com.bx.jz.jy.jybx.bean.FridgeInfoBean;
import com.bx.jz.jy.jybx.bean.ImgBean;
import com.bx.jz.jy.jybx.bean.WeatherBean;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.MarqueeText;
import com.bx.jz.jy.jybx.view.MyViewPager;
import com.bx.jz.jy.jybx.view.SettingDialog;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.Mode;
import com.joyoung.sdk.interface_sdk.ChangeDevCallBack;
import com.joyoung.sdk.interface_sdk.CommandCallBack;
import com.joyoung.sdk.interface_sdk.SendCmdCallback;
import com.joyoung.sdk.utils.JoyoungSDK;
import com.suke.widget.SwitchButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.bx.jz.jy.jybx.ConstantPool.devId;
import static com.bx.jz.jy.jybx.ConstantPool.devTypeId;
import static com.bx.jz.jy.jybx.activity.MainActivity.xxteaKey;

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
    ViewPager imgPage1;
    @BindView(R.id.tv_food_name_1)
    TextView tvFoodName1;
    @BindView(R.id.tv_data_1)
    TextView tvData1;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.img_page_2)
    ViewPager imgPage2;
    @BindView(R.id.tv_food_name_2)
    TextView tvFoodName2;
    @BindView(R.id.tv_data_2)
    TextView tvData2;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.img_page_3)
    ViewPager imgPage3;
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

    @BindView(R.id.img_leco)
    ImageView imgLeco;
    Unbinder unbinder;

    private List<View> viewList = new ArrayList<>();
    private Timer timer = new Timer();
    private SettingDialog dialog;

    private boolean overDue1 = false;//冷藏室过期开关
    private boolean overDue2 = false;//变温室过期开关
    private boolean overDue3 = false;//冷冻室过期开关

    // 图片数组
    private List<String> arrayPictures1 = new ArrayList<>();
    private List<String> arrayPictures2 = new ArrayList<>();
    private List<String> arrayPictures3 = new ArrayList<>();

    // 图片数组
    private List<ImageView> imageList1 = new ArrayList<>();
    private List<ImageView> imageList2 = new ArrayList<>();
    private List<ImageView> imageList3 = new ArrayList<>();

    private List<String> arrayFoods1 = new ArrayList<>();
    private List<String> arrayFoods2 = new ArrayList<>();
    private List<String> arrayFoods3 = new ArrayList<>();

    private List<Double> arrayOverDue1 = new ArrayList<>();
    private List<Double> arrayOverDue2 = new ArrayList<>();
    private List<Double> arrayOverDue3 = new ArrayList<>();

    private MyPagerAdapter1 mPagerAdapter1;
    private MyPagerAdapter2 mPagerAdapter2;
    private MyPagerAdapter3 mPagerAdapter3;

    private int Refrigerate;//冷藏室温度
    private int Heterotherm;//变温室温度
    private int Freeze;//冷冻室温度

    private boolean Mode_1 = false;
    private boolean Mode_2 = false;
    private boolean Mode_3 = false;
    private boolean Mode_4 = false;
    private boolean Mode_5 = false;
    private boolean Mode_6 = false;
    private boolean Mode_100 = false;

    private int Refrigerate_1;//冷藏室温度
    private int Heterotherm_1;//变温室温度
    private int Freeze_1;//冷冻室温度

    private boolean Mode_1_1 = false;
    private boolean Mode_2_1 = false;
    private boolean Mode_3_1 = false;
    private boolean Mode_4_1 = false;
    private boolean Mode_5_1 = false;
    private boolean Mode_6_1 = false;
    private boolean Mode_100_1 = false;

    //向MQTT发送的指令
    private String CMD;
    //冰箱指令
    private byte[] FridgeData;

    private RequestOptions options = new RequestOptions()
            .centerCrop().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).priority(Priority.HIGH);

    private byte DATA_0 = ConstantPool.Data0_beginning_commend;
    private byte DATA_1 = ConstantPool.Data1_beginning_commend;
    private byte DATA_2 = ConstantPool.Zero;
    private byte DATA_3 = ConstantPool.Zero;
    private byte DATA_4 = ConstantPool.Zero;
    private byte DATA_5 = ConstantPool.Zero;
    private byte DATA_6 = ConstantPool.Default;
    private byte DATA_7 = ConstantPool.Default;
    private byte DATA_8 = ConstantPool.Default;
    private byte DATA_9 = ConstantPool.Zero;
    private byte DATA_10 = ConstantPool.Zero;
    private byte DATA_11 = ConstantPool.Zero;
    private byte DATA_12 = ConstantPool.Zero;
    private byte DATA_13 = ConstantPool.Zero;
    private byte DATA_14 = ConstantPool.Zero;
    private byte DATA_15 = ConstantPool.Zero;
    private byte DATA_16 = ConstantPool.Zero;
    private byte DATA_17 = ConstantPool.Zero;
    private byte DATA_18 = ConstantPool.Zero;
    private byte DATA_19 = ConstantPool.Zero;
    private byte DATA_20 = ConstantPool.Zero;
    private byte DATA_21 = ConstantPool.Zero;
    private byte DATA_22 = ConstantPool.Zero;
    private byte DATA_23;
    private byte DATA_24 = ConstantPool.Zero;
    private byte DATA_25 = ConstantPool.Zero;
    private byte DATA_26 = ConstantPool.Zero;
    private byte DATA_27 = ConstantPool.Zero;
    private byte DATA_28 = ConstantPool.Zero;
    private byte DATA_29 = ConstantPool.Zero;
    private byte DATA_30 = ConstantPool.Zero;
    private byte DATA_31 = ConstantPool.Zero;
    private byte DATA_32 = ConstantPool.Zero;
    private byte DATA_33 = ConstantPool.Zero;
    private byte DATA_34 = ConstantPool.Zero;
    private byte DATA_35 = ConstantPool.Zero;
    private byte DATA_36 = ConstantPool.Zero;
    private byte DATA_37 = ConstantPool.Zero;
    private byte DATA_38 = ConstantPool.Zero;
    private byte DATA_39 = ConstantPool.Zero;
    private byte DATA_40 = ConstantPool.Zero;
    private byte DATA_41 = ConstantPool.Zero;
    private byte DATA_42 = ConstantPool.Zero;
    private byte DATA_43 = ConstantPool.Zero;
    private byte DATA_44 = ConstantPool.Zero;
    private byte DATA_45 = ConstantPool.Zero;
    private byte DATA_46;

    private int MODE;

    private SwitchButton switchbutton1;
    private SwitchButton switchbutton2;
    private SwitchButton switchbutton3;
    private SwitchButton switchbutton4;
    private SwitchButton switchbutton5;
    private SwitchButton switchbutton6;
    private SwitchButton switchbutton100;

    @Override
    public View makeView() {
        return new ImageView(getActivity());
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    for (int i = 0; i < arrayPictures1.size(); i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageList1.add(imageView);
                        Glide.with(getActivity()).load(arrayPictures1.get(i)).apply(options).into(imageView);
                    }
                    if (imageList1 != null && imageList1.size() != 0) {
                        mPagerAdapter1 = new MyPagerAdapter1(imageList1, imgPage1, getActivity());
                        imgPage1.setAdapter(mPagerAdapter1);
                        imgPage1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                tvFoodName1.setText(arrayFoods1.get(position));
                                tvNum1.setText(1 + position + "/" + arrayPictures1.size());
                                if (arrayOverDue1.get(position) > 0) {
                                    tvData1.setText("还有" + String.valueOf(Math.abs(arrayOverDue1.get(position))) + "天过期");
                                } else {
                                    tvData1.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue1.get(position))) + "天");
                                }
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }

                    tvFoodName1.setText(arrayFoods1.get(0));
                    if (arrayOverDue1.get(0) > 0) {
                        tvData1.setText("还有" + String.valueOf(Math.abs(arrayOverDue1.get(0))) + "天过期");
                    } else {
                        tvData1.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue1.get(0))) + "天");
                    }
                    tvNum1.setText(1 + 0 + "/" + arrayPictures1.size());

                    ViewAnimation(relativeLayout1);
                    break;
                case 1:
                    for (int i = 0; i < arrayPictures2.size(); i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageList2.add(imageView);
                        Glide.with(getActivity()).load(arrayPictures2.get(i)).apply(options).into(imageView);
                    }
                    if (imageList2 != null && imageList2.size() != 0) {
                        mPagerAdapter2 = new MyPagerAdapter2(imageList2, imgPage2, getActivity());
                        imgPage2.setAdapter(mPagerAdapter2);
                        imgPage2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                tvFoodName2.setText(arrayFoods2.get(position));

                                if (arrayOverDue2.get(position) > 0) {
                                    tvData2.setText("还有" + String.valueOf(Math.abs(arrayOverDue2.get(position))) + "天过期");
                                } else {
                                    tvData2.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue2.get(position))) + "天");
                                }

                                tvNum2.setText(1 + position + "/" + arrayPictures2.size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }

                    tvFoodName2.setText(arrayFoods2.get(0));
                    if (arrayOverDue2.get(0) > 0) {
                        tvData2.setText("还有" + String.valueOf(Math.abs(arrayOverDue2.get(0))) + "天过期");
                    } else {
                        tvData2.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue2.get(0))) + "天");
                    }
                    tvNum2.setText(1 + 0 + "/" + arrayPictures2.size());

                    ViewAnimation(relativeLayout2);
                    break;
                case 2:
                    for (int i = 0; i < arrayPictures3.size(); i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageList3.add(imageView);
                        Glide.with(getActivity()).load(arrayPictures3.get(i)).apply(options).into(imageView);
                    }
                    if (imageList3 != null && imageList3.size() != 0) {
                        mPagerAdapter3 = new MyPagerAdapter3(imageList3, imgPage3, getActivity());
                        imgPage3.setAdapter(mPagerAdapter3);
                        imgPage3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                tvFoodName3.setText(arrayFoods3.get(position));
                                if (arrayOverDue3.get(position) > 0) {
                                    tvData3.setText("还有" + String.valueOf(Math.abs(arrayOverDue3.get(position))) + "天过期");
                                } else {
                                    tvData3.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue3.get(position))) + "天");
                                }
                                tvNum3.setText(1 + position + "/" + arrayPictures3.size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }

                    tvFoodName3.setText(arrayFoods3.get(0));
                    if (arrayOverDue2.get(0) > 0) {
                        tvData3.setText("还有" + String.valueOf(Math.abs(arrayOverDue3.get(0))) + "天过期");
                    } else {
                        tvData3.setText("已经过期" + String.valueOf(Math.abs(arrayOverDue3.get(0))) + "天");
                    }
                    tvNum3.setText(1 + "/" + arrayPictures3.size());

                    ViewAnimation(relativeLayout3);
                    break;
            }
        }
    };

    private void ViewAnimation(View view) {
        view.setVisibility(View.VISIBLE);
        PropertyValuesHolder holder7 = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder holder8 = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder holder9 = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        ObjectAnimator.ofPropertyValuesHolder(view, holder7, holder8, holder9).start();


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e(TAG, "点击了  " + v.getId() + "/////");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_one_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        getWeather();
        getGoodList();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void startImageView(final int sw) {

        switch (sw) {
            case 0:
                if (overDue1) {
                    handler.sendEmptyMessage(sw);
                }
                break;
            case 1:
                if (overDue2) {
                    handler.sendEmptyMessage(sw);
                }
                break;
            case 2:
                if (overDue3) {
                    handler.sendEmptyMessage(sw);
                }
                break;
        }
    }

    private void initView(ImgBean imgBean) {

        @SuppressLint("InflateParams") View view1 = getLayoutInflater().inflate(R.layout.view_pager_layout, null, false);
        ImageView img1 = view1.findViewById(R.id.img_1);
        ImageView img2 = view1.findViewById(R.id.img_2);
        ImageView img3 = view1.findViewById(R.id.img_3);

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
        if (timer != null) {
            timer.cancel();
        }
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
        JoyoungSDK joyoungSDK = JoyoungSDK.getInstance();

        final ViewGroup mContainerView;
        dialog = new SettingDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.setting_view, null);
        ImageView imageView = layout.findViewById(R.id.setting_back);
        TextView tvConfirm = layout.findViewById(R.id.confirm);
        mContainerView = layout.findViewById(R.id.container);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joyoungSDK.changeDev(getActivity(), xxteaKey, devTypeId, devId, mCommandCallBack, changeDevCallBack);
            }
        });

        final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
                R.layout.add_layout_1, mContainerView, false);

        DiscreteSeekBar seekBar1 = newView.findViewById(R.id.seekBar1);
        DiscreteSeekBar seekBar2 = newView.findViewById(R.id.seekBar2);
        DiscreteSeekBar seekBar3 = newView.findViewById(R.id.seekBar3);
        final TextView tvC1 = newView.findViewById(R.id.c_1);
        final TextView tvC2 = newView.findViewById(R.id.c_2);
        final TextView tvC3 = newView.findViewById(R.id.c_3);

        switchbutton1 = layout.findViewById(R.id.switchbutton1);
        switchbutton2 = layout.findViewById(R.id.switchbutton2);
        switchbutton3 = layout.findViewById(R.id.switchbutton3);
        switchbutton4 = layout.findViewById(R.id.switchbutton4);
        switchbutton5 = layout.findViewById(R.id.switchbutton5);
        switchbutton6 = layout.findViewById(R.id.switchbutton6);
        switchbutton100 = layout.findViewById(R.id.switchbutton100);

        seekBar1.setIndicatorPopupEnabled(false);
        seekBar1.setMin(2);
        seekBar1.setMax(8);
        seekBar1.setProgress(Refrigerate);
        tvC1.setText(String.valueOf(Refrigerate + "°C"));
        seekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar1 ");
                tvC1.setText(String.valueOf(value + "°C"));
                Refrigerate_1 = value;
                tvConfirm.setVisibility(View.VISIBLE);
                if (Mode_2) {
                    switchbutton2.setChecked(false);
                }
                if (Mode_3) {
                    switchbutton3.setChecked(false);
                }
                if (Mode_4) {
                    switchbutton4.setChecked(false);
                }
                if (Mode_100) {
                    switchbutton100.setChecked(false);
                }
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
        seekBar2.setProgress(Heterotherm);
        tvC2.setText(String.valueOf(Heterotherm + "°C"));
        seekBar2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar2 ");
                tvC2.setText(String.valueOf(value + "°C"));
                Heterotherm_1 = value;
                tvConfirm.setVisibility(View.VISIBLE);
                if (Mode_2) {
                    switchbutton2.setChecked(false);
                }
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
        seekBar3.setProgress(Freeze);
        tvC3.setText(String.valueOf(Freeze + "°C"));
        seekBar3.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                L.e(TAG, value + "  seekBar3 ");
                tvC3.setText(String.valueOf(value + "°C"));
                Freeze_1 = value;
                tvConfirm.setVisibility(View.VISIBLE);
                if (Mode_2) {
                    switchbutton2.setChecked(false);
                }
                if (Mode_5) {
                    switchbutton5.setChecked(false);
                }
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

        switchbutton1.setChecked(Mode_1);
        if (Mode_1) {
            mContainerView.addView(newView, 1);
            if (mContainerView.getChildCount() == 0) {
                layout.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
            }
        } else {
            mContainerView.removeView(newView);
        }
        switchbutton2.setChecked(Mode_2);
        switchbutton3.setChecked(Mode_3);
        switchbutton4.setChecked(Mode_4);
        switchbutton5.setChecked(Mode_5);
        switchbutton6.setChecked(Mode_6);
        switchbutton100.setChecked(Mode_100);


        L.e(TAG, "showSettingView   " + Mode_1 + "  " + Mode_2 + "  " + Mode_100 + "  " + Mode_3 + "  " + Mode_4 + "  " + Mode_5 + "  " + Mode_6);

        //自定义模式
        switchbutton1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
                if (isChecked) {
                    mContainerView.addView(newView, 1);
                    if (mContainerView.getChildCount() == 0) {
                        layout.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                    }
                    Mode_1_1 = true;
                } else {
                    mContainerView.removeView(newView);
                    Mode_1_1 = false;
                }
            }
        });

        //智能模式
        switchbutton2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
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
                    if (switchbutton100.isChecked()) {
                        switchbutton100.setChecked(false);
                    }
                    Refrigerate_1 = 5;
                    Heterotherm_1 = 0;
                    Freeze_1 = -18;
                    Mode_2_1 = true;
                } else {
                    Mode_2_1 = false;
                }
            }
        });

        //假日模式
        switchbutton3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
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
                    if (switchbutton100.isChecked()) {
                        switchbutton100.setChecked(false);
                    }
                    Refrigerate_1 = 14;
                    Mode_3_1 = true;
                } else {
                    Mode_3_1 = false;
                }
            }
        });

        //速冷模式
        switchbutton4.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
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
                    if (switchbutton100.isChecked()) {
                        switchbutton100.setChecked(false);
                    }
                    Refrigerate_1 = 2;
                    Mode_4_1 = true;
                } else {
                    Mode_4_1 = false;
                }
            }
        });

        //速冻模式
        switchbutton5.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
                if (isChecked) {
                    if (switchbutton1.isChecked()) {
                        switchbutton1.setChecked(false);
                    }
                    if (switchbutton2.isChecked()) {
                        switchbutton2.setChecked(false);
                    }
                    Freeze = -32;
                    Mode_5_1 = true;
                } else {
                    Mode_5_1 = false;
                }
            }
        });

        //去味模式
        switchbutton6.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
                if (isChecked) {
                    Mode_6_1 = true;
                } else {
                    Mode_6_1 = false;
                }
            }
        });

        //冷藏关闭模式
        switchbutton100.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                tvConfirm.setVisibility(View.VISIBLE);
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
//                    if (switchbutton5.isChecked()) {
//                        switchbutton5.setChecked(false);
//                    }
                    Mode_100_1 = true;
                } else {
                    Mode_100_1 = false;
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
                    arrayPictures1.clear();
                    arrayPictures2.clear();
                    arrayPictures3.clear();
                    arrayOverDue1.clear();
                    arrayOverDue2.clear();
                    arrayOverDue3.clear();
                    imageList1.clear();
                    imageList2.clear();
                    imageList3.clear();

                    if (mPagerAdapter1 != null) {
                        mPagerAdapter1.notifyDataSetChanged();
                    }
                    if (mPagerAdapter2 != null) {
                        mPagerAdapter2.notifyDataSetChanged();
                    }
                    if (mPagerAdapter3 != null) {
                        mPagerAdapter3.notifyDataSetChanged();
                    }

                    if (response.getRefrigerators() != null) {
                        if (response.getRefrigerators().getPattern() != null && !"".equals(response.getRefrigerators().getPattern())) {
                            switch (response.getRefrigerators().getPattern()) {
                                case "自定义模式":
                                    Mode_1 = true;
                                    break;
                                case "智能模式":
                                    Mode_2 = true;
                                    break;
                                case "冷藏关闭模式":
                                    Mode_100 = true;
                                    break;
                                case "假日模式":
                                    Mode_3 = true;
                                    break;
                                case "速冷模式":
                                    Mode_4 = true;
                                    break;
                                case "速冻模式":
                                    Mode_5 = true;
                                    break;
                            }
                            llAiMode.setText(response.getRefrigerators().getPattern());
                        }

                        Refrigerate = response.getRefrigerators().getRefrigerate();
                        Heterotherm = response.getRefrigerators().getHeterotherm();
                        Freeze = response.getRefrigerators().getFreeze();

                        Refrigerate_1 = Refrigerate;
                        Heterotherm_1 = Heterotherm;
                        Freeze_1 = Freeze;

                        lengCangDegree.setText(String.valueOf(Refrigerate));
                        bian_wen_degree.setText(String.valueOf(Heterotherm));
                        leng_dong_degree.setText(String.valueOf(Freeze));
//                        if (response.getRefrigerators().getRefrigerate() < 0) {
//                            leng_cang_10.setVisibility(View.VISIBLE);
//                        } else {
//                            leng_cang_10.setVisibility(View.INVISIBLE);
//                        }
//                        if (response.getRefrigerators().getFreeze() < 0) {
//                            leng_dong_10.setVisibility(View.VISIBLE);
//                        } else {
//                            leng_dong_10.setVisibility(View.INVISIBLE);
//                        }
//                        if (response.getRefrigerators().getHeterotherm() < 0) {
//                            bian_wen_10.setVisibility(View.VISIBLE);
//                        } else {
//                            bian_wen_10.setVisibility(View.INVISIBLE);
//                        }

                        FridgeData = response.getRefrigerators().getData().getBytes();
                        if (response.getRefrigerators().isLECO()) {
                            imgLeco.setVisibility(View.VISIBLE);
                            Mode_6 = true;
                        } else {
                            imgLeco.setVisibility(View.GONE);
                            Mode_6 = false;
                        }

                        L.e(TAG, "getFridgeInfo  " + Mode_1 + "  " + Mode_2 + "  " + Mode_100 + "  " + Mode_3 + "  " + Mode_4 + "  " + Mode_5 + "  " + Mode_6 + "  " + Mode_100);

                        //0异常1正常2冷藏门3变温门4冷冻门5冷藏变温6冷藏冷冻7变温冷冻8冷藏变温冷冻 type=0异常 1正常
                        switch (response.getRefrigerators().getAbnormity()) {
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
                            case 5:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱冷藏门,变温门未关");
                                break;
                            case 6:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱冷藏门,冷冻门未关");
                                break;
                            case 7:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱变温门,冷冻门未关");
                                break;
                            case 8:
                                error_ll.setVisibility(View.VISIBLE);
                                tvErrorCode.setText("冰箱冷藏门，变温门，冷冻门未关");
                                break;
                        }
                    }
                    //冷藏有食材快过期了
                    if (response.getRefrigerate() != null && response.getRefrigerate().size() > 0) {
                        overDue1 = true;
                        for (int i = 0; i < response.getRefrigerate().size(); i++) {
                            if (response.getRefrigerate().get(i).getImgUrl() != null && !"".equals(response.getRefrigerate().get(i).getImgUrl())) {
                                arrayPictures1.add(response.getRefrigerate().get(i).getImgUrl());
                                arrayOverDue1.add(response.getRefrigerate().get(i).getShelfLifeRemaining());
                                arrayFoods1.add(response.getRefrigerate().get(i).getIngredientsName());
                            }
                        }
                        startImageView(0);
                    } else {
                        overDue1 = false;
                        relativeLayout1.setVisibility(View.INVISIBLE);
                    }
                    //冷冻室有食材快过期了
                    if (response.getFreeze() != null && response.getFreeze().size() > 0) {
                        overDue2 = true;
                        for (int i = 0; i < response.getFreeze().size(); i++) {
                            if (response.getFreeze().get(i).getImgUrl() != null && !"".equals(response.getFreeze().get(i).getImgUrl())) {
                                arrayPictures2.add(response.getFreeze().get(i).getImgUrl());
                                arrayOverDue2.add(response.getFreeze().get(i).getShelfLifeRemaining());
                                arrayFoods2.add(response.getFreeze().get(i).getIngredientsName());
                            }
                        }
                        startImageView(1);
                    } else {
                        overDue2 = false;
                        relativeLayout2.setVisibility(View.INVISIBLE);
                    }
                    //变温室有食材快过期了
                    if (response.getHeterotherm() != null && response.getHeterotherm().size() > 0) {
                        overDue3 = true;
                        for (int i = 0; i < response.getHeterotherm().size(); i++) {
                            if (response.getHeterotherm().get(i).getImgUrl() != null && !"".equals(response.getHeterotherm().get(i).getImgUrl())) {
                                arrayPictures3.add(response.getHeterotherm().get(i).getImgUrl());
                                arrayOverDue3.add(response.getHeterotherm().get(i).getShelfLifeRemaining());
                                arrayFoods3.add(response.getHeterotherm().get(i).getIngredientsName());
                            }
                        }
                        startImageView(2);
                    } else {
                        overDue3 = false;
                        relativeLayout3.setVisibility(View.INVISIBLE);
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
        getFridgeInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        L.e(TAG, "onHiddenChanged " + hidden);
        if (!hidden) {
            L.e(TAG, "onHiddenChanged  可见");
            getFridgeInfo();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.e(TAG, "setUserVisibleHint " + isVisibleToUser);
    }

    private CommandCallBack mCommandCallBack = new CommandCallBack() {
        @Override
        public void connectionLost(String msg) {
            L.e(TAG, "mCommandCallBack  connectionLost" + msg);
        }

        @Override
        public void messageArrived(String topic, String msg) {
            L.e(TAG, "mCommandCallBack  messageArrived " + msg);
        }

        @Override
        public void deliveryComplete(String token) {

        }
    };


    private ChangeDevCallBack changeDevCallBack = new ChangeDevCallBack() {
        @Override
        public void onSuccess() {
            try {
                JoyoungSDK.getInstance().sendCMD(
                        sendCMD(switchbutton1, switchbutton2, switchbutton3, switchbutton4, switchbutton5, switchbutton6, switchbutton100), devId, true, sendCmdCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError() {

        }
    };

    private SendCmdCallback sendCmdCallback = new SendCmdCallback() {
        @Override
        public void onResponse(String s) {
            L.e(TAG, "指令发送成功" + s);
            Gson gson = new Gson();
            CodeBean codeBean = gson.fromJson(s, CodeBean.class);

            switch (codeBean.getCode()) {
                case 0:
                    mHandler.sendEmptyMessage(0);
                    break;
                case 100:
                    mHandler.sendEmptyMessage(100);
                    break;
                case 1001:
                    mHandler.sendEmptyMessage(1001);
                    break;
                case 1003:
                    mHandler.sendEmptyMessage(1003);
                    break;
                case 101:
                    mHandler.sendEmptyMessage(101);
                    break;
                case 102:
                    mHandler.sendEmptyMessage(102);
                    break;
                case 103:
                    mHandler.sendEmptyMessage(103);
                    break;
            }
        }

        @Override
        public void onFailure(String s, IOException e) {
            L.e(TAG, "指令发送失败" + s);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lengCangDegree.setText(String.valueOf(Refrigerate_1));
                    bian_wen_degree.setText(String.valueOf(Heterotherm_1));
                    leng_dong_degree.setText(String.valueOf(Freeze_1));

                    if (Mode_6_1) {
                        imgLeco.setVisibility(View.VISIBLE);
                    } else {
                        imgLeco.setVisibility(View.GONE);
                    }

                    if (Mode_4_1 && Mode_5_1) {
                        llAiMode.setText("速冷,速冻模式");
                    } else if (Mode_100_1 && Mode_5_1) {
                        llAiMode.setText("冷藏关闭,速冻模式");
                    } else if (Mode_1_1) {
                        llAiMode.setText("自定义模式");
                    } else if (Mode_2_1) {
                        llAiMode.setText("智能模式");
                    } else if (Mode_3_1) {
                        llAiMode.setText("假日模式");
                    } else if (Mode_4_1) {
                        llAiMode.setText("速冷模式");
                    } else if (Mode_5_1) {
                        llAiMode.setText("速冻模式");
                    } else if (Mode_100_1) {
                        llAiMode.setText("冷藏关闭模式");
                    }
                    dialog.cancel();

                    Refrigerate = Refrigerate_1;
                    Heterotherm = Heterotherm_1;
                    Freeze = Freeze_1;

                    Mode_1 = Mode_1_1;
                    Mode_2 = Mode_2_1;
                    Mode_3 = Mode_3_1;
                    Mode_4 = Mode_4_1;
                    Mode_5 = Mode_5_1;
                    Mode_6 = Mode_6_1;
                    Mode_100 = Mode_100_1;

                    break;
                case 100:
                    T.showShort(getActivity(), "设备未注册");
                    break;
                case 1001:
                    T.showShort(getActivity(), "系统错误");
                    break;
                case 1003:
                    T.showShort(getActivity(), "请求第三方接口超时");
                    break;
                case 101:
                    T.showShort(getActivity(), "key失效，需要同步");
                    break;
                case 102:
                    T.showShort(getActivity(), "seq 失效，需要同步");
                    break;
                case 103:
                    T.showShort(getActivity(), "设备不在线");
                    break;
            }
        }
    };


    private String sendCMD(SwitchButton s1, SwitchButton s2, SwitchButton s3, SwitchButton s4, SwitchButton s5, SwitchButton s6, SwitchButton s100) {

        Mode_1_1 = s1.isChecked();
        Mode_2_1 = s2.isChecked();
        Mode_3_1 = s3.isChecked();
        Mode_4_1 = s4.isChecked();
        Mode_5_1 = s5.isChecked();
        Mode_6_1 = s6.isChecked();
        Mode_100_1 = s100.isChecked();

        //自定义模式
        if (Mode_1_1 && !Mode_2_1 && !Mode_3_1 && !Mode_4_1 && !Mode_5_1 && !Mode_6_1 && !Mode_100_1) {//无去味儿
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Temperature;
            DATA_3 = (byte) (Refrigerate_1 + 100);
            DATA_4 = (byte) (Heterotherm_1 + 100);
            DATA_5 = (byte) (Freeze_1 + 100);
            DATA_6 = 10;
            DATA_7 = 10;
            DATA_8 = 10;
            DATA_9 = 0x00;
        } else if (Mode_1_1 && !Mode_2_1 && !Mode_3_1 && !Mode_4_1 && !Mode_5_1 && Mode_6_1 && !Mode_100_1) {//有去味儿
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Temperature;
            DATA_3 = (byte) (Refrigerate_1 + 100);
            DATA_4 = (byte) (Heterotherm_1 + 100);
            DATA_5 = (byte) (Freeze_1 + 100);
            DATA_6 = 10;
            DATA_7 = 10;
            DATA_8 = 10;
            DATA_9 = (byte) MODE;
        }

        //冰箱模式判断
        //去味模式
        //===================================================
        if (!Mode_1_1 && Mode_6_1 & Mode_4_1 & Mode_5_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.Quick_Cooling_Mode;
            MODE |= ConstantPool.Quick_Freezing_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_6_1 & Mode_2_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.Intelligent_Model;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_6_1 & Mode_3_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.Holiday_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_6_1 & Mode_4_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.Quick_Cooling_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_6_1 & Mode_5_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.Quick_Freezing_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_6_1 & Mode_100_1) {
            MODE = FridgeData[2];
            MODE |= ConstantPool.LECO_Mode;
            MODE |= ConstantPool.LengCang_Shutdown_Model;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        }
        //===================================================
        else if (!Mode_1_1 && Mode_4_1 & Mode_5_1) {//速冷速冻
            MODE = FridgeData[2];
            MODE |= ConstantPool.Quick_Cooling_Mode;
            MODE |= ConstantPool.Quick_Freezing_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_2_1) {//智能模式
            MODE = FridgeData[2];
            MODE |= ConstantPool.Intelligent_Model;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_100_1) {//冷藏关闭模式
            MODE = FridgeData[2];
            MODE |= ConstantPool.LengCang_Shutdown_Model;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_3_1) {//假日模式
            MODE = FridgeData[2];
            MODE |= ConstantPool.Holiday_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_4_1) {//速冷模式
            MODE = FridgeData[2];
            MODE |= ConstantPool.Quick_Cooling_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        } else if (!Mode_1_1 && Mode_5_1) {//速冻模式
            MODE = FridgeData[2];
            MODE |= ConstantPool.Quick_Freezing_Mode;
            DATA_0 = ConstantPool.Data0_beginning_commend;
            DATA_1 = ConstantPool.Data1_beginning_commend;
            DATA_2 = ConstantPool.Data2_Modify_Mode;
            DATA_9 = (byte) MODE;
        }
        //===================================================

        DATA_23 = (byte) (DATA_0 + DATA_1 + DATA_2 + DATA_3 + DATA_4 + DATA_5 + DATA_6
                + DATA_7 + DATA_8 + DATA_9 + DATA_10 + DATA_11 + DATA_12 + DATA_13 + DATA_14
                + DATA_15 + DATA_16 + DATA_17 + DATA_18 + DATA_19 + DATA_20 + DATA_21 + DATA_22);
        byte[] data = new byte[]{DATA_0, DATA_1, DATA_2, DATA_3, DATA_4, DATA_5, DATA_6, DATA_7, DATA_8, DATA_9, DATA_10, DATA_11, DATA_12, DATA_13, DATA_14, DATA_15, DATA_16, DATA_17, DATA_18, DATA_19, DATA_20, DATA_21, DATA_22, DATA_23};
        L.e(TAG, "sendByte  " + Arrays.toString(data));

        return Arrays.toString(data);
    }
}
