package com.example.fcg.utils;

import android.content.Context;
import android.os.BatteryManager;
import android.provider.Settings;

/**
 * Created by HieuHo on 4/13/2017.
 */

public class Utilities {
    public static String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        if(minutes > 0){
            finalTimerString =  minutes + " minute";
        }else {
            finalTimerString = seconds+" seconds";
        }
        return finalTimerString;
    }

    public static void setTimeout(int screenOffTimeout, Context context) {
        int time;
        switch (screenOffTimeout) {
            case 0:
                time = 30000;
                break;
            case 1:
                time = 60000;
                break;
            case 2:
                time = 120000;
                break;
            case 3:
                time = 180000;
                break;
            case 4:
                time = 240000;
                break;
            case 5:
                time = 300000;
                break;
            default:
                time = -1;
        }
        android.provider.Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, time);
    }



    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                break;
        }
        return plugType;
    }

    private String getHealthString(int health) {
        String healthString = "Unknown";

        switch (health) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "Over Heat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "Failure";
                break;
        }
        return healthString;
    }

    public static int getTimeout(int screenOffTimeout, Context context) {
        int time;
        switch (screenOffTimeout) {
            case 30000:
                time = 0;
                break;
            case 60000:
                time = 1;
                break;
            case 120000:
                time = 2;
                break;
            case 180000:
                time = 3;
                break;
            case 240000:
                time = 4;
                break;
            case 300000:
                time = 5;
                break;
            default:
                time = -1;
        }
        return time;
    }

}
