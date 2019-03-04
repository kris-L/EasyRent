package com.rent.kris.easyrent;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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

    public static int sWidthPix;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public void init(){
        sWidthPix = getResources().getDisplayMetrics().widthPixels;

        AppModel.createAppModel(this);
        UMConfigure.init(this,"5b4ca2aab27b0a51c0000306"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");

        //各个平台的配置，建议放在全局Application或者程序入口
        //微信
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        //QQ登录
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        initImageLoader(this);
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

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(300)
//                .imageDownloader(new MyImageDownloader(context))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
                .diskCacheExtraOptions(sWidthPix / 3, sWidthPix / 3, null)
                .build();

        ImageLoader.getInstance().init(config);
    }

}
