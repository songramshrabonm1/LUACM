package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;

public class ModelCppModule {
    String   InstructorName = "",  Module = "" , TotalVideo ="", VideoTitle ="" , id = "" , uid = "";
    long timeStamp ;

    public ModelCppModule(){

    }

    public ModelCppModule(String instructorName, String module, String TotalVideo, String videoTitle, String id, String uid, long timeStamp) {
        this.InstructorName = instructorName;
        this.Module = module;
        this.TotalVideo = TotalVideo;
        this.VideoTitle = videoTitle;
        this.id = id;
        this.uid = uid;
        this.timeStamp = timeStamp;
    }


    public String getInstructorName() {
        return InstructorName;
    }

    public void setInstructorName(String instructorName) {
        InstructorName = instructorName;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        this.Module = module;
    }

    public String getTotalVideo() {
        return TotalVideo;
    }

    public void setTotalVideoCnt(String totalVideoCnt) {
        this.TotalVideo = totalVideoCnt;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.VideoTitle = videoTitle;
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
