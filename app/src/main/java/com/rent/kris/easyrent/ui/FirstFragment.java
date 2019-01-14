package com.rent.kris.easyrent.ui;

import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseFragment;


/**
 * created by kris at 2018/4/5/0005
 */
public class FirstFragment extends BaseFragment {

    private static FirstFragment instance = null;
    public static FirstFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = FirstFragment.newInstance();
        }
        return instance;
    }

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }
}
