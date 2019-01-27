package com.xw.common;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by XWCHQ on 2017/2/21-17:15
 */
public class AppToast {
    public static void makeText(final Context context, final String text) {
        if (context == null) {
            return;
        }
        if(context instanceof Activity) {
            Activity activity=(Activity)context;
            if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT) {
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast mToast = createToast(context, text);
                            mToast.show();
                        }
                    });
                }
            }else{
                if (!activity.isFinishing()) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast mToast = createToast(context, text);
                            mToast.show();
                        }
                    });
                }
            }
        }else{
            Toast mToast = createToast(context, text);
            mToast.show();
        }
    }

    private static Toast createToast(Context context, String text) {
        Toast mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        View view = View.inflate(context, R.layout.common_toast_text, null);
        mToast.setView(view);
        TextView textView = (TextView) mToast.getView().findViewById(
                R.id.textview);
        textView.setText(text);
        return mToast;
    }

    public static void makeTextBelow(View achor, String text) {
        if (achor == null) {
            return;
        }
        Toast mToast = createToast(achor.getContext(), text);
        mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, achor.getHeight());
        mToast.show();
    }

    public static void makeTextCenter(Context context, String text) {
        Toast mToast = createToast(context, text);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }
}
