package com.xw.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XWCHQ on 2018/1/30-11:28
 */

public abstract class BaseHeaderAdapter<T, VH extends BaseHeaderAdapter.BaseViewHolder> extends HeaderRecyclerViewAdapter {
    protected List<T> datas = new ArrayList<>();
    private BaseViewHolder headerViewHolder;
    private BaseViewHolder footerViewHolder;
    public void add(T data) {
        this.datas.add(data);
        notifyItemInserted(datas.size() - 1);
    }

    public void add(int index, T data) {
        this.datas.add(index, data);
        notifyItemInserted(index);
    }

    public void replace(int index, T data) {
        this.datas.set(index, data);
        notifyItemChanged(index);
    }

    public void addAll(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addAll(T[] datas) {
        this.datas.addAll(Arrays.asList(datas));
        notifyDataSetChanged();
    }

    public void clear() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    public void remove(int index) {
        this.datas.remove(index);
        notifyItemRemoved(index);
    }

    public List<T> getAll() {
        return datas;
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public abstract VH onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        ((VH) holder).setData(getItem(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return headerViewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return footerViewHolder;
    }

    @Override
    public boolean useHeader() {
        return headerViewHolder != null;
    }

    @Override
    public boolean useFooter() {
        return footerViewHolder != null;
    }

    public void setHeaderData(Object data){
        if(headerViewHolder != null){
            headerViewHolder.setData(data);
        }
    }

    public void setFooterData(Object data){
        if(footerViewHolder != null){
            footerViewHolder.setData(data);
        }
    }

    @Override
    public int getBasicItemCount() {
        return datas.size();
    }

    @Override
    public int getBasicItemType(int position) {
        return 0;
    }

    public BaseViewHolder getHeaderViewHolder() {
        return headerViewHolder;
    }

    public void setHeaderViewHolder(BaseViewHolder headerViewHolder) {
        this.headerViewHolder = headerViewHolder;
    }

    public BaseViewHolder getFooterViewHolder() {
        return footerViewHolder;
    }

    public void setFooterViewHolder(BaseViewHolder footerViewHolder) {
        this.footerViewHolder = footerViewHolder;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, Object tag, int position);
    }

    public abstract static class BaseViewHolder<T> extends BaseAdapter.BaseViewHolder<T> {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }
    }

}
