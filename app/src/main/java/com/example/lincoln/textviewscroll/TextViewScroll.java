package com.example.lincoln.textviewscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Lincoln on 2016-11-22.
 */

public class TextViewScroll extends TextView {
    private int x, y;
    // 滚动速度
    private int speed = 5;
    // 字幕从那边出来
    public static final int FROM_RIGHT = 0;
    public static final int FROM_LEFT = 1;
    public static final int FROM_TOP = 2;
    public static final int FROM_BOTTOM = 3;
    private int scrollType = FROM_RIGHT;
    public static final boolean START = true;
    public static final boolean STOP = false;
    private boolean scrollStatus = START;


    public TextViewScroll(Context context) {
        super(context);
    }

    public TextViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        x = getTextWidth();
        y = getTextHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        scrollType(scrollType);
        super.onDraw(canvas);
    }

    private void scrollType(int type) {
        if (scrollStatus) {
            switch (type) {
                case FROM_RIGHT:
                    // 右到左
                    if (x >= getTextWidth()) {
                        x = -getWidth();
                    }
                    scrollTo(x, 0);
                    x = x + speed;
                    postInvalidate();
                    break;
                case FROM_LEFT:
                    // 左到右
                    if (x <= -getWidth()) {
                        x = getTextWidth();
                    }
                    scrollTo(x, 0);
                    x = x - speed;
                    postInvalidate();
                    break;
                case FROM_TOP:
                    // 上到下
                    if (y <= -getHeight()) {
                        y = getTextHeight();
                    }
                    scrollTo(0, y);
                    y = y - speed;
                    postInvalidate();
                    break;
                case FROM_BOTTOM:
                    // 下到上
                    if (y >= getTextHeight()) {
                        y = -getHeight();
                    }
                    scrollTo(0, y);
                    y = y + speed;
                    postInvalidate();
                    break;

                default:
                    break;
            }
        }

    }

    // 获取字体行宽度
    private int getTextWidth() {
        int mTextWidth;
        Paint mPaint = getPaint();
        if (getLineCount() > 1) {
            // 如果有多行文字，则获取最长的一行文字宽度
            String[] lineContent = getText().toString().split("\n");
            int maxLine = 0, maxLineNumber = 0;
            for (int i = 0; i < lineContent.length; i++) {
                if (lineContent[i].length() > maxLine) {
                    maxLine = lineContent[i].length();
                    maxLineNumber = i;
                }
            }
            mTextWidth = (int) mPaint.measureText(lineContent[maxLineNumber]);
        } else {
            mTextWidth = (int) mPaint.measureText(getText().toString());
        }
        return mTextWidth;
    }

    // 获取字体总高度
    private int getTextHeight() {
        return getLineHeight() * getLineCount();
    }

    public int getScrollType() {
        return scrollType;
    }

    public void setScrollType(int scrollType) {
        this.scrollType = scrollType;
        setScrollStatus(START);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isScrollStatus() {
        return scrollStatus;
    }

    public void setScrollStatus(boolean scrollStatus) {
        this.scrollStatus = scrollStatus;
        postInvalidate();
    }
}
