package com.example.admin.custom_frame;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class FrameParent extends FrameLayout implements View.OnTouchListener, ParentInterface {

    private static final String TAG = "FrameParent";
    private View touchView;
    private View imgDelete;
    private Pair<Float, Float> oldCoordinate;

    private GestureDetectorCompat mDetector;

    public FrameParent(Context context) {
        super(context);
        init();
    }

    public FrameParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FrameParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        imgDelete = findViewById(R.id.imgDelete);
        imgDelete.setVisibility(GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //LogUtil.info(this, "Tag: "+view.getTag());
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchView = view;
                oldCoordinate = new Pair<>(motionEvent.getX(), motionEvent.getY());
                imgDelete.setVisibility(VISIBLE);
                removeView(imgDelete);
                addView(imgDelete);
                return false;
            case MotionEvent.ACTION_UP:
                touchView = null;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (touchView != null) {
                float mX = motionEvent.getX() - oldCoordinate.first;
                float mY = motionEvent.getY() - oldCoordinate.second;

                touchView.setX(touchView.getX()+mX);
                touchView.setY(touchView.getY()+mY);

                oldCoordinate = new Pair<>(motionEvent.getX(), motionEvent.getY());
                touchView.forceLayout();

                imgDelete.setX(touchView.getX());
                imgDelete.setY(touchView.getY());
                imgDelete.forceLayout();

//                    touchView.setRotation(  - вращение
//                imgDelete.getMeasuredWidth()    -

                return true;
                }
        }

        return mDetector.onTouchEvent(motionEvent);
    }


    @Override
    public void addMobileView(View view) {
        view.setOnTouchListener(this);
        view.setTag("My "+getChildCount());
        addView(view);
    }

    @Override
    public void deleteMobileView(View view) {
        view.setOnTouchListener(this);
    }

    @Override
    public int countMobileView() {
        return 0;
    }
}
