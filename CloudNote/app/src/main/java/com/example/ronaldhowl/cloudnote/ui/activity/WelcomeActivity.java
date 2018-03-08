package com.example.ronaldhowl.cloudnote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.inf.TimerCallBack;
import com.example.ronaldhowl.cloudnote.utils.TimerUtils;
import com.example.ronaldhowl.cloudnote.widget.FullScreen;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 欢迎页面
 * Created by RonaldHowl on 2018/1/28.
 */

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        countDownTimer();
    }

    private void initConfig() {
        x.view().inject(this);
        FullScreen.fullScreen(this,null);
    }

    private void countDownTimer() {
        TimerUtils timerUtils = new TimerUtils(1000, 1000);
        timerUtils.start();
        timerUtils.setTimerCallBack(new TimerCallBack() {
            @Override
            public void timerOnTick(int timeLeft) {
                if (timeLeft == 0) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                } else {

                }
            }
        });
    }
}
