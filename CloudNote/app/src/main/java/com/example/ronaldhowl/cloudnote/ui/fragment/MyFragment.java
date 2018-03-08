package com.example.ronaldhowl.cloudnote.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.ui.activity.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的页面
 * Created by RonaldHowl on 2018/1/31.
 */

@ContentView(R.layout.fragment_my)
public class MyFragment extends Fragment {

    @ViewInject(R.id.my_layout_login)
    private LinearLayout login;
    @ViewInject(R.id.my_iv_avatar)
    private ImageView avatar;
    @ViewInject(R.id.my_tv_nickname)
    private TextView nicknme;
    @ViewInject(R.id.my_tv_phoneNumber)
    private TextView phoneNumber;
    @ViewInject(R.id.my_btn_synchronous)
    private ToggleButton synchronous;
    @ViewInject(R.id.my_btn_wifi)
    private ToggleButton wifiSyn;
    @ViewInject(R.id.my_tv_about)
    private TextView about;
    @ViewInject(R.id.my_tv_advice)
    private TextView advice;
    @ViewInject(R.id.my_tv_update)
    private TextView update;
    @ViewInject(R.id.my_btn_login)
    private Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        initEvent();
        return view;
    }

    private void initEvent() {
        login.setOnClickListener(new myOnClickListener());
        synchronous.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        wifiSyn.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        about.setOnClickListener(new myOnClickListener());
        advice.setOnClickListener(new myOnClickListener());
        update.setOnClickListener(new myOnClickListener());
        loginBtn.setOnClickListener(new myOnClickListener());
    }

    private class myOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.my_layout_login:
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
                case R.id.my_tv_about:
                    break;
                case R.id.my_tv_advice:
                    break;
                case R.id.my_tv_update:
                    break;
                case R.id.my_btn_login:
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
            }
        }
    }

    private class myOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.my_btn_synchronous:
                    if (isChecked) {

                    } else {

                    }
                    break;
                case R.id.my_btn_wifi:
                    if (isChecked) {

                    } else {

                    }
                    break;
            }
        }
    }
}
