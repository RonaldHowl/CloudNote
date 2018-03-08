package com.example.ronaldhowl.cloudnote.inf;

/**
 * 倒计时回调借口
 * Created by RonaldHowl on 2018/1/27.
 */

public interface TimerCallBack {

    /**
     * @param timeLeft
     */
    void timerOnTick(int timeLeft);

    //void timerOnFinish(String text);
}
