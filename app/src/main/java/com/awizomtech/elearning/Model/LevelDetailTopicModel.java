package com.awizomtech.elearning.Model;

public class LevelDetailTopicModel {
    public int LevelTopicID;
    public int CourseID;
    public int LevelID;
    public String TopicName;

    public int getLevelTopicID() {
        return LevelTopicID;
    }

    public void setLevelTopicID(int levelTopicID) {
        LevelTopicID = levelTopicID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getLevelID() {
        return LevelID;
    }

    public void setLevelID(int levelID) {
        LevelID = levelID;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }
}
