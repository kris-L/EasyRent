package com.rent.kris.easyrent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.ui.base.BaseFragment;

/**
 * Created by kris on 2019/1/15.
 */

public class SeventhFragment extends BaseFragment {

    private static SeventhFragment instance = null;
    public static SeventhFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = SeventhFragment.newInstance();
        }
        return instance;
    }

    public static SeventhFragment newInstance() {
        SeventhFragment fragment = new SeventhFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seventh, container, false);
        return view;
    }



}
