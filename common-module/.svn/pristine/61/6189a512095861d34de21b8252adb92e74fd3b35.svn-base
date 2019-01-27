package com.xw.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by XWCHQ on 2017/6/22-14:03
 */

public class SimpleFragmentStatePagerAdapter extends ArrayFragmentStatePagerAdapter<SimpleFragmentStatePagerAdapter.FragmentBean> {

    public SimpleFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return getData(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getData(position).getTitle();
    }

    public static class FragmentBean {
        private Fragment fragment;
        private CharSequence title;

        public FragmentBean() {

        }

        public FragmentBean(Fragment fragment, CharSequence title) {
            this.fragment = fragment;
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
