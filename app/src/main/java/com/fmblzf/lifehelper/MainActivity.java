package com.fmblzf.lifehelper;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fmblzf.annotion.utils.FmblzfViewUtils;
import com.fmblzf.annotion.view.FmblzfEvent;
import com.fmblzf.annotion.view.FmblzfEventType;
import com.fmblzf.annotion.view.FmblzfLayout;
import com.fmblzf.annotion.view.FmblzfView;
import com.fmblzf.picture.module.GlideApp;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.centerInsideTransform;
import static com.bumptech.glide.request.RequestOptions.option;

@FmblzfLayout(layoutResId = R.layout.glide_layout)
public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    String imageurl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504249607532&di=ef4ff344ad1a403d3ed78a160ab9c5a5&imgtype=0&src=http%3A%2F%2Fwww.51wendang.com%2Fpic%2F3e3c22ccdffb1567678b659b%2F1-1088-jpg_6_0_______-709-0-0-709.jpg";

    String gifUrl = "http://img.zcool.cn/community/01f9e7572c5d7c32f875a399e373fa.gif";

    String bigurl = "http://img3.imgtn.bdimg.com/it/u=1549765377,3134764962&fm=214&gp=0.jpg";

    @FmblzfView(viewId = R.id.imageview)
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FmblzfViewUtils.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504844323&di=7eb855117e7ef66187a548fdb6f65ccf&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.inlur.com%2Fwp-content%2Fdata%2Fattachment%2Fforum%2F2014%2F11%2F13%2F89f21752bd9f5f3101e04f75c5003ea3.jpg";
//        GlideApp.with(this).load(url) .override(400)
//                .placeholder(com.fmblzf.lifehelper.R.mipmap.placeholder)
//                .error(com.fmblzf.lifehelper.R.mipmap.error)
//                .fallback(com.fmblzf.lifehelper.R.mipmap.fallback)
//                .transition(DrawableTransitionOptions.withCrossFade(1000))
//                .into(imageView);

        Glide.with(this).load(bigurl)
                .apply(new RequestOptions().transform(new RoundedCorners(10)).override(100))
                .into(imageView)
        ;

    }


    public void useTransformations(){
        Glide.with(this)
                .load(imageurl)
                .apply(new RequestOptions().transform(new MultiTransformation(new FitCenter(),new CenterCrop())))
                .into(imageView);
        GlideApp.with(this)
                .load(imageurl)
                .transforms(new Transformation[]{new FitCenter(),new CenterCrop()})
                .into(imageView);
    }


    public void useCompenentOptions(){
        Glide.with(this)
                .load(imageurl)
                .apply(option(MyCustomModelLoader.TIMEOUT_MS, 1000L))
                .into(imageView);
    }

    boolean isclick = false;


    @FmblzfEvent(viewId = R.id.imageview,eventType = FmblzfEventType.CLICK)
    public void imgeClick(View View){
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504249607532&di=ef4ff344ad1a403d3ed78a160ab9c5a5&imgtype=0&src=http%3A%2F%2Fwww.51wendang.com%2Fpic%2F3e3c22ccdffb1567678b659b%2F1-1088-jpg_6_0_______-709-0-0-709.jpg";
        if (isclick){
            url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504844323&di=7eb855117e7ef66187a548fdb6f65ccf&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.inlur.com%2Fwp-content%2Fdata%2Fattachment%2Fforum%2F2014%2F11%2F13%2F89f21752bd9f5f3101e04f75c5003ea3.jpg";
        }
        Glide.with(this).load(url).apply(centerCropTransform()).into(imageView);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Glide.with(this).load(url).apply(new RequestOptions().circleCrop().autoClone()).into(imageView);
        isclick = !isclick;
    }

    @FmblzfEvent(viewId = R.id.button,eventType = FmblzfEventType.CLICK)
    public void clearClick(View view){
        Glide.with(this).clear(imageView);
        GlideApp.with(this).asMyGif().load(gifUrl).into(imageView);
    }

    @FmblzfEvent(viewId = R.id.addpic,eventType = FmblzfEventType.CLICK)
    public void addpic(View view){
//        GlideApp.with(this).load(imageurl).circleCrop().into(imageView);
        GlideApp.with(this).load(imageurl).miniThumb(400).circleCrop().into(imageView);
    }

}
