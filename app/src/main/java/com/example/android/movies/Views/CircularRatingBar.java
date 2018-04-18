package com.example.android.movies.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.movies.R;

/**
 * Created by glm9637 on 18.03.2018 12:00.
 */

public class CircularRatingBar extends View {

    private int mWidht;
    private int mHeight;

    private double mRatingPercent;

    private int mArcColor;
    private int mTextColor;

    private Paint mPaint;

    public CircularRatingBar(Context context) {
        super(context);
        init();
    }

    public CircularRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        mTextColor = ContextCompat.getColor(getContext(), R.color.primary_text);
    }

    /**
     * Set the percent Rating of the RatingBar
     * @param ratingPercent The Rating as percental value
     */
    public void setRatingPercent(Double ratingPercent){
        mRatingPercent = ratingPercent;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight = getHeight();
        mWidht = getWidth();

        drawArc(canvas);
        drawText(canvas);
    }

    /**
     * Draws the Arc representing the Rating, beginning at the Top
     */
    private void drawArc(Canvas canvas) {
        int diameter = Math.min(mWidht, mHeight);
        float pad = 5;
        RectF outerOval = new RectF(pad, pad, diameter - pad, diameter - pad);


        mPaint.setColor(mArcColor);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(outerOval,-90, (float) (3.6*mRatingPercent),false,mPaint);

    }

    /**
     * Draws the Text representation of the Rating inside the arc
     */
    private void drawText(Canvas canvas) {
        mPaint.setTextSize(Math.min(mWidht, mHeight) / 5f);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);

        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;

        canvas.drawText(mRatingPercent + "%", xPos, yPos, mPaint);
    }
}
