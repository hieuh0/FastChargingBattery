package com.example.fcg.activities;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fcg.R;
import com.example.fcg.utils.ActiveStatus;
import com.example.fcg.utils.Preferences;
import com.example.fcg.utils.Utilities;

import static com.example.fcg.R.layout.brightness;


public class BaseActivity extends AppCompatActivity {
    String[] timeout = {"30 seconds","1 minute","2 minute","3 minute","4 minute","5 minute"};
    String prefname="status";
    SharedPreferences pre;
    SharedPreferences.Editor editor;
    Animation animation;
    TextView txtBattery,txtMenu1,txtmenu2,txtmenu3;
    Toolbar toolbar;
    ImageView imagemenu1,imagemenu2,imageloading,imagemenu3,loadingmenu1,loadingmenu3;
    ProgressBar progressBar;
    Switch mySwitch;
    Preferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pre=getSharedPreferences(prefname, MODE_PRIVATE);
        editor=pre.edit();
    }

    public void dialogBrightness(final TextView textView){
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(brightness,null);
        builder.setView(v);
        final TextView txtseekbar = (TextView) v.findViewById(R.id.txtseekbar);
        final SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar2);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        seekBar.setMax(100);
        /*int key = pre.getInt("brightness",0);
        if(key == 0){
            checkBox.setChecked(true);
            seekBar.setEnabled(false);
        }else {
            int a = (key*100)/255;
            txtseekbar.setText(a+"%");
            seekBar.setProgress(a);
        }*/


        int key = pre.getInt("brightness",0);
        if(key == 1){
            txtseekbar.setText("10%");
            seekBar.setProgress(10);
        }
        if(key > 1){
            txtseekbar.setText(key+"%");
            seekBar.setProgress(key);
        }
        seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#25A8F2"), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.parseColor("#25A8F2"), PorterDuff.Mode.SRC_IN);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progress+"%");
                txtseekbar.setText(progress+"%");
                textView.setText(String.valueOf(seekBar.getProgress())+"%");
                Log.e("brightness",String.valueOf(progress));
                editor.putInt("brightness",progress);
                editor.commit();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    seekBar.setEnabled(false);
                    editor.putInt("brightness",1);
                    editor.commit();
                }else {
                    seekBar.setEnabled(true);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.dismiss();
            }
        });
        builder.show();
    }

    public void dialogTimeOut(final TextView textView){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.time, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(R.string.timeout);
        final ListView lv = (ListView) convertView.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,timeout);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) lv.getItemAtPosition(position);
                int item = position;
                editor.putInt("timeout",item);
                editor.commit();
                textView.setText(selectedFromList);
                alertDialog.dismiss();
            }
        });
        Button button = (Button) convertView.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
     }

     public void dialogSoundMode(final TextView txTextView){
         final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
         LayoutInflater inflater = getLayoutInflater();
         View convertView = (View) inflater.inflate(R.layout.soundmode, null);
         alertDialog.setView(convertView);
         ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.slient);
         imageButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 txTextView.setText("Silent");
                 editor.putInt("soundmode",0);
                 editor.commit();
                 alertDialog.dismiss();
             }
         });
         ImageButton imageButton1 = (ImageButton) convertView.findViewById(R.id.speaker);
         imageButton1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 txTextView.setText("Normal");
                 editor.putInt("soundmode",1);
                 editor.commit();
                 alertDialog.dismiss();
             }
         });

         ImageButton imageButton2 = (ImageButton) convertView.findViewById(R.id.vibrate);
         imageButton2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 editor.putInt("soundmode",2);
                 editor.commit();
                 txTextView.setText("Vibrate");
                 alertDialog.dismiss();
             }
         });

         alertDialog.show();
     }


    public void checkStatusDevice(){
        WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(wifi.isWifiEnabled()){
            editor.putInt("network",0);
            editor.commit();
        }else {
            editor.putInt("network",1);
            editor.commit();
        }
        if(bluetoothAdapter.isEnabled()){
            editor.putInt("bluetoothdevices",0);
            editor.commit();
        }else {
            editor.putInt("bluetoothdevices",1);
            editor.commit();
        }
        editor.putInt("sound",ActiveStatus.SoundMode(this));
        editor.commit();
              try {
         long a = Settings.System.getLong(
                    getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        editor.putInt("time", Utilities.getTimeout(Integer.parseInt(String.valueOf(a)),this));
                  editor.commit();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        int brightnessmode = 0;
        try {
            brightnessmode= Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            Log.d("tag", e.toString());
        }
     if(   brightnessmode== 1){
         editor.putInt("bright",1);
         editor.commit();
     }else {
         try {
             int curBrightnessValue=android.provider.Settings.System.getInt(
                     getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
             editor.putInt("bright",curBrightnessValue);
             editor.commit();
         } catch (Settings.SettingNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }

     }

    }
    public void startcharging(){
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        int keywifi = pre.getInt("wifi",0);
        int keyblu = pre.getInt("bluetooth",0);
        int keysound = pre.getInt("soundmode",0);
        int key = pre.getInt("brightness",0);
        int time = pre.getInt("timeout",0);
        if(keyblu == 0){
            mBluetoothAdapter.disable();
        }
        if(keywifi == 0){
            wifi.setWifiEnabled(false);
        }else {
            wifi.setWifiEnabled(true);
        }
        if(keysound == 0){
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }if(keysound == 1){
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        if (keysound==2){
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        if(key > 1){
            int a = (int) Math.round( key*2.55);
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,a);
        }
        if(key == 0 ){
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,10);
        }
        if(key == 1){
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
        Utilities.setTimeout(time,this);
        Intent intent = new Intent(getApplicationContext(),Loadding.class);
        startActivity(intent);
    }
    public void restore(){
        int time = pre.getInt("time",0);
        int network = pre.getInt("network",0);
        int bluetooth = pre.getInt("bluetoothdevices",0);
        int keybrigh = pre.getInt("bright",0);
        int sound = pre.getInt("sound",0);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(network == 0){
        wifi.setWifiEnabled(true);
        }else {
            wifi.setWifiEnabled(false);
        }
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetooth == 0){
            bluetoothAdapter.enable();
        }else {
            bluetoothAdapter.disable();
        }
        ActiveStatus.turnOnSound(sound,this);
        if(keybrigh == 1 ){
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }else {
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,keybrigh);
        }
        Utilities.setTimeout(time,this);
    }


    public void dialogLoading(){
        final int[] status = {0};
        final Handler handler = new Handler();
        final ProgressBar progressBar;
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_loading, null);
        alertDialog.setView(convertView);
        progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status[0] <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status[0]);
                            if(status[0] == 100){
                                progressBar.setVisibility(View.GONE);
                                ImageView imageView = (ImageView)findViewById(R.id.active);
                                imageView.setImageResource(R.drawable.ic_activate);
                               /* ScaleAnimation fade_in =  new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                fade_in.setDuration(1000);
                                fade_in.setFillAfter(true);
                                imageView.startAnimation(fade_in);*/
                                imageView.setBackgroundResource(R.drawable.cricle_image_active);
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status[0]++;
                }

            }
        }).start();
        alertDialog.show();
    }
}
