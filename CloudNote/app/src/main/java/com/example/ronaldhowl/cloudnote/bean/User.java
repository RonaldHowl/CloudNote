package com.example.ronaldhowl.cloudnote.bean;

import cn.bmob.v3.BmobUser;

/**
 * 用户实体类
 * Created by RonaldHowl on 2018/1/25.
 */

public class User extends BmobUser {

    /**
     * username:15625780475
     * password:123456
     * mobilePhoneNumber:15625780475
     * sex：男
     * nickname：峰峰
     */
    private String nickname;
    private String sex;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
