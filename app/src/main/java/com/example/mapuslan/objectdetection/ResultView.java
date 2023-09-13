package com.example.mapuslan.objectdetection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class ResultView extends View {

    private final static int TEXT_X = 40;
    private final static int TEXT_Y = 35;
    private final static int TEXT_WIDTH = 260;
    private final static int TEXT_HEIGHT = 50;

    private Paint mPaintRectangle;
    private Paint mPaintText;
    private ArrayList<Result> mResults;

    public ResultView(Context context) {
        super(context);
    }

    public ResultView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaintRectangle = new Paint();
        mPaintText = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mResults == null) return;
        for (Result result : mResults) {
            mPaintRectangle.setStrokeWidth(5);
            mPaintRectangle.setStyle(Paint.Style.STROKE);
            canvas.drawRect(result.rect, mPaintRectangle);

            // added colors per class
            int backgroundColor = Color.BLACK;
            int bboxColor = Color.BLACK;
            if (result.classIndex == 0) {   // glass
                backgroundColor = Color.BLUE;
                bboxColor = Color.BLUE;
            } else if (result.classIndex == 1 ) { // paper
                backgroundColor = Color.RED;
                bboxColor = Color.RED;
            } else if (result.classIndex == 2 ) { // cardboard
                backgroundColor = Color.DKGRAY;
                bboxColor = Color.DKGRAY;
            } else if (result.classIndex == 3 ) { // plastic
                backgroundColor = Color.rgb(139,64,0);
                bboxColor = Color.rgb(139,64,0);
            } else if (result.classIndex == 4 ) { // metal
                backgroundColor = Color.CYAN;
                bboxColor = Color.CYAN;
            } else if (result.classIndex == 5 ) { // non-recyclable
                backgroundColor = Color.MAGENTA;
                bboxColor = Color.MAGENTA;
            } else if (result.classIndex == 6 ) { // biodegradable
                backgroundColor = Color.GREEN;
                bboxColor = Color.GREEN;
            }

            Path mPath = new Path();
            RectF mRectF = new RectF(result.rect.left, result.rect.top, result.rect.left + TEXT_WIDTH,  result.rect.top + TEXT_HEIGHT);
            mPath.addRect(mRectF, Path.Direction.CW);

            // added
            // Set the background color for text
            mPaintText.setColor(backgroundColor);
            canvas.drawPath(mPath, mPaintText);

            // Draw bounding box with the matched color
            mPaintRectangle.setColor(bboxColor);
            canvas.drawRect(result.rect, mPaintRectangle);

//            mPaintText.setColor(Color.MAGENTA);
//            canvas.drawPath(mPath, mPaintText);

            mPaintText.setColor(Color.WHITE);
            mPaintText.setStrokeWidth(0);
            mPaintText.setStyle(Paint.Style.FILL);
            mPaintText.setTextSize(32);
            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, result.rect.top + TEXT_Y, mPaintText);
        }
    }

    public void setResults(ArrayList<Result> results) {
        mResults = results;
    }
}
