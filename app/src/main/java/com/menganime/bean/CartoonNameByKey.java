package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class CartoonNameByKey {
    private String Status;
    private List<CartoonName> MH_InfoList;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<CartoonName> getMH_InfoList() {
        return MH_InfoList;
    }

    public void setMH_InfoList(List<CartoonName> MH_InfoList) {
        this.MH_InfoList = MH_InfoList;
    }

    public class CartoonName {
        private String MH_Type_ID;
        private String Name;

        public String getMH_Type_ID() {
            return MH_Type_ID;
        }

        public void setMH_Type_ID(String MH_Type_ID) {
            this.MH_Type_ID = MH_Type_ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
