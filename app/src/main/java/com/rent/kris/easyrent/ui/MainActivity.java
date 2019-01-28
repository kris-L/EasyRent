package com.rent.kris.easyrent.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.ui.view.BottomBar;
import com.rent.kris.easyrent.ui.view.PopupMenuUtil;
import com.rent.kris.easyrent.util.Base64Util;
import com.rent.kris.easyrent.util.Common;
import com.rent.kris.easyrent.util.RealPathUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

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
    private FirstFragment2 firstFragment;
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
        firstFragment = FirstFragment2.getInstance(true);
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


    private String pickPhotoName;
    private String takePhotoName;
    private String photoFileName;
    private static final int REQUEST_PICK_IMAGE = 10086;
    private static final int PDD_PLAY_SNAKE = REQUEST_PICK_IMAGE + 1;
    private static final int REQUEST_TAKE_PHOTO = PDD_PLAY_SNAKE + 1;
    //去拍照
    public void makePhoto(String funcName) {
        takePhotoName = funcName;
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    photoFileName = "img_" + System.currentTimeMillis() + ".jpeg";
                    File currentPhotoFile = new File(Common.getBasePath(MainActivity.this) + Common.TEMP_DIR, photoFileName);
                    photoFileName = currentPhotoFile.getAbsolutePath();
                    int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentApiVersion < 24) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentPhotoFile));
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, currentPhotoFile.getAbsolutePath());
                        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "请给予权限，谢谢", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //去系统相册
    public void pickPhoto(String funcName) {
        pickPhotoName = funcName;
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, REQUEST_PICK_IMAGE);
                } else {
                    Toast.makeText(MainActivity.this, "请给予权限，谢谢", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            //系统相册选取完成
            Uri uri = data.getData();
            if (uri != null) {
                String filePath;
                if (!TextUtils.isEmpty(uri.toString()) && uri.toString().startsWith("file")) {
                    filePath = uri.getPath();
                } else {
                    filePath = RealPathUtil.getRealPathFromURI(this, uri);
                }
                String base64Image = Base64Util.encodeBase64ImageFile(filePath);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("image64", base64Image);
                jsonObject.addProperty("message", "图片获取成功");
                Log.d("WebViewActivity", "jsonObject:" + jsonObject);
//                mWebView.loadUrl("javascript:sdk_nativeCallback(\'" + pickPhotoName + "\',\'" + jsonObject + "\')");
            }

        } else if (requestCode == PDD_PLAY_SNAKE && resultCode == RESULT_OK) {
            //玩了一波蛇
            String funcName = data.getStringExtra("funcName");
            String someWord = data.getStringExtra("someWord");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("someWord", someWord);
//            mWebView.loadUrl("javascript:sdk_nativeCallback(\'" + funcName + "\',\'" + jsonObject + "\')");

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && null != photoFileName) {
            //拍照回来
            Log.d("WebViewActivity1", photoFileName);
            String base64Image = Base64Util.encodeBase64ImageFile(photoFileName);
            Log.d("WebViewActivity2", base64Image);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("image64", base64Image);
//            mWebView.loadUrl("javascript:sdk_nativeCallback(\'" + takePhotoName + "\',\'" + jsonObject + "\')");
        }
    }

}
