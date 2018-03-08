package com.example.ronaldhowl.cloudnote.utils;


import android.util.Log;

import com.example.ronaldhowl.cloudnote.bean.User;
import com.example.ronaldhowl.cloudnote.inf.ToastCallBack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 登录、注册、重置密码、获取验证码工具类
 * Created by RonaldHowl on 2018/1/26.
 */

public class SignOrLoginUtils {

    private static ToastCallBack toastCallBack;

    public void setToastCallBack(ToastCallBack toastCallBack) {
        this.toastCallBack = toastCallBack;
    }

    /**
     * 发送短信
     *
     * @param phoneNumber 手机
     */
    public static void toSendMessage(String phoneNumber) {
        if (isPhone(phoneNumber)) {
            BmobSMS.requestSMSCode(phoneNumber, "CloudNote", new QueryListener<Integer>() {

                @Override
                public void done(Integer smsId, BmobException ex) {
                    if (ex == null) {//验证码发送成功
                        toastCallBack.toastInfo("短息发送成功");
                        Log.i("smile", "短信id：" + smsId);//用于后续的查询本次短信发送状态
                    } else {
                        toastCallBack.onFailure("短信发送失败");
                    }
                }
            });
        }
    }

    /**
     * 密码登录
     *
     * @param userName 账户/手机
     * @param password 密码
     */
    public static void toLoginByPassword(String userName, String password) {
        if (isNoEmpty(userName) && isPassword(password)) {
            BmobUser.loginByAccount(userName, password, new LogInListener<User>() {

                @Override
                public void done(User user, BmobException e) {
                    if (user != null) {
                        toastCallBack.onSuccess(0);
                        toastCallBack.toastInfo("登陆成功");
                        Log.i("smile", "用户登陆成功");
                    } else {
                        toastCallBack.onFailure("登陆失败");
                    }
                }
            });
        }
    }

    /**
     * 短信登录
     *
     * @param userName 手机
     * @param message  验证码
     */
    public static void toLoginByMessage(String userName, String message) {
        if (isPhone(userName) && isMessage(message)) {
            BmobUser.loginBySMSCode(userName, message, new LogInListener<User>() {

                @Override
                public void done(User user, BmobException e) {
                    if (user != null) {
                        toastCallBack.onSuccess(0);
                        toastCallBack.toastInfo("登陆成功");
                        Log.i("smile", "用户登陆成功");
                    } else {
                        toastCallBack.onFailure("登陆失败");
                    }
                }
            });
        }
    }

    /**
     * 短信注册
     *
     * @param userName 手机
     * @param password 密码
     * @param message  验证码
     */
    public static void toSign(String userName, String password, String message) {
        if (toJudgeInformation(userName, password) && isMessage(message)) {
            User user = new User();
            user.setMobilePhoneNumber(userName);
            user.setPassword(password);
            user.signOrLogin(message, new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        toastCallBack.onSuccess(0);
                        toastCallBack.toastInfo("注册成功");
                        Log.i("sign", "注册或登录成功");
                    } else {
                        toastCallBack.onFailure("注册失败");
                    }
                }
            });
        }
    }

    /**
     * 短信重制密码
     *
     * @param message  验证码
     * @param password 新密码
     */
    public static void toResetPassword(String message, String password) {

        BmobUser.resetPasswordBySMSCode(message, password, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toastCallBack.onSuccess(0);
                    toastCallBack.toastInfo("密码重置成功");
                    Log.i("smile", "密码重置成功");
                } else {
                    toastCallBack.onFailure("密码重置失败");
                    Log.i("smile", "重置失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 验证手机和密码格式
     *
     * @param phoneNumber 手机
     * @param password    密码
     * @return
     */
    public static boolean toJudgeInformation(String phoneNumber, String password) {
        if (isPhone(phoneNumber) && isPassword(password)) {
            return true;
        } else if (!isPhone(phoneNumber) && isPassword(password)) {
            return false;
        } else if (isPhone(phoneNumber) && !isPassword(password)) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断账户/手机号是否为空
     *
     * @param userName
     * @return
     */
    public static boolean isNoEmpty(String userName) {
        if (!userName.isEmpty() && !userName.equals(" ")) {
            return true;
        } else {
            toastCallBack.toastInfo("请输入账户/手机号");
            return false;
        }
    }

    /**
     * 验证手机格式
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhone(String phoneNumber) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches() && phoneNumber.length() == 11) {
            return true;
        } else {
            toastCallBack.toastInfo("请输入正确的手机号码");
            return false;
        }
    }

    /**
     * 验证密码格式
     *
     * @param password 密码
     * @return
     */
    public static boolean isPassword(String password) {
        if (!password.isEmpty() && !password.equals(" ") && password.length() > 5) {
            return true;
        } else if (!password.isEmpty() && !password.equals(" ") && password.length() < 5) {
            toastCallBack.toastInfo("密码少于5位");
            return false;
        } else if (password.isEmpty() || password.equals(" ")) {
            toastCallBack.toastInfo("密码为空");
            return false;
        } else {
            toastCallBack.toastInfo("密码错误");
            return false;
        }
    }

    /**
     * 验证验证码格式
     *
     * @param message 验证码
     * @return
     */
    public static boolean isMessage(String message) {
        if (message.length() == 6) {
            return true;
        } else {
            toastCallBack.toastInfo("验证码格式错误");
            return false;
        }
    }
}

