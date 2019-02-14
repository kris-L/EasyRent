package com.rent.kris.easyrent.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.adapter.SelectModuleAdapter;
import com.rent.kris.easyrent.adapter.ZoomOutPageTransformer;

/**
 * Created by lsz  on 2019-02-13
 */
public class SelectModuleDialog extends Dialog {

    private ViewPager mViewPager;
    private LinearLayout ll_layout;
    public Context mContext;

    public int tabType = 0;

    //向导界面的图片
    private int[] mPics = new int[]{R.mipmap.easy_rent_screenshot, R.mipmap.easy_life_screenshot,R.mipmap.easy_travel_screenshot,
            R.mipmap.easy_vedio_screenshot,R.mipmap.easy_discover_screenshot, R.mipmap.icon_3};

    private String[] moduleName = new String[]{"易租","士多百货","美食餐饮","农特产品","美容美发","论坛"};


    public SelectModuleAdapter.OnItemViewClickListener listener;

    public SelectModuleDialog(@NonNull Context context,int tabType,SelectModuleAdapter.OnItemViewClickListener listener) {
        super(context, R.style.MainAddDialog);
        mContext = context;
        this.tabType = tabType;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_module);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);

        //设置适配器
        mViewPager.setAdapter(new SelectModuleAdapter(mContext, mPics,moduleName,listener));
        mViewPager.setPageMargin(6);
        mViewPager.setOffscreenPageLimit(mPics.length);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());//设置画廊模式

        if(tabType > 0){
            mViewPager.setCurrentItem(tabType -1);
        }else{
            mViewPager.setCurrentItem(0);
        }

        //viewPager左右两边滑动无效的处理
        ll_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
    }





}
