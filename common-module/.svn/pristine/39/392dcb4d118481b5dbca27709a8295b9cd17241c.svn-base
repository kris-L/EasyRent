package com.xw.common.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XWCHQ on 2017/6/22-14:03
 */

public abstract class ArrayFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter {
    private List<T> dataList = new ArrayList<>();

    public ArrayFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(T data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    public void add(int index, T data) {
        dataList.add(index, data);
        notifyDataSetChanged();
    }

    public void addAll(T[] datas) {
        addAll(Arrays.asList(datas));
    }

    public void addAll(List<? extends T> datas) {
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void remove(int index) {
        dataList.remove(index);
        notifyDataSetChanged();
    }

    public List<T> getAll() {
        return dataList;
    }

    public T getData(int position) {
        if (position >= 0 && position < dataList.size()) {
            return dataList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void replace(int index, T data) {
        dataList.set(index, data);
        notifyDataSetChanged();
    }
}
