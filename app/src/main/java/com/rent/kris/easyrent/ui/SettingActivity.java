package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/16.
 */

public class SettingActivity extends BaseActivity {

    private Context mContext;
    public static void intentTo(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;

        initViews();
    }

    private void initViews() {

    }

    public Intent intent;
    public String title = "";
    public String url = "";

    @OnClick({R.id.log_out_tv,R.id.change_password_ll,R.id.address_ll,R.id.instructions_ll,
            R.id.feedback_ll,R.id.about_us_ll})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.log_out_tv:
                UserProfilePrefs.getInstance().saveUserToken("");
                LoginActivity.intentTo(this);
                finish();
                break;

            case R.id.change_password_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/member_password_step1.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);

                break;

            case R.id.address_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/address_list.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.instructions_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/guide.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.feedback_ll:

                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/member_feedback.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.about_us_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/yizu/about-us.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
        }
    }


}
