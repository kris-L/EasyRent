package com.rent.kris.easyrent.ui;

import android.view.View;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseFragment;
import com.xw.common.prefs.LoginInfoPrefs;

/**
 * Created by lsz  on 2019-01-28
 */
public class FirstFragment2  extends BaseFragment {


    private static FirstFragment2 instance = null;
    public static FirstFragment2 getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = FirstFragment2.newInstance();
        }
        return instance;
    }

    public static FirstFragment2 newInstance() {
        FirstFragment2 fragment = new FirstFragment2();
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_first_2, container, false);
//        return view;
//    }


    @Override
    public void initView(View view) {
        tvTitle.setText("易租");
        String url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/indexa.html"+
                "?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        mWebView.loadUrl(url);
    }



}
