package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 * 漫画集数
 */

public class CartoonChapterBean {
    private String Status;
    private String UpdateTime;
    private List<LZ> LZ;
    private List<DHB> DHB;
    private List<FWP> FWP;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public List<CartoonChapterBean.LZ> getLZ() {
        return LZ;
    }

    public void setLZ(List<CartoonChapterBean.LZ> LZ) {
        this.LZ = LZ;
    }

    public List<CartoonChapterBean.DHB> getDHB() {
        return DHB;
    }

    public void setDHB(List<CartoonChapterBean.DHB> DHB) {
        this.DHB = DHB;
    }

    public List<CartoonChapterBean.FWP> getFWP() {
        return FWP;
    }

    public void setFWP(List<CartoonChapterBean.FWP> FWP) {
        this.FWP = FWP;
    }

    /**
     * 连载
     */
    public class LZ{
        private String MH_Chapter_ID;//章节Id
        private String WhichChapter;//章节

        public String getMH_Chapter_ID() {
            return MH_Chapter_ID;
        }

        public void setMH_Chapter_ID(String MH_Chapter_ID) {
            this.MH_Chapter_ID = MH_Chapter_ID;
        }

        public String getWhichChapter() {
            return WhichChapter;
        }

        public void setWhichChapter(String whichChapter) {
            WhichChapter = whichChapter;
        }
    }
    /**
     * 单行本
     */
    public class DHB{
        private String MH_Chapter_ID;//章节Id
        private String WhichChapter;//章节

        public String getMH_Chapter_ID() {
            return MH_Chapter_ID;
        }

        public void setMH_Chapter_ID(String MH_Chapter_ID) {
            this.MH_Chapter_ID = MH_Chapter_ID;
        }

        public String getWhichChapter() {
            return WhichChapter;
        }

        public void setWhichChapter(String whichChapter) {
            WhichChapter = whichChapter;
        }
    }
    /**
     * 番外篇
     */
    public class FWP{
        private String MH_Chapter_ID;//章节Id
        private String WhichChapter;//章节

        public String getMH_Chapter_ID() {
            return MH_Chapter_ID;
        }

        public void setMH_Chapter_ID(String MH_Chapter_ID) {
            this.MH_Chapter_ID = MH_Chapter_ID;
        }

        public String getWhichChapter() {
            return WhichChapter;
        }

        public void setWhichChapter(String whichChapter) {
            WhichChapter = whichChapter;
        }
    }
}
