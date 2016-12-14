package com.example.admin.custom_frame;

import android.view.MotionEvent;

public interface TouchInterface {
    boolean onDown(MotionEvent event);
    boolean onTouchEvent(MotionEvent event);
    boolean onFling(MotionEvent event1, MotionEvent event2, float x, float y);
    void onLongPress(MotionEvent event);
    void onShowPress(MotionEvent event);
    boolean onSingleTapUp(MotionEvent event);
}
