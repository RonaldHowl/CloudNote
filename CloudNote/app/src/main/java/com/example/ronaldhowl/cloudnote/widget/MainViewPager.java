package com.example.ronaldhowl.cloudnote.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.ronaldhowl.cloudnote.R;

/**
 * 主页面
 * 设置isSlide = true/false 使得控件是否可以左右滑动
 * Created by RonaldHowl on 2018/2/1.
 */

public class MainViewPager extends ViewPager {

    public boolean isSlide = false;

    public boolean getIsSlide() {
        return isSlide;
    }

    public void setIsSlide(boolean isSlide) {
        this.isSlide = isSlide;
    }

    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MainViewPager);
        //获取布局中设置的属性
        isSlide = array.getBoolean(R.styleable.MainViewPager_isSlide, false);
        array.recycle();
    }

    /**
     * 触摸事件是否触发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    /**
     * 是否处理触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    /**
     * 分发事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 分发事件，这个是必须要的，如果把这个方法覆盖了，那么ViewPager的子View就接收不到事件了
        if (isSlide) {
            return super.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
