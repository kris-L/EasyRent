package com.xw.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xw.common.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击选中/取消选中，复选
 * Created by XWCHQ on 2016/12/29-21:08.
 */
public abstract class SelectableAdapter<T, VH extends SelectableAdapter.SelectableViewHolder<T>> extends RecyclerView.Adapter<VH>{
    private final List<SelectableItem<T>> mItems = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnItemSelectChangeListener onItemSelectChangeListener;

    public boolean add(T data) {
        mItems.add(new SelectableItem<>(data));
        notifyItemSizeChanged();
        return true;
    }

    protected void notifyItemSizeChanged() {
        notifyDataSetChanged();
    }

    public boolean add(int index,T data) {
        if(index < 0 || index > mItems.size()){
            return false;
        }
        mItems.add(index,new SelectableItem<>(data));
        notifyItemSizeChanged();
        return true;
    }

    public synchronized void addAll(List<? extends T> dataList) {
        for (T data : dataList) {
            mItems.add(new SelectableItem<>(data));
        }
        notifyItemSizeChanged();
    }

    public synchronized void clear() {
        mItems.clear();
        notifyItemSizeChanged();
    }

    public T getItem(int position) {
        if(checkPosition(position)) {
            return mItems.get(position).getData();
        }else{
            return null;
        }
    }

    public synchronized List<T> getAll() {
        return ListUtils.convertList(mItems, new ListUtils.Converter<SelectableItem<T>, T>() {
            @Override
            public T convert(SelectableItem<T> src) {
                return src.getData();
            }
        });
    }

    public void replace(int position, T data) {
        if(checkPosition(position)) {
            SelectableItem<? extends T> selectableItem = mItems.get(position);
            mItems.set(position, new SelectableItem<T>(data,selectableItem.isSelected));
            notifyItemChanged(position);
        }
    }

    protected boolean checkPosition(int position) {
        return position >= 0 && position < getItemCount();
    }

    public void remove(int position){
        if(checkPosition(position)){
            mItems.remove(position);
            notifyItemSizeChanged();
        }
    }

    public  void remove(T data){
        if(checkNull(data)){
            return;
        }
        int position = indexOf(new SelectableItem<>(data));
        remove(position);
    }

    public int indexOf(SelectableItem<T> item) {
        return mItems.indexOf(item);
    }

    final protected boolean checkNull(Object data) {
        return data == null;
    }

    protected SelectableItem<T> getSelectableItem(int position) {
        if (position >= 0 && position < mItems.size()) {
            return mItems.get(position);
        } else {
            return null;
        }
    }

    /**
     * 不触发选中状态变更回调
     * @see #setSelected(int, boolean, boolean)
     */
    public void setSelected(int position, boolean isSelected) {
        setSelected(position, isSelected, true,false);
    }

    public void setSelected(int position){
        setSelected(position,true);
    }

    public void setSelected(int position, boolean isSelected,boolean isNotifyListener) {
        setSelected(position,isSelected,true,isNotifyListener);
    }

    protected synchronized void setSelected(int position, boolean isSelected,boolean isNotifyAdapter, boolean isNotifyListener) {
        if(!isSelectable(position)){
            return;
        }
        boolean isChanged = performChangeSelected(position,isSelected);
        if(!isChanged){
            return;
        }
        if(isNotifyAdapter) {
            notifyItemChanged(position);
        }
        if (isNotifyListener) {
            if (baseItemSelectChangeListener != null) {
                baseItemSelectChangeListener.onSelectChange(null, SelectableAdapter.this, position, isSelected);
            }
        }
    }

    protected boolean performChangeSelected(int position,boolean desState) {
        return setSelectedImmediately(position,desState);
    }

    final protected boolean setSelectedImmediately(int position,boolean desState){
        SelectableItem selectableItem = getSelectableItem(position);
        if(checkNull(selectableItem)){
            return false;
        }
        selectableItem.setSelected(desState);
        return true;
    }

    /**
     * 不自动刷新UI，也不触发选中状态变更回调
     */
    public void setSelectedWithoutNotify(int position, boolean isSelected) {
        setSelected(position,isSelected,false,false);
    }

    public synchronized void selectedNone() {
        for (int i = 0 ; i < getItemCount();i++) {
            setSelectedWithoutNotify(i,false);
        }
        notifyDataSetChanged();
    }

    public synchronized void selectedAll() {
        for (int i = 0 ; i < getItemCount();i++) {
            setSelectedWithoutNotify(i,true);
        }
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        if(checkPosition(position)) {
            SelectableItem selectableItem = getSelectableItem(position);
            return selectableItem != null && selectableItem.isSelected();
        }else{
            return false;
        }
    }

    public boolean isSelectable(int position){
        return checkPosition(position);
    }

    public List<T> getSelectedItems() {
        List<T> dataList = new ArrayList<>();
        for (SelectableItem<? extends T> item : mItems) {
            if (item.isSelected()) {
                dataList.add(item.getData());
            }
        }
        return dataList;
    }

    public List<Integer> getSelectedPositions(){
        List<Integer> positionList = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).isSelected()) {
                positionList.add(i);
            }
        }
        return positionList;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(baseItemClickListener);
        holder.setData(getItem(position), isSelected(position));
    }

    private View.OnClickListener baseItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof SelectableAdapter.SelectableViewHolder) {
                SelectableViewHolder holder = (SelectableViewHolder) tag;
                if(!isSelectable(holder.getAdapterPosition())){
                    return;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
                performChangeByUser(holder.getAdapterPosition());
            }
        }
    };

    protected void performChangeByUser(int adapterPosition) {
        setSelected(adapterPosition,!isSelected(adapterPosition),true);
    }

    private OnItemSelectChangeListener baseItemSelectChangeListener = new OnItemSelectChangeListener() {
        @Override
        public void onSelectChange(View view, SelectableAdapter adapter, int position, boolean isSelected) {
            if (onItemSelectChangeListener != null) {
                onItemSelectChangeListener.onSelectChange(view, adapter, position, isSelected);
            }
        }
    };

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectChangeListener(OnItemSelectChangeListener onItemSelectChangeListener) {
        this.onItemSelectChangeListener = onItemSelectChangeListener;
    }

    public OnItemSelectChangeListener getOnItemSelectChangeListener() {
        return onItemSelectChangeListener;
    }

    public abstract static class SelectableViewHolder<T> extends RecyclerView.ViewHolder {

        public SelectableViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setData(T data);

        public void setData(T data, boolean isSelected) {
            setData(data);
            setSelected(isSelected);
        }

        public abstract void setSelected(boolean isSelected);
    }

    public static class SelectableItem<T> {
        private boolean isSelected = false;
        private T data;

        public SelectableItem() {

        }

        public SelectableItem(T data) {
            this.data = data;
        }

        public SelectableItem(T data,boolean isSelected) {
            this.data = data;
            this.isSelected = isSelected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SelectableItem<?> that = (SelectableItem<?>) o;

            return data != null ? data.equals(that.data) : that.data == null;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemSelectChangeListener {
        void onSelectChange(View view, SelectableAdapter adapter, int position, boolean isSelected);
    }
}
