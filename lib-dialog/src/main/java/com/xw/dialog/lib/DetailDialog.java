package com.xw.dialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;


public class DetailDialog extends Dialog {
    private String mDetail;

    public DetailDialog(Context context, String des) {
        super(context, R.style.BaseDialogStyle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDetail = des;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_dialog__dialog_detail);
        TextView mDetailTextView = (TextView) findViewById(R.id.tv_des);
        mDetailTextView.setText(mDetail);
    }
}
