package com.example.applicantend_se_fyp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {


    private int screenWidth, screenHeight, myViewPaddingLeft, myViewPaddingTop;
    private int  langLine = 100,
            shortLine = 50,
            origin = 0;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMyParams(int screenWidth, int screenHeight, int myViewPaddingLeft, int myViewPaddingTop) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.myViewPaddingLeft = myViewPaddingLeft;
        this.myViewPaddingTop = myViewPaddingTop;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(250);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawLine(origin, origin, langLine, origin, paint);
        canvas.drawLine(origin, origin, origin, shortLine, paint);
        canvas.drawLine(origin, screenHeight - myViewPaddingTop, langLine, screenHeight - myViewPaddingTop, paint);
        canvas.drawLine(origin, screenHeight - myViewPaddingTop, origin, screenHeight - myViewPaddingTop - shortLine, paint);

        canvas.drawLine(screenWidth - myViewPaddingLeft, origin, screenWidth - myViewPaddingLeft - langLine, origin, paint);
        canvas.drawLine(screenWidth - myViewPaddingLeft, origin, screenWidth - myViewPaddingLeft, shortLine, paint);

        canvas.drawLine(screenWidth - myViewPaddingLeft, screenHeight - myViewPaddingTop, screenWidth - myViewPaddingLeft - langLine, screenHeight - myViewPaddingTop, paint);
        canvas.drawLine(screenWidth - myViewPaddingLeft, screenHeight - myViewPaddingTop, screenWidth - myViewPaddingLeft, screenHeight - myViewPaddingTop - shortLine, paint);
        super.onDraw(canvas);
    }
}
