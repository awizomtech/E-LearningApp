package com.awizomtech.elearning.Model;

public class InstructorModel {
    public int TeacherID ;
    public String Name ;
    public String Phone;
    public String Email;
    public String DegreeOption;
    public String DegreeText;
    public String Createdby;
    public String Image ;
    public String AboutText ;
    public Boolean IsActive ;
    public String CreatedOn ;
    public int ViewIndex ;

    public int getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(int teacherID) {
        TeacherID = teacherID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDegreeOption() {
        return DegreeOption;
    }

    public void setDegreeOption(String degreeOption) {
        DegreeOption = degreeOption;
    }

    public String getDegreeText() {
        return DegreeText;
    }

    public void setDegreeText(String degreeText) {
        DegreeText = degreeText;
    }

    public String getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(String createdby) {
        Createdby = createdby;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAboutText() {
        return AboutText;
    }

    public void setAboutText(String aboutText) {
        AboutText = aboutText;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getViewIndex() {
        return ViewIndex;
    }

    public void setViewIndex(int viewIndex) {
        ViewIndex = viewIndex;
    }
}
