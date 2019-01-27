package com.xw.common.popup;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by XWCHQ on 2017/6/27-19:22
 */

public class BaseBottomWindow extends PopupWindow {
    protected Activity activity;
    private float initAlpha;
    private final float DEFAULT_DES_ALPHA = 0.7f;
    private float realDesAlpha = DEFAULT_DES_ALPHA;
    private int displayWidth;
    private int displayHeight;
    private float windowHeight = 0.4f;

    public BaseBottomWindow(Activity context) {
        super(context);
        this.activity = context;
        setFocusable(true);
        setOutsideTouchable(true);
        Rect outRect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        displayWidth = outRect.width();
        setWidth(displayWidth);
        displayHeight = outRect.height();
        setHeight(displayHeight);
        initAlpha = activity.getWindow().getAttributes().alpha;
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                playBackgroundAnimate(realDesAlpha,initAlpha);
            }
        });
    }

    @Override
    public void setContentView(View contentView) {
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(contentView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (displayHeight * windowHeight)));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.BOTTOM);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(linearLayout);
    }

    /**
     * @param windowHeight 显示窗口占显示区域的高度比例，0~1
     */
    public void setWindowHeight(float windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void showAtLocation(View parent){
        super.showAtLocation(parent, Gravity.TOP,0,0);
        playBackgroundAnimate(initAlpha, realDesAlpha);
    }
    protected ValueAnimator bgAnimate;
    protected void playBackgroundAnimate(float startAlpha,float endAlpha) {
        if(bgAnimate != null){
            bgAnimate.cancel();
        }
        bgAnimate = ValueAnimator.ofFloat(startAlpha,endAlpha);
        bgAnimate.setDuration(1000);
        bgAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object value = animation.getAnimatedValue();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (value instanceof Float) {
                        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                        attributes.alpha = (float) value;
                        activity.getWindow().setAttributes(attributes);
                    }
                }else{
                    updateBackgroundColor(Color.argb((int) (0xff*(1 - (float) value)),0,0,0));
                }
            }
        });
        bgAnimate.start();
    }

    protected void updateBackgroundColor(int color) {
        if(getContentView() != null) {
            getContentView().setBackgroundColor(color);
        }
    }

    public void setDesAlpha(float desAlpha) {
        this.realDesAlpha = initAlpha > desAlpha ? desAlpha :initAlpha;
    }
}
