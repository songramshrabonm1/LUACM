package com.example.myapplication.dashboardadmin.routinepart;

public class ModelFreeClassShow {

    String  classType = "" , Batch = "" ,Section = "" , Department = ""  , className = "" , classTime = "" , day = "";
    public ModelFreeClassShow() {
    }

    public ModelFreeClassShow(String classType, String batch, String section, String department, String className, String classTime, String day) {
        this.classType = classType;
        Batch = batch;
        Section = section;
        Department = department;
        this.className = className;
        this.classTime = classTime;
        this.day = day;
    }


    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
