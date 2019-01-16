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

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class FouthFragment extends BaseFragment {

    private static FouthFragment instance = null;
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.go_landlord_tv,R.id.user_setting_ll})
    public void OnClickView(View view) {
        switch(view.getId()){
            case R.id.go_landlord_tv:
                LandlordActivity.intentTo(getActivity());
                break;
            case R.id.user_setting_ll:
                SettingActivity.intentTo(getActivity());
                break;
        }
    }

}
