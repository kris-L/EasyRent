package com.rent.kris.easyrent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseFragment;

/**
 * Created by kris on 2019/1/15.
 */

public class EighthFragment extends BaseFragment {


    private static EighthFragment instance = null;
    public static EighthFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = EighthFragment.newInstance();
        }
        return instance;
    }

    public static EighthFragment newInstance() {
        EighthFragment fragment = new EighthFragment();
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_eighth, container, false);
//        return view;
//    }


    @Override
    public void initView(View view) {
        tvTitle.setText("我的商城");
        String url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/member.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
        mWebView.loadUrl(url);
    }


}
