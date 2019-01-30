package com.rent.kris.easyrent.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rent.kris.easyrent.R;

/**
 * Created by zhm on 2018/12/18.
 */

public class ExamineMoreDialog extends Dialog implements View.OnClickListener{

    public ExamineMoreDialog(@NonNull Context context) {
        super(context, R.style.MainAddDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_examine_more);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.take_photos_tv).setOnClickListener(this);
        findViewById(R.id.photo_album_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photos_tv:
                if(listener != null){
                    listener.onTakePhotos();
                }
                dismiss();
                break;

            case R.id.photo_album_tv:
                if(listener != null){
                    listener.onPhotoAlbum();
                }
                dismiss();
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public interface onItemClickListener{
        void onTakePhotos();
        void onPhotoAlbum();
    }
}
