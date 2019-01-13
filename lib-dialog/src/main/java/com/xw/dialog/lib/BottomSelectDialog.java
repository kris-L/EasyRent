package com.xw.dialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class BottomSelectDialog<T> extends Dialog {

    private Context mContext;

    private ListView lv;

    private TextView tvPositive, tvCancel;

    private List<T> mDataList;

    private MyAdapter adapter;

    public BottomSelectDialog(Context context, List<T> dataList) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mDataList = dataList;
    }

    public BottomSelectDialog(Context context, T[] list) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mDataList = Arrays.asList(list);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.lib_dialog__bottom_select_dialog);
        initView();
        init();
        addListener();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv_data);
        adapter = new MyAdapter(mDataList);
        lv.setAdapter(adapter);
    }

    private void init() {
        WindowManager manager = this.getWindow().getWindowManager();
        Window window = this.getWindow();
        LayoutParams params = window.getAttributes();
        Display d = manager.getDefaultDisplay();
        LayoutParams p = window.getAttributes();
        params.height = (int) (d.getHeight() * 0.45); // 高度设置为屏幕的0.45
        params.width = d.getWidth();
        params.gravity = Gravity.BOTTOM;

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        lv.measure(w, h);
        //得到的只是一个item的高度，所以要乘以size
        int height = lv.getMeasuredHeight() * mDataList.size();

        if (height < params.height) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        window.setAttributes(p);
        window.setWindowAnimations(R.style.Animation_Popup);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    private onItemSelectListener<T> mListener;

    // 监听器
    public interface onItemSelectListener<T> {
        void onSelect(T value, int pos);
    }

    public BottomSelectDialog setOnItemSelectListener(onItemSelectListener<T> mListener) {
        this.mListener = mListener;
        return this;
    }

    private void addListener() {
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onSelect(mDataList.get(position), position);
                }
                dismiss();
            }
        });

    }

    private class MyAdapter<E> extends BaseAdapter {

        private List<E> mDataList;

        MyAdapter(List<E> dataList) {
            this.mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public E getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.lib_dialog__bottom_select_dialog_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_textItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv.setText(mDataList.get(position).toString());
            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }

    }

}
