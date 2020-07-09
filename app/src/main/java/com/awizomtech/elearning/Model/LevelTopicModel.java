package com.awizomtech.elearning.Model;

public class LevelTopicModel {
    public int PlannerDetailID;

    public int PlannerMainID ;

    public String PlannerList ;
    public int LevelID;

    public int getPlannerDetailID() {
        return PlannerDetailID;
    }

    public void setPlannerDetailID(int plannerDetailID) {
        PlannerDetailID = plannerDetailID;
    }

    public int getPlannerMainID() {
        return PlannerMainID;
    }

    public void setPlannerMainID(int plannerMainID) {
        PlannerMainID = plannerMainID;
    }

    public String getPlannerList() {
        return PlannerList;
    }

    public void setPlannerList(String plannerList) {
        PlannerList = plannerList;
    }

    public int getLevelID() {
        return LevelID;
    }

    public void setLevelID(int levelID) {
        LevelID = levelID;
    }
}
