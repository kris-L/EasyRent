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
import com.rent.kris.easyrent.api.RequestInterceptor;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.event.EventManager;
import com.rent.kris.easyrent.event.LogOutEvent;
import com.rent.kris.easyrent.event.OnLoginEvent;
import com.rent.kris.easyrent.prefs.AppPrefs;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.web.WebViewHelper;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoard;
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

    private static String TAG = "LoginActivity";

    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.password_et)
    EditText password_et;

    private Context mContext;
    public ArrayList<SnsPlatform> platforms = new ArrayList<>();
    public int registerCode = 1001; //注册
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN};

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
        initPlatforms();
    }

    private void initPlatforms(){
        platforms.clear();
        for (SHARE_MEDIA e :list) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())){
                platforms.add(e.toSnsPlatform());
            }
        }
    }

    private void initView() {
        String username = LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        phone_et.setText(username);
        if(!TextUtils.isEmpty(UserProfilePrefs.getInstance().getUserToken())){
            Log.e("lsz","key="+UserProfilePrefs.getInstance().getUserToken());
//            MainActivity.intentTo(LoginActivity.this);
//            finish();
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
                RegisterActivity.intentTo(LoginActivity.this,registerCode);
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
//                SetPasswordActivity.intentTo(mContext);
                String pictureUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553427441501&di=1e1f6341c636b2f0974d48719022aee5&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F23%2F20140923094045_BNYji.thumb.700_0.png";
                new ShareAction(LoginActivity.this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(new UMImage(LoginActivity.this, pictureUrl))
                        .open();
                break;
        }
    }

    UMShareListener umShareListener = new UMShareListener(){
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onStart");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onResult");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e(TAG, "分享"+"onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onCancel");
        }
    };

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

    /**
     * 第三方登录验证注册
     */
    private void requestWqwregister(final String uid, final String member_name, final String member_sex, final String member_avatar, final String source) {
        showProgressDialog("正在校验…");
        WebViewHelper.clearWebViewCacheNCookies();
        AppModel.model().wqwregister(uid,member_name,member_sex,member_avatar,source, new NoneProgressSubscriber<UserProfile>() {
            @Override
            protected void onError(ApiException ex) {
                dismissProgressDialog();
                if (ex.getCause() instanceof MalformedJsonException) {
                    //服务端返回{"code":1,"msg":"登录失败，用户名或密码不能为空","data":}，GsonResponseBodyConverter无法解析data
                    AppToast.makeText(LoginActivity.this, "登录失败");
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
                if(student.state == 2 || student.state == 0){
//                    RequestInterceptor.getInstance().setTokenValue("");
                    RegisterActivity.intentTo(LoginActivity.this,registerCode,uid,member_name,member_sex,member_avatar,source);
                    AppToast.makeText(LoginActivity.this, "请绑定手机号码");
                    finish();
                }if(student.state == 1){
                    MainActivity.intentTo(LoginActivity.this,1);
                    AppToast.makeText(LoginActivity.this, "登录成功");
                    finish();
                }else{
                    AppToast.makeText(LoginActivity.this, "登录失败");
                }
                EventBus.getDefault().post(new LogOutEvent());
            }
        });
    }

    private void UmEnter(int pos) {
        final boolean isauth = UMShareAPI.get(this).isAuthorize(this,platforms.get(pos).mPlatform);
        UMShareAPI.get(this).getPlatformInfo(this,platforms.get(pos).mPlatform,authListener);
//        if (isauth){
//            UMShareAPI.get(this).deleteOauth(this,platforms.get(pos).mPlatform,authListener);
//        }else {
//            UMShareAPI.get(this).doOauthVerify(this,platforms.get(pos).mPlatform,authListener);
//        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this,"授权成功",Toast.LENGTH_LONG).show();
            if(data != null){
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    Log.e(TAG,"Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
            }
            String uid = data.get("uid");
            String access_token = data.get("access_token");
            String name = data.get("name");
            String gender = data.get("gender");
            String iconurl = data.get("iconurl");
            String source = "qq";
            if("SINA".equals(platform.toString())){
                source = "weibo";
            }else if("WEIXIN".equals(platform.toString())){
                source = "wechat";
            }else if("QQ".equals(platform.toString())){
                source = "qq";
            }
            requestWqwregister(uid,name,gender,iconurl,source);
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            intent.putExtra("platform",platform);
//            startActivity(intent);
//            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this,"授权失败："+t.getMessage(),Toast.LENGTH_LONG).show();
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
        if(registerCode == requestCode){
            if(resultCode == RESULT_OK){
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
