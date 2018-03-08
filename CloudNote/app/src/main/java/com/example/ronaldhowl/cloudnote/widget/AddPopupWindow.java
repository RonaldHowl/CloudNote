package com.example.ronaldhowl.cloudnote.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ronaldhowl.cloudnote.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 点击新建按钮时，底部弹出框
 * Created by RonaldHowl on 2018/3/6.
 */


public class AddPopupWindow extends PopupWindow {

    private RadioGroup button;
    private RadioButton addNote;
    private RadioButton addVoice;
    private RadioButton addPicture;
    private RadioButton addVideo;
    private Button cancel;

    private View view;

    /**
     * @param activity
     * @param onClickListener
     */
    public AddPopupWindow(Activity activity, View.OnClickListener onClickListener) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_addpopupwindow, null);
        initConfig();
        initEvent(onClickListener);
    }

    /**
     * @param onClickListener
     */
    private void initEvent(View.OnClickListener onClickListener) {
        addNote.setOnClickListener(onClickListener);
        addVoice.setOnClickListener(onClickListener);
        addPicture.setOnClickListener(onClickListener);
        addVideo.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
    }

    /**
     *
     */
    private void initConfig() {
        button = view.findViewById(R.id.add_rg_button);
        addNote = view.findViewById(R.id.add_rb_note);
        addVoice = view.findViewById(R.id.add_rb_voice);
        addPicture = view.findViewById(R.id.add_rb_picture);
        addVideo = view.findViewById(R.id.add_rb_video);
        cancel = view.findViewById(R.id.add_btn_cancel);

        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AddAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable colorDrawable = new ColorDrawable(0xFFFFFFFF);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(colorDrawable);
    }
}
