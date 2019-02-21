package com.rent.kris.easyrent.ui.view;

import android.animation.AnimatorSet;
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

    int[] centreLocation = new int[2];


    public static PopupMenuUtil getInstance() {
        return MenuUtilHolder.INSTANCE;
    }

    public OnButtonClick mOnButtonClick;
    public void setOnBottombarOnclick(OnButtonClick onButtonClick) {
        mOnButtonClick = onButtonClick;
    }

    public interface OnButtonClick {
        void onViewClick(int index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy_rent_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(0);
                }
                _rlClickAction();
                break;

            case R.id.easy_life_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(1);
                }
                _rlClickAction();
                break;

            case R.id.cate_memu_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(2);
                }
                _rlClickAction();
                break;
            case R.id.hairdressing_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(3);
                }
                _rlClickAction();
                break;
            case R.id.farm_produce_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(4);
                }
                _rlClickAction();
                break;
            case R.id.maker_ll:
                if(mOnButtonClick != null){
                    mOnButtonClick.onViewClick(5);
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

//    private RelativeLayout rlClick;
//    private ImageView ivBtn;
    private LinearLayout llTest1, llTest2,llTest3, llTest4,llTest5, llTest6;

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
//        rlClick = (RelativeLayout) rootVew.findViewById(R.id.pop_rl_click);
//        ivBtn = (ImageView) rootVew.findViewById(R.id.pop_iv_img);
        llTest1 = (LinearLayout) rootVew.findViewById(R.id.easy_rent_ll);
        llTest2 = (LinearLayout) rootVew.findViewById(R.id.easy_life_ll);
        llTest3 = (LinearLayout) rootVew.findViewById(R.id.cate_memu_ll);
        llTest4 = (LinearLayout) rootVew.findViewById(R.id.hairdressing_ll);
        llTest5 = (LinearLayout) rootVew.findViewById(R.id.farm_produce_ll);
        llTest6 = (LinearLayout) rootVew.findViewById(R.id.maker_ll);

//        rlClick.setOnClickListener(this);
        llTest1.setOnClickListener(this);
        llTest2.setOnClickListener(this);
        llTest3.setOnClickListener(this);
        llTest4.setOnClickListener(this);
        llTest5.setOnClickListener(this);
        llTest6.setOnClickListener(this);

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
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 0f, 135f);
//        objectAnimator.setDuration(200);
//        objectAnimator.start();

//      拿到终点控件的绝对坐标
        int [] locationEnd = new int[2];
        llTest1.getLocationInWindow(locationEnd);
        int centreX = centreLocation[0];
        int centreY = centreLocation[1];

        float animatorPropertyX [] = new float[]{100,50, 10, 0};
        float animatorPropertyX2 [] = new float[]{-100,-50, -10, 0};
//        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(llTest1, "translationX", animatorPropertyX);
//        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(llTest1, "translationY", animatorProperty);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
//        animatorSet.setDuration(500);
//        animatorSet.start();

        _startAnimation(llTest1, 500, animatorPropertyX);
        _startAnimation(llTest2, 500, animatorPropertyX);
        _startAnimation(llTest5, 500, animatorPropertyX2);
        _startAnimation(llTest6, 500, animatorPropertyX2);
    }


    /**
     * 关闭 popupWindow执行的动画
     */
    public void _rlClickAction() {
        _closeAnimation(llTest1, 300, top);
        _closeAnimation(llTest2, 200, top);
        _close();

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
            v.getLocationOnScreen(centreLocation);
//            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, location[1] - measuredHeight);
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, centreLocation[1] - popupHeight);
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
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", distance);
        anim.setDuration(duration);
        anim.start();
    }

    private void _startAnimation(View view, int duration,float[] distanceX, float[] distanceY) {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "translationX", distanceX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "translationY", distanceY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }


}
