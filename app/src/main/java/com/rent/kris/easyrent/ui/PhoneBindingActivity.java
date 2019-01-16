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

public class PhoneBindingActivity extends BaseActivity {

    private Context mContext;
    public static void intentTo(Context context) {
        Intent intent = new Intent(context, PhoneBindingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_binding);
        ButterKnife.bind(this);
        mContext = this;
        initViews();
    }

    private void initViews() {

    }

    @OnClick({R.id.next_tv})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.next_tv:
                SetPasswordActivity.intentTo(this);
                break;
        }
    }


}
