package com.menganime.bean;

/**
 * Created by Administrator on 2017/5/12.
 * 精彩推荐
 */

public class CartoonInfo {
    private String Name;//漫画名字
    private String Chapter_Count;//漫画话数
    private String Subtitle;//漫画描述
    private String Column_IconURL;//漫画图片

    public String getName() {
        return Name;
    }

    public void setName(String author) {
        Name = author;
    }

    public String getChapter_Count() {
        return Chapter_Count;
    }

    public void setChapter_Count(String chapter_Count) {
        Chapter_Count = chapter_Count;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getColumn_IconURL() {
        return Column_IconURL;
    }

    public void setColumn_IconURL(String column_IconURL) {
        Column_IconURL = column_IconURL;
    }
}
