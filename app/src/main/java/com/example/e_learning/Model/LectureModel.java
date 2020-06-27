package com.example.e_learning.Model;

public class LectureModel {
    public int LectureID;

    public int CourseID ;

    public int PlanID;

    public String Title;
    public String Type;
    public String TextContant;
    public String Video;

  /*  public String UploadedDate;*/

    public String CreatedBy;

    public int getLectureID() {
        return LectureID;
    }

    public void setLectureID(int lectureID) {
        LectureID = lectureID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getPlanID() {
        return PlanID;
    }

    public void setPlanID(int planID) {
        PlanID = planID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTextContant() {
        return TextContant;
    }

    public void setTextContant(String textContant) {
        TextContant = textContant;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

   /* public String getUploadedDate() {
        return UploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        UploadedDate = uploadedDate;
    }*/

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
