package com.example.fcg.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by HieuHo on 4/13/2017.
 */

public class ActiveStatus {


    public static String getSoundMode(Context context){
        String sound = null;
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch( audio.getRingerMode() ){
            case AudioManager.RINGER_MODE_NORMAL:
                sound = "Normal";
                break;
            case AudioManager.RINGER_MODE_SILENT:
                sound = "Silent";
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                sound = "Vibrate";
                break;
        }
        return sound;
    }
    public static int SoundMode(Context context){
        int sound = 0;
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch( audio.getRingerMode() ){
            case AudioManager.RINGER_MODE_SILENT:
                sound = 0;
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                sound = 1;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                sound = 2;
                break;
        }
        return sound;
    }
        public static void turnOnSound(int i,Context context){
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (i){
            case 0:
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case 1:
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case 2:
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
        }
    }

}
