package com.rent.kris.easyrent.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 包含一个 Fragment 的 Activity
 */
public abstract class ContainerActivity<F extends Fragment> extends BaseActivity {
    public static final int TRANSITION_INIT_FRAGMENT = FragmentTransaction.TRANSIT_NONE;

    public static final String TAG_CONTENT_FRAGMENT = "app:content_fragment";

    protected F contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateContentView(savedInstanceState);
        initContentFragment(savedInstanceState);
    }

    protected void onCreateContentView(Bundle savedInstanceState) {
    }

    protected void initContentFragment(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            contentFragment = createContentFragment();
            if (contentFragment == null) {
                return;
            }
            if (contentFragment.getArguments() == null) {
                contentFragment.setArguments(getIntent().getExtras());
            }
            fragmentManager.beginTransaction()
                    .setTransition(TRANSITION_INIT_FRAGMENT)
                    .add(fragmentContainerId(), contentFragment, TAG_CONTENT_FRAGMENT)
                    .commit();
        } else {
            contentFragment = (F) fragmentManager.findFragmentByTag(TAG_CONTENT_FRAGMENT);
        }
    }

    protected void replaceContentFragment(boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        contentFragment = createContentFragment();
        if (contentFragment.getArguments() == null) {
            contentFragment.setArguments(getIntent().getExtras());
        }
        @SuppressLint("CommitTransaction") FragmentTransaction transaction = fragmentManager
                .beginTransaction()
                .setTransition(TRANSITION_INIT_FRAGMENT)
                .replace(fragmentContainerId(), contentFragment, TAG_CONTENT_FRAGMENT);
        if (addToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }

    protected int fragmentContainerId() {
        return android.R.id.content;
    }

    protected abstract F createContentFragment();
}
