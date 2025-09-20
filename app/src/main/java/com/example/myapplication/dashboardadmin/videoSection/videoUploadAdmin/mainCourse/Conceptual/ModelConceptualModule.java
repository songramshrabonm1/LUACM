package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual;

public class ModelConceptualModule {

    String   InstructorName = "",  Module = "" , TotalVideo ="", VideoTitle ="" , id = "" , uid = "";
    long timeStamp ;
    public ModelConceptualModule() {
    }

    public ModelConceptualModule(String instructorName, String module, String totalVideo, String videoTitle, String id, String uid, long timeStamp) {
        InstructorName = instructorName;
        Module = module;
        TotalVideo = totalVideo;
        VideoTitle = videoTitle;
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
        Module = module;
    }

    public String getTotalVideo() {
        return TotalVideo;
    }

    public void setTotalVideo(String totalVideo) {
        TotalVideo = totalVideo;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
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
