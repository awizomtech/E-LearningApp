package com.awizomtech.elearning.Model;

public class AnswerModel {
    public int AnswerID ;
    public String AnswerText;
    public int QuizID ;
    public Boolean IsCorrect;
    public Boolean IsSelect;

    public Boolean getSelect() {
        return IsSelect;
    }

    public void setSelect(Boolean select) {
        IsSelect = select;
    }

    public int getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(int answerID) {
        AnswerID = answerID;
    }

    public String getAnswerText() {
        return AnswerText;
    }

    public void setAnswerText(String answerText) {
        AnswerText = answerText;
    }

    public int getQuizID() {
        return QuizID;
    }

    public void setQuizID(int quizID) {
        QuizID = quizID;
    }

    public Boolean getCorrect() {
        return IsCorrect;
    }

    public void setCorrect(Boolean correct) {
        IsCorrect = correct;
    }
}
