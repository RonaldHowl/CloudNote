package com.example.ronaldhowl.cloudnote.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.example.ronaldhowl.cloudnote.R;
import com.example.ronaldhowl.cloudnote.ui.activity.MainActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * 初始化工具类
 * Created by RonaldHowl on 2018/1/25.
 */

public class InitUtils {

    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    public static void bmobInit(Context context) {
        BmobConfig config = new BmobConfig.Builder(context)
                //设置appkey
                .setApplicationId("8efde90af596e2809ba901b2f5e2b8f2")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(5)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    public static void matisseInit(Activity activity){
        Matisse
                .from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.example.ronaldhowl.cloudnote.fileProvider"))
                .countable(true)//有序选择图片
                .maxSelectable(9)//最大选择数量为9
                .gridExpectedSize(320)//图片显示表格的大小getResources().getDimensionPixelSize(R.dimen.grid_expected_size)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new PicassoEngine())//加载方式
                .forResult(REQUEST_CODE_CHOOSE);//请求码
    }
}
