package com.rent.kris.easyrent.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.rent.kris.easyrent.BuildConfig;
import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.event.GpsNotify;
import com.rent.kris.easyrent.event.UploadSuccessEvent;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.MainActivity;
import com.rent.kris.easyrent.util.JavaAndJSBridge;
import com.rent.kris.easyrent.web.WebViewSettings;
import com.xw.common.prefs.LoginInfoPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lsz  on 2019-02-14
 */
public class CommonFragment extends Fragment {

    private static String TAG = "CommonFragment";

    private Unbinder unbinder;
    private String mTitle = "";
    public WebView mWebView;
    public TextView tvTitle;
    public String urlStr = "";


    public static CommonFragment newInstance(String url,String mTitle) {
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", mTitle);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonality, container, false);
        unbinder = ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        initWebView(view);
        initView(view);
        return view;
    }


    private void initWebView(View view) {
        Bundle args = getArguments();
        if (args != null) {
            urlStr = args.getString("url");
            mTitle = args.getString("title");
        }
        //设置可调试
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        mWebView = (WebView) view.findViewById(R.id.m_web_view);
        WebViewSettings.config(mWebView.getSettings());
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    tvTitle.setText(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            // 处理定位权限请求
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });

        mWebView.setWebViewClient(mWebViewClient);
        WebSettings settings = mWebView.getSettings();
        //启用地理定位
        settings.setGeolocationEnabled(true);
        //启用数据库
        settings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = MyApplication.getInstance().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        settings.setDomStorageEnabled(true);  //定位的关键设置

        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.addJavascriptInterface(new JavaAndJSBridge(mWebView, getActivity(), ((MainActivity)getActivity()).jsListener), "App");
//        mWebView.getSettings().setBlockNetworkImage(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
    }


    public void initView(View view) {
        tvTitle.setText(mTitle);
        String url = urlStr;
        if(!TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            url = urlStr+
                    "?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                    LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        }
//        WebViewHelper.syncCookie(getActivity(), url);
        mWebView.loadUrl(url);

    }


    public WebViewClient mWebViewClient = new WebViewClient() {
//        //将约定好的空js文件替换为本地的
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
//            if (url == null) {
//                return webResourceResponse;
//            }
//            return webResourceResponse;
//        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //6.0以下执行
            showErrorPage();
        }

        //处理网页加载失败时
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //6.0以上执行
            showErrorPage();
        }
    };

    private void showErrorPage() {
//        lay_multi_state_view.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UploadSuccessEvent messageEvent) {
        if(mWebView != null){
            mWebView.loadUrl("javascript:uploadSuccess()");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(GpsNotify messageEvent) {
        Log.e(TAG,"GpsNotify mWebView ="+mWebView);
        if(mWebView != null){
            String location = MainActivity.latLng.latitude+";"+MainActivity.latLng.longitude;
            Log.e(TAG,"Event GpsNotify location ="+location);
            mWebView.loadUrl("javascript:setGpsLocation(\""+location+"\")");
        }
    }


}
