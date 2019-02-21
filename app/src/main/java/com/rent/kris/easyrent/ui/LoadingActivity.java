package com.rent.kris.easyrent.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.entity.UploadResult;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.dialog.ExamineMoreDialog;
import com.rent.kris.easyrent.util.JavaAndJSBridge;
import com.rent.kris.easyrent.util.RealPathUtil;
import com.rent.kris.easyrent.web.WebViewSettings;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.common.prefs.LoginInfoPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lsz  on 2019-02-21
 */

public class LoadingActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 10086;
    private static final int PDD_PLAY_SNAKE = REQUEST_PICK_IMAGE + 1;
    private static final int REQUEST_TAKE_PHOTO = PDD_PLAY_SNAKE + 1;
    private WebView mWebView;
    private String pickPhotoName;
    private String takePhotoName;
    private String photoFileName;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);
        mContext = this;
        initWebView();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        RelativeLayout title_rl = findViewById(R.id.title_rl);
        title_rl.setVisibility(View.GONE);
        tvTitle.setText(getIntent().getStringExtra("title"));
        findViewById(R.id.iv_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView = (WebView) findViewById(R.id.m_web_view);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }



        WebViewSettings.config(mWebView.getSettings());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.addJavascriptInterface(new JavaAndJSBridge(mWebView, this, jsListener), "App");

        String url = "file:///android_asset/index.html";
        mWebView.loadUrl(url);
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        //将约定好的空js文件替换为本地的
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            if (url == null) {
                return webResourceResponse;
            }
            return webResourceResponse;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, request);
            if (request == null) {
                return webResourceResponse;
            }
            String url = request.getUrl().toString();
            return webResourceResponse;
        }
    };

    public String typeStr, sonpathStr, newnameStr, timestampStr;
    public Uri photoUri;
    @SuppressLint("CheckResult")
    public JavaAndJSBridge.OnJSCallBack jsListener = new JavaAndJSBridge.OnJSCallBack() {

        @Override
        public void onPickPhoto() {
        }


        @Override
        public void onMakePhoto() {
        }

        @Override
        public void uploadImage(String type, String sonpath, String newname, String timestamp) {

        }

        @Override
        public void onLoginNotify() {

        }

        @Override
        public void onCallPhone(final String phone) {

        }

        @Override
        public void onModuleSelected(int index) {
            Log.e("lsz","onModuleSelected index="+index);
            MainActivity.intentTo(LoadingActivity.this,index+1);
            finish();
        }
    };





}
