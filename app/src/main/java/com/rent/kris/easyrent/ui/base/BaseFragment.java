package com.rent.kris.easyrent.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.rent.kris.easyrent.BuildConfig;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.event.UploadSuccessEvent;
import com.rent.kris.easyrent.web.WebViewSettings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by kris at 2017/4/5/0005
 */
public class BaseFragment extends Fragment {

    private Unbinder unbinder;
    public TextView tvTitle;

    private String mTitle = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonality, container, false);
        unbinder = ButterKnife.bind(this,view);
        initWebView(view);
        initView(view);
        return view;
    }

    private void initWebView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
//      tvTitle.setText(getIntent().getStringExtra("title"));

    }

    public void initView(View view) {


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
    }

}
