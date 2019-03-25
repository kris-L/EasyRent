package com.rent.kris.easyrent.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.constant.Constant;


public class BottomBar extends LinearLayout {

    private Context mContext;

    private FrameLayout mFirst_bottom, mSecond_bottom, mThird_bottom, mFouth_bottom, mCenter_bottom;
    private ImageView tab_first_iv,tab_second_iv,tab_third_iv,tab_fouth_iv;
    private TextView tab_first_text_tv,tab_second_text_tv,tab_third_text_tv,tab_fouth_text_tv;
    private OnBottonbarClick mOnBottonbarClick;

    private int tabType = Constant.TYPE_TAB_EASY_HOME;


    public BottomBar(Context context) {
        super(context);
        init(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_bottom, this, true);
        //获取控件id
        initId();
        onBottomBarClick();
    }

    private void initId() {
        mFirst_bottom = (FrameLayout) findViewById(R.id.first);
        mSecond_bottom = (FrameLayout) findViewById(R.id.second);
        mThird_bottom = (FrameLayout) findViewById(R.id.third);
        mFouth_bottom = (FrameLayout) findViewById(R.id.fouth);
        mCenter_bottom = (FrameLayout) findViewById(R.id.center);

        tab_first_iv = (ImageView) findViewById(R.id.tab_first_iv);
        tab_first_text_tv = (TextView) findViewById(R.id.tab_first_text_tv);
        tab_second_iv = (ImageView) findViewById(R.id.tab_second_iv);
        tab_second_text_tv = (TextView) findViewById(R.id.tab_second_text_tv);
        tab_third_iv = (ImageView) findViewById(R.id.tab_third_iv);
        tab_third_text_tv = (TextView) findViewById(R.id.tab_third_text_tv);
        tab_fouth_iv = (ImageView) findViewById(R.id.tab_fouth_iv);
        tab_fouth_text_tv = (TextView) findViewById(R.id.tab_fouth_text_tv);
    }

    public void setBottonTabView(int type,int select){
        if(type == Constant.TYPE_TAB_EASY_HOME){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText(getResources().getText(R.string.easy_rent_str));
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText(getResources().getText(R.string.easy_family_str));
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_message));
            tab_third_text_tv.setText(getResources().getText(R.string.easy_msg_str));
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText(getResources().getText(R.string.easy_mine_str));
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_message_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }

        }else if(type == Constant.TYPE_APP_EASY_LIFE){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText(getResources().getText(R.string.merchant_str));
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText(getResources().getText(R.string.merchandise_str));
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session));
            tab_third_text_tv.setText(getResources().getText(R.string.shopping_cart_str));
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText(getResources().getText(R.string.easy_mine_str));
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }
        }else if(type == Constant.TYPE_TAB_EASY_CATE){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText("美食餐饮");
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText(getResources().getText(R.string.merchandise_str));
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session));
            tab_third_text_tv.setText(getResources().getText(R.string.shopping_cart_str));
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText(getResources().getText(R.string.easy_mine_str));
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }


        }else if(type == Constant.TYPE_APP_EASY_BEAUTY){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText("美容美发");
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText(getResources().getText(R.string.merchandise_str));
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session));
            tab_third_text_tv.setText(getResources().getText(R.string.shopping_cart_str));
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText(getResources().getText(R.string.easy_mine_str));
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }

        }else if(type == Constant.TYPE_TAB_EASY_FARM){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText("农特产品");
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText(getResources().getText(R.string.merchandise_str));
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session));
            tab_third_text_tv.setText(getResources().getText(R.string.shopping_cart_str));
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText(getResources().getText(R.string.easy_mine_str));
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_session_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }
        }else if(type == Constant.TYPE_APP_EASY_FORUM){
            tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home));
            tab_first_text_tv.setText("首页");
            tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family));
            tab_second_text_tv.setText("搜索");
            tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_message));
            tab_third_text_tv.setText("唠叨");
            tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine));
            tab_fouth_text_tv.setText("文章");
            tab_first_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_second_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_third_text_tv.setTextColor(getResources().getColor(R.color.gray_33));
            tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.gray_33));

            switch (select){
                case 1:
                    tab_first_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_home_pre));
                    tab_first_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 2:
                    tab_second_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_family_pre));
                    tab_second_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 3:
                    tab_third_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_message_pre));
                    tab_third_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
                case 4:
                    tab_fouth_iv.setBackground(getResources().getDrawable(R.drawable.icon_tab_mine_pre));
                    tab_fouth_text_tv.setTextColor(getResources().getColor(R.color.circle_orange_bg));
                    break;
            }


        }
    }

    /**
     * 底部按钮点击监听器
     */
    private void onBottomBarClick() {

        mFirst_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFirstClick();
                }
            }
        });
        mSecond_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onSecondClick();
                }
            }
        });
        mThird_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onThirdClick();
                }
            }
        });
        mFouth_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFouthClick();
                }
            }
        });
        mCenter_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onCenterClick();
                }
            }
        });

    }
    public void setOnBottombarOnclick(OnBottonbarClick onBottonbarClick) {
        mOnBottonbarClick = onBottonbarClick;
    }

    public interface OnBottonbarClick {
        void onFirstClick();

        void onSecondClick();

        void onThirdClick();

        void onFouthClick();

        void onCenterClick();
    }
}
