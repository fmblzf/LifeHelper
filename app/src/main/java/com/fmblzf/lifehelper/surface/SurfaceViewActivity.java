package com.fmblzf.lifehelper.surface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.fmblzf.lifehelper.R;

/**
 * 作者： fmblzf
 * 时间：2017/10/9
 * 描述：SurfaceView实现后台实时刷新GUI操作
 */

public class SurfaceViewActivity extends AppCompatActivity {

    private static final String TAG = "SurfaceViewActivity";

    //SurfaceView控件
    private SurfaceView mSurfaceView;
    //SurfaceView的控制器
    private SurfaceHolder mSurfaceViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview_layout);
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceview);
        mSurfaceViewHolder = mSurfaceView.getHolder();
        mSurfaceViewHolder.addCallback(new DoThings());
        Log.e(TAG,"onCreate");
    }

    /**
     * SurfaceView通过该接口实现类，实现SurfaceView的创建，变更，以及销毁的整个过程
     */
    private class DoThings implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
//            System.out.println("surfaceCreated");
            Log.e(TAG,"surfaceCreated");
            new Thread(){
                @Override
                public void run() {
                    while (true) {
                        Canvas canvas = mSurfaceViewHolder.lockCanvas();
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
                        Rect rect = new Rect((int)(Math.random()*100),(int)(Math.random()*100),(int)(Math.random()*500),(int)(Math.random()*500));
                        canvas.drawRect(rect,paint);
                        mSurfaceViewHolder.unlockCanvasAndPost(canvas);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            System.out.println("surfaceChanged");
            Log.e(TAG,"surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
//            System.out.println("surfaceDestroyed");
            Log.e(TAG,"surfaceDestroyed");
        }
    }

}
