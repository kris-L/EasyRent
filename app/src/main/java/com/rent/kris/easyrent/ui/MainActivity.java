package com.rent.kris.easyrent.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.adapter.SelectModuleAdapter;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.entity.UploadResult;
import com.rent.kris.easyrent.event.LogOutEvent;
import com.rent.kris.easyrent.event.UploadSuccessEvent;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.base.BaseActivity;
import com.rent.kris.easyrent.ui.base.CommonFragment;
import com.rent.kris.easyrent.ui.dialog.SelectModuleDialog;
import com.rent.kris.easyrent.ui.photopick.ImageInfo;
import com.rent.kris.easyrent.ui.photopick.PhotoPickActivity;
import com.rent.kris.easyrent.ui.view.BottomBar;
import com.rent.kris.easyrent.util.Base64Util;
import com.rent.kris.easyrent.util.CommonUtils;
import com.rent.kris.easyrent.util.RealPathUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.common.AppToast;
import com.xw.common.prefs.LoginInfoPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rent.kris.easyrent.ui.photopick.PhotoPickActivity.PHOTO_MAX_COUNT;

public class MainActivity extends BaseActivity {

    @BindView(R.id.center_img)
    ImageView mCenterImage;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.fl_tab_container)
    FrameLayout fl_tab_container;

    private static final String TAG_FRAG_FIRST = "app:fragment:first";
    private static final String TAG_FRAG_SECOND = "app:fragment:second";
    private static final String TAG_FRAG_THIRD = "app:fragment:third";
    private static final String TAG_FRAG_FOURTH = "app:fragment:fourth";

    private static final String TAG_FRAG_FIFTH = "app:fragment:fifth";
    private static final String TAG_FRAG_SIXTH = "app:fragment:sixth";
    private static final String TAG_FRAG_SEVENTH = "app:fragment:seventh";
    private static final String TAG_FRAG_EIGHTH = "app:fragment:eighth";
    private static final String TAG_FRAG_COMMON_31 = "app:fragment:common31";
    private static final String TAG_FRAG_COMMON_32 = "app:fragment:common32";
    private static final String TAG_FRAG_COMMON_33 = "app:fragment:common33";
    private static final String TAG_FRAG_COMMON_34 = "app:fragment:common34";
    private static final String TAG_FRAG_COMMON_41 = "app:fragment:common41";
    private static final String TAG_FRAG_COMMON_42 = "app:fragment:common42";
    private static final String TAG_FRAG_COMMON_43 = "app:fragment:common43";
    private static final String TAG_FRAG_COMMON_44 = "app:fragment:common44";
    private static final String TAG_FRAG_COMMON_51 = "app:fragment:common51";
    private static final String TAG_FRAG_COMMON_52 = "app:fragment:common52";
    private static final String TAG_FRAG_COMMON_53 = "app:fragment:common53";
    private static final String TAG_FRAG_COMMON_54 = "app:fragment:common54";
    private static final String TAG_FRAG_COMMON_61 = "app:fragment:common61";
    private static final String TAG_FRAG_COMMON_62 = "app:fragment:common62";
    private static final String TAG_FRAG_COMMON_63 = "app:fragment:common63";
    private static final String TAG_FRAG_COMMON_64 = "app:fragment:common64";

    private Context mContext;
    private FirstFragment2 firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FouthFragment fouthFragment;

    private FifthFragment fifthFragment;
    private SixthFragment sixthFragment;
    private SeventhFragment seventhFragment;
    private EighthFragment eighthFragment;

    private CommonFragment commonFragment31;
    private CommonFragment commonFragment32;
    private CommonFragment commonFragment33;
    private CommonFragment commonFragment34;

    private CommonFragment commonFragment41;
    private CommonFragment commonFragment42;
    private CommonFragment commonFragment43;
    private CommonFragment commonFragment44;

    private CommonFragment commonFragment51;
    private CommonFragment commonFragment52;
    private CommonFragment commonFragment53;
    private CommonFragment commonFragment54;

    private CommonFragment commonFragment61;
    private CommonFragment commonFragment62;
    private CommonFragment commonFragment63;
    private CommonFragment commonFragment64;


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
        EventBus.getDefault().register(this);
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

        String urlStr = "http://app.tit306.com/appa/food/index.html";
        String title = "美食";
        commonFragment31 = CommonFragment.newInstance(urlStr,title);
        urlStr = "http://app.tit306.com/appa/farmers/index.html";
        title = "农产品";
        commonFragment41 = CommonFragment.newInstance(urlStr,title);
        urlStr = "http://app.tit306.com/appa/beauty/index.html";
        title = "美容美发";
        commonFragment51 = CommonFragment.newInstance(urlStr,title);

        urlStr = "http://app.tit306.com/appa/bbs/";
        title = "论坛";
        commonFragment61 = CommonFragment.newInstance(urlStr,title);
        urlStr = "http://app.tit306.com/appa/bbs/index.php/search";
        title = "论坛";
        commonFragment62 = CommonFragment.newInstance(urlStr,title);
        urlStr = "http://app.tit306.com/appa/bbs/weibo";
        title = "论坛";
        commonFragment63 = CommonFragment.newInstance(urlStr,title);
        urlStr = "http://app.tit306.com/appa/bbs/index.php/article";
        title = "论坛";
        commonFragment64 = CommonFragment.newInstance(urlStr,title);



        currentFragmentTag = TAG_FRAG_FIRST;
        transaction.add(fragmentContainerId(), firstFragment, TAG_FRAG_FIRST).commit();

        mBottomBar.setOnBottombarOnclick(new BottomBar.OnBottonbarClick() {
            @Override
            public void onFirstClick() {
                selectIndex = 1;
                mBottomBar.setBottonTabView(tabType, selectIndex);
                switchContent(tabType, selectIndex);
            }

            @Override
            public void onSecondClick() {
                selectIndex = 2;
                mBottomBar.setBottonTabView(tabType, selectIndex);
                switchContent(tabType, selectIndex);
            }

            @Override
            public void onThirdClick() {
                selectIndex = 3;
                mBottomBar.setBottonTabView(tabType, selectIndex);
                switchContent(tabType, selectIndex);
            }

            @Override
            public void onFouthClick() {
                selectIndex = 4;
                mBottomBar.setBottonTabView(tabType, selectIndex);
                switchContent(tabType, selectIndex);
            }

            @Override
            public void onCenterClick() {
                showSelectDialog();
            }
        });
    }



    public SelectModuleDialog dialog;
    private void showSelectDialog() {
         dialog = new SelectModuleDialog(this,tabType,new SelectModuleAdapter.OnItemViewClickListener() {

            @Override
            public void onImageClick(int position) {
                dialog.dismiss();
                if(position == 0){
                    tabType = Constant.TYPE_TAB_EASY_HOME;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }else if(position == 1){
                    tabType = Constant.TYPE_APP_EASY_LIFE;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }else if(position == 2){
                    tabType = Constant.TYPE_TAB_EASY_CATE;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }else if(position == 3){
                    tabType = Constant.TYPE_APP_EASY_BEAUTY;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }else if(position == 4){
                    tabType = Constant.TYPE_TAB_EASY_FARM;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }else if(position == 5){
                    tabType = Constant.TYPE_APP_EASY_FORUM;
                    mBottomBar.setBottonTabView(tabType, selectIndex);
                    switchBottomTab();
                    switchContent(tabType, selectIndex);
                }
            }
        });
        dialog.show();
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
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_FIFTH)) {
            return fifthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_SIXTH)) {
            return sixthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_SEVENTH)) {
            return seventhFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_EIGHTH)) {
            return eighthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_31)) {
            return commonFragment31;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_32)) {
            return sixthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_33)) {
            return seventhFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_34)) {
            return eighthFragment;
        }else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_41)) {
            return commonFragment41;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_42)) {
            return sixthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_43)) {
            return seventhFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_44)) {
            return eighthFragment;
        }else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_51)) {
            return commonFragment51;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_52)) {
            return sixthFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_53)) {
            return seventhFragment;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_54)) {
            return eighthFragment;
        }else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_61)) {
            return commonFragment61;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_62)) {
            return commonFragment62;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_63)) {
            return commonFragment63;
        } else if (TextUtils.equals(currentFragmentTag, TAG_FRAG_COMMON_64)) {
            return commonFragment64;
        }else {
            return firstFragment;
        }

    }

    private void switchContent(int tabType, int selectIndex) {
        if (tabType == Constant.TYPE_TAB_EASY_HOME) {
            switch (selectIndex) {
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
        } else if (tabType == Constant.TYPE_APP_EASY_LIFE) {
            switch (selectIndex) {
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
        } else if (tabType == Constant.TYPE_TAB_EASY_CATE){
            switch (selectIndex) {
                case 1:
                    switchContentFragment(getCurrFragment(), commonFragment31, TAG_FRAG_COMMON_31);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), sixthFragment, TAG_FRAG_COMMON_32);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), seventhFragment, TAG_FRAG_COMMON_33);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), eighthFragment, TAG_FRAG_COMMON_34);
                    break;
            }
        } else if (tabType == Constant.TYPE_APP_EASY_BEAUTY){
            switch (selectIndex) {
                case 1:
                    switchContentFragment(getCurrFragment(), commonFragment41, TAG_FRAG_COMMON_41);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), sixthFragment, TAG_FRAG_COMMON_42);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), seventhFragment, TAG_FRAG_COMMON_43);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), eighthFragment, TAG_FRAG_COMMON_44);
                    break;
            }
        } else if (tabType == Constant.TYPE_TAB_EASY_FARM){
            switch (selectIndex) {
                case 1:
                    switchContentFragment(getCurrFragment(), commonFragment51, TAG_FRAG_COMMON_51);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), sixthFragment, TAG_FRAG_COMMON_52);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), seventhFragment, TAG_FRAG_COMMON_53);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), eighthFragment, TAG_FRAG_COMMON_54);
                    break;
            }
        } else if (tabType == Constant.TYPE_APP_EASY_FORUM){
            switch (selectIndex) {
                case 1:
                    switchContentFragment(getCurrFragment(), commonFragment61, TAG_FRAG_COMMON_61);
                    break;
                case 2:
                    switchContentFragment(getCurrFragment(), commonFragment62, TAG_FRAG_COMMON_62);
                    break;
                case 3:
                    switchContentFragment(getCurrFragment(), commonFragment63, TAG_FRAG_COMMON_63);
                    break;
                case 4:
                    switchContentFragment(getCurrFragment(), commonFragment64, TAG_FRAG_COMMON_64);
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
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fl_tab_container, "rotationY", 0f, 360f);
        objectAnimator.setDuration(800);
        objectAnimator.start();
    }


    private String pickPhotoName;
    private String takePhotoName;
    private String photoFileName;
    private static final int REQUEST_PICK_IMAGE = 10086;
    private static final int PDD_PLAY_SNAKE = REQUEST_PICK_IMAGE + 1;
    private static final int REQUEST_TAKE_PHOTO = PDD_PLAY_SNAKE + 1;
    public static final int RESULT_TAKE_IMAGE1 = 1024;

    //去拍照
    public void makePhoto(String funcName) {
        takePhotoName = funcName;
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    photoFileName = "img_" + System.currentTimeMillis() + ".jpeg";
                    File currentPhotoFile = new File(CommonUtils.getBasePath(MainActivity.this) + CommonUtils.TEMP_DIR, photoFileName);
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

    public String typeStr ="";
    public String sonpathStr="";
    public String newnameStr="";
    public String timestampStr="";
    private ArrayList<ImageInfo> pickImages = new ArrayList<>();

    public void uploadImages(String type, String sonpath, String newname, String timestamp) {
        typeStr = type;
        sonpathStr = sonpath;
        newnameStr = newname;
        timestampStr = timestamp;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pickPhoto();
            }
        });

    }

    public void onCallPhone(final String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    //去系统相册
    public void pickPhoto() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                    startActivityForResult(intent, REQUEST_PICK_IMAGE);

                    Intent intent = new Intent(MainActivity.this, PhotoPickActivity.class);
                    intent.putExtra(PhotoPickActivity.EXTRA_MAX, PHOTO_MAX_COUNT);
                    startActivityForResult(intent, MainActivity.RESULT_TAKE_IMAGE1);
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
                List<String> pathList = new ArrayList<>();
                    pathList.add(filePath);
                uploadPicS(pathList);
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
        }else if(requestCode == RESULT_TAKE_IMAGE1 ){
            if (resultCode == Activity.RESULT_OK) {
                try {
                    ArrayList<ImageInfo> pickPhots = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
                    pickImages.addAll(pickPhots);
                    List<String> pathList = new ArrayList<>();
                    for (ImageInfo item : pickPhots) {
                        pathList.add(item.path);
                    }
                    uploadPicS(pathList);
                } catch (Exception e) {
                    Log.e("lsz",e+"");
                }
            }
        }

    }

    private void uploadPicS(List<String> pathList) {
        if (pathList == null || pathList.size() == 0) {
            Log.e("lsz", "未选择图片");
            return;
        }
        String key = UserProfilePrefs.getInstance().getUserToken();
        String userName = LoginInfoPrefs.getInstance(this).getUserName();
        AppModel.model().uploadPicS(key, userName,
                pathList, typeStr, sonpathStr, newnameStr, timestampStr,
                new Callback<UploadResult>() {
                    @Override
                    public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                        Log.e("lsz", "上传成功");
//                        mWebView.loadUrl("javascript:uploadSuccess()");
                        EventBus.getDefault().post(new UploadSuccessEvent("上传成功"));
                    }

                    @Override
                    public void onFailure(Call<UploadResult> call, Throwable t) {
                        Log.e("lsz", "上传失败");
                    }
                });
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                AppToast.makeText(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LogOutEvent messageEvent) {
        finish();
    }

}
