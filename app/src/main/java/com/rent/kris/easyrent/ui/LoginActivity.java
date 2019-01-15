package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/14.
 */

public class LoginActivity extends BaseActivity {

    private Context mContext;

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

    @OnClick(R.id.login_tv)
    public void OnViewClick(View view){
        switch (view.getId()){
            case R.id.login_tv:
                MainActivity.intentTo(LoginActivity.this);
                break;
        }
    }



}
