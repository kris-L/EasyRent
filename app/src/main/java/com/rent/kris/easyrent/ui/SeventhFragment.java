package com.rent.kris.easyrent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * Created by kris on 2019/1/15.
 */

public class SeventhFragment extends BaseFragment {

    private static String TAG = "SeventhFragment";
    private static SeventhFragment instance = null;
    public static SeventhFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = SeventhFragment.newInstance();
        }
        return instance;
    }

    public static SeventhFragment newInstance() {
        SeventhFragment fragment = new SeventhFragment();
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_seventh, container, false);
//        return view;
//    }


    @Override
    public void initView(View view) {
        tvTitle.setText("购物车");
        String url = Constant.BASE_URL +"appa/app2/public/wap/tmpl/shopping.html"+
                "?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        Log.e(TAG,"url="+url);
        mWebView.loadUrl(url);
    }



}
