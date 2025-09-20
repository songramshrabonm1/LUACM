package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure;

public class DsModelClasses {
    String Classs ="" ,DriveLink =""  , id = "" , ModuleId = "";
    long Timestamp ;


    public DsModelClasses() {
    }

    public DsModelClasses(String classs, String driveLink, String id, String moduleId, long timestamp) {
        Classs = classs;
        DriveLink = driveLink;
        this.id = id;
        ModuleId = moduleId;
        Timestamp = timestamp;
    }

    public String getClasss() {
        return Classs;
    }

    public void setClasss(String classs) {
        Classs = classs;
    }

    public String getDriveLink() {
        return DriveLink;
    }

    public void setDriveLink(String driveLink) {
        DriveLink = driveLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return ModuleId;
    }

    public void setModuleId(String moduleId) {
        ModuleId = moduleId;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}
