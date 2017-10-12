package fmblzf.surfaceview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import fmblzf.surfaceview.R;

public class DragImgView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener,Runnable {


        /**
         *  实现拖拽，那么就必须实现touch事件，根据touch事件的三种状态分别处理，拖拽前后的显示样式
         *
         *
         *
         */
        private boolean isInit = false;

        private Context mContext;
        private SurfaceHolder mSurfaceViewHolder;
        private Paint mPaint;
        private Paint mClearPaint;//清屏画笔
        private Bitmap mBitmap;
        //当前的拖拽是否仍然继续，如果继续那么就重画，否则就不重画样式
        private boolean running = true;

        //图片大小
        private Rect rect;
        //图片资源
        private int imgId;
        //背景颜色
        private int bgColor;
        //图片资源的位置
        private int left = 0, top = 0;
        //是否点击在图片上
        private boolean canDrag = false;
        //点击点距离左上角的距离
        int offsetX = 0,offsetY = 0;


        /**
         *
         * @param context
         */
        public DragImgView(Context context) {
            this(context,null);
        }

        public DragImgView(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public DragImgView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            //初始化样式
            initWidget(context, attrs);
        }

        /**
         * 初始化样式
         * @param context
         * @param attrs
         */
        private void initWidget(Context context, AttributeSet attrs) {
//            setBackgroundColor(Color.GRAY);
            this.mContext = context;
            this.mSurfaceViewHolder = getHolder();
            this.mSurfaceViewHolder.addCallback(this);
            this.setOnTouchListener(this);
            if (attrs != null) {
                //根据xml配置获取对应的属性值
//                TypedArray array = context.obtainStyledAttributes()
            }
            //图片资源
            imgId = R.drawable.ic_launcher;
            //背景颜色
            bgColor = Color.argb(0,0,0,0);
            //初始化工具类和资源类
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), imgId);
            rect = new Rect(left,top,left+mBitmap.getWidth(),top+mBitmap.getHeight());
            mClearPaint = new Paint();
            mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //清屏结束后，如果仍然使用该对象进行绘制，需要重新设置属性
//            mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            running = true;
            new Thread(this).start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            running = false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if (rect.contains(x,y)){
                        canDrag = true;
                        offsetX = x - rect.left;
                        offsetY = y - rect.top;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (canDrag){
                        rect.left = (int) (event.getX()-offsetX);
                        rect.top = (int) (event.getY()-offsetY);
                        rect.right = rect.left + mBitmap.getWidth();
                        rect.bottom = rect.top + mBitmap.getHeight();
                        if (rect.left < 0){
                            rect.left = 0;
                            rect.right = rect.left + mBitmap.getWidth();
                        }
                        if (rect.top < 0){
                            rect.top = 0;
                            rect.bottom = rect.top + mBitmap.getHeight();
                        }
                        if (rect.right > getMeasuredWidth()){
                            rect.right = getMeasuredWidth();
                            rect.left = getMeasuredWidth() - mBitmap.getWidth();
                        }
                        if (rect.bottom > getMeasuredHeight()){
                            rect.bottom = getMeasuredHeight();
                            rect.top = getMeasuredHeight() - mBitmap.getHeight();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    canDrag = false;
                    break;
                default:
                    break;
            }
            return true;
        }

        @Override
        public void run() {
            while (running){
                boolean isOperate = !isInit || canDrag;
                if (isOperate) {
                    isInit = true;
                    Canvas canvas = mSurfaceViewHolder.lockCanvas();
                    //canvas.drawColor(bgColor);
                    //清屏
                    //canvas.drawPaint(mClearPaint);

                    canvas.drawBitmap(mBitmap, rect.left, rect.top, null);
//                    canvas.save();
                    mSurfaceViewHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        super.onDraw(canvas);
    }

    @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            params.height = 500;
            params.width = 500;
            super.setLayoutParams(params);
            Log.e("DragImgView","setLayoutParams");
        }

//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(500, 500);
//            Log.e("DragImgView","onMeasure");
//        }
//
//        @Override
//        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//            super.onLayout(changed, left, top, left+500, top+500);
//            Log.e("DragImgView","onLayout");
//        }
}