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
 * 重置密码页面
 * Created by RonaldHowl on 2018/1/26.
 */

@ContentView(R.layout.activity_forget)
public class ForgetActivity extends AppCompatActivity {

    @ViewInject(R.id.forget_ib_back)
    private ImageButton back;
    @ViewInject(R.id.forget_et_userName)
    private EditText userName;
    @ViewInject(R.id.forget_et_password)
    private EditText password;
    @ViewInject(R.id.forget_et_message)
    private EditText message;
    @ViewInject(R.id.forget_btn_sendMessage)
    private Button sendMessage;
    @ViewInject(R.id.forget_btn_reset)
    private Button reset;

    private static int isReset = 1;

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
        back.setOnClickListener(new forgetOnClickListener());
        sendMessage.setOnClickListener(new forgetOnClickListener());
        reset.setOnClickListener(new forgetOnClickListener());
    }

    private class forgetOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.forget_ib_back:
                    startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
                    finish();
                    break;
                case R.id.forget_btn_sendMessage:
                    toSendMessage();
                    break;
                case R.id.forget_btn_reset:
                    toReset();
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

    private void toReset() {
        SignOrLoginUtils.toResetPassword(message.getText().toString().trim(),
                password.getText().toString().trim());
        if (isReset == 0) {
            startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void callBack() {
        SignOrLoginUtils signOrLoginUtils = new SignOrLoginUtils();
        signOrLoginUtils.setToastCallBack(new ToastCallBack() {
            @Override
            public void toastInfo(String toastStr) {
                Toast.makeText(ForgetActivity.this, toastStr, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int successCode) {
                isReset = successCode;
            }

            @Override
            public void onFailure(String failureStr) {
                Toast.makeText(ForgetActivity.this, failureStr, Toast.LENGTH_LONG).show();
            }
        });
    }
}
