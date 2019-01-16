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

    private static final String TAG_FRAG_FIFTH = "app:fragment:fifth";
    private static final String TAG_FRAG_SIXTH = "app:fragment:sixth";
    private static final String TAG_FRAG_SEVENTH = "app:fragment:seventh";
    private static final String TAG_FRAG_EIGHTH = "app:fragment:eighth";


    private Context mContext;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FouthFragment fouthFragment;

    private FifthFragment fifthFragment;
    private SixthFragment sixthFragment;
    private SeventhFragment seventhFragment;
    private EighthFragment eighthFragment;


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
        secondFragment = SecondFragment.getInstance(true);
        thirdFragment = ThirdFragment.getInstance(true);
        fouthFragment = FouthFragment.getInstance(true);
        fifthFragment = FifthFragment.getInstance(true);
        sixthFragment = SixthFragment.getInstance(true);
        seventhFragment = SeventhFragment.getInstance(true);
        eighthFragment = EighthFragment.getInstance(true);

        currentFragmentTag = TAG_FRAG_FIRST;
        transaction.add(fragmentContainerId(), firstFragment, TAG_FRAG_FIRST).commit();

        mBottomBar.setOnBottombarOnclick(new BottomBar.OnBottonbarClick() {
            @Override
            public void onFirstClick() {
                selectIndex = 1;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContent(tabType,selectIndex);
            }

            @Override
            public void onSecondClick() {
                selectIndex =2;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContent(tabType,selectIndex);
            }

            @Override
            public void onThirdClick() {
                selectIndex =3;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContent(tabType,selectIndex);
            }

            @Override
            public void onFouthClick() {
                selectIndex = 4;
                mBottomBar.setBottonTabView(tabType,selectIndex);
                switchContent(tabType,selectIndex);
            }

            @Override
            public void onCenterClick() {
                PopupMenuUtil.getInstance().showUp(mContext, mCenterImage,new PopupMenuUtil.OnButtonClick(){

                    @Override
                    public void onRentClick() {
                        tabType = Constant.TYPE_TAB_EASY_HOME;
                        mBottomBar.setBottonTabView(tabType,selectIndex);
                        switchBottomTab();
                        switchContent(tabType,selectIndex);
                    }

                    @Override
                    public void onLifeClick() {
                        tabType = Constant.TYPE_APP_EASY_LIFE;
                        mBottomBar.setBottonTabView(tabType,selectIndex);
                        switchBottomTab();
                        switchContent(tabType,selectIndex);
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
        } else if(TextUtils.equals(currentFragmentTag, TAG_FRAG_FIFTH)){
            return fifthFragment;
        }else if(TextUtils.equals(currentFragmentTag, TAG_FRAG_SIXTH)){
            return sixthFragment;
        }else if(TextUtils.equals(currentFragmentTag, TAG_FRAG_SEVENTH)){
            return seventhFragment;
        } else if(TextUtils.equals(currentFragmentTag, TAG_FRAG_EIGHTH)){
            return eighthFragment;
        } else{
            return firstFragment;
        }

    }

    private void switchContent(int tabType,int selectIndex){
        if(tabType == Constant.TYPE_TAB_EASY_HOME){
            switch (selectIndex){
                case 1:
                    switchContentFragment(getCurrFragment(), firstFragment, TAG_FRAG_FIRST);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), secondFragment, TAG_FRAG_SECOND);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), thirdFragment, TAG_FRAG_THIRD);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), fouthFragment, TAG_FRAG_FOURTH);
                    break;
            }
        }else{
            switch (selectIndex){
                case 1:
                    switchContentFragment(getCurrFragment(), fifthFragment, TAG_FRAG_FIFTH);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), sixthFragment, TAG_FRAG_SIXTH);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), seventhFragment, TAG_FRAG_SEVENTH);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), eighthFragment, TAG_FRAG_EIGHTH);
                    break;
            }
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
