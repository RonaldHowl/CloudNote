package com.example.ronaldhowl.cloudnote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.utils.TextUtils;
import com.example.ronaldhowl.cloudnote.widget.FullScreen;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 添加文字文本页面
 * Created by RonaldHowl on 2018/1/28.
 */

@ContentView(R.layout.activity_note)
public class NoteActivity extends AppCompatActivity {

    @ViewInject(R.id.note_ib_back)
    private ImageButton back;
    @ViewInject(R.id.note_tv_submit)
    private TextView submit;
    @ViewInject(R.id.note_et_title)
    private EditText title;
    @ViewInject(R.id.note_et_content)
    private EditText content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        initEvent();
    }

    private void initConfig() {
        x.view().inject(this);
        FullScreen.fullScreen(this, "#888888");
    }

    private void initEvent() {
        back.setOnClickListener(new textOnClickListener());
        submit.setOnClickListener(new textOnClickListener());
    }

    private class textOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.note_ib_back:
                    finish();
                    break;
                case R.id.note_tv_submit:
                    toSubmit();
                    break;
            }
        }
    }

    private void toSubmit() {
        if (TextUtils.isNoEmpty(title.getText().toString())) {

        } else {

        }
        finish();
    }
}
