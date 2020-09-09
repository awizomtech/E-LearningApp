package com.awizomtech.elearning.Model;

public class CourseLevelModel {
    public int LevelID ;
    public String Level ;
    public int CourseID ;
    public String Duration ;
    public float Price ;
    public float Percentage;
    public float DiscountedAmount ;
    public float PayableAmount ;

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

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getPercentage() {
        return Percentage;
    }

    public void setPercentage(float percentage) {
        Percentage = percentage;
    }

    public float getDiscountedAmount() {
        return DiscountedAmount;
    }

    public void setDiscountedAmount(float discountedAmount) {
        DiscountedAmount = discountedAmount;
    }

    public float getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(float payableAmount) {
        PayableAmount = payableAmount;
    }
}
