package com.bx.jz.jy.jybx.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.fragment.FragmentFour;
import com.bx.jz.jy.jybx.fragment.FragmentOne;
import com.bx.jz.jy.jybx.fragment.FragmentThree;
import com.bx.jz.jy.jybx.fragment.FragmentTwo;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener,EasyPermissions.PermissionCallbacks {

    BottomNavigationBar mBottomNavigationBar;

    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;
    private FragmentFour mFragmentFour;
    private ArrayList<Fragment> fragments;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};
    private int RC_CAMERA_AND_WIFI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_main);

        mFragmentOne = new FragmentOne();
        mFragmentTwo = new FragmentTwo();
        mFragmentThree = new FragmentThree();
        mFragmentFour = new FragmentFour();

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
//.setActiveColorResource(R.color.color_0e)
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.white);
        mBottomNavigationBar.setInActiveColor(R.color.color_7c);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_one, R.string.tab_one)
                .setBadgeItem(null))
                .addItem(new BottomNavigationItem(R.mipmap.icon_two, R.string.tab_two).setBadgeItem(null).setActiveColorResource(R.color.color_0e))
                .addItem(new BottomNavigationItem(R.mipmap.icon_three, R.string.tab_three).setBadgeItem(null).setActiveColorResource(R.color.color_0e))
                .addItem(new BottomNavigationItem(R.mipmap.icon_four, R.string.tab_four).setBadgeItem(null).setActiveColorResource(R.color.color_0e))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    RC_CAMERA_AND_WIFI, perms);
        }
    }

    /**
     * 设置默认的Item
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.ll_content, mFragmentOne);
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragmentOne);
        fragments.add(mFragmentTwo);
        fragments.add(mFragmentThree);
        fragments.add(mFragmentFour);
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //当前的fragment
                Fragment from = fm.findFragmentById(R.id.ll_content);
                //点击即将跳转的fragment
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    // 隐藏当前的fragment，显示下一个
                    ft.hide(from).show(fragment);
                } else {
                    // 隐藏当前的fragment，add下一个到Activity中
                    ft.hide(from).add(R.id.ll_content, fragment);
                    if (fragment.isHidden()) {
                        ft.show(fragment);
                        L.d("被隐藏了");
                    }
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        //这儿也要操作隐藏，否则Fragment会重叠
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                // 隐藏当前的fragment
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this,null);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
