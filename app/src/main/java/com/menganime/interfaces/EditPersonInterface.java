package com.menganime.interfaces;

/**
 * Created by Administrator on 2017/5/16.
 * 编辑用户资料-界面接口
 */

public interface EditPersonInterface {
    /**
     * 获取用户信息
     * @param json
     */
    void getUserInfo(String json);

    /**
     * 保存昵称
     * @param json
     */
    void updateUserInfo(String json);
}
