package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.util.Utils;
import com.xw.common.prefs.LoginInfoPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/16.
 */

public class LandlordActivity  extends BaseActivity {

    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.release_house_tv)
    TextView release_house_tv;

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
        if(TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            name_tv.setText("未登录");
        }else{
            name_tv.setText(LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName());
        }
    }


    public Intent intent;
    public String title = "";
    public String url = "";

    @OnClick({R.id.go_renter_tv,R.id.my_appointment_ll,R.id.earnings_ll,R.id.housing_ll,R.id.tenant_ll,
            R.id.work_ll,R.id.release_house_tv,R.id.release_house_ll})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.release_house_ll:
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/fabufa1.html";
                Utils.GotoWebView(this,url);
                break;

            case R.id.go_renter_tv:
                finish();
                break;

            case R.id.my_appointment_ll:
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/my-order.html";
                Utils.GotoWebView(this,url);
                break;

            case R.id.earnings_ll:
                url = "http://app.tit306.com/appa/app2/public/wap//tmpl/member/my_account.html";
                Utils.GotoWebView(this,url);
                break;

            case R.id.housing_ll:
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yizu/my-house.html";
                Utils.GotoWebView(this,url);
                break;

            case R.id.tenant_ll:
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/my-tenant.html";
                Utils.GotoWebView(this,url);
                break;

            case R.id.work_ll:
                url = "http://app.tit306.com/appa/app2/public/wap/tmpl/yijia/my-work.html";
                Utils.GotoWebView(this,url);
                break;
        }
    }


}
