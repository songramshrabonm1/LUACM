package com.example.myapplication.dashboardadmin.routinepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchingFreeClassRoom extends AppCompatActivity {


    private TextView Days , Timee;
    private ImageButton backButton;
    private AppCompatButton CreateModuleBtn ;
    ArrayList<String> timess, dayss;
    String day = "", time = "";
    ArrayList<String>FreeClassList , BlockClassList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_free_class_room);


        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        FreeClassList = new ArrayList<>();
        BlockClassList = new ArrayList<>();
        Days = findViewById(R.id.Days);
        Timee = findViewById(R.id.Timee);
        timess = new ArrayList<>();  // Initialize timess
        dayss = new ArrayList<>();   // Initialize dayss

        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picDays();
            }
        });
        Timee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTimes();
            }
        });

        backButton = findViewById(R.id.backBtnnc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CreateModuleBtn = findViewById(R.id.CreateModuleBtn);
        CreateModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDAta();
            }
        });
    }

    private void pickTimes() {
        timess.add("8:00-8:59");
        timess.add("9:00-9:59");
        timess.add("10:00-10:59");
        timess.add("11:00-11:59");
        timess.add("12:00-12:59");
        timess.add("1:00-1:59");
        timess.add("2:00-2:59");
        timess.add("3:00-3:59");
        timess.add("4:00-4:59");

        String [] times = new String[timess.size()];
        for(int i = 0 ;i<timess.size(); i++){
            times[i] = timess.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Times")
                .setItems(times, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        time = times[i];
                        Timee.setText(time);

                    }
                }).show();
    }

    private void picDays() {
        dayss.add("SATURDAY");
        dayss.add("SUNDAY");
        dayss.add("MONDAY");
        dayss.add("TUESDAY");
        dayss.add("WEDNESDAY");
        dayss.add("THURSDAY");
        dayss.add("FRIDAY");

        String [] daysss = new String[dayss.size()];

        for(int i = 0 ;i<dayss.size(); i++){
            daysss[i] = dayss.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Day")
                .setItems(daysss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        day = daysss[i];
                        Days.setText(day);

                    }
                }).show();

    }

    private void validateDAta() {
        if(TextUtils.isEmpty(day)){
            Toast.makeText(SearchingFreeClassRoom.this, "Pick Day" , Toast.LENGTH_SHORT).show();;
        }else if(TextUtils.isEmpty(time)){
            Toast.makeText(SearchingFreeClassRoom.this, "Pick Time" , Toast.LENGTH_SHORT).show();;

        }else{

                Intent inte = new Intent(SearchingFreeClassRoom.this, ShowFreeClass.class);
                inte.putExtra("day" , day );
                inte.putExtra("time" ,time);
                startActivity(inte);
        }
    }
}