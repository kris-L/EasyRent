package com.xw.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import com.xw.common.AppUtil;

import java.io.File;
import java.io.FileOutputStream;

public class PicassoHelper {
    public static void loadBitmap(Context context, String url, Target callback) {
        Picasso.with(context).load(url).into(new BaseTarget(callback));
    }

    public static void loadVehicleMediaImageView(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url)) {
            Picasso.with(context).load(defResId)
                    .resize(AppUtil.dip2px(context, 180), AppUtil.dip2px(context, 100))
                    .centerCrop().into(imageView);
            return;
        }
        Picasso.with(context).load(url)
                .resize(AppUtil.dip2px(context, 180), AppUtil.dip2px(context, 100))
                .centerCrop()
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadLeftTopCornerIcon(Context context, int resId, ImageView imageView, float percent) {
        loadCornerIcon(context, resId, imageView, new float[]{percent});
    }

    public static void loadCornerIcon(Context context, int resId, ImageView imageView, float[] percent) {
        Picasso.with(context).load(resId)
                .transform(new CornerTransform(percent)).into(imageView);
    }

    public static void loadCornerView(Context context, String url, ImageView imageView, float[] percent, int defResId) {
        if (TextUtils.isEmpty(url)) {
            loadCornerIcon(context, defResId, imageView, percent);
            return;
        }
        Picasso.with(context).load(url)
                .transform(new CornerTransform(percent)).placeholder(defResId)
                .error(defResId).into(imageView);
    }

    public static void loadRoundView(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            Picasso.with(context).load(defResId)
                    .transform(new RoundTransform())
                    .into(imageView);
            return;
        }
        Picasso.with(context).load(url)
                .transform(new RoundTransform())
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadCircleView(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            Picasso.with(context).load(defResId)
                    .transform(new CircleTransform())
                    .into(imageView);
            return;
        }
        Picasso.with(context).load(url)
                .transform(new CircleTransform())
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadRoundResizeView(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            Picasso.with(context).load(defResId)
                    .transform(new RoundTransform())
                    .into(imageView);
            return;
        }
        Picasso.with(context).load(url)
                .resize(AppUtil.dip2px(context, 100), AppUtil.dip2px(context, 100))
                .transform(new RoundTransform())
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadRoundRectView(Context context, String url, ImageView imageView, float cornerValue,float ratio, int defResId) {
        loadRoundRectView(context, url, imageView, cornerValue, ratio,false, defResId, true);
    }
    public static void loadScaleRoundRectView(Context context, String url, ImageView imageView, float cornerValue,float ratio, int defResId) {
        loadRoundRectView(context, url, imageView, cornerValue, ratio,true, defResId, true);
    }

    public static void loadRoundRectView(Context context, int resId, ImageView imageView, float cornerValue,float ratio) {
        loadRoundRectView(context, resId, imageView, cornerValue, ratio, true);
    }
    public static void loadScaleRoundRectView(Context context, int resId, ImageView imageView, float cornerValue,float ratio) {
        loadRoundRectView(context, resId, imageView, cornerValue, ratio, true);
    }

    public static void loadRoundRectView(Context context, int resId, ImageView imageView, float cornerValue,float ratio, boolean fromCache) {
        RequestCreator load = Picasso.with(context).load(resId);
        if (!fromCache) {
            load = load.noFade();
        }
        load.transform(new RoundRectTransform(cornerValue,ratio)).into(imageView);
    }

    public static void loadRoundRectView(Context context, String url, ImageView imageView, float cornerValue, int defResId) {
        loadRoundRectView(context, url, imageView, cornerValue, defResId, true);
    }

    public static void loadRoundRectView(Context context, String url, ImageView imageView, int defResId) {
        loadRoundRectView(context, url, imageView, 0, defResId, true);
    }

    public static void loadRoundRectView(Context context, String url, ImageView imageView, float cornerValue, int defResId, boolean fromCache) {
        loadRoundRectView(context, url, imageView, cornerValue,0, false,defResId, fromCache);
    }

    public static void loadRoundRectView(Context context, String url, ImageView imageView, float cornerValue, float ratio, int defResId, boolean fromCache) {
        loadRoundRectView(context, url, imageView, cornerValue, ratio,false, defResId, fromCache);
    }
    public static void loadRoundRectView(Context context, String url, ImageView imageView, float cornerValue, float ratio, boolean isScale, int defResId, boolean fromCache) {
        if (TextUtils.isEmpty(url)) {
            RequestCreator load = Picasso.with(context).load(defResId);
            if (!fromCache) {
                load = load.noFade();
            }
            load.transform(new RoundRectTransform(cornerValue,ratio,isScale))
                    .placeholder(defResId)
                    .error(defResId)
                    .into(imageView);
            return;
        }
        RequestCreator load = Picasso.with(context).load(url);
        if (!fromCache) {
            load = load.noFade();
        }
        load.transform(new RoundRectTransform(cornerValue,ratio,isScale))
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadCoachPhotosThumbnail(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Picasso.with(context).load(url)
                .resize(AppUtil.dip2px(context, 48), AppUtil.dip2px(context, 48))
                .centerCrop()
                .into(imageView);
    }

    public static void loadIntoView(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defResId);
            return;
        }
        Picasso.with(context).load(url).placeholder(defResId)
                .error(defResId).into(imageView);
    }

    public static void loadUserAvatar(Context context, String url, ImageView imageView, int defResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defResId);
            return;
        }
        Picasso.with(context)
                .load(url)
                .transform(new CircleTransform())
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    public static void loadUserAvatar(Context context, File file, ImageView imageView, int defResId) {
        if (file == null || TextUtils.isEmpty(file.getAbsolutePath())) {
            imageView.setImageResource(defResId);
            return;
        }
        Picasso.with(context)
                .load(file)
                .transform(new CircleTransform())
                .placeholder(defResId)
                .error(defResId)
                .into(imageView);
    }

    protected static class BaseTarget implements Target {

        private Target callback;

        public BaseTarget(Target callback) {
            this.callback = callback;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (callback != null) {
                callback.onBitmapLoaded(bitmap, from);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            if (callback != null) {
                callback.onBitmapFailed(errorDrawable);
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (callback != null) {
                callback.onPrepareLoad(placeHolderDrawable);
            }
        }
    }

    public static class DownloadImageTarget implements Target{

        private String savePath;

        public DownloadImageTarget(String savePath) {
            this.savePath = savePath;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            File saveFile = new File(savePath);
            File parentFile = saveFile.getParentFile();
            if(parentFile.exists() || parentFile.mkdirs()){
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(saveFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.close();
                    onDownloadSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    onDownloadFailed();
                }
            }else{
                onDownloadFailed();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

        public void onDownloadSuccess(){

        }

        public void onDownloadFailed(){

        }
    }

    private static class CircleTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                //释放
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    private static class RoundTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                //释放
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            paint.setAntiAlias(true);

            float r = size * 0.15f;
            canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), r, r, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(squaredBitmap, 0, 0, paint);
            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "round";
        }
    }

    private static class RoundRectTransform implements Transformation {
        private float cornerValue = 0f;
        private float ratio = 4 / 3f;
        private boolean isScale;

        RoundRectTransform() {
        }

        RoundRectTransform(float cornerValue) {
            this.cornerValue = cornerValue;
        }

        RoundRectTransform(float cornerValue,float ratio) {
            this(cornerValue,ratio,false);
        }
        RoundRectTransform(float cornerValue,float ratio,boolean isScale) {
            this.cornerValue = cornerValue;
            if(ratio != 0) {
                this.ratio = ratio;
            }
            this.isScale = isScale;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int min = (int) Math.min(source.getWidth() / ratio, source.getHeight()); //取宽高比例 4:3
            int height = min;
            int width = (int) (min * ratio);
            Bitmap squaredBitmap;
            if(isScale) {
                int x = source.getWidth() - width;
                int y = source.getHeight() - height;
                squaredBitmap = Bitmap.createBitmap(source, x, y, width, height);
            }else {
               squaredBitmap = Bitmap.createScaledBitmap(source,width, height,false);
            }
            if (squaredBitmap != source) {
                //释放
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(squaredBitmap.getWidth(), squaredBitmap.getHeight(), source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            paint.setAntiAlias(true);

            float r = min * 0.1f;
            r = r > 15 ? 15 : r;
            if (cornerValue > 0) {
                r = cornerValue;
            }
            canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), r , r *  ratio, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(squaredBitmap, 0, 0, paint);
            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "roundrect";
        }
    }

    private static class CornerTransform implements Transformation {

        private float[] percent = new float[4];

        public CornerTransform(float[] percent) {
            int len = this.percent.length < percent.length ? this.percent.length : percent.length;
            System.arraycopy(percent, 0, this.percent, 0, len);
        }

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap des = source;
            float topLeft = percent[0];
            if (topLeft > 0) {
                des = transform(des, new float[]{topLeft, 0, 0, 0});
            }
            float topRight = percent[1];
            if (topRight > 0) {
                des = transform(des, new float[]{0, topRight, 0, 0});
            }
            float bottomLeft = percent[2];
            if (bottomLeft > 0) {
                des = transform(des, new float[]{0, 0, bottomLeft, 0});
            }
            float bottomRight = percent[3];
            if (bottomRight > 0) {
                des = transform(des, new float[]{0, 0, 0, bottomRight});
            }
            return des;
        }

        public Bitmap transform(Bitmap source, float[] percent) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                //释放
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            paint.setAntiAlias(true);

            float topLeft = size * percent[0];
            if (topLeft > 0) {
                canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth() + topLeft, bitmap.getHeight() + topLeft), topLeft, topLeft, paint);
            }
            float topRight = size * percent[1];
            if (topRight > 0) {
                canvas.drawRoundRect(new RectF(0 - topRight, 0, bitmap.getWidth(), bitmap.getHeight() + topRight), topRight, topRight, paint);
            }
            float bottomLeft = size * percent[2];
            if (bottomLeft > 0) {
                canvas.drawRoundRect(new RectF(0, 0 - bottomLeft, bitmap.getWidth() + bottomLeft, bitmap.getHeight()), bottomLeft, bottomLeft, paint);
            }
            float bottomRight = size * percent[3];
            if (bottomRight > 0) {
                canvas.drawRoundRect(new RectF(0 - bottomRight, 0 - bottomRight, bitmap.getWidth(), bitmap.getHeight()), bottomRight, bottomRight, paint);
            }

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(squaredBitmap, 0, 0, paint);
            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "cornerRect";
        }
    }

}
