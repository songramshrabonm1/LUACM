package com.example.myapplication.dashboardadmin.addAdminCr;

import java.lang.reflect.Array;

public class ModelShowInfo {


    String name = "" , email = "" , Mobile = ""  , ADMIN = "" ,CR = "";
    long Timestamp ;
    public ModelShowInfo() {

    }

    public ModelShowInfo(String name, String email, String mobile, String ADMIN, String CR, long timestamp) {
        this.name = name;
        this.email = email;
        Mobile = mobile;
        this.ADMIN = ADMIN;
        this.CR = CR;
        Timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getADMIN() {
        return ADMIN;
    }

    public void setADMIN(String ADMIN) {
        this.ADMIN = ADMIN;
    }

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    //    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getMobile() {
//        return Mobile;
//    }
//
//    public void setMobile(String mobile) {
//        Mobile = mobile;
//    }
//
//    public String getTimestamp() {
//        return Timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//        Timestamp = timestamp;
//    }
//
//    public String getADMIN() {
//        return ADMIN;
//    }
//
//    public void setADMIN(String ADMIN) {
//        this.ADMIN = ADMIN;
//    }
//
//    public String getCR() {
//        return CR;
//    }
//
//    public void setCR(String CR) {
//        this.CR = CR;
//    }
}
