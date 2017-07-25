package com.example.xdf.testrangtu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;

/**
 * Created by xdf on 2017/7/24.
 */

public class RangTuView extends View {
    Path path;
    Path path2;
    private Paint mPaint;//绘制线
    private Paint mPaint2;//绘制实心圆
    private Paint mPaint3;//绘制大圆
    private Paint mPaint4;//绘制圆弧

    float first_progess=5.1f;
    float sec_progess=7.1f;
    float rz=6.5f;
    public RangTuView(Context context) {
        super(context);
    }

    public RangTuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path=new Path();
        path2=new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.RED);
        mPaint2.setStrokeWidth(10);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setAntiAlias(true);
        mPaint2.setTextSize(45);

        mPaint3 = new Paint();
        mPaint3.setColor(Color.GRAY);
        mPaint3.setStrokeWidth(20);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setAntiAlias(true);

        mPaint4 = new Paint();
        mPaint4.setColor(Color.RED);
        mPaint4.setStrokeWidth(20);
        mPaint4.setStyle(Paint.Style.STROKE);
        mPaint4.setAntiAlias(true);
    }

    public RangTuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RangTuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(0,1000);
        //画线
        path.rLineTo(300,0);
        path.rLineTo(300,-150);
        path.rLineTo(320,0);
        //canvas.drawPath(path,mPaint);
        if(dx>=300){
            path2.addCircle(300,1000,25, Path.Direction.CCW);
        }
        if(dx>=600){
            path2.addCircle(600,850,25, Path.Direction.CCW);
        }
        canvas.drawPath(path2,mPaint2);
        Path dst = new Path();
        PathMeasure measure = new PathMeasure(path, true);
        measure.getSegment(0,dx, dst, true);
        canvas.drawPath(dst,mPaint);
        //画圆和圆弧
        if(dx>=920){
            canvas.drawCircle((920+90-40),850,90,mPaint3);
            RectF oval=new RectF(920-40,850-90,920+180-40,850+90);
            float  progress=proc_x/(220*1.0f);
            canvas.drawArc(oval,0,progress*220,false,mPaint4);
        }
        //画文字
        DecimalFormat df = new DecimalFormat("0.0");
        float pp=dx/(920*1.0f);
        canvas.drawText(df.format(pp*first_progess)+"",270,950,mPaint2);
        canvas.drawText(df.format(pp*sec_progess)+"",570,800,mPaint2);

        float pp2=proc_x/(220*1.0f);
        //画大圆的燃值
        float x=mPaint2.measureText(rz+"");
        float y=850 +(mPaint2.getFontMetrics().bottom - mPaint2.getFontMetrics().top)/2 - mPaint2.getFontMetrics().bottom;
        canvas.drawText(df.format(pp2*rz), 920+(90*2-x)/2-40,y,mPaint2);

    }
    int dx;
    public void startAnim(){
        ValueAnimator animator=ValueAnimator.ofInt(0,920);
        //animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx= (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAnim2();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
    int proc_x;
    public void startAnim2(){
        ValueAnimator animator=ValueAnimator.ofInt(0,220);
        //animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                proc_x= (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
