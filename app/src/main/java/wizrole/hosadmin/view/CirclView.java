package wizrole.hosadmin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import wizrole.hosadmin.R;

/**
 * Created by liushengping on 2018/3/13.
 * 何人执笔？
 * 自定义进度圈
 */

public class CirclView  extends View {
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private Paint mArcPaint,mArcPaintt;
    private Paint mArcPaint2,mArcPaint2t;
    private int mCircleX;
    private int mCircleY;
    private float mCurrentAngle,mCurrentAngle2;
    private RectF mArcRectF ,mArcRectF2,mArcRectFF,mArcRectF2F;
    private float mStartSweepValue,mStartSweepValue2;
    private float mTargetPercent;
    private float mCurrentPercent;

    private int mRadius,mRadius2;
    private int mCircleBackground;
    private int mRingColor;
    private int mTextSize;
    private int mTextColor;
    public CirclView(Context context, AttributeSet attrs , int defStyle) {
        super(context, attrs,defStyle);
        //自定义属性 values/attr
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PercentageRing);
        //中间圆的背景颜色  默认为浅紫色
        mCircleBackground = typedArray.getColor(R.styleable.PercentageRing_circleBackground, 0xffafb4db);
        //外圆环的颜色  默认为深紫色
        mRingColor = typedArray.getColor(R.styleable.PercentageRing_ringColor, 0xff6950a1);
        //中间圆的半径 默认为60
        mRadius = typedArray.getInt(R.styleable.PercentageRing_radius, 60);
        mRadius2=mRadius-15;//跟外圆环相差5
        //字体颜色 默认为白色
        mTextColor = typedArray.getColor(R.styleable.PercentageRing_textColor, 0xffffffff);
        //最后一定要调用这个 释放掉TypedArray
        typedArray.recycle();
        //初始化数据
        init(context);
    }
    public CirclView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }
    public CirclView(Context context) {
        this(context, null);
    }
    private void init(Context context){
        //圆环开始角度 -90° 正北方向
        mStartSweepValue = 0;
        //圆环开始角度 -90° 正北方向
        mStartSweepValue2 = -180;
        //当前角度
        mCurrentAngle = 0;
        //覆盖圆环的角度
        mCurrentAngle2=0;
        //当前百分比
        mCurrentPercent = 0;
        //设置中心园的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleBackground);
        mCirclePaint.setStyle(Paint.Style.FILL);
        //设置文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth((float) (0.025 * mRadius));
        mTextPaint.setTextSize(mRadius/2);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //设置外圆环的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mRingColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth((float) (0.075 * mRadius));
        //设置外圆环的画笔
        mArcPaintt = new Paint();
        mArcPaintt.setAntiAlias(true);
        mArcPaintt.setColor(Color.GRAY);
        mArcPaintt.setStyle(Paint.Style.STROKE);
        mArcPaintt.setStrokeWidth((float) (0.075 * mRadius));
        //设置内圆环的画笔
        mArcPaint2 = new Paint();
        mArcPaint2.setAntiAlias(true);
        mArcPaint2.setColor(Color.RED);//默认红色
        mArcPaint2.setStyle(Paint.Style.STROKE);
        mArcPaint2.setStrokeWidth((float) (0.075*mRadius2));
        //设置外圆环的画笔
        mArcPaint2t = new Paint();
        mArcPaint2t.setAntiAlias(true);
        mArcPaint2t.setColor(Color.GRAY);
        mArcPaint2t.setStyle(Paint.Style.STROKE);
        mArcPaint2t.setStrokeWidth((float) (0.075 * mRadius2));
        //获得文字的字号 因为要设置文字在圆的中心位置
        mTextSize = (int) mTextPaint.getTextSize();


    }

    //主要是测量wrap_content时候的宽和高，因为宽高一样，只需要测量一次宽即可，高等于宽
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(widthMeasureSpec));
        //设置圆心坐标
        mCircleX = getMeasuredWidth()/2;
        mCircleY = getMeasuredHeight()/2;
        //如果半径大于圆心横坐标，需要手动缩小半径的值，否则就画到外面去了
        if (mRadius>mCircleX) {
            //设置半径大小为圆心横坐标到原点的距离
            mRadius = mCircleX;

            mRadius = (int) (mCircleX-0.075*mRadius);
            mRadius2=mRadius-5;
            // mRadius2= (int) (mCircleX-0.075*mRadius2);
            //因为半径改变了，所以要重新设置一下字体宽度
            mTextPaint.setStrokeWidth((float) (0.025 * mRadius));
            //重新设置字号
            mTextPaint.setTextSize(mRadius / 2);
            //重新设置外圆环宽度
            mArcPaint.setStrokeWidth((float) (0.075*mRadius));
            mArcPaint2.setStrokeWidth((float) (0.075 * (mRadius - 5)));
            mArcPaintt.setStrokeWidth((float) (0.075*mRadius));
            mArcPaint2t.setStrokeWidth((float) (0.075*(mRadius-5)));
            //重新获得字号大小
            mTextSize = (int) mTextPaint.getTextSize();
        }
        //画中心园的外接矩形，用来画圆环用
        mArcRectF  = new RectF(mCircleX-mRadius, mCircleY-mRadius, mCircleX+mRadius, mCircleY+mRadius);
        mArcRectF2 = new RectF(mCircleX-mRadius2, mCircleY-mRadius2, mCircleX+mRadius2, mCircleY+mRadius2);
        mArcRectFF  = new RectF(mCircleX-mRadius, mCircleY-mRadius, mCircleX+mRadius, mCircleY+mRadius);
        mArcRectF2F = new RectF(mCircleX-mRadius2, mCircleY-mRadius2, mCircleX+mRadius2, mCircleY+mRadius2);
    }

    //当wrap_content的时候，view的大小根据半径大小改变，但最大不会超过屏幕
    private int measure(int measureSpec){
        int result=0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }else {
            result =(int) (1.075*mRadius*2);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;

    }
    //开始画中间圆、文字和外圆环
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画中间圆
        //     canvas.drawCircle(mCircleX, mCircleY, mRadius, mCirclePaint);
        //画圆环
        canvas.drawArc(mArcRectF, mStartSweepValue, mCurrentAngle, false, mArcPaint);
        //画内圆环
        canvas.drawArc(mArcRectF2, mStartSweepValue2 ,mCurrentAngle, false, mArcPaint2);
        //画文字
        //     canvas.drawText(String.valueOf(mCurrentPercent)+"%", mCircleX, mCircleY+mTextSize/4, mTextPaint);
        //判断当前百分比是否小于设置目标的百分比
        //  if (mCurrentPercent<mTargetPercent) {
        if (mCurrentPercent>=50.0f) {

            //画圆环
            canvas.drawArc(mArcRectFF, mStartSweepValue, mCurrentAngle2, false, mArcPaintt);
            //画内圆环
            canvas.drawArc(mArcRectF2F, mStartSweepValue2, mCurrentAngle2, false, mArcPaint2t);
            mCurrentAngle2+=3.6;
        }
        //当前百分比+1
        mCurrentPercent+=1;
        //当前角度+360
        mCurrentAngle+=3.6;
        if (mCurrentAngle>=360) {
            mCurrentPercent=0;
            mCurrentAngle = 0;
            mCurrentAngle2=0;
            mStartSweepValue=0;
        }
        //每10ms重画一次
        postInvalidateDelayed(10);
    }

    //设置目标的百分比
    public void setTargetPercent(int percent){
        this.mTargetPercent = percent;
    }
}
