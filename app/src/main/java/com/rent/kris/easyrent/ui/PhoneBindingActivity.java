package com.rent.kris.easyrent.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.xw.lib.custom.view.BannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kris on 2019/1/16.
 */

public class PhoneBindingActivity extends BaseActivity {

    @BindView(R.id.banner)
    BannerView banner;
    @BindView(R.id.phone_et)
    EditText phone_et;

    @BindView(R.id.next_tv)
    TextView next_tv;
    @BindView(R.id.get_verification_code_tv)
    TextView get_verification_code_tv;

    private boolean isCountDown = false ;
    private TimeCount timeCount;
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
        phone_et.addTextChangedListener(watcher);
        banner.setRightBtn("跳过", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPasswordActivity.intentTo(mContext);
            }
        });
        banner.setRightBtnColor(getResources().getColor(R.color.black));
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
                next_tv.setBackground(getResources().getDrawable(R.drawable.shape_circular_bead_orange));
                if(!isCountDown){
                    get_verification_code_tv.setBackgroundColor(getResources().getColor(R.color.green_46DECE));
                }
            } else {
                next_tv.setBackground(getResources().getDrawable(R.drawable.shape_disable_btn_orange));
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
                String phone = phone_et.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }else{
                    SetPasswordActivity.intentTo(this);
                }
                break;

            case R.id.get_verification_code_tv:
                if(timeCount == null){
                    timeCount = new TimeCount(60000, 1000);
                    timeCount.start();
                }else {
                    timeCount.start();
                }
                break;
        }
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
