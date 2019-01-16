package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rent.kris.easyrent.R;
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

    @OnClick({R.id.log_out_tv})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.log_out_tv:
                LoginActivity.intentTo(this);
                finish();
                break;
        }
    }


}
