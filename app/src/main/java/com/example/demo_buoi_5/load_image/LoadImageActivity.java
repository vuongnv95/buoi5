package com.example.demo_buoi_5.load_image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_buoi_5.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import static android.widget.Toast.LENGTH_LONG;

public class LoadImageActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_load_image);
        loadImage();
    }

    private void loadImage() {
        String url = "http://img.blogtamsu.vn/2015/11/ngoc-trinh-blogtamsuvn-38.jpg";
        // Cấu hình tùy chọn hiển thị ảnh
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        // Khởi tại đối tượng imageLoader với cấu hình ta đã set vên trên
        // bạn cũng có thể sử dụng config mặc định bằng cách
        // imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        //download ảnh và show ImageView tại đây
        imageLoader.loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Toast.makeText(getApplicationContext(), "onLoadingStarted", LENGTH_LONG).show();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Toast.makeText(getApplicationContext(), "onLoadingFailed", LENGTH_LONG).show();

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Toast.makeText(getApplicationContext(), "onLoadingComplete", LENGTH_LONG).show();
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Toast.makeText(getApplicationContext(), "onLoadingCancelled", LENGTH_LONG).show();
            }
        });
        imageLoader.displayImage(url, imageView);
    }
}
