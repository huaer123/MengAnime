package com.menganime.interfaces;

/**
 * Created by Administrator on 2017/5/23.
 */

public interface CartoonClassifyInterface {
    /**
     * 获取漫画分类列表
     * @param json
     */
    void getCartoonClassify(String json);

    /**
     * 通过关键字获取漫画名字
     * @param json
     */
    void getCartoonNameForKey(String json);
}
