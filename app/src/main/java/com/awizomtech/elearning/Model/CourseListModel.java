package com.awizomtech.elearning.Model;

public class CourseListModel {
    public int CourseID ;
    public String CourseName ;
    public String Description ;
    public String CourseImage;
    public String Type;
    public String CreatedOn;
    public boolean PaymentStatus;

    public boolean isPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
