package fmblzf.surfaceview.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.PopupWindow;

/**
 * 作者： fmblzf
 * 时间：2017/10/9
 * 描述：
 */

public class FloatButton extends SurfaceView {

    //悬浮按钮的大小
    private int mWidth = 20;
    private int mHeight = 20;


    public FloatButton(Context context) {
        this(context,null);
    }

    public FloatButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context, attrs);
    }

    private void initWidget(Context context, AttributeSet attrs) {
        PopupWindow p = new PopupWindow();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, left+mWidth, top+mHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(mWidth, mHeight);
    }
}
