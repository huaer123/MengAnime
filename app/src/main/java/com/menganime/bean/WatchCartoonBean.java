package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 * 获取具体漫画Bean
 */

public class WatchCartoonBean {
    private String Status;
    private List<Picturelist> picturelist;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Picturelist> getPicturelist() {
        return picturelist;
    }

    public void setPicturelist(List<Picturelist> picturelist) {
        this.picturelist = picturelist;
    }

    public class Picturelist {
        private String MH_Chapter_Content_ID;
        private String ChapterURL;

        public String getMH_Chapter_Content_ID() {
            return MH_Chapter_Content_ID;
        }

        public void setMH_Chapter_Content_ID(String MH_Chapter_Content_ID) {
            this.MH_Chapter_Content_ID = MH_Chapter_Content_ID;
        }

        public String getChapterURL() {
            return ChapterURL;
        }

        public void setChapterURL(String chapterURL) {
            ChapterURL = chapterURL;
        }
    }
}
