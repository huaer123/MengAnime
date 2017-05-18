package com.menganime.bean;

/**
 * Created by Administrator on 2017/5/18.
 * 保存收藏历史Bean
 */

public class CollectionHistoryBean {
    private String cartoonId;//漫画Id;
    private String cartoonName;//漫画名字
    private String cartoonPicture;//漫画图片
    private String type;//0-历史/1-收藏/2-历史收藏
    private String maxChapter;//更新到多少章
    private String watchChapter;//观看到多少章节
    private String watchChapterContent;//观看的章节的内容

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public String getCartoonPicture() {
        return cartoonPicture;
    }

    public void setCartoonPicture(String cartoonPicture) {
        this.cartoonPicture = cartoonPicture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxChapter() {
        return maxChapter;
    }

    public void setMaxChapter(String maxChapter) {
        this.maxChapter = maxChapter;
    }

    public String getWatchChapter() {
        return watchChapter;
    }

    public void setWatchChapter(String watchChapter) {
        this.watchChapter = watchChapter;
    }

    public String getWatchChapterContent() {
        return watchChapterContent;
    }

    public void setWatchChapterContent(String watchChapterContent) {
        this.watchChapterContent = watchChapterContent;
    }
}
