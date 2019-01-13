package com.rent.kris.easyrent.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.MainActivity;
import com.xw.dialog.lib.LoadingDialog;
import com.xw.ext.http.retrofit.api.ProgressCancelListener;
import com.xw.ext.http.retrofit.api.ProgressSubscriber;


public class BaseActivity extends AppCompatActivity implements ProgressSubscriber.ProgressDialogHandle {
    private LoadingDialog loadingDialog;

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(String text) {
        showProgressDialog(text, true);
    }

    public void showProgressDialog(String text, boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            if (text != null) {
                loadingDialog.setLoadingText(text);
            } else {
                loadingDialog.setLoadingText(getString(R.string.network_loading_text));
            }
            loadingDialog.setCancelable(cancelable);
            loadingDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void setProgressCancelListener(final ProgressCancelListener progressCancelListener) {
        if (loadingDialog == null) {
            return;
        }
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (progressCancelListener != null) {
                    progressCancelListener.onCancelProgress();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();

        if (mHomeWatcherReceiver != null) {
            try {
                unregisterReceiver(mHomeWatcherReceiver);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final Class<?> homeClass = MainActivity.class;
    protected void exitToHome() {
        Intent intent = new Intent(this, homeClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    //按下Home键后的处理

    private HomeWatcherReceiver mHomeWatcherReceiver = null;
    private boolean mIsFinishAfterHomePressed = true;

    public void setIsFinishAfterHomePressed(boolean b) {
        mIsFinishAfterHomePressed = b;
    }

    private void registerReceiver() {
        mHomeWatcherReceiver = new HomeWatcherReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatcherReceiver, filter);
    }

    public class HomeWatcherReceiver extends BroadcastReceiver {

        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            //Log.i("BaseActivity", "intentAction =" + intentAction);
            if (TextUtils.equals(intentAction, Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                //Log.i("BaseActivity", "reason =" + reason);
                if (TextUtils.equals(SYSTEM_DIALOG_REASON_HOME_KEY, reason)) {
                    onHomePressed();
                }
            }
        }

    }

    protected void onHomePressed() {
//        finish();
    }

}
