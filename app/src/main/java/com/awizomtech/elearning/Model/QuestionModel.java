package com.awizomtech.elearning.Model;

public class QuestionModel {
    public int QuizID ;
    public int CourseID;
    public String Question ;
    public String CreatedBy ;

    public int getQuizID() {
        return QuizID;
    }

    public void setQuizID(int quizID) {
        QuizID = quizID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
