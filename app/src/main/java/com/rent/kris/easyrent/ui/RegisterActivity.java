package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.stream.MalformedJsonException;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.entity.CommonEntity;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.event.EventManager;
import com.rent.kris.easyrent.event.OnLoginEvent;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.util.Utils;
import com.rent.kris.easyrent.web.WebViewHelper;
import com.xw.common.AppToast;
import com.xw.ext.http.retrofit.api.NoneProgressSubscriber;
import com.xw.ext.http.retrofit.api.error.ApiException;
import com.xw.ext.http.retrofit.api.error.ErrorSubscriber;
import com.xw.lib.custom.view.BannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lsz  on 2019-01-28
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.banner)
    BannerView banner;
    @BindView(R.id.phone_et)
    EditText phone_et;

    @BindView(R.id.next_tv)
    TextView next_tv;
    @BindView(R.id.get_verification_code_tv)
    TextView get_verification_code_tv;
    @BindView(R.id.code_et)
    EditText code_et;
    @BindView(R.id.password_et)
    EditText password_et;

    private boolean isCountDown = false ;
    private TimeCount timeCount;
    private Context mContext;
    private String phone = "";
    private String code = "";
    private String password = "";

    public static void intentTo(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        initViews();
    }

    private void initViews() {
        phone_et.addTextChangedListener(watcher);
    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String search = editable.toString();
            if (!TextUtils.isEmpty(search)) {
//                next_tv.setBackground(getResources().getDrawable(R.drawable.shape_circular_bead_orange));
                if(!isCountDown){
                    get_verification_code_tv.setBackgroundColor(getResources().getColor(R.color.green_46DECE));
                }
            } else {
//                next_tv.setBackground(getResources().getDrawable(R.drawable.shape_disable_btn_orange));
                if(!isCountDown){
                    get_verification_code_tv.setBackgroundColor(getResources().getColor(R.color.gray_D2));
                }
            }
        }
    };


    @OnClick({R.id.next_tv,R.id.get_verification_code_tv})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.next_tv:
                phone = phone_et.getText().toString().trim();
                code = code_et.getText().toString().trim();
                password = password_et.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(code)){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else{
                    requestRegister(phone,password,code);
                }
                break;

            case R.id.get_verification_code_tv:
                String phoneStr = phone_et.getText().toString().trim();
                if(Utils.isMobileNO(phoneStr)){
                    requestCode(phoneStr);
                    if(timeCount == null){
                        timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                    }else {
                        timeCount.start();
                    }
                }else{
                    AppToast.makeText(mContext,"请输入正确的手机号码");
                }

                break;
        }
    }


    private void requestCode(final String phoneStr) {
        showProgressDialog("正在获取验证码…");
        AppModel.model().getCode(phoneStr,new ErrorSubscriber<CommonEntity>() {
            @Override
            protected void onError(ApiException ex) {
                if (ex.getCause() instanceof MalformedJsonException) {
                    AppToast.makeText(mContext, "获取验证码失败");
                } else {
                    AppToast.makeText(mContext, "获取验证码失败：" + ex.message);
                }
                dismissProgressDialog();
            }

            @Override
            public void onCompleted() {
                dismissProgressDialog();
            }

            @Override
            public void onNext(CommonEntity s) {

            }
        });
    }

    private void requestRegister(final String name, final String pwd,String codeStr) {
        showProgressDialog("正在注册…");
        WebViewHelper.clearWebViewCacheNCookies();
        AppModel.model().register(name, pwd,codeStr, new NoneProgressSubscriber<UserProfile>() {
            @Override
            protected void onError(ApiException ex) {
                dismissProgressDialog();
                if (ex.getCause() instanceof MalformedJsonException) {
                    AppToast.makeText(mContext, "注册失败"); //服务端返回{"code":1,"msg":"登录失败，用户名或密码不能为空","data":}，GsonResponseBodyConverter无法解析data
                } else {
                    AppToast.makeText(mContext, ex.message);
                }
            }

            @Override
            public void onCompleted() {
                EventManager.sendEvent(new OnLoginEvent());
            }

            @Override
            public void onNext(UserProfile student) {
                dismissProgressDialog();
                MainActivity.intentTo(mContext,1);
                AppToast.makeText(mContext, "注册成功");
                finish();
            }
        });
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            get_verification_code_tv.setBackgroundColor(getResources().getColor(R.color.gray_D2));
            get_verification_code_tv.setClickable(false);
            get_verification_code_tv.setText("获取验证码"+millisUntilFinished / 1000);
            isCountDown = true;
        }

        @Override
        public void onFinish() {
            isCountDown = false;
            get_verification_code_tv.setText("重新获取");
            get_verification_code_tv.setClickable(true);
            get_verification_code_tv.setBackgroundColor(getResources().getColor(R.color.green_46DECE));
        }
    }



}
