package com.xxxx.crm.model;

public class UserModel {
//    private Integer userId;
    private String userName;
    private String trueName;
    private String userIdStr;//加密后的用户id

    public UserModel() {

    }


    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userIdStr=" + userIdStr +
                ", userName='" + userName + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public UserModel(String userName, String trueName, String userIdStr) {
        this.userName = userName;
        this.trueName = trueName;
        this.userIdStr = userIdStr;
    }
}
