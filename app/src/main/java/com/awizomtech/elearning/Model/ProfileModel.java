package com.awizomtech.elearning.Model;

public class ProfileModel {
    public int ID;
    public String FirstName ;
    public String LastName;
    public String MobileNumber ;
    public String EmailAddrss ;
    public String Password ;
    public String Gender ;
    public String UserID ;
    public String RegistrationDate;
    public String ProfilePhoto ;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmailAddrss() {
        return EmailAddrss;
    }

    public void setEmailAddrss(String emailAddrss) {
        EmailAddrss = emailAddrss;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }
}

