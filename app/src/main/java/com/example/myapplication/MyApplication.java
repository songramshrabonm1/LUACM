package com.example.myapplication;

import android.app.Application;
import android.text.format.DateFormat;

import com.cloudinary.android.MediaManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Cloudinary কনফিগারেশন
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "db3f58uvh");
        config.put("api_key", "432285265761625");
        config.put("api_secret", "rV-bKS6ZKwEsazAuuol1mb2sv0Y");
//        if(MediaManager.get() == null) {
        MediaManager.init(this, config);
    }
    public static final String formatTimestamp(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        System.out.println("Calendar: "+ cal);
        cal.setTimeInMillis(timestamp);
//        System.out.println("Calendar time Set: ");
        String date = DateFormat.format("dd/MM/YYYY",cal).toString();//DateFormat ta nibo 3 number ta
        return date;
    }
}
