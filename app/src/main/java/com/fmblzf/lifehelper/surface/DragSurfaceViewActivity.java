package com.fmblzf.lifehelper.surface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;

import com.fmblzf.lifehelper.R;

import fmblzf.surfaceview.view.DragImgView;

/**
 * 作者： fmblzf
 * 时间：2017/10/9
 * 描述：通过SurfaceView实现拖拽效果
 */

public class DragSurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bg);

        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.ll_layout);

        DragImgView imgView = new DragImgView(this);
        linearLayout.addView(imgView);

//        setContentView(imgView);
    }

}
