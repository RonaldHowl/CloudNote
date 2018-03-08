package com.example.ronaldhowl.cloudnote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
 * 用户登录页面
 * Created by RonaldHowl on 2018/1/25.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewInject(R.id.login_iv_avatar)
    private ImageView avatar;//头像
    @ViewInject(R.id.login_tv_userName)
    private AutoCompleteTextView userName;//账号
    @ViewInject(R.id.login_et_password)
    private EditText password;//密码
    @ViewInject(R.id.login_btn_sendMessage)
    private Button sendMessage;//发送短信按钮
    @ViewInject(R.id.login_btn_login)
    private Button login;//登录按钮
    @ViewInject(R.id.login_tv_sign)
    private TextView sign;//用户注册按钮
    @ViewInject(R.id.login_tv_forget)
    private TextView forget;//忘记密码按钮

    private static int isLogin = 1;
    private static int isSendMessage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        initEvent();
        callBack();
    }

    private void initConfig() {
        x.view().inject(this);
        FullScreen.fullScreen(this, "#888888");
    }

    private void initEvent() {
        sendMessage.setOnClickListener(new loginOnClickListener());
        login.setOnClickListener(new loginOnClickListener());
        sign.setOnClickListener(new loginOnClickListener());
        forget.setOnClickListener(new loginOnClickListener());
    }

    private class loginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_sendMessage:
                    toSendMessage();
                    break;
                case R.id.login_btn_login:
                    toLogin();
                    break;
                case R.id.login_tv_sign:
                    startActivity(new Intent(LoginActivity.this, SignActivity.class));
                    break;
                case R.id.login_tv_forget:
                    startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                    break;
            }
        }
    }

    private void toSendMessage() {
        isSendMessage = 0;
        SignOrLoginUtils.toSendMessage(userName.getText().toString().trim());
        TimerUtils timerUtils = new TimerUtils(10000, 1000);
        timerUtils.start();
        timerUtils.setTimerCallBack(new TimerCallBack() {
            @Override
            public void timerOnTick(int timeLeft) {
                if (timeLeft == 0) {
                    sendMessage.setEnabled(true);
                    isSendMessage = 1;
                    sendMessage.setText(R.string.sendMessage);
                } else {
                    sendMessage.setEnabled(false);
                    sendMessage.setText(timeLeft + "秒后可重发");
                }
            }
        });
    }

    private void toLogin() {
        switch (isSendMessage) {
            case 0:
                SignOrLoginUtils.toLoginByMessage(userName.getText().toString().trim(),
                        password.getText().toString().trim());
                break;
            case 1:
                SignOrLoginUtils.toLoginByPassword(userName.getText().toString().trim(),
                        password.getText().toString().trim());
                break;
        }
        if (isLogin == 0) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void callBack() {
        SignOrLoginUtils signOrLoginUtils = new SignOrLoginUtils();
        signOrLoginUtils.setToastCallBack(new ToastCallBack() {
            @Override
            public void toastInfo(String toastStr) {
                Toast.makeText(LoginActivity.this, toastStr, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int successCode) {
                isLogin = successCode;
            }

            @Override
            public void onFailure(String failureStr) {
                Toast.makeText(LoginActivity.this, failureStr, Toast.LENGTH_LONG).show();
            }
        });
    }
}
