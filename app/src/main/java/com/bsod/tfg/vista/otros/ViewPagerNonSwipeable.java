package com.bsod.tfg.vista.otros;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Payton on 23/09/2014.
 */
public class ViewPagerNonSwipeable extends ViewPager {

    public ViewPagerNonSwipeable(Context context) {
        super(context);
    }

    public ViewPagerNonSwipeable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
