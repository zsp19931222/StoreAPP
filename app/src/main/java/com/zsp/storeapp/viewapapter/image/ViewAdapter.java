package com.zsp.storeapp.viewapapter.image;


import android.graphics.Color;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.zsp.storeapp.glide.GlideLoadUtils;


/**
 * Created by andy on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes", "radius"}, requireAll = false)
    public static void setImageUri(ImageView imageView, Object url, int placeholderRes, int radius) {
        //使用Glide框架加载图片
        if (radius>0) {
            GlideLoadUtils.getInstance().glideLoad(imageView.getContext(), url, imageView, placeholderRes, radius);
        }else {
            GlideLoadUtils.getInstance().glideLoad(imageView.getContext(), url, imageView, placeholderRes);
        }
    }
    @BindingAdapter(value = {"imageTint"}, requireAll = false)
    public static void setImageTint(ImageView imageView, String imageTint){
        imageView.setColorFilter(Color.parseColor(imageTint));
    }
}

