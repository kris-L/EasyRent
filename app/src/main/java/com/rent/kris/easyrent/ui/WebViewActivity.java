package com.rent.kris.easyrent.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rent.kris.easyrent.BuildConfig;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.entity.ShareInfo;
import com.rent.kris.easyrent.entity.UploadResult;
import com.rent.kris.easyrent.event.GpsNotify;
import com.rent.kris.easyrent.event.UploadSuccessEvent;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.dialog.ExamineMoreDialog;
import com.rent.kris.easyrent.util.CommonUtils;
import com.rent.kris.easyrent.util.JavaAndJSBridge;
import com.rent.kris.easyrent.util.RealPathUtil;
import com.rent.kris.easyrent.util.TakePhotoUtils;
import com.rent.kris.easyrent.web.WebViewSettings;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xw.common.prefs.LoginInfoPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {

    private static String TAG = "WebViewActivity";

    private static final int REQUEST_PICK_IMAGE = 10086;
    private static final int PDD_PLAY_SNAKE = REQUEST_PICK_IMAGE + 1;
    private static final int REQUEST_TAKE_PHOTO = PDD_PLAY_SNAKE + 1;
    private WebView mWebView;
    private String pickPhotoName;
    private String takePhotoName;
    private String photoFileName;

    private Context mContext;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);
        mContext = this;
        initWebView();
        EventBus.getDefault().register(this);
    }

    private void initWebView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getIntent().getStringExtra("title"));
        findViewById(R.id.iv_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView = (WebView) findViewById(R.id.m_web_view);
        WebViewSettings.config(mWebView.getSettings());
        mWebView.addJavascriptInterface(new JavaAndJSBridge(mWebView, this, jsListener), "App");

        mWebView.setWebViewClient(mWebViewClient);
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            // 处理定位权限请求
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    public WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //6.0以下执行
        }

        //处理网页加载失败时
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //6.0以上执行
        }
    };


    public void onPickPhoto() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RxPermissions(WebViewActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent, REQUEST_PICK_IMAGE);
                        } else {
                            Toast.makeText(WebViewActivity.this, "请给予权限，谢谢", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void onMakePhoto() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RxPermissions(WebViewActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_TAKE_PHOTO);

//                                try {
//                                    photoUri = TakePhotoUtils.takePhoto(WebViewActivity.this, REQUEST_TAKE_PHOTO);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }

                        } else {
                            Toast.makeText(WebViewActivity.this, "请给予权限，谢谢", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    public String typeStr, sonpathStr, newnameStr, timestampStr;
    public Uri photoUri;
    @SuppressLint("CheckResult")
    public JavaAndJSBridge.OnJSCallBack jsListener = new JavaAndJSBridge.OnJSCallBack() {

        @Override
        public void uploadImage(String type, String sonpath, String newname, String timestamp) {
            typeStr = type;
            sonpathStr = sonpath;
            newnameStr = newname;
            timestampStr = timestamp;
            showMoreDialog();
        }

        @Override
        public void uploadImages(String type, String sonpath, String newname, String timestamp) {
            typeStr = type;
            sonpathStr = sonpath;
            newnameStr = newname;
            timestampStr = timestamp;
            showMoreDialog();
        }

        @Override
        public void onLoginNotify() {
            LoginActivity.intentTo(mContext);
            finish();
        }

        @Override
        public void onCallPhone(final String phone) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onModuleSelected(int index) {

        }

        @Override
        public void onWechatShare(final ShareInfo data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String title = data.title;
                    String desc = data.desc;
                    String icon_url = data.icon_url;
                    String web_url = data.web_url;

                    UMImage image = new UMImage(WebViewActivity.this, icon_url);
                    UMWeb web = new UMWeb(web_url);
                    web.setTitle(title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(desc);//描述

                    new ShareAction(WebViewActivity.this).withText(title)
                            .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .open();
                }
            });
        }

        @Override
        public void onGpsNotify() {
            if(MainActivity.latLng != null){
                EventBus.getDefault().post(new GpsNotify());
            }else{
                Log.e(TAG,"定位数据为空");
            }
        }
    };

    UMShareListener umShareListener = new UMShareListener(){
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onStart");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onResult");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e(TAG, "分享"+"onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e(TAG, "分享"+"onCancel");
        }
    };

    private void showMoreDialog() {
        ExamineMoreDialog dialog = new ExamineMoreDialog(this);
        dialog.setOnItemClickListener(new ExamineMoreDialog.onItemClickListener() {
            @Override
            public void onTakePhotos() {
                onMakePhoto();
            }

            @Override
            public void onPhotoAlbum() {
                onPickPhoto();
            }
        });
        dialog.show();
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
                uploadPic(filePath);
//                mWebView.loadUrl("javascript:sdk_nativeCallback(\'" + pickPhotoName + "\',\'" + jsonObject + "\')");
            }
        } else if (requestCode == PDD_PLAY_SNAKE && resultCode == RESULT_OK) {
            //玩了一波蛇
            String funcName = data.getStringExtra("funcName");
            String someWord = data.getStringExtra("someWord");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("someWord", someWord);
            mWebView.loadUrl("javascript:sdk_nativeCallback(\'" + funcName + "\',\'" + jsonObject + "\')");
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(WebViewActivity.this, "点击取消从相册选择", Toast.LENGTH_LONG).show();
                return;
            }
            saveCameraImage(data);
        }
    }

    private void saveCameraImage(Intent data) {
        // 检查sd card是否存在
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
            Log.e("lsz", "sd card is not avaiable/writeable right now.");
            return;
        }
        String fileName = "";
        if (data == null) { //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            if (ContentResolver.SCHEME_FILE.equals(photoUri.getScheme())) {
                fileName = photoUri.getPath();
            }
        } else {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            String filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "EasyRent";
            fileName = filePath + "Pic_" + System.currentTimeMillis() + ".jpg";

            // 保存文件
            FileOutputStream fos = null;
            File file = new File(filePath);
            //判断文件夹是否存在,如果不存在则创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
            try {// 写入SD card
                fos = new FileOutputStream(fileName);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }// 显示图片
        }
        uploadPic(fileName);
    }

    private void uploadPic(String imgPath) {
        Log.e("lsz","imgPath = "+ imgPath);
        if (TextUtils.isEmpty(imgPath)) {
            return;
        }
        String key = UserProfilePrefs.getInstance().getUserToken();
        String userName = LoginInfoPrefs.getInstance(this).getUserName();
        AppModel.model().uploadPic(key, userName,
                imgPath, typeStr, sonpathStr, newnameStr, timestampStr,
                new Callback<UploadResult>() {
                    @Override
                    public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                        Log.e("lsz", "上传成功");
                        mWebView.loadUrl("javascript:uploadSuccess()");
                    }

                    @Override
                    public void onFailure(Call<UploadResult> call, Throwable t) {
                        Log.e("lsz", "上传失败");
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(GpsNotify messageEvent) {
        Log.e(TAG,"GpsNotify mWebView ="+mWebView);
        if(mWebView != null){
            String location = MainActivity.latLng.latitude+";"+MainActivity.latLng.longitude;
            Log.e(TAG,"Event GpsNotify location ="+location);
            mWebView.loadUrl("javascript:setGpsLocation(\""+location+"\")");
        }
    }


}
