package com.example.e_learning.Model;

public class CourseListModel {
    public int CourseID ;

    public int CategoryID ;

    public String CourseName ;

    public String CourseCode;

    public String Description ;

    public String StartsFrom;

    public String Duration;

    public String getStartsFrom() {
        return StartsFrom;
    }

    public void setStartsFrom(String startsFrom) {
        StartsFrom = startsFrom;
    }

    public int ProfessorID;

    public float Price;

    public String CourseImage;

    public String InstructorID;

    public String CreatedOn;

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public int getProfessorID() {
        return ProfessorID;
    }

    public void setProfessorID(int professorID) {
        ProfessorID = professorID;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getInstructorID() {
        return InstructorID;
    }

    public void setInstructorID(String instructorID) {
        InstructorID = instructorID;
    }
}
