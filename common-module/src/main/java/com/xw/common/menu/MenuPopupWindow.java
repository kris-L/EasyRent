package com.xw.common.menu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xw.common.R;
import com.xw.common.adapter.BaseAdapter;
import com.xw.lib.custom.view.DividerItemDecoration;
import com.xw.lib.custom.view.SpaceDividerDecoration;
import com.xw.lib.custom.view.util.PxUtil;

import java.util.List;

/**
 * Created by XWCHQ on 2017/8/22-17:33
 */

public class MenuPopupWindow extends PopupWindow {

    protected View mContentView;
    protected Context mContext;
    private RecyclerView rvItems;
    private MenuItemAdapter menuItemAdapter;
    private OnMenuItemClickListener onMenuItemClickListener;
    private DividerItemDecoration dividerItemDecoration;

    public MenuPopupWindow(Context context) {
        this(context,null);
    }

    public MenuPopupWindow(Context context,List<MenuItem> items) {
        this(context, null, R.layout.common__popupwindow_menu);
    }

    public MenuPopupWindow(Context context, List<MenuItem> items, int layoutId) {
        super(context);
        this.mContext = context;
        setFocusable(true);
        setOutsideTouchable(true);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.common__bg_menu));
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mContentView = View.inflate(context, layoutId,null);
        setContentView(mContentView);
        initViews();
        if(items != null) {
            setMenuItems(items);
        }
    }

    public void setMenuItems(List<MenuItem> items) {
        menuItemAdapter.clear();
        menuItemAdapter.addAll(items);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
        this.onMenuItemClickListener = listener;
    }

    private void initViews() {
        rvItems = (RecyclerView) mContentView.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(mContext));
        menuItemAdapter = new MenuItemAdapter(new BaseAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, Object tag, int position) {
                dismiss();
                if(onMenuItemClickListener != null){
                    onMenuItemClickListener.onItemClick(menuItemAdapter.getItem(position),position);
                }
            }
        });
        rvItems.setAdapter(menuItemAdapter);
        dividerItemDecoration = new DividerItemDecoration(mContext, SpaceDividerDecoration.VERTICAL);
        dividerItemDecoration.setPaddingStart(40);
        rvItems.addItemDecoration(dividerItemDecoration);
    }

    public static interface OnMenuItemClickListener{
        void onItemClick(MenuItem item, int position);
    }

    public void show(View anchor, int xoff, int yoff){
        super.showAsDropDown(anchor, xoff, yoff);
    }

    public void show(View anchor){
        show(anchor,- PxUtil.dip2px(mContext,82),PxUtil.dip2px(mContext,5));
    }

    public DividerItemDecoration getDividerItemDecoration() {
        return dividerItemDecoration;
    }
}
