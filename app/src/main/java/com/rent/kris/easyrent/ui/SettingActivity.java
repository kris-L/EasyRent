package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.event.LogOutEvent;
import com.rent.kris.easyrent.event.UploadSuccessEvent;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.web.WebViewHelper;
import com.xw.common.prefs.LoginInfoPrefs;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/16.
 */

public class SettingActivity extends BaseActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.log_out_tv)
    TextView log_out_tv;

    private Context mContext;
    public static void intentTo(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;

        initViews();
    }

    private void initViews() {
        if(TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            log_out_tv.setBackground(getResources().getDrawable(R.drawable.shape_circular_gray));
            log_out_tv.setEnabled(false);
        }
    }

    public Intent intent;
    public String title = "";
    public String url = "";




    @OnClick({R.id.log_out_tv,R.id.change_password_ll,R.id.address_ll,R.id.instructions_ll,
            R.id.feedback_ll,R.id.about_us_ll})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.log_out_tv:
                UserProfilePrefs.getInstance().saveUserToken("");
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).clearLoginInfo();
                EventBus.getDefault().post(new LogOutEvent());
                WebViewHelper.clearWebViewCacheNCookies();
                clearWebViewCache();
                finish();
                MainActivity.intentTo(this,1);
                break;

            case R.id.change_password_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/member_password_step1.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);

                break;

            case R.id.address_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/address_list.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.instructions_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/guide.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.feedback_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/member_feedback.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.about_us_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/yizu/about-us.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
        }
    }

    private static final String APP_CACAHE_DIRNAME = "/webcache";
    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        File webviewFile = this.getApplicationContext().getCacheDir().getAbsoluteFile();
        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
//            deleteFile(webviewCacheDir);
            deleteFile(webviewFile);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }



}
