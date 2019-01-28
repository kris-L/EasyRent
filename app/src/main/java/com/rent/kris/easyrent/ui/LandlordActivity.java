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

public class LandlordActivity  extends BaseActivity {

    private Context mContext;
    public static void intentTo(Context context) {
        Intent intent = new Intent(context, LandlordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord);
        ButterKnife.bind(this);
        mContext = this;

        initViews();
    }

    private void initViews() {

    }


    public Intent intent;
    public String title = "";
    public String url = "";

    @OnClick({R.id.go_renter_tv,R.id.my_appointment_ll,R.id.earnings_ll,R.id.housing_ll,R.id.tenant_ll,R.id.work_ll})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.go_renter_tv:
                finish();
                break;

            case R.id.my_appointment_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/my-order.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.earnings_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/my_account.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.housing_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/my-house.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;

            case R.id.tenant_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/my-tenant.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;


            case R.id.work_ll:
                intent = new Intent(this, WebViewActivity.class);
                title = "";
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/my-work.html"+"?key="+UserProfilePrefs.getInstance().getUserToken();
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
        }
    }


}
