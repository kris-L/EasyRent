package com.rent.kris.easyrent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseFragment;
import com.xw.common.prefs.LoginInfoPrefs;

/**
 * Created by kris on 2019/1/15.
 */

public class SixthFragment extends BaseFragment {

    private static SixthFragment instance = null;
    public static SixthFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = SixthFragment.newInstance();
        }
        return instance;
    }

    public static SixthFragment newInstance() {
        SixthFragment fragment = new SixthFragment();
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_sixth, container, false);
//        return view;
//    }

    @Override
    public void initView(View view) {
        tvTitle.setText("商品");
        String url = "http://app.tit306.com/appa/app2/public/wap/tmpl/product_list.html"+
                "?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        mWebView.loadUrl(url);
    }



}
