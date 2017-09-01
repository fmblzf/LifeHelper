package com.fmblzf.mvp.view.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fmblzf.mvp.R;

/**
 * Created by fmblzf on 2017/8/28.
 */

@SuppressLint("AppCompatCustomView")
public class CircularImageView extends ImageView {

    private static final String TAG = CircularImageView.class.getSimpleName();

    /** 上下文内容 */
    private Context mContext;

    /** 默认值 */
    private static final float BORDER_WIDTH = 0.0f;
    private static final int BORDER_COLOR = Color.TRANSPARENT;
    private static final float CORNER_RADIUS = 0.0f;

    /** 定义画笔 */
    private Paint mPaint;
    /** 定义画笔的宽度 */
    private float borderWidth = BORDER_WIDTH;
    /** 定义画笔的颜色 */
    private int borderColor = BORDER_COLOR;
    /** 圆角的角度大小 */
    private float radius = CORNER_RADIUS;


    public CircularImageView(Context context) {
        super(context);
        initWidget(context,null,-1);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context,attrs,defStyleAttr);
    }

    /**
     * 初始化控件
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initWidget(Context context,AttributeSet attrs, int defStyleAttr){
        this.mContext = context;
        //自定义属性获取
        if (attrs != null && defStyleAttr != -1){
            initAttrs(attrs);
        }

        mPaint = new Paint();
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        mPaint.setAntiAlias(true);
    }

    /**
     * 获取配置属性值（如果是xml配置view,执行该方法）
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.CircularImageView);
        borderColor = typedArray.getColor(R.styleable.CircularImageView_borderColor,BORDER_COLOR);
        borderWidth = typedArray.getDimension(R.styleable.CircularImageView_borderWidth,BORDER_WIDTH);
        radius = typedArray.getDimension(R.styleable.CircularImageView_radius,CORNER_RADIUS);
        typedArray.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Drawable drawable = getDrawable();
//        if (drawable != null){
//            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//            Bitmap roundBitmap = canvasDrawable(bitmap);
//            Rect rect = new Rect(getLeft(),getTop(),roundBitmap.getWidth()+getLeft(),roundBitmap.getHeight()+getTop());
//            mPaint.reset();
//            canvas.drawBitmap(roundBitmap,rect,rect,mPaint);
////            canvas.save();
//        }else{
//            super.onDraw(canvas);
//        }
    }

    private Bitmap canvasDrawable(Bitmap bitmap){
        //创建一个位图实例
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        //创建区域大小
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        //创建画布的区域
        RectF rectF = new RectF(rect);
        //设置画笔
        mPaint.setColor(borderColor);
        //清空画布，全透明
        canvas.drawARGB(0,0,0,0);
        //然后在画布上画出圆角的矩形区域
        canvas.drawRoundRect(rectF,radius,radius,mPaint);
        //
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //将原图片画在画布上
        canvas.drawBitmap(bitmap,rect,rect,mPaint);

        return outBitmap;
    }
}
