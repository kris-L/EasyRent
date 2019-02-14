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
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rent.kris.easyrent.R;
import com.rent.kris.easyrent.api.AppModel;
import com.rent.kris.easyrent.entity.UploadResult;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.dialog.ExamineMoreDialog;
import com.rent.kris.easyrent.util.CommonUtils;
import com.rent.kris.easyrent.util.JavaAndJSBridge;
import com.rent.kris.easyrent.util.RealPathUtil;
import com.rent.kris.easyrent.util.TakePhotoUtils;
import com.rent.kris.easyrent.web.WebViewSettings;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.common.prefs.LoginInfoPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 10086;
    private static final int PDD_PLAY_SNAKE = REQUEST_PICK_IMAGE + 1;
    private static final int REQUEST_TAKE_PHOTO = PDD_PLAY_SNAKE + 1;
    private WebView mWebView;
    private String pickPhotoName;
    private String takePhotoName;
    private String photoFileName;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);
        mContext = this;
        initWebView();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getIntent().getStringExtra("title"));
        findViewById(R.id.iv_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView = (WebView) findViewById(R.id.m_web_view);
        mWebView.setWebViewClient(mWebViewClient);
        WebViewSettings.config(mWebView.getSettings());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new JavaAndJSBridge(mWebView, this, jsListener), "App");

        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        //将约定好的空js文件替换为本地的
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            if (url == null) {
                return webResourceResponse;
            }
//            if (url.endsWith("native-app.js")) {
//                try {
//                    webResourceResponse = new WebResourceResponse("text/javascript", "UTF-8", WebViewActivity.this.getAssets().open("local.js"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            return webResourceResponse;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, request);
            if (request == null) {
                return webResourceResponse;
            }
            String url = request.getUrl().toString();
//            if (url != null && url.endsWith("native-app.js")) {
//                try {
//                    webResourceResponse = new WebResourceResponse("text/javascript", "UTF-8", WebViewActivity.this.getAssets().open("local.js"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            return webResourceResponse;
        }
    };

    public String typeStr, sonpathStr, newnameStr, timestampStr;
    public Uri photoUri;
    @SuppressLint("CheckResult")
    public JavaAndJSBridge.OnJSCallBack jsListener = new JavaAndJSBridge.OnJSCallBack() {

        @Override
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


        @Override
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

        @Override
        public void uploadImage(String type, String sonpath, String newname, String timestamp) {
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
    };

    private void showMoreDialog() {
        ExamineMoreDialog dialog = new ExamineMoreDialog(this);
        dialog.setOnItemClickListener(new ExamineMoreDialog.onItemClickListener() {
            @Override
            public void onTakePhotos() {
                jsListener.onMakePhoto();
            }

            @Override
            public void onPhotoAlbum() {
                jsListener.onPickPhoto();
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

}
