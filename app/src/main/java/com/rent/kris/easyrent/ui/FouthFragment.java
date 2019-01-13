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
public class FouthFragment extends BaseFragment {

    private static FouthFragment instance = null;
    public static FouthFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = FouthFragment.newInstance();
        }
        return instance;
    }

    public static FouthFragment newInstance() {
        FouthFragment fragment = new FouthFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        return view;
    }
}
