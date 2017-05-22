package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class RecommendInfo {
    private String Status;
    private String count;
    private List<CartoonInfo> list;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<CartoonInfo> getList() {
        return list;
    }

    public void setList(List<CartoonInfo> list) {
        this.list = list;
    }
}
