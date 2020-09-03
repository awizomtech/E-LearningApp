package com.awizomtech.elearning.Model;

public class LoginModel {
    public String UserName ;
    public String Name ;
    public String UserID;
    public String MobileNo;
    public int ID ;

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
