package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.stream.MalformedJsonException;
import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.event.EventManager;
import com.rent.kris.easyrent.event.LogOutEvent;
import com.rent.kris.easyrent.event.OnLoginEvent;
import com.rent.kris.easyrent.prefs.AppPrefs;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.web.WebViewHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.xw.common.AppToast;
import com.xw.common.prefs.LoginInfoPrefs;
import com.xw.ext.http.retrofit.api.NoneProgressSubscriber;
import com.xw.ext.http.retrofit.api.error.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/14.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.password_et)
    EditText password_et;

    private Context mContext;
    public ArrayList<SnsPlatform> platforms = new ArrayList<>();
    private SHARE_MEDIA[] loginTypeList = {SHARE_MEDIA.QQ,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN};
    public static void intentTo(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String username = LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        phone_et.setText(username);
        initPlatforms();
        if(!TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            Log.e("lsz","key="+UserProfilePrefs.getInstance().getUserToken());
//            MainActivity.intentTo(LoginActivity.this);
//            finish();
        }
    }

    private void initPlatforms(){
        platforms.clear();
        for (SHARE_MEDIA e :loginTypeList) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())){
                platforms.add(e.toSnsPlatform());
            }
        }
    }


    @OnClick({R.id.login_tv,R.id.new_user_tv,R.id.iv_qq_login,R.id.iv_weixin_login,
            R.id.iv_sina_login,R.id.forget_password_tv})
    public void OnViewClick(View view){
        switch (view.getId()){
            case R.id.login_tv:
                String phone = phone_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else{
                    requestLogin(phone,password);
                }
                break;
            case R.id.new_user_tv:
                RegisterActivity.intentTo(LoginActivity.this);
                break;

            case R.id.iv_qq_login:
                UmEnter(0);
                break;

            case R.id.iv_weixin_login:
                UmEnter(2);
                break;

            case R.id.iv_sina_login:
                UmEnter(1);
                break;

            case R.id.forget_password_tv:
                SetPasswordActivity.intentTo(mContext);
                break;
        }
    }

    private void requestLogin(final String name, final String pwd) {
        showProgressDialog("正在登录…");
        WebViewHelper.clearWebViewCacheNCookies();
        AppModel.model().login(name, pwd, new NoneProgressSubscriber<UserProfile>() {
            @Override
            protected void onError(ApiException ex) {
                dismissProgressDialog();
                if (ex.getCause() instanceof MalformedJsonException) {
                    AppToast.makeText(LoginActivity.this, "登录失败"); //服务端返回{"code":1,"msg":"登录失败，用户名或密码不能为空","data":}，GsonResponseBodyConverter无法解析data
                } else {
                    AppToast.makeText(LoginActivity.this, ex.message);
                }
            }

            @Override
            public void onCompleted() {
                EventManager.sendEvent(new OnLoginEvent());
            }

            @Override
            public void onNext(UserProfile student) {
                dismissProgressDialog();
                EventBus.getDefault().post(new LogOutEvent());
                MainActivity.intentTo(LoginActivity.this,1);
                AppToast.makeText(LoginActivity.this, "登录成功");
                finish();
            }
        });
    }




    private void UmEnter(int pos) {
        final boolean isauth = UMShareAPI.get(this).isAuthorize(this,platforms.get(pos).mPlatform);
        if (isauth){
            UMShareAPI.get(this).deleteOauth(this,platforms.get(pos).mPlatform,authListener);
        }else {
            UMShareAPI.get(this).doOauthVerify(this,platforms.get(pos).mPlatform,authListener);
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this,"成功了",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("platform",platform);
            startActivity(intent);
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this,"失败："+t.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this,"取消了",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
