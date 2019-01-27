package com.xw.common.pager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xw.lib.custom.view.pager.PageNestedScrollView;

/**
 * Created by XWCHQ on 2017/6/21-14:10
 */

public class CoverCardTransformer implements ViewPager.PageTransformer {
    private static final int SCROLLING_ELEVATION = 100;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int elevation = 0;
        if (position < -1) { // [-Infinity,-1)
            elevation = 0;
            view.setAlpha(1);
            view.setTranslationX(0);
        } else if (position < 0) { // [-1,0]
            view.setAlpha(1);
            view.setTranslationX(0);
            elevation = SCROLLING_ELEVATION;
        } else if (position == 0) {
            view.setAlpha(1);
            view.setTranslationX(0);
            elevation = 0;
        } else if (position < 1) { // (0,1]
            view.setAlpha(1);
            view.setTranslationX(pageWidth * -position);
            elevation = 0;
        } else { // (1,+Infinity]
            elevation = 0;
            view.setAlpha(0);
            view.setTranslationX(0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        }
        if(view instanceof PageNestedScrollView){
            PageNestedScrollView.OnPageScrollListener onPageScrollListener = ((PageNestedScrollView) view).getOnPageScrollListener();
            if(onPageScrollListener != null){
                onPageScrollListener.onPageScroll((PageNestedScrollView)view,position);
            }
        }
    }
}
