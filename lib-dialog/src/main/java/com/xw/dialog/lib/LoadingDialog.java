package com.xw.dialog.lib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingDialog extends Dialog {

    private TextView loadingText;
    private LoadingDrawable mMaterialDrawable;
    private Context context;

    public LoadingDialog(Context context) {

        super(context, R.style.loading_dialog);
        this.context = context;
        setContentView(R.layout.lib_dialog__loading_dialog);
        ImageView mImageView = (ImageView) findViewById(R.id.loadingdialog_img);
        mMaterialDrawable = new LoadingDrawable(new MaterialLoadingRenderer(context));
        mImageView.setImageDrawable(mMaterialDrawable);
        loadingText = (TextView) findViewById(R.id.loadingdialog_text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMaterialDrawable.stop();
    }

    @Override
    public void dismiss() {
        Activity activity=(Activity)context;
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT) {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                super.dismiss();
                mMaterialDrawable.stop();
            }
        }else{
            if (!activity.isFinishing()) {
                super.dismiss();
                mMaterialDrawable.stop();
            }
        }
    }

    @Override
    public void show() {
        Activity activity=(Activity)context;
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT) {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                super.show();
                mMaterialDrawable.start();
            }
        }else{
            if (!activity.isFinishing()) {
                super.dismiss();
                mMaterialDrawable.start();
            }
        }
    }

    public void setLoadingText(String str) {
        if (loadingText != null)
            loadingText.setText(str);
    }

    public void setLoadingText(int resId) {
        if (loadingText != null)
            loadingText.setText(resId);
    }
}
