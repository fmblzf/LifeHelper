package com.fmblzf.lifehelper.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fmblzf.annotion.utils.FmblzfViewUtils;
import com.fmblzf.annotion.view.FmblzfEvent;
import com.fmblzf.annotion.view.FmblzfEventType;
import com.fmblzf.annotion.view.FmblzfLayout;
import com.fmblzf.annotion.view.FmblzfView;
import com.fmblzf.lifehelper.R;

/**
 * 作者： fmblzf
 * 时间：2017/10/12
 * 描述：抓取Gallery中的照片
 */
@FmblzfLayout(layoutResId = R.layout.gallery_layout)
public class GalleryScanActivity extends AppCompatActivity {

    private static final String TAG = "GalleryScanActivity";
    //抓取gallery中照片的回调标记
    private static final int GALLERY_IMAGE_RESULT_CODE = 1;

    @FmblzfView(viewId = R.id.gallery_imageview)
    public ImageView galleryView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dd = this.getIntent().getCharSequenceExtra("test").toString();
        Toast.makeText(this,dd,Toast.LENGTH_LONG).show();
        try {
            FmblzfViewUtils.register(this);
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    @FmblzfEvent(viewId = R.id.gallery_button,eventType = FmblzfEventType.CLICK)
    public void galleryClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GALLERY_IMAGE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            Log.d(TAG,"cancel or other exception!");
            return ;
        }
        if (requestCode == GALLERY_IMAGE_RESULT_CODE){
            //抓取照片的回调函数的标记
            Uri uri = data.getData();
            Log.d(TAG,uri.toString());
            galleryView.setImageURI(uri);
        }
    }
}
