package com.rent.kris.easyrent.ui;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseFragment;
import com.xw.common.prefs.LoginInfoPrefs;
import com.xw.common.util.TextViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class FouthFragment extends BaseFragment {

    @BindView(R.id.login_tv)
    TextView login_tv;
    private static FouthFragment instance = null;
    private Unbinder unbinder;
    private Boolean isLogin = false;
    public static FouthFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = FouthFragment.newInstance();
        }
        return instance;
    }

    public static FouthFragment newInstance() {
        FouthFragment fragment = new FouthFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    public void initData() {
        if(TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            isLogin =false;
            login_tv.setText("立即登录");
        }else{
            isLogin = true;
            login_tv.setText(LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public Intent intent;
    public String title = "";
    public String url = "";
    @OnClick({R.id.go_landlord_tv,R.id.user_setting_ll,R.id.my_account_ll,R.id.my_contract_ll,
            R.id.my_appointment_ll,R.id.my_order_ll,R.id.evaluate_ll,R.id.favorite_ll,R.id.share_ll,
    R.id.my_homepage_ll,R.id.attention_ll,R.id.user_identification_ll,R.id.user_service_ll,
            R.id.user_message_ll,R.id.login_tv})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.go_landlord_tv:
                LandlordActivity.intentTo(getActivity());
                break;
            case R.id.user_setting_ll:
                SettingActivity.intentTo(getActivity());
                break;

            case R.id.my_account_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                 title = "";
                 url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/my_account.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.my_contract_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                 title = "";
                 url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/pact.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.my_appointment_ll:
                 intent = new Intent(getActivity(), WebViewActivity.class);
                 title = "";
                 url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/my-order.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.my_order_ll:
                 intent = new Intent(getActivity(), WebViewActivity.class);
                 title = "";
                 url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/the-order.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.evaluate_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/evaluate.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.favorite_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/collection.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.share_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/the-order.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.my_homepage_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/homepage.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;


            case R.id.attention_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/homepage.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.user_identification_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/landlord.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;


            case R.id.user_service_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/index.php/mobile/yizu/keguphone.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;


            case R.id.user_message_ll:
                intent = new Intent(getActivity(), WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/member/homepage.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.login_tv:
                if(isLogin){
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    title = "";
                    url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/member.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }else{
                    LoginActivity.intentTo(getActivity());
                }

                break;

        }
    }

}
