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

public class FifthFragment extends BaseFragment {

    private static FifthFragment instance = null;
    public static FifthFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = FifthFragment.newInstance();
        }
        return instance;
    }

    public static FifthFragment newInstance() {
        FifthFragment fragment = new FifthFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        return view;
    }


}
