package com.rent.kris.easyrent.prefs;

import android.util.Log;


import java.util.HashMap;
import java.util.List;

/**
 * 基础设置SharedPreferences
 * Created by ms on 2017/10/16.
 */

public class SPSetting extends BaseSharedPreferences {
    private static final String SP_NAME = "SETTING";


    private static SPSetting INSTANCE;

    public static SPSetting getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SPSetting();
        }
        return INSTANCE;
    }

    public SPSetting() {
        super(SP_NAME);
    }



}
