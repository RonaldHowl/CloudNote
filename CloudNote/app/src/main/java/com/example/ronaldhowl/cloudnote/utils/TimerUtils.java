package com.example.ronaldhowl.cloudnote.utils;

import android.os.CountDownTimer;

import com.example.ronaldhowl.cloudnote.inf.TimerCallBack;

/**
 * 倒计时工具类
 * Created by RonaldHowl on 2018/1/27.
 */

public class TimerUtils extends CountDownTimer {

    private TimerCallBack timerCallBack;

    public void setTimerCallBack(TimerCallBack timerCallBack) {
        this.timerCallBack = timerCallBack;
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timerCallBack.timerOnTick(((int) millisUntilFinished / 1000));
    }

    @Override
    public void onFinish() {
        timerCallBack.timerOnTick(0);
    }
}
