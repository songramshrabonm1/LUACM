package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

public class ModelRating {
//     hashMap.put("rating" ,Rating );
//        hashMap.put("link" , linkString);
//        hashMap.put("password" , passwordVjudge);
//        hashMap.put("platform", platform);
    String rating = "" ,link = "",  password = "" , platform = "";
    public ModelRating() {
    }

    public ModelRating(String rating, String link, String password, String platform) {
        this.rating = rating;
        this.link = link;
        this.password = password;
        this.platform = platform;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
