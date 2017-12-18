package com.bx.jz.jy.jybx.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.broadcom.cooee.Cooee;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.Settings;
import com.bx.jz.jy.jybx.utils.T;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手动配网
 */

public class AddBySelfActivity extends BaseActivity {

    private static final String TAG = AddBySelfActivity.class.getSimpleName();

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
    @BindView(R.id.tv_open_wifi)
    EditText tvOpenWifi;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.btn_deploy)
    AppCompatButton btnDeploy;

    private boolean eyeStatus = false;
    private int mLocalIp;
    private boolean mDone = false;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(AddBySelfActivity.this);
        setContentView(R.layout.add_by_self_activity);
        ButterKnife.bind(this);

        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("连接网络");

        updateWifiInfo();
    }

    private void updateWifiInfo() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connManager != null;
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        L.d(TAG, "connected: " + networkInfo.isConnected());
        if (!networkInfo.isConnected()) {
            L.d(TAG, getString(R.string.connect_wifi));
            showErrorDialog();
            return;
        }

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo info = wifiManager.getConnectionInfo();
        mLocalIp = info.getIpAddress();
        L.d(TAG, String.format("ip: 0x%x", mLocalIp));

        String ssid = info.getSSID();
        if (ssid.startsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        tvOpenWifi.setText(ssid);
        L.d(TAG, "ssid: " + ssid);
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.connect_wifi);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddBySelfActivity.this, null);
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etPwd};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_pwd};
    }

    @OnClick({R.id.img_back, R.id.tv_open_wifi, R.id.img_cancel, R.id.img_eye, R.id.btn_deploy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_open_wifi:
                break;
            case R.id.img_cancel:
                tvOpenWifi.setText("");
                break;
            case R.id.img_eye:
                if (!eyeStatus) {
                    imgEye.setImageResource(R.mipmap.eye_off);
                    eyeStatus = true;
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imgEye.setImageResource(R.mipmap.eye_on);
                    eyeStatus = false;
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btn_deploy:
                send();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWifiInfo();
    }

    private void send() {
        if (!mDone) {
            mDone = true;
            final String ssid = tvOpenWifi.getText().toString();
            final String password = etPwd.getText().toString();

                        /* Set packet interval. Default 8ms in lib. Probably you don't need to set it */
                SharedPreferences sp = Settings.getPrefs(AddBySelfActivity.this);
                String packetInterval = sp.getString("packet_interval", getString(R.string.default_packet_interval));
                int interval = Integer.parseInt(packetInterval);
                Cooee.SetPacketInterval(interval); /* default 8ms */

            if (mThread == null) {
                mThread = new Thread() {
                    public void run() {
                        while (mDone) {
                            Cooee.send(ssid, password, mLocalIp);
                            L.e(TAG,"ssid  : " + ssid + "  password  : " + password);
                        }
                    }
                };
            }
            mThread.start();
        } else {
            mDone = false;
            mThread = null;
        }
    }
}
