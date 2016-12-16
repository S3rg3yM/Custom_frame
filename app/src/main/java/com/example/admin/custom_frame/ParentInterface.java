package com.example.admin.custom_frame;

public interface ParentInterface {
    void addMobileView(MobileView view);
    void deleteMobileView(MobileView view);
    MobileView getMobileViewByIndex(int index);
    int countMobileView();
}
