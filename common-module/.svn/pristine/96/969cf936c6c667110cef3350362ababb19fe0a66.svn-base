package com.xw.common.menu;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xw.common.R;
import com.xw.common.adapter.BaseAdapter;

/**
 * Created by XWCHQ on 2017/8/22-17:52
 */

public class MenuItemAdapter extends BaseAdapter<MenuItem,MenuItemViewHolder> {

    private OnItemClickListener onItemClickListener;

    public MenuItemAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuItemViewHolder(parent,onItemClickListener);
    }
}

class MenuItemViewHolder extends BaseAdapter.BaseViewHolder<MenuItem>{

    private ImageView ivIcon;
    private TextView tvTitle;
    public MenuItemViewHolder(ViewGroup parent,final BaseAdapter.OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.common__item_menu,parent,false),onItemClickListener);
        ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
    }

    @Override
    protected void setData(MenuItem data) {
        if(data.iconResId != 0) {
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(data.iconResId);
        }else{
            ivIcon.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(data.title)){
            tvTitle.setText(data.title);
        }
    }
}

