package com.fmblzf.picture.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import javax.xml.parsers.SAXParserFactory;

/**
 * 作者： fmblzf
 * 时间：2017/9/1
 * 描述：自定义AppGlideModule-创建新的Api,根据@GlideModule中默认的API名称“GlideApp”可以直接使用，并且将RequestOptions直接链式调用
 *      编译时自动生成对应的GlideApp类
 */
@GlideModule
public class FmblzfAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
//        registry.replace()
    }
}
