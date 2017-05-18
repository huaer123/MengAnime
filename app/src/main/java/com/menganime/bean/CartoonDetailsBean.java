package com.menganime.bean;

/**
 * Created by Administrator on 2017/5/17.
 * 漫画详情
 */

public class CartoonDetailsBean {
    private String Status;
    private String Name;
    private String Author;
    private String Title;
    private String Subtitle;
    private String FightPower;
    private String Star;
    private String Source;
    private String Introduce;
    private String ShortIntroduce;
    private String Cover_IconURL;
    private String ISCollect;
    private String UserVisitLog;
    private String MH_Chapter_ID;
    private String MaxChapter;//更新到多少章
    private String UserComment_Count;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getFightPower() {
        return FightPower;
    }

    public void setFightPower(String fightPower) {
        FightPower = fightPower;
    }

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getShortIntroduce() {
        return ShortIntroduce;
    }

    public void setShortIntroduce(String shortIntroduce) {
        ShortIntroduce = shortIntroduce;
    }

    public String getCover_IconURL() {
        return Cover_IconURL;
    }

    public void setCover_IconURL(String cover_IconURL) {
        Cover_IconURL = cover_IconURL;
    }

    public String getISCollect() {
        return ISCollect;
    }

    public void setISCollect(String ISCollect) {
        this.ISCollect = ISCollect;
    }

    public String getUserVisitLog() {
        return UserVisitLog;
    }

    public void setUserVisitLog(String userVisitLog) {
        UserVisitLog = userVisitLog;
    }

    public String getMH_Chapter_ID() {
        return MH_Chapter_ID;
    }

    public void setMH_Chapter_ID(String MH_Chapter_ID) {
        this.MH_Chapter_ID = MH_Chapter_ID;
    }

    public String getMaxChapter() {
        return MaxChapter;
    }

    public void setMaxChapter(String maxChapter) {
        MaxChapter = maxChapter;
    }

    public String getUserComment_Count() {
        return UserComment_Count;
    }

    public void setUserComment_Count(String userComment_Count) {
        UserComment_Count = userComment_Count;
    }
}
