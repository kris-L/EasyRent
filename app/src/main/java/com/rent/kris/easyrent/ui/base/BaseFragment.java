package com.rent.kris.easyrent.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.util.JSBridge;
import com.rent.kris.easyrent.util.JavaAndJSBridge;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by kris at 2017/4/5/0005
 */
public class BaseFragment extends Fragment {

    private Unbinder unbinder;
    public WebView mWebView;
    public TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonality, container, false);
        unbinder = ButterKnife.bind(this,view);
        initWebView(view);
        initView(view);
        return view;
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
//        tvTitle.setText(getIntent().getStringExtra("title"));

        mWebView = (WebView) view.findViewById(R.id.m_web_view);
        mWebView.setWebViewClient(mWebViewClient);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new JSBridge(mWebView, getActivity()), "App");

    }

    public void initView(View view) {


    }


    public WebViewClient mWebViewClient = new WebViewClient() {
        //将约定好的空js文件替换为本地的
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            if (url == null) {
                return webResourceResponse;
            }
            return webResourceResponse;
        }
    };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

}
