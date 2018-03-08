package com.example.ronaldhowl.cloudnote.inf;

/**
 * Toast回调借口
 * Created by RonaldHowl on 2018/1/27.
 */

public interface ToastCallBack {

    /**
     * 回调信息
     *
     * @param toastStr
     */
    void toastInfo(String toastStr);

    /**
     * 成功时回调信息
     *
     * @param successCode
     */
    void onSuccess(int successCode);

    /**
     * 失败时回调信息
     *
     * @param failureStr
     */
    void onFailure(String failureStr);
}
