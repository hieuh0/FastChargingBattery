package com.example.fcg.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fcg.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SettingSystem extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private Toolbar toolbar;
    private RelativeLayout relativeLayout, relativeLayout1, relativeLayout2;
    private TextView txtbrightness,txtTimeOut,txtsoundmode;
    private CheckBox cbWifi,cbbluetooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fcg_003);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventSwitch();
    }


    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativelayout2);
        txtbrightness = (TextView) findViewById(R.id.textView2);
        txtTimeOut = (TextView) findViewById(R.id.textView4);
        cbWifi = (CheckBox) findViewById(R.id.checkboxwifi);
        cbbluetooth = (CheckBox) findViewById(R.id.checkboxbluetooth);
        cbWifi.setOnCheckedChangeListener(this);
        cbbluetooth.setOnCheckedChangeListener(this);
        int key = pre.getInt("timeout",0);
        if(key == 0){
            txtTimeOut.setText("30 seconds");
        }
        if(key == 1){
            txtTimeOut.setText("1 minute");
        }
        if(key == 2){
            txtTimeOut.setText("2 minute");
        }
        if(key == 3){
            txtTimeOut.setText("3 minute");
        }
        if(key == 4){
            txtTimeOut.setText("4 minute");
        }
        if(key == 5){
            txtTimeOut.setText("5 minute");
        }
        txtsoundmode = (TextView) findViewById(R.id.soundmode);
        int keysound = pre.getInt("soundmode",0);
        if(keysound == 0){
            txtsoundmode.setText("Silent");
        }else if(keysound == 1){
            txtsoundmode.setText("Normal");
        }else {
            txtsoundmode.setText("Vibrate");
        }
        int brig = pre.getInt("brightness",0);
        if(brig == 0){
            txtbrightness.setText("10%");
        }if(brig == 1){
            txtbrightness.setText("Auto");
        }

        if(brig > 1){
            txtbrightness.setText(String.valueOf(brig)+"%");
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void eventSwitch(){
        int keywifi = pre.getInt("wifi",0);
        int keyblu = pre.getInt("bluetooth",0);
        if(keywifi == 1){
           cbWifi.setChecked(false);
        }else {
            cbWifi.setChecked(true);
        }
        if(keyblu == 1){
            cbbluetooth.setChecked(false);
        }else {
            cbbluetooth.setChecked(true);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativelayout:
                dialogBrightness(txtbrightness);
                break;
            case R.id.relativelayout1:
                dialogTimeOut(txtTimeOut);
                break;
            case R.id.relativelayout2:
                dialogSoundMode(txtsoundmode);
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkboxwifi:
                if(isChecked){
                   editor.putInt("wifi",0);
                   editor.commit();
                }else {
                    editor.putInt("wifi",1);
                    editor.commit();
                }
                break;
            case R.id.checkboxbluetooth:
                if(isChecked){
                    editor.putInt("bluetooth",0);
                    editor.commit();
                }else {
                    editor.putInt("bluetooth",1);
                    editor.commit();
                }
                break;
        }
    }
}
