package com.chencc.androidstudynotescode.view_dispatch;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomSRL2 extends SwipeRefreshLayout {
    private static final String TAG = "CustomSRL2";
    public CustomSRL2(Context context) {
        super(context);
    }

    public CustomSRL2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int startX = 0;
    private int startY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            super.onInterceptTouchEvent(ev);
//            return false;
//        }
//        return true;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                int deltax = x - startX;
                int deltay = y - startY;
                if (Math.abs(deltax) > Math.abs(deltay)){
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
