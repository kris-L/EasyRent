package com.rent.kris.easyrent.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.ui.view.BottomBar;
import com.rent.kris.easyrent.ui.view.PopupMenuUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.center_img)
    ImageView mCenterImage;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.fl_tab_container)
    FrameLayout fl_tab_container;

    private static final String TAG_FRAG_FIRST= "app:fragment:first";
    private static final String TAG_FRAG_SECOND = "app:fragment:second";
    private static final String TAG_FRAG_THIRD = "app:fragment:third";
    private static final String TAG_FRAG_FOURTH = "app:fragment:fourth";

    private Context mContext;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FouthFragment fouthFragment;

    private String currentFragmentTag;
    private int tabType = Constant.TYPE_TAB_EASY_HOME;
    private int selectIndex = 1;


    public static void intentTo(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        firstFragment = FirstFragment.getInstance(true);
        secondFragment = secondFragment.getInstance(true);
        thirdFragment = thirdFragment.getInstance(true);
        fouthFragment = fouthFragment.getInstance(true);
        currentFragmentTag = TAG_FRAG_FIRST;
        transaction.add(fragmentContainerId(), firstFragment, TAG_FRAG_FIRST).commit();

        mBottomBar.setOnBottombarOnclick(new BottomBar.OnBottonbarClick() {
            @Override
            public void onFirstClick() {
                selectIndex = 1;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContentFragment(getCurrFragment(), firstFragment, TAG_FRAG_FIRST);
            }

            @Override
            public void onSecondClick() {
                selectIndex =2;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContentFragment(getCurrFragment(), secondFragment, TAG_FRAG_SECOND);
            }

            @Override
            public void onThirdClick() {
                selectIndex =3;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContentFragment(getCurrFragment(), thirdFragment, TAG_FRAG_THIRD);
            }

            @Override
            public void onFouthClick() {
                selectIndex = 4;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContentFragment(getCurrFragment(), fouthFragment, TAG_FRAG_FOURTH);
            }

            @Override
            public void onCenterClick() {
                PopupMenuUtil.getInstance().showUp(mContext, mCenterImage,new PopupMenuUtil.OnButtonClick(){

                    @Override
                    public void onRentClick() {
                        tabType = Constant.TYPE_TAB_EASY_HOME;
                        mBottomBar.setBottonTabView(tabType,selectIndex);
                        switchBottomTab();
                    }

                    @Override
                    public void onLifeClick() {
                        tabType = Constant.TYPE_APP_EASY_LIFE;
                        mBottomBar.setBottonTabView(tabType,selectIndex);
                        switchBottomTab();
                    }
                });
            }
        });
    }

    private Fragment getCurrFragment() {
        if (TextUtils.equals(currentFragmentTag, TAG_FRAG_FIRST)) {
            return firstFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_SECOND)) {
            return secondFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_THIRD)) {
            return thirdFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_FOURTH)) {
            return fouthFragment;
        }else{
            return firstFragment;
        }
    }
    private void switchContentFragment(Fragment from, Fragment to, String tag) {
        if (TextUtils.equals(currentFragmentTag, tag)) {
            return;
        }
        currentFragmentTag = tag;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (to.isAdded()) {
            if (from.isAdded() && !from.isHidden()) {
                transaction.hide(from).show(to).commit();
            } else {
                transaction.show(to).commit();
            }
        } else {
            if (from.isAdded() && !from.isHidden()) {
                transaction.hide(from).add(fragmentContainerId(), to, tag).commit();
            } else {
                transaction.add(fragmentContainerId(), to, tag).commit();
            }
        }
    }

    private int fragmentContainerId() {
        return R.id.fl_tab_container;
    }


    /**
     * 刚打开popupWindow 执行的动画
     */
    private void switchBottomTab() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fl_tab_container, "rotationY", 0f,360f);
        objectAnimator.setDuration(800);
        objectAnimator.start();
    }
}
