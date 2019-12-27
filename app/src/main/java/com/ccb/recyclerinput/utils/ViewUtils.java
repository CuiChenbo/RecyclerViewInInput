package com.ccb.recyclerinput.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.ccb.recyclerinput.R;

public class ViewUtils {
    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    private static int screenWidth = 0;
    public static int getScreenWidth(Context context) {
        if (0 == screenWidth)
            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        return screenWidth;
    }


    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    public static void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        // * other https://www.jianshu.com/p/28f5bcee409f
        DrawableCrossFadeFactory drawableCrossFadeFactory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .transition(DrawableTransitionOptions.withCrossFade(drawableCrossFadeFactory))
                .into(imageView);
    }
}
