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
public class ThirdFragment extends BaseFragment {

    private static ThirdFragment instance = null;
    public static ThirdFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = ThirdFragment.newInstance();
        }
        return instance;
    }

    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        return fragment;
    }

    @Override
    public void initView(View view) {
        tvTitle.setText("易租");
        String url = Constant.BASE_URL +"appa/app2/public/wap/tmpl/member/news.html"+
                "?key="+ UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        mWebView.loadUrl(url);
    }
}
