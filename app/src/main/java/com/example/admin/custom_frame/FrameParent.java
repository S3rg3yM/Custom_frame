package com.example.admin.custom_frame;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class FrameParent extends FrameLayout implements View.OnTouchListener, ParentInterface {

    private static final String TAG = "FrameParent";
    private MobileView touchView;
    private View imgDelete;
    private View imgResize;
    private Pair<Float, Float> oldCoordinate;
    float oldX, oldY;

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
        imgDelete.setOnTouchListener(this);
        imgDelete.setVisibility(GONE);
        imgResize = findViewById(R.id.imgResize);
        imgResize.setOnTouchListener(this);
        imgResize.setVisibility(GONE);
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //LogUtil.info(this, "Tag: "+view.getTag());
        mDetector.onTouchEvent(motionEvent);
        if (view instanceof MobileView) {
            return actionMobileView((MobileView) view, motionEvent);
            //else return mDetector.onTouchEvent(motionEvent);
        }

        switch (view.getId()) {
            case R.id.imgResize:

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        oldX = view.getX();
                        oldY = view.getY();

                        LogUtil.info(this,"imgResize_DOWN");
                        return true;
                    case MotionEvent.ACTION_UP:

                        LogUtil.info(this,"imgResize_UP");
                        return false;
                    case MotionEvent.ACTION_MOVE:

                        float differenceX = motionEvent.getX() - oldX;
                        float differenceY = motionEvent.getY() - oldY;

                        LogUtil.info(this, "differentX: " + differenceX);
                        LogUtil.info(this, "differentY: " + differenceY);

                        int vHight = touchView.getLayoutParams().height;
                        int vWidth = touchView.getLayoutParams().width;

                        if(motionEvent.getX() > motionEvent.getAction() && motionEvent.getY() > motionEvent.getAction()){

                            touchView.setLayoutParams(new LayoutParams(vWidth+10, vHight+10));

                            LogUtil.info(this,"Increase Size View");
                        }
                        else if (motionEvent.getX() < motionEvent.getAction() && motionEvent.getY() < motionEvent.getAction()){
                            touchView.setLayoutParams(new LayoutParams(vWidth-10, vHight-10));


                            LogUtil.info(this,"Decrease Size View");
                        }
                        else if (motionEvent.getX() < motionEvent.getAction() && motionEvent.getY() > motionEvent.getAction()){

                            LogUtil.info(this,"Rotetion+ Size View");
                        }
                        else if (motionEvent.getX() > motionEvent.getAction() && motionEvent.getY() < motionEvent.getAction()){

                            LogUtil.info(this,"Rotetion- Size View");

                        }
                        touchView.forceLayout();

                        LogUtil.info(this,"imgResize_MOVE");
                        return false;
                }
                break;
        }

        return false;
    }

    private boolean actionMobileView(MobileView view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchView!=null ) {
                    touchView.setActive(false);
                }
                touchView = view;
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
                    oldCoordinate = new Pair<>(motionEvent.getX(), motionEvent.getY());

                    touchView.setX(touchView.getX() + mX);
                    touchView.setY(touchView.getY() + mY);

                    //touchView.forceLayout();

                    imgDelete.setX(touchView.getX() - imgDelete.getWidth() / 2);
                    imgDelete.setY(touchView.getY() - imgDelete.getHeight() / 2);
                    //imgDelete.forceLayout();
                    imgResize.setX(touchView.getX() + touchView.getWidth() - imgResize.getWidth() / 2);
                    imgResize.setY(touchView.getY() + touchView.getHeight() - imgResize.getHeight() / 2);


                    touchView.forceLayout();
//                touchView.setRotation(  - вращение
//                imgDelete.getMeasuredWidth()
                    return true;
                }
        }
        return false;
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
