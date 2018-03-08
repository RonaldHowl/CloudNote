package com.example.ronaldhowl.cloudnote.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.adapter.MainAdapter;
import com.example.ronaldhowl.cloudnote.ui.fragment.MyFragment;
import com.example.ronaldhowl.cloudnote.ui.fragment.NoteFragment;
import com.example.ronaldhowl.cloudnote.ui.fragment.PictureFragment;
import com.example.ronaldhowl.cloudnote.ui.fragment.VideoFragment;
import com.example.ronaldhowl.cloudnote.utils.InitUtils;
import com.example.ronaldhowl.cloudnote.widget.AddPopupWindow;
import com.example.ronaldhowl.cloudnote.widget.FullScreen;
import com.example.ronaldhowl.cloudnote.widget.MainViewPager;
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity;
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureQuality.HIGH;
import static com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureResolution.RES_1440P;

/**
 * 主页面Activity
 */
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.main_content)
    private LinearLayout main_content;
    @ViewInject(R.id.main_view_pager)
    private MainViewPager mainViewPager;
    @ViewInject(R.id.main_rg_bottom)
    private RadioGroup bottom;
    @ViewInject(R.id.main_rb_note)
    private RadioButton noteBtn;
    @ViewInject(R.id.main_rb_picture)
    private RadioButton pictureBtn;
    @ViewInject(R.id.main_rb_add)
    private RadioButton addBtn;
    @ViewInject(R.id.main_rb_video)
    private RadioButton videoBtn;
    @ViewInject(R.id.main_rb_my)
    private RadioButton myBtn;

    private NoteFragment noteFragment;
    private PictureFragment pictureFragment;
    private VideoFragment videoFragment;
    private MyFragment myFragment;
    private List<Fragment> fragmentList;

    private AddPopupWindow addPopupWindow;//底部弹出框

    private List<Uri> mSelected;//选择图片的地址
    private static final int REQUEST_CODE_PICTURE = 23;//定义请求码常量
    private static final int REQUEST_CODE_VIDEO = 26;//定义请求码常量
    private long exitTime = 0;//点击返回键时间

    public static final String EXTRA_OUTPUT_FILENAME = "com.example.ronaldhowl.cloudnote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConfig();
        initEvent();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        x.view().inject(this);
        FullScreen.fullScreen(this, "#888888");
        InitUtils.bmobInit(this);

        noteFragment = new NoteFragment();
        pictureFragment = new PictureFragment();
        videoFragment = new VideoFragment();
        myFragment = new MyFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(noteFragment);
        fragmentList.add(pictureFragment);
        fragmentList.add(videoFragment);
        fragmentList.add(myFragment);
        mainViewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), fragmentList));

        mainViewPager.setCurrentItem(0);
        noteBtn.setChecked(true);

        addPopupWindow = new AddPopupWindow(this, new mainOnClickListener());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        bottom.setOnCheckedChangeListener(new mainOnCheckedChangeListener());
        addBtn.setOnClickListener(new mainOnClickListener());
    }

    private class mainOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                //底部导航栏按钮
                case R.id.main_rb_note:
                    mainViewPager.setCurrentItem(0);
                    break;
                case R.id.main_rb_picture:
                    mainViewPager.setCurrentItem(1);
                    break;
                case R.id.main_rb_video:
                    mainViewPager.setCurrentItem(2);
                    break;
                case R.id.main_rb_my:
                    mainViewPager.setCurrentItem(3);
                    break;
            }
        }
    }

    private class mainOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_rb_add:
                    addPopupWindow.showAtLocation(main_content, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                //底部弹出框按钮
                case R.id.add_rb_note:
                    startActivity(new Intent(MainActivity.this, NoteActivity.class));
                    addPopupWindow.dismiss();
                    break;
                case R.id.add_rb_voice:
                    startActivity(new Intent(MainActivity.this, NoteActivity.class));
                    addPopupWindow.dismiss();
                    break;
                case R.id.add_rb_picture:
                    InitUtils.matisseInit(MainActivity.this);
                    addPopupWindow.dismiss();
                    break;
                case R.id.add_rb_video:
                    Intent intent = new Intent(MainActivity.this, VideoCaptureActivity.class);
                    intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION,new CaptureConfiguration(RES_1440P, HIGH,60,1024,true,true));
                    intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, EXTRA_OUTPUT_FILENAME);
                    startActivityForResult(intent, REQUEST_CODE_VIDEO);
                    addPopupWindow.dismiss();
                    break;
                case R.id.add_btn_cancel:
                    addPopupWindow.dismiss();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 物理返回键监听事件
     * 两秒内点击两次退出app
     */
    public void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override      //接收返回的地址
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICTURE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK) {
            Log.d("hlog","视频路径："+data.getStringExtra(EXTRA_OUTPUT_FILENAME));
            Log.d("hlog","视频大小："+getVideoSize(data.getStringExtra(EXTRA_OUTPUT_FILENAME)+"M"));
            double size=getVideoSize(data.getStringExtra(EXTRA_OUTPUT_FILENAME));
            Toast.makeText(MainActivity.this,
                    "视频大小："+size+"M",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private double getVideoSize(String videoPath){
        File file = new File(videoPath);
        long fileLength = 0;
        try {
            fileLength = getFileSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSize = Double.valueOf(df.format((double)fileLength/1048576));

        return fileSize;
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
}
