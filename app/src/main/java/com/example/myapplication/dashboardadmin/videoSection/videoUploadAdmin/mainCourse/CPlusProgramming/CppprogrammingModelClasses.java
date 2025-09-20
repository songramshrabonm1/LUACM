package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;

public class CppprogrammingModelClasses {
    String Classs ="" ,DriveLink =""  , id = "" , ModuleId = "";
    long Timestamp ;


    public CppprogrammingModelClasses() {
    }

    public CppprogrammingModelClasses(String Classs, String driveLink, String id , String ModuleId, long timestamp) {
        this.Classs = Classs;
        this.DriveLink = driveLink;
        this.id = id;
        this.ModuleId = ModuleId;
        this.Timestamp = timestamp;
    }

    public String getClasss(){
        return Classs;
    }
    public void setClasss(String Class){
        this.Classs = Class;
    }


    public String getDriveLink() {
        return DriveLink;
    }

    public void setDriveLink(String driveLink) {
        this.DriveLink = driveLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId(){
        return ModuleId;
    }

    public void setModuleId(String ModuleId){
        this.ModuleId = ModuleId;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.Timestamp = timestamp;
    }



}
