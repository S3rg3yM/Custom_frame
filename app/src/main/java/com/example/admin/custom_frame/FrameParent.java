package com.example.admin.custom_frame;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class FrameParent extends FrameLayout implements View.OnTouchListener, ParentInterface {

    private static final String TAG = "FrameParent";
    private MobileView touchView;
    private View imgDelete;
    private View imgResize;
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
        imgResize = findViewById(R.id.imgResize);
        imgResize.setVisibility(GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //LogUtil.info(this, "Tag: "+view.getTag());
        if (view instanceof MobileView) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (touchView!=null ) {
                        touchView.setActive(false);
                    }
                    touchView = (MobileView) view;
                    touchView.setActive(true);
                    removeView(touchView);
                    addView(touchView);
                    oldCoordinate = new Pair<>(motionEvent.getX(), motionEvent.getY());

                    imgDelete.setVisibility(VISIBLE);
                    removeView(imgDelete);
                    addView(imgDelete);

                    imgResize.setVisibility(VISIBLE);
                    removeView(imgResize);
                    addView(imgResize);

                    return true;
                case MotionEvent.ACTION_UP:
                    //touchView = null;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (touchView != null) {
                        float mX = motionEvent.getX() - oldCoordinate.first;
                        float mY = motionEvent.getY() - oldCoordinate.second;

                        touchView.setX(touchView.getX() + mX);
                        touchView.setY(touchView.getY() + mY);

                        oldCoordinate = new Pair<>(motionEvent.getX(), motionEvent.getY());
                        //touchView.forceLayout();

                        imgDelete.setX(touchView.getX() - imgDelete.getWidth() / 2);
                        imgDelete.setY(touchView.getY() - imgDelete.getHeight() / 2);
                        //imgDelete.forceLayout();
                        imgResize.setX(touchView.getX() + touchView.getWidth() - imgResize.getWidth() / 2);
                        imgResize.setY(touchView.getY() + touchView.getHeight() - imgResize.getHeight() / 2);

                        touchView.forceLayout();
//                touchView.setRotation(  - вращение
//                imgDelete.getMeasuredWidth()
//  -
                        return true;
                    }
            }
        }

        return mDetector.onTouchEvent(motionEvent);
    }

    @Override
    public void addMobileView(MobileView view) {
        view.setOnTouchListener(this);
        view.setTag("My "+getChildCount());
        addView(view);
    }

    @Override
    public void deleteMobileView(MobileView view) {
        LogUtil.info(this, "View: "+view);
        if (view.isActive()) {
            imgResize.setVisibility(GONE);
            imgDelete.setVisibility(GONE);
        }
        view.setOnTouchListener(null);
        removeView(view);
    }

    @Override
    public MobileView getMobileViewByIndex(int index) {
        int mIndex=-1;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof MobileView) {
                mIndex++;
                if (index == mIndex) {
                    LogUtil.info(this, "mIndex: "+mIndex+" v: " +v);
                    return (MobileView) v;
                }
            }
        }

        return null;
//        ArrayList<View> mViews = new ArrayList<>();
//        for (int i = 0; i < countMobileView() ; i++) {
//            if(getChildAt(index) instanceof MobileView) {
//                mViews.add(getChildAt(i));
//            }
//        }
//        //TODO count
//        return (MobileView) mViews.get(index);
    }

    @Override
    public int countMobileView() {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof MobileView) {
                count++;
            }
        }
        return count;
    }
}
