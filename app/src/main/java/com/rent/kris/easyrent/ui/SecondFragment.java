package com.rent.kris.easyrent.ui;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseFragment;
import com.xw.common.prefs.LoginInfoPrefs;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class SecondFragment extends BaseFragment {

    private static SecondFragment instance = null;
    public static SecondFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = SecondFragment.newInstance();
        }
        return instance;
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_second, container, false);
//        return view;
//    }

    @Override
    public void initView(View view) {
        tvTitle.setText("易家");
        String url = Constant.BASE_URL +"appa/app2/public/wap/tmpl/yijia/index.html" +
                "?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        mWebView.loadUrl(url);
    }



}
