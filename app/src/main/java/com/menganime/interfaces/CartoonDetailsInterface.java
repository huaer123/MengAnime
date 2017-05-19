package com.menganime.interfaces;

/**
 * Created by Administrator on 2017/5/17.
 * 漫画详情接口
 */

public interface CartoonDetailsInterface {
    void selectDetails(String json);

    /**
     * 获取漫画集数
     */
    void selectchapter(String json);

    /**
     * 获取用户查看了哪些原创漫画
     * @param json
     */
    void selectUserChapter(String json);
}
