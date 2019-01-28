package com.rent.kris.easyrent;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.prefs.SPSetting;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


public class MyApplication extends MultiDexApplication {


    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public void init(){
        AppModel.createAppModel(this);
        UMConfigure.init(this,"5b4ca2aab27b0a51c0000306"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
    }

//    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        //QQ登录
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    /**
     * 设置新接口使用Token  SharedPreferences的key参数不要变，直播库要用
     *
     * @param token
     */
    public void setToken(String token) {
        SPSetting.getInstance().saveData(Constant.TOKEN, token);
    }

    /**
     * 获取新接口使用Token   SharedPreferences的key参数不要变，直播库要用
     *
     * @return
     */
    public String getToken() {
        String token = (String) SPSetting.getInstance().getData(Constant.TOKEN, "");
        return token;
    }

}
