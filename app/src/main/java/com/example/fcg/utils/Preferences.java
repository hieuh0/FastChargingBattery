package com.example.fcg.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HieuHo on 4/13/2017.
 */

public class Preferences {
    private static String TAG = Preferences.class.getSimpleName();
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "status";
    public Preferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setChecked(boolean isLoggedIn) {
        editor.putBoolean("Checked", isLoggedIn);
        editor.commit();
    }
    public boolean isChecked(){
        return pref.getBoolean("Checked", false);
    }

}
