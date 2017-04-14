package com.hr.rippleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RippleProgress extends View {

    public static final String DEFAULT_COLOR = "004D40";
    Paint paint;
    int index[] = {99, 99, 99};
    boolean start[] = {true, false, false};
    int increments[] = {1, 2, 3};
    String baseColor = DEFAULT_COLOR;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            index[0] -= increments[0];
            if (start[1]) {
                index[1] -= increments[1];
            }
            if (start[2]) {
                index[2] -= increments[2];
            }

            for (int i = 0; i < index.length; i++) {
                if (index[i] <= 0) {
                    index[i] = 99;
                }

            }
            if (index[0] == 50) {
                start[1] = true;
            }

            if (index[0] == 33) {
                start[2] = true;
            }

            update();
        }
    };

    public RippleProgress(Context context) {
        super(context);
        init();
    }

    public RippleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleProgress, 0, 0);
        int color = a.getColor(R.styleable.RippleProgress_rippleBaseColor, Color.DKGRAY);
        baseColor = String.format("#%06X", 0xFFFFFF & color);
        baseColor = baseColor.substring(1);
        init();
    }

    public RippleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleProgress, defStyleAttr, 0);
        int color = a.getColor(R.styleable.RippleProgress_rippleBaseColor, Color.DKGRAY);
        baseColor = String.format("#%06X", 0xFFFFFF & color);
        baseColor = baseColor.substring(1);
        init();
    }

    public void update() {
        invalidate();
        postDelayed(runnable, 30);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        float centerX = Math.round(canvasWidth * 0.5f);
        float centerY = Math.round(canvasHeight * 0.5f);

        paint.setColor(Color.parseColor(getColor(index[0])));

        canvas.drawCircle(centerX, centerY, getRadius(canvasWidth, canvasHeight, index[0]), paint);

        if (start[1]) {
            paint.setColor(Color.parseColor(getColor(index[1])));

            canvas.drawCircle(centerX, centerY, getRadius(canvasWidth, canvasHeight, index[1]), paint);
        }

        if (start[2]) {
            paint.setColor(Color.parseColor(getColor(index[2])));

            canvas.drawCircle(centerX, centerY, getRadius(canvasWidth, canvasHeight, index[2]), paint);
        }

    }

    private float getRadius(int canvasWidth, int canvasHeight, int index) {
        float radius = canvasWidth > canvasHeight ? canvasHeight / 2 : canvasWidth / 2;
        radius = radius * (100 - index) / 100;
        return radius;
    }

    private String getColor(int index) {
        String alpha = Integer.toHexString((int) (2.56 * index));
        if (alpha.length() == 1) {
            alpha = "0" + alpha;
        }
        String color = "#" + alpha + baseColor;
        return color;
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#" + baseColor));
    }


}
