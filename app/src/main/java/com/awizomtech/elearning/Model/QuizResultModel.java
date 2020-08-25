package com.awizomtech.elearning.Model;

public class QuizResultModel {
    /*public int AnswerID;
    public int QuizID;
    public String AnswerText;
    public String UserID ;
    public boolean IsSelecded ;
    public boolean IsCorrect;

    public int getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(int answerID) {
        AnswerID = answerID;
    }

    public int getQuizID() {
        return QuizID;
    }

    public void setQuizID(int quizID) {
        QuizID = quizID;
    }

    public String getAnswerText() {
        return AnswerText;
    }

    public void setAnswerText(String answerText) {
        AnswerText = answerText;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public boolean isSelecded() {
        return IsSelecded;
    }

    public void setSelecded(boolean selecded) {
        IsSelecded = selecded;
    }

    public boolean isCorrect() {
        return IsCorrect;
    }

    public void setCorrect(boolean correct) {
        IsCorrect = correct;
    }*/


    public int ResultID ;
    public String StudentID ;
    public int CourseID ;
    public int  QuizID ;
    public int AnswerID ;
    public int LevelID;

    public int getResultID() {
        return ResultID;
    }

    public void setResultID(int resultID) {
        ResultID = resultID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getQuizID() {
        return QuizID;
    }

    public void setQuizID(int quizID) {
        QuizID = quizID;
    }

    public int getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(int answerID) {
        AnswerID = answerID;
    }

    public int getLevelID() {
        return LevelID;
    }

    public void setLevelID(int levelID) {
        LevelID = levelID;
    }
}
