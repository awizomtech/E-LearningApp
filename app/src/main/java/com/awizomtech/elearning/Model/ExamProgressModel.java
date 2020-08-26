package com.awizomtech.elearning.Model;

public class ExamProgressModel {
    public int PlannerDetailID ;
    public String PlannerList ;
    public int TotalQuestion ;
    public int Correct ;
    public int Incorrect ;

    public int getPlannerDetailID() {
        return PlannerDetailID;
    }

    public void setPlannerDetailID(int plannerDetailID) {
        PlannerDetailID = plannerDetailID;
    }

    public String getPlannerList() {
        return PlannerList;
    }

    public void setPlannerList(String plannerList) {
        PlannerList = plannerList;
    }

    public int getTotalQuestion() {
        return TotalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        TotalQuestion = totalQuestion;
    }

    public int getCorrect() {
        return Correct;
    }

    public void setCorrect(int correct) {
        Correct = correct;
    }

    public int getIncorrect() {
        return Incorrect;
    }

    public void setIncorrect(int incorrect) {
        Incorrect = incorrect;
    }
}
