package com.chencc.androidstudynotescode.view_dispatch;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class CustomVPInner extends ViewPager {
    private static final String TAG = "CustomVPInner";
    private float startX;
    private float startY;
    private float x;
    private float y;
    private float deltaX;
    private float deltaY;

    public CustomVPInner(Context context) {
        super(context);
    }

    public CustomVPInner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = ev.getX();
//                startY = ev.getY();
//                ViewCompat.setNestedScrollingEnabled(this, true);
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                x = ev.getX();
//                y = ev.getY();
//                deltaX = Math.abs(x - startX);
//                deltaY = Math.abs(y - startY);
//                if (deltaX < deltaY) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
