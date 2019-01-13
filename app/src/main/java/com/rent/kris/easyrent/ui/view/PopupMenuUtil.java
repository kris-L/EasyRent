package com.rent.kris.easyrent.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rent.kris.easyrent.R;

/**
 * Created by kris
 * on 2018/10/28.
 */
public class PopupMenuUtil implements View.OnClickListener {

    private static final String TAG = "PopupMenuUtil";

    public static PopupMenuUtil getInstance() {
        return MenuUtilHolder.INSTANCE;
    }

    public OnButtonClick mOnButtonClick;
    public void setOnBottombarOnclick(OnButtonClick onButtonClick) {
        mOnButtonClick = onButtonClick;
    }

    public interface OnButtonClick {
        void onRentClick();
        void onLifeClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy_rent_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onRentClick();
                }
                _rlClickAction();
                break;

            case R.id.easy_life_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onLifeClick();
                }
                _rlClickAction();
                break;

        }
    }

    private static class MenuUtilHolder {
        public static PopupMenuUtil INSTANCE = new PopupMenuUtil();
    }

    private View rootVew;
    private PopupWindow popupWindow;

    private RelativeLayout rlClick;
    private ImageView ivBtn;
    private LinearLayout llTest1, llTest2;

    /**
     * 动画执行的 属性值数组
     */
    float animatorProperty[] = null;
    /**
     * 第一排图 距离屏幕底部的距离
     */
    int top = 0;
    /**
     * 第二排图 距离屏幕底部的距离
     */
    int bottom = 0;

    private int popupHeight;

    /**
     * 创建 popupWindow 内容
     *
     * @param context context
     */
    private void _createView(final Context context) {
        rootVew = LayoutInflater.from(context).inflate(R.layout.popup_menu, null);
        popupWindow = new PopupWindow(rootVew,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置为失去焦点 方便监听返回键的监听
        popupWindow.setFocusable(true);

        // 如果想要popupWindow 遮挡住状态栏可以加上这句代码
        //popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        if (animatorProperty == null) {
            top = dip2px(context, 310);
            bottom = dip2px(context, 210);
            animatorProperty = new float[]{bottom, 60, -30, -20 - 10, 0};
        }
        initLayout(context);
    }

    /**
     * dp转化为px
     *
     * @param context  context
     * @param dipValue dp value
     * @return 转换之后的px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 初始化 view
     */
    private void initLayout(Context context) {
        rlClick = (RelativeLayout) rootVew.findViewById(R.id.pop_rl_click);
        ivBtn = (ImageView) rootVew.findViewById(R.id.pop_iv_img);
        llTest1 = (LinearLayout) rootVew.findViewById(R.id.easy_rent_ll);
        llTest2 = (LinearLayout) rootVew.findViewById(R.id.easy_life_ll);

        rlClick.setOnClickListener(this);
        llTest1.setOnClickListener(this);
        llTest2.setOnClickListener(this);

    }

    /**
     * 点击事件
     */
    private class MViewClick implements View.OnClickListener {

        public int index;
        public Context context;

        public MViewClick(int index, Context context) {
            this.index = index;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (index == 0) {
                //加号按钮点击之后的执行
                _rlClickAction();
            } else {
                showToast(context, "index=" + index);
            }
        }
    }

    Toast toast = null;

    /**
     * 防止toast 多次被创建
     *
     * @param context context
     * @param str     str
     */
    private void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    /**
     * 刚打开popupWindow 执行的动画
     */
    private void _openPopupWindowAction() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 0f, 135f);
        objectAnimator.setDuration(200);
        objectAnimator.start();

        _startAnimation(llTest1, 500, animatorProperty);
        _startAnimation(llTest2, 430, animatorProperty);

    }


    /**
     * 关闭 popupWindow执行的动画
     */
    public void _rlClickAction() {
        if (ivBtn != null && rlClick != null) {

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 135f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.start();

            _closeAnimation(llTest1, 300, top);
            _closeAnimation(llTest2, 200, top);


            rlClick.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _close();
                }
            }, 300);

        }
    }


    /**
     * 弹起 popupWindow
     *
     * @param context context
     * @param parent  parent
     */
    public void _show(Context context, View parent) {
        _createView(context);
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
            _openPopupWindowAction();
        }
    }

    public void showUp(Context context, View v,OnButtonClick onButtonClick) {
        mOnButtonClick = onButtonClick;
        _createView(context);
        if (popupWindow != null && !popupWindow.isShowing()) {
            rootVew.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupHeight = rootVew.getMeasuredHeight();
            int[] location = new int[2];
            v.getLocationOnScreen(location);
//            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, location[1] - measuredHeight);
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, location[1] - popupHeight);
            _openPopupWindowAction();
        }

    }


    /**
     * 关闭popupWindow
     */

    public void _close() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * @return popupWindow 是否显示了
     */
    public boolean _isShowing() {
        if (popupWindow == null) {
            return false;
        } else {
            return popupWindow.isShowing();
        }
    }

    /**
     * 关闭 popupWindow 时的动画
     *
     * @param view     mView
     * @param duration 动画执行时长
     * @param next     平移量
     */
    private void _closeAnimation(View view, int duration, int next) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", 0f, next);
        anim.setDuration(duration);
        anim.start();
    }

    /**
     * 启动动画
     *
     * @param view     view
     * @param duration 执行时长
     * @param distance 执行的轨迹数组
     */
    private void _startAnimation(View view, int duration, float[] distance) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", distance);
        anim.setDuration(duration);
        anim.start();
    }


}
