package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;

public class ModelDailyContestModule {
    String ContestName = "" , InstructorName = "" , TotalVideo = "" , Division = "" , SelectContestSite = "" , value =""  , id = "" , uid = "";
    long timeStamp ;

    public ModelDailyContestModule() {
    }

    public ModelDailyContestModule(String contestName, String instructorName, String totalVideo, String division, String selectContestSite, String value, String id, String uid, long timeStamp) {
        ContestName = contestName;
        InstructorName = instructorName;
        TotalVideo = totalVideo;
        Division = division;
        SelectContestSite = selectContestSite;
        this.value = value;
        this.id = id;
        this.uid = uid;
        this.timeStamp = timeStamp;
    }

    public String getContestName() {
        return ContestName;
    }

    public void setContestName(String contestName) {
        ContestName = contestName;
    }

    public String getInstructorName() {
        return InstructorName;
    }

    public void setInstructorName(String instructorName) {
        InstructorName = instructorName;
    }

    public String getTotalVideo() {
        return TotalVideo;
    }

    public void setTotalVideo(String totalVideo) {
        TotalVideo = totalVideo;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getSelectContestSite() {
        return SelectContestSite;
    }

    public void setSelectContestSite(String selectContestSite) {
        SelectContestSite = selectContestSite;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
