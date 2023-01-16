package com.example.legendofbounca;

import android.util.DisplayMetrics;

public class ScreenHandler {
    private int screenHeight;
    private int screenWidth;
    private int navigationHeight;
    private int statusBarHeight;

    public ScreenHandler(DisplayMetrics displayMetrics){
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        navigationHeight = 0;
        statusBarHeight = 0;
    }

    int getScreenHeight(){
        return screenHeight;
    }

    int getScreenWidth(){
        return screenWidth;
    }

    int getNavigationHeight(){
        return navigationHeight;
    }

    int getStatusBarHeight(){
        return statusBarHeight;
    }

    void setNavigationHeight(int _navigationHeight){
        navigationHeight = _navigationHeight;
        screenHeight -= navigationHeight;
    }

    void setStatusBarHeight(int _statusBarHeight){
        statusBarHeight = _statusBarHeight;
        screenHeight -= statusBarHeight;
    }
}
