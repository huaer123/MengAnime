package com.menganime.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 * 个人中心
 */

public class UserInfoAll {
    private String Status;
    private List<UserInfo> User;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<UserInfo> getUser() {
        return User;
    }

    public void setUser(List<UserInfo> user) {
        User = user;
    }

    public class UserInfo {
        private String MH_UserInfo_ID;
        private String IMEI;
        private String PetName;
        private String ICONURL;
        private String Sex;
        private String Source;
        private String Birthday;
        private String Introduce;

        public String getMH_UserInfo_ID() {
            return MH_UserInfo_ID;
        }

        public void setMH_UserInfo_ID(String MH_UserInfo_ID) {
            this.MH_UserInfo_ID = MH_UserInfo_ID;
        }

        public String getIMEI() {
            return IMEI;
        }

        public void setIMEI(String IMEI) {
            this.IMEI = IMEI;
        }

        public String getPetName() {
            return PetName;
        }

        public void setPetName(String petName) {
            PetName = petName;
        }

        public String getICONURL() {
            return ICONURL;
        }

        public void setICONURL(String ICONURL) {
            this.ICONURL = ICONURL;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String birthday) {
            Birthday = birthday;
        }

        public String getIntroduce() {
            return Introduce;
        }

        public void setIntroduce(String introduce) {
            Introduce = introduce;
        }
    }
}
