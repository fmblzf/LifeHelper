package com.fmblzf.picture.extension;

import android.graphics.Bitmap;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

import static com.fmblzf.picture.module.GlideOptions.decodeTypeOf;

/**
 * 作者： fmblzf
 * 时间：2017/9/1
 * 描述：Glide的扩展类
 * 要求：1.有私有并且空构造器；
 *       2.类是不可被继承的final
 *       3.所有的方法都是静态的
 *       4.可能包含静态变量和引用其他类或者对象
 *       5.类必须被@GlideExtension标记，作为自定义扩展类
 *       6.静态方法必须被@GlideOption标记，作为自定义扩展选项RequestOptions
 *       7.@GlideType可以增加支持的资源类型（gif,svc etc）
 */
@GlideExtension
public final class FmblzfExtension {

//    private static final int MINI_THUMB_SIZE = 100;

    private static final RequestOptions DECODE_TYPE_GIF = decodeTypeOf(GifDrawable.class).lock();

    private FmblzfExtension(){}

    @GlideOption
    public static void miniThumb(RequestOptions options,int size){
        options.fitCenter().override(size);
    }
    @GlideOption
    public static void transforms(RequestOptions options, Transformation... transformation){
        options.transform(new MultiTransformation(transformation));
    }

    @GlideType(GifDrawable.class)
    public static void asMyGif(RequestBuilder<GifDrawable> requestBuilder){
        requestBuilder.transition(new DrawableTransitionOptions()).apply(DECODE_TYPE_GIF);
    }

}
