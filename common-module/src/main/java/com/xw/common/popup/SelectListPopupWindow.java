package com.xw.common.popup;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xw.common.R;
import com.xw.common.adapter.BaseAdapter;
import com.xw.lib.custom.view.DividerItemDecoration;
import com.xw.lib.custom.view.util.PxUtil;

import java.util.List;

/**
 * Created by XWCHQ on 2017/10/16-10:24
 */

public class SelectListPopupWindow<T> extends BaseBottomWindow {

    private ListAdapter<T> listAdapter;
    private OnItemSelectedListener<T> onItemSelectedListener;

    public SelectListPopupWindow(Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.common__popup_select_list,null);
        setContentView(view);
        setAnimationStyle(R.style.PopupAnim_translate);
        initViews(view);
    }

    private RecyclerView rvData;
    private TextView tvTitle;
    private void initViews(View view) {
        rvData = (RecyclerView) view.findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvData.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        listAdapter = new ListAdapter<>();
        rvData.setAdapter(listAdapter);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
    }

    public void setTitle(CharSequence title){
        if(title != null){
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }else{
            tvTitle.setVisibility(View.GONE);
            tvTitle.setText("");
        }
    }

    public void setData(List<T> data){
        listAdapter.clear();
        listAdapter.addAll(data);
    }

    public void setData(T[] data){
        listAdapter.clear();
        listAdapter.addAll(data);
    }

    public void addData(List<T> data){
        listAdapter.addAll(data);
    }

    public void addData(T[] data){
        listAdapter.addAll(data);
    }

    private void setOnItemClickListener(BaseAdapter.OnItemClickListener setOnItemClickListener){
        listAdapter.setOnItemClickListener(setOnItemClickListener);
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag, int position) {
                dismiss();
                if(onItemSelectedListener != null){
                    onItemSelectedListener.onItemSelected(view,(T)tag,position);
                }
            }
        });
    }

    private class ListAdapter<T> extends BaseAdapter<T,ListViewHolder<T>> {

        private OnItemClickListener onItemClickListener;

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            TextView itemView = new TextView(context);
            itemView.setGravity(Gravity.CENTER);
            itemView.setPadding(PxUtil.dip2px(context,15f), PxUtil.dip2px(context,15f), PxUtil.dip2px(context,15f), PxUtil.dip2px(context,15f));
            itemView.setTextSize(14);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setTextColor(context.getResources().getColor(R.color.gray_33));
            itemView.setBackgroundResource(R.drawable.custom_view_selector_default);
            return new ListViewHolder(itemView, onItemClickListener);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    private class ListViewHolder<T> extends BaseAdapter.BaseViewHolder<T>{

        public ListViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
            super(itemView,onItemClickListener);
        }

        @Override
        protected void setData(T data) {
            itemView.setTag(data);
            if(itemView instanceof TextView){
                ((TextView) itemView).setText(data.toString());
            }
        }
    }

    public static interface OnItemSelectedListener<T>{
        void onItemSelected(View view, T data, int position);
    }
}
