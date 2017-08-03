package com.example.fcg.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fcg.R;
import com.example.fcg.utils.Preferences;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends BaseActivity {
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fcg_002);
        setTitle(R.string.expressbattery);
        registerBatteryLevelReceiver();
        init();
        setSupportActionBar(toolbar);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        checkStatusDevice();
         preferences = new Preferences(this);
        if(!preferences.isChecked()){
            mySwitch.setChecked(false);
        }else {
            mySwitch.setChecked(true);
        }
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstital));

        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySwitch.isChecked()){
                    preferences.setChecked(true);
                    mInterstitialAd.show();

                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }else {
                        startcharging();
                        startActivity(new Intent(getApplicationContext(),Loadding.class));
                        finish();
                    }
                }else {
                    preferences.setChecked(false);
                }
            }
        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                startcharging();
                startActivity(new Intent(getApplicationContext(),Loadding.class));
                finish();
            }
        });
        new AdRequest.Builder().addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBatteryLevelReceiver();
    }

    private void init(){
        txtMenu1 = (TextView) findViewById(R.id.txtMenu1);
        txtBattery = (TextView) findViewById(R.id.txtbattery);
        txtmenu2 = (TextView) findViewById(R.id.txtMenu2);
        txtmenu3 = (TextView) findViewById(R.id.txtmenu3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imagemenu1 = (ImageView) findViewById(R.id.menu1);
        imageloading = (ImageView) findViewById(R.id.loading);
        imagemenu2 = (ImageView)findViewById(R.id.menu2);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        imagemenu3 = (ImageView) findViewById(R.id.menu3);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        loadingmenu1 = (ImageView) findViewById(R.id.loadingmenu1);
        loadingmenu3 = (ImageView) findViewById(R.id.imagemenu3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(),SettingSystem.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private BroadcastReceiver battery_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPresent = intent.getBooleanExtra("present", false);
            int scale = intent.getIntExtra("scale", -1);
            int status = intent.getIntExtra("status", 0);
            int rawlevel = intent.getIntExtra("level", -1);
            int level = 0;
            Bundle bundle = intent.getExtras();
            Log.i("BatteryLevel", bundle.toString());
            if (isPresent) {
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                getStatusString(status,level);
                progressBar.setProgress(level);
                setBatteryLevelText(level + "%" );
            } else {
                setBatteryLevelText("Battery not present!!!");
            }
        }
    };


    private String getStatusString(int status,int level) {
        String statusString = "Unknown";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Charging";
                animation.start();
                if(level <= 90){
                    loadingmenu1.setVisibility(View.VISIBLE);
                    loadingmenu1.setAnimation(animation);
                    imageloading.setVisibility(View.GONE);
                    loadingmenu3.setVisibility(View.GONE);
                    txtMenu1.setText(R.string.menusub1);

                }else if(level < 99 && level >90) {
                    imageloading.setVisibility(View.VISIBLE);
                    imageloading.setAnimation(animation);
                    loadingmenu1.setVisibility(View.GONE);
                    loadingmenu3.setVisibility(View.GONE);
                    txtMenu1.setText(R.string.menusub2);
                    txtmenu2.setText(R.string.submenu1);
                    imagemenu1.setBackgroundResource(R.drawable.cricle_menu);

                }else if(level == 99) {
                    loadingmenu3.setVisibility(View.GONE);
                    txtMenu1.setText(R.string.menusub2);
                    txtmenu2.setText(R.string.submenu2);
                    txtmenu3.setText(R.string.menu3sub);
                    imagemenu1.setBackgroundResource(R.drawable.cricle_menu);
                    imagemenu2.setBackgroundResource(R.drawable.cricle_menu);
                    loadingmenu3.setAnimation(animation);
                }else if(level == 100){
                    txtMenu1.setText(R.string.menusub2);
                    txtmenu2.setText(R.string.submenu2);
                    txtmenu3.setText(R.string.menu3sub1);
                    imagemenu1.setBackgroundResource(R.drawable.cricle_menu);
                    imagemenu2.setBackgroundResource(R.drawable.cricle_menu);
                    imagemenu3.setBackgroundResource(R.drawable.cricle_menu);
                }
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
               statusString = "Discharging";
                loadingmenu1.clearAnimation();
                imageloading.clearAnimation();
                loadingmenu3.clearAnimation();
                txtMenu1.setText(R.string.menu1);
                txtmenu2.setText(R.string.menu2);
                txtmenu3.setText(R.string.menu3);
                imageloading.setVisibility(View.GONE);
                loadingmenu1.setVisibility(View.GONE);
                loadingmenu3.setVisibility(View.GONE);
                imagemenu1.setBackgroundResource(R.drawable.cricle_no);
                imagemenu2.setBackgroundResource(R.drawable.cricle_no);
                imagemenu3.setBackgroundResource(R.drawable.cricle_no);
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Full";
                txtMenu1.setText(R.string.menusub2);
                txtmenu2.setText(R.string.submenu2);
                txtmenu3.setText(R.string.menu3sub1);
                imagemenu2.setBackgroundResource(R.drawable.cricle_menu);
                imagemenu1.setBackgroundResource(R.drawable.cricle_menu);
                imagemenu3.setBackgroundResource(R.drawable.cricle_menu);
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "Not Charging";
                txtMenu1.setText(R.string.menu1);
                txtmenu2.setText(R.string.menu2);
                txtmenu3.setText(R.string.menu3);
                imageloading.clearAnimation();
                loadingmenu1.clearAnimation();
                imagemenu3.clearAnimation();
                imageloading.setVisibility(View.GONE);
                loadingmenu1.setVisibility(View.GONE);
                loadingmenu3.setVisibility(View.GONE);
                imagemenu1.setBackgroundResource(R.drawable.cricle_no);
                imagemenu2.setBackgroundResource(R.drawable.cricle_no);
                imagemenu3.setBackgroundResource(R.drawable.cricle_no);
                break;
        }
        return statusString;
    }

    private void setBatteryLevelText(String text) {
        txtBattery.setText(text);

    }

    private void registerBatteryLevelReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(battery_receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBatteryLevelReceiver();
    }
}
