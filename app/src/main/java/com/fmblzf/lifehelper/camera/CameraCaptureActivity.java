package com.fmblzf.lifehelper.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fmblzf.annotion.utils.FmblzfViewUtils;
import com.fmblzf.annotion.view.FmblzfEvent;
import com.fmblzf.annotion.view.FmblzfEventType;
import com.fmblzf.annotion.view.FmblzfLayout;
import com.fmblzf.annotion.view.FmblzfView;
import com.fmblzf.lifehelper.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者： fmblzf
 * 时间：2017/10/12
 * 描述：通过调用系统相机，实现拍照功能
 */
@FmblzfLayout(layoutResId = R.layout.camera_layout)
public class CameraCaptureActivity extends AppCompatActivity {

    private static final String TAG = "CameraCaptureActivity";

    //获取照相机预览图片的回调标记
    private static final int PREVIEW_CAMERA_RESULT_CODE = 1;
    //获取照相机原始图片的回调标记
    private static final int URIVIEW_CAMERA_RESULT_CODE = 2;

    @FmblzfView(viewId = R.id.cameraview)
    public ImageView mCameraView;
    //点击调用系统相机，返回预览图片，该预览图片是通过对象Bitmap来传递
    @FmblzfView(viewId = R.id.previewbutton)
    public Button previewPic;
    //点击调用系统相机，返回预览图片，该预览图片是通过对象Bitmap来传递
    @FmblzfView(viewId = R.id.uriviewbutton)
    public Button uriviewPic;

    //当前通过uri获取原始照片的时的uri路径
    Uri uri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FmblzfViewUtils.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FmblzfEvent(viewId = R.id.previewbutton,eventType = FmblzfEventType.CLICK)
    public void previewButton(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,PREVIEW_CAMERA_RESULT_CODE);
    }

    @FmblzfEvent(viewId = R.id.uriviewbutton,eventType = FmblzfEventType.CLICK)
    public void uriviewButton(View view){
        File image = CameraCaptureActivity.createImagePath();
        if (image == null){
            Log.d(TAG,"uri create failed");
            return ;
        }
        uri = Uri.fromFile(image);
        Log.d(TAG,uri.toString());
        Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //设置照片的额外输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,URIVIEW_CAMERA_RESULT_CODE);
    }

    @FmblzfEvent(viewId = R.id.open_gallery,eventType = FmblzfEventType.CLICK)
    public void openGallery(View view){
        Intent intent = new Intent(CameraCaptureActivity.this,GalleryScanActivity.class);
        intent.putExtra("test","测试");
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            Log.d(TAG,"cancel or other exception");
            return ;
        }
        if (requestCode == PREVIEW_CAMERA_RESULT_CODE){
            //获取的预览图
            Bitmap bitmap = data.getExtras().getParcelable("data");
            mCameraView.setImageBitmap(bitmap);
        }
        if (requestCode == URIVIEW_CAMERA_RESULT_CODE){
            //获取原始图片
            mCameraView.setImageURI(uri);
        }
    }

    public static File createImagePath(){
        String tempdate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
        String filename = "JPEG_" + tempdate + "_";
        try {
            File iamge = File.createTempFile(filename,".jpg", Environment.getExternalStorageDirectory());
            return iamge;
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG,e.toString());
        }
        return null;
    }
}
