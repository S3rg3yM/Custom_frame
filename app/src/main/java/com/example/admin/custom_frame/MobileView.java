package com.example.admin.custom_frame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MobileView extends FrameLayout implements ChildInterface{
    private boolean active = false;

    public MobileView(Context context) {
        super(context);
    }

    public MobileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MobileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }

    @Override
    public void setActive(boolean status) {
        active = status;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
