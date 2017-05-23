package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 * 漫画分类Bean
 */

public class CartoonClassifyBean {
    private String Status;
    private List<CartoonClassfiy> MH_TypeList;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<CartoonClassfiy> getMH_TypeList() {
        return MH_TypeList;
    }

    public void setMH_TypeList(List<CartoonClassfiy> MH_TypeList) {
        this.MH_TypeList = MH_TypeList;
    }

    public class CartoonClassfiy {
        private String MH_Type_ID;
        private String MH_Type_Name;
        private String MH_Type_ICONURL;

        public String getMH_Type_ID() {
            return MH_Type_ID;
        }

        public void setMH_Type_ID(String MH_Type_ID) {
            this.MH_Type_ID = MH_Type_ID;
        }

        public String getMH_Type_Name() {
            return MH_Type_Name;
        }

        public void setMH_Type_Name(String MH_Type_Name) {
            this.MH_Type_Name = MH_Type_Name;
        }

        public String getMH_Type_ICONURL() {
            return MH_Type_ICONURL;
        }

        public void setMH_Type_ICONURL(String MH_Type_ICONURL) {
            this.MH_Type_ICONURL = MH_Type_ICONURL;
        }
    }
}
