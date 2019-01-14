package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.os.Bundle;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseActivity;

import butterknife.ButterKnife;

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

}
