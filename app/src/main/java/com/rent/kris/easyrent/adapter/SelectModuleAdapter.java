package com.rent.kris.easyrent.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.dialog.ExamineMoreDialog;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/7/27 9:26
 * @类描述 ${TODO}适配器
 */
public class SelectModuleAdapter extends PagerAdapter {

    private int[] mData;
    private String[] nameList;
    private Context mContext;

    public SelectModuleAdapter(Context ctx, int[] data,String[] nameList,OnItemViewClickListener listener) {
        this.mContext = ctx;
        this.mData = data;
        this.nameList = nameList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mData.length;// 返回数据的个数
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {//子View显示
        View view = View.inflate(container.getContext(), R.layout.item_select_module, null);
        ImageView imageView = view.findViewById(R.id.iv_icon);
        TextView name_tv = view.findViewById(R.id.name_tv);
        imageView.setImageResource(mData[position]);

        name_tv.setText(nameList[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onImageClick(position);
                }
            }
        });

        container.addView(view);//添加到父控件
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;// 过滤和缓存的作用
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//从viewpager中移除掉
    }

    private OnItemViewClickListener listener;

    public void setOnItemClickListener(OnItemViewClickListener listener){
        this.listener = listener;
    }

    public interface OnItemViewClickListener{
        void onImageClick(int position);
    }


}