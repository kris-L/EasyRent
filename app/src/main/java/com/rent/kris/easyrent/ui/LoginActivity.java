package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseActivity;

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


    }

    @OnClick({R.id.login_tv,R.id.new_user_tv})
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
                    MainActivity.intentTo(LoginActivity.this);
                    finish();
                }
                break;
            case R.id.new_user_tv:
                PhoneBindingActivity.intentTo(LoginActivity.this);
                break;
        }
    }



}
