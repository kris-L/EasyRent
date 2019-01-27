package com.xw.common.adapter;

/**
 * Created by XWCHQ on 2017/11/24-17:07
 * 在item都是相同值的String时有误
 */

public abstract class SingleSelectableAdapter<T, VH extends SelectableAdapter.SelectableViewHolder<T>> extends SelectableAdapter<T, VH> {
    private boolean enableSelectNone = true;
    public static final int NO_POSITION = -1;
    private SelectableItem<T> selectedItem = null;
    public SingleSelectableAdapter() {
        this(true);
    }

    public SingleSelectableAdapter(boolean enableSelectNone) {
        this.enableSelectNone = enableSelectNone;
    }

    public boolean isEnableSelectNone() {
        return enableSelectNone;
    }

    public void setEnableSelectNone(boolean enableSelectNone) {
        this.enableSelectNone = enableSelectNone;
        if(!enableSelectNone && !hasSelected()){
            notifyItemSizeChanged();
        }
    }

    public int getSelectedPosition() {
        if(selectedItem == null){
            return NO_POSITION;
        }
        return indexOf(selectedItem);
    }

    public T getSelectedItem() {
        if(selectedItem != null) {
            return selectedItem.getData();
        }else{
            return null;
        }
    }

    public boolean hasSelected(){
        return !checkNull(selectedItem);
    }

    @Override
    public synchronized void selectedAll() {
        //单选不需要全选
    }

    @Override
    public synchronized void selectedNone() {
        if(enableSelectNone) {
            super.selectedNone();
        }
    }

    @Override
    protected void notifyItemSizeChanged() {
        if(getItemCount() > 0 && !hasSelected()) {
            checkEnableSelectNone();
        }else if (getItemCount() == 0){
            selectedItem = null;
        }
        super.notifyItemSizeChanged();
    }

    private void checkEnableSelectNone() {
        if (!enableSelectNone) {
            selectedFirstSelectableItem();
        }
    }

    @Override
    public synchronized void remove(int position) {
        if(position == indexOf(selectedItem)){
            selectedItem = null;
        }
        super.remove(position);
    }

    @Override
    public synchronized void clear() {
        super.clear();
        selectedItem = null;
    }

    /**
     * 选中第一个可选的项
     */
    private void selectedFirstSelectableItem() {
        if(getItemCount() == 0){
            return;
        }
        int position = NO_POSITION;
        for(int i = 0 ; i < getItemCount();i++){
            if(isSelectable(i)){
                position = i;
                break;
            }
        }
        if(position != NO_POSITION) {
            setSelected(position, true);
        }else{
            selectedItem = null;
        }
    }

    @Override
    protected boolean performChangeSelected(int position, boolean desState) {
        final int selectedPosition = indexOf(selectedItem);
        if(selectedPosition != NO_POSITION){
            if(desState){
                if(selectedPosition == position){
                    return false;
                }else{
                    boolean isChange = setSelectedImmediately(selectedPosition,false);
                    if(isChange){
                        notifyItemChanged(selectedPosition);
                    }
                    selectedItem = getSelectableItem(position);
                }
            }else{
                if(selectedPosition == position){
                    if(!enableSelectNone){
                        return false;
                    }else{
                        selectedItem = null;
                    }
                }
            }
        }else if(desState){
            selectedItem = getSelectableItem(position);
        }
        return super.performChangeSelected(position, desState);
    }
}
