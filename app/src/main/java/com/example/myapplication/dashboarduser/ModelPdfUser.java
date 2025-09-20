package com.example.myapplication.dashboarduser;


public class ModelPdfUser {
    //    String uid, id, title , description , categoryId, url ;
    String uid , id,  Batch , Category , SemesterExam, SessionName , Year , TeacherName , categoryId , url ;
    long timestamp;

    public ModelPdfUser() {
    }

    public ModelPdfUser(String uid,String Year ,   String id, String Batch,  String Category,String SemesterExam , String SessionName , String categoryId, String url, long timestamp) {
        this.uid = uid;
        this.id = id;
        this.Batch = Batch;
        this.Category = Category;
        this.SemesterExam = SemesterExam;
        this.SessionName = SessionName ;
        this.categoryId = categoryId;
        this.url = url;
        this.Year = Year;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }
    public String getYear(){
        return Year;
    }
    public String getBatch() {
        return Batch;
    }
    public String getCategory() {
        return Category;
    }

    public void setYear(String Year){
        this.Year = Year;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setBatch(String batch) {
        Batch = batch;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSemesterExam() {
        return SemesterExam;
    }

    public void setSemesterExam(String semesterExam) {
        SemesterExam = semesterExam;
    }

    public String getSessionName() {
        return SessionName;
    }

    public void setSessionName(String sessionName) {
        SessionName = sessionName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
