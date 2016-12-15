package com.example.admin.custom_frame;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.info(this, "onDown: " + e.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtil.info(this, "onFling: " + e1+e2);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtil.info(this, "onLongPress: " + e);
        super.onLongPress(e);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtil.info(this, "onShowPress: " + e);
        super.onShowPress(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.info(this, "onSingleTapUp: " + e.toString());
        return true;
    }
}

