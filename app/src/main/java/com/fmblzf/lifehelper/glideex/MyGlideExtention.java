package com.fmblzf.lifehelper.glideex;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * 作者： fmblzf
 * 时间：2017/9/5
 * 描述：根据不同的业务需求添加自定义Glide扩展选项，或者自定义公共的方法设置对应的placeholder
 */
@GlideExtension
public final class MyGlideExtention {

    private static int DEFAULT_SIZE = 128;

    private MyGlideExtention(){}

    @GlideOption
    public static void defaultPlaceholder(RequestOptions options,int width,int height){
        options
                .override(width==0?DEFAULT_SIZE:width,height==0?DEFAULT_SIZE:height)
//                .placeholder(com.fmblzf.lifehelper.R.mipmap.placeholder)
//                .error(com.fmblzf.lifehelper.R.mipmap.error)
//                .fallback(com.fmblzf.lifehelper.R.mipmap.fallback)
        ;
    }


}
