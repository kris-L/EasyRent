package com.xw.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XWCHQ on 2017/2/23-09:46
 */
public abstract class BaseAdapter<T, VH extends BaseAdapter.BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    protected List<T> datas = new ArrayList<>();

    public void add(T data) {
        this.datas.add(data);
        notifyItemInserted(datas.size() - 1);
    }

    public void add(int index, T data) {
        this.datas.add(index, data);
        notifyItemInserted(index);
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

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setData(getItem(position));
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        private BaseAdapter<T, ? extends BaseViewHolder<T>> adapter;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public BaseViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object tag = v.getTag();
                        onItemClickListener.onItemClick(v, tag, getAdapterPosition());
                    }
                });
            } else {
//                itemView.setOnClickListener(null);
            }
        }

        protected abstract void setData(T data);

        BaseAdapter<T, ? extends BaseViewHolder<T>> getAdapter() {
            return adapter;
        }

        void setAdapter(BaseAdapter<T, ? extends BaseViewHolder<T>> adapter) {
            this.adapter = adapter;
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, Object tag, int position);
    }

}

