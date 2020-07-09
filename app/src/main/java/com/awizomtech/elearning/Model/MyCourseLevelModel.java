package com.awizomtech.elearning.Model;

public class MyCourseLevelModel {
    public int LevelID;
    public String Level ;
    public float Price ;
    public String Duration ;
    public String SubscribeDate;
    public String UserID ;
    public int CourseID ;

    public int getLevelID() {
        return LevelID;
    }

    public void setLevelID(int levelID) {
        LevelID = levelID;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getSubscribeDate() {
        return SubscribeDate;
    }

    public void setSubscribeDate(String subscribeDate) {
        SubscribeDate = subscribeDate;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }
}
