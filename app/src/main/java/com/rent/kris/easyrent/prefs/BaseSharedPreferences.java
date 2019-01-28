package com.rent.kris.easyrent.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.rent.kris.easyrent.MyApplication;


/**
 * Created by ms on 2017/10/16.
 */

public class BaseSharedPreferences {
    private SharedPreferences sp = null;
    private SharedPreferences.Editor editor;

    public BaseSharedPreferences(String spName) {
        this.sp = MyApplication.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    /**
     * 保存数据到文件
     *
     * @param key
     * @param data
     */
    public void saveData(String key, Object data) {
        String type = data.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }
        editor.commit();
    }

    /**
     * 从文件中读取数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public Object getData(String key, Object defValue) {
        String type = defValue.getClass().getSimpleName();
        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sp.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }

    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void setLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public Long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }
}
