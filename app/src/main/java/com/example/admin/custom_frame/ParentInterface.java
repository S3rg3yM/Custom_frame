package com.example.admin.custom_frame;

import android.view.View;

public interface ParentInterface {
    void addMobileView(MobileView view);
    void deleteMobileView(MobileView view);
    MobileView getMobileViewByIndex(int index);
    int countMobileView();
}
