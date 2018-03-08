package com.example.ronaldhowl.cloudnote.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.inf.TimerCallBack;
import com.example.ronaldhowl.cloudnote.inf.ToastCallBack;
import com.example.ronaldhowl.cloudnote.utils.SignOrLoginUtils;
import com.example.ronaldhowl.cloudnote.utils.TimerUtils;
import com.example.ronaldhowl.cloudnote.widget.FullScreen;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 用户注册页面
 * Created by RonaldHowl on 2018/1/25.
 */

@ContentView(R.layout.activity_sign)
public class SignActivity extends AppCompatActivity {

    @ViewInject(R.id.sign_ib_back)
    private ImageButton back;//返回按钮
    @ViewInject(R.id.sign_et_userName)
    private EditText userName;//手机号
    @ViewInject(R.id.sign_et_password)
    private EditText password;//密码
    @ViewInject(R.id.sign_et_message)
    private EditText message;//验证码
    @ViewInject(R.id.sign_btn_sendMessage)
    private Button sendMessage;//发送短信按钮
    @ViewInject(R.id.sign_btn_sign)
    private Button sign;//注册按钮

    private static int isSign = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        initEvent();
        callBack();
    }

    private void initConfig() {
        x.view().inject(this);
        FullScreen.fullScreen(this,"#888888");
    }

    private void initEvent() {
        sendMessage.setOnClickListener(new signListener());
        sign.setOnClickListener(new signListener());
    }

    private class signListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_sendMessage:
                    toSendMessage();
                    break;
                case R.id.sign_btn_sign:
                    toSign();
                    break;
            }
        }
    }

    private void toSendMessage() {
        SignOrLoginUtils.toSendMessage(userName.getText().toString().trim());
        TimerUtils timerUtils = new TimerUtils(10000, 1000);
        timerUtils.start();
        timerUtils.setTimerCallBack(new TimerCallBack() {
            @Override
            public void timerOnTick(int timeLeft) {
                if (timeLeft == 0) {
                    sendMessage.setEnabled(true);
                    sendMessage.setText(R.string.sendMessage);
                } else {
                    sendMessage.setEnabled(false);
                    sendMessage.setText(timeLeft + "秒后可重发");
                }
            }
        });
    }

    private void toSign() {
        SignOrLoginUtils.toSign(userName.getText().toString().trim(),
                password.getText().toString().trim(),
                message.getText().toString().trim());
        if (isSign == 0) {
            startActivity(new Intent(SignActivity.this, MainActivity.class));
            finish();
        }
    }

    private void callBack() {
        SignOrLoginUtils signOrLoginUtils = new SignOrLoginUtils();
        signOrLoginUtils.setToastCallBack(new ToastCallBack() {
            @Override
            public void toastInfo(String toastStr) {
                Toast.makeText(SignActivity.this, toastStr, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int successCode) {
                isSign = successCode;
            }

            @Override
            public void onFailure(String failureStr) {
                Toast.makeText(SignActivity.this, failureStr, Toast.LENGTH_LONG).show();
            }
        });
    }
}
