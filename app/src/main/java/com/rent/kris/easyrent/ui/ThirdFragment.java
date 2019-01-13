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
public class ThirdFragment extends BaseFragment {

    private static ThirdFragment instance = null;
    public static ThirdFragment getInstance(boolean isNew) {
        if (instance == null || isNew) {
            instance = ThirdFragment.newInstance();
        }
        return instance;
    }

    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        return view;
    }
}
