package com.rent.kris.easyrent.ui;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseFragment;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class SecondFragment extends BaseFragment {

    private static SecondFragment instance = null;
    public static SecondFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = SecondFragment.newInstance();
        }
        return instance;
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }
}
