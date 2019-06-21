package cn.pf.aaronp.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.pf.aaronp.R;

/**
 * author: aaron.pf
 * date: 2019/3/8 20:49.
 * desc:
 */

public class CircleLoadingView extends View {

    private Paint paint;
    private int countdownTimeTotal = 1000;
    private int defaultWidth = 150;
    private int defaultHeight = 225;
    private int outsideArcColor = Color.WHITE;
    private int insideArcColor = Color.WHITE;
    private float outsideArcWidth = 8f;
    private float insideArcWidth = 8f;
    private float outsideArcAngle = 330f;
    private float insideArcAngle = 60f;
    private float defaultStartAngle = 105;
    private float startAngle = defaultStartAngle;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
        countdownTimeTotal = typedArray.getInteger(R.styleable.CircleLoadingView_round_one_time, 1500);
        outsideArcColor = typedArray.getColor(R.styleable.CircleLoadingView_round_outside_color, Color.WHITE);
        insideArcColor = typedArray.getColor(R.styleable.CircleLoadingView_round_inside_color, Color.WHITE);
        outsideArcAngle = typedArray.getFloat(R.styleable.CircleLoadingView_round_outside_angle, 300f);
        insideArcAngle = typedArray.getFloat(R.styleable.CircleLoadingView_round_inside_angle, 60f);
        defaultStartAngle = typedArray.getFloat(R.styleable.CircleLoadingView_round_start_angle, 105);
        typedArray.recycle();

        init();
    }

    private CountDownTimer mCountDownTimer;

    private void init() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(countdownTimeTotal, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ratio = (float) millisUntilFinished / (float) countdownTimeTotal;
                float addAngle = 360 - 360 * ratio;
                startAngle = defaultStartAngle;
                startAngle = defaultStartAngle + addAngle;
                invalidate();
            }

            @Override
            public void onFinish() {
                if (mCountDownTimer != null) {
                    mCountDownTimer.start();
                }
            }
        };

        mCountDownTimer.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        //外圆
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outsideArcWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(outsideArcColor);
        Path path = new Path();
        path.addArc(10, 10, defaultWidth - 10, defaultHeight - 10, startAngle, outsideArcAngle);
        canvas.drawPath(path, paint);
        //内圆
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(insideArcWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(insideArcColor);
        canvas.drawArc(30 + outsideArcWidth, 30 + outsideArcWidth, defaultWidth - (30 + outsideArcWidth), defaultHeight - (30 + outsideArcWidth),
                (360 - startAngle), -insideArcAngle, false, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defaultWidth = dealReadSize(widthMeasureSpec, defaultWidth);
        defaultHeight = dealReadSize(heightMeasureSpec, defaultHeight);
        if (defaultHeight > defaultWidth) {
            defaultHeight = defaultWidth;
        } else {
            defaultWidth = defaultHeight;
        }
        setMeasuredDimension(defaultWidth, defaultHeight);
    }

    private int dealReadSize(int measureSpec, int defaultSize) {
        int result = 0;
        int model = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(defaultSize);
        switch (model) {
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(defaultSize, size);
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
        }
        return result;
    }

    public void setFinish() {
        if (mCountDownTimer != null) {
            mCountDownTimer.onFinish();
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
