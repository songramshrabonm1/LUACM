package com.example.myapplication.dashboardadmin.routinepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FreeClassRoomHome extends AppCompatActivity {

    private ImageButton backkk ;
    private AppCompatButton uploadClassTimeEnters , freeClassBtns , blockClassBtns ;
    private FirebaseAuth firebaseAuth;
    private String User = "";
    private RelativeLayout classtime , blockClasss ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        setContentView(R.layout.activity_free_class);

        firebaseAuth = FirebaseAuth.getInstance();
        usersType();

        classtime = findViewById(R.id.classTimeBox);
        blockClasss = findViewById(R.id.blockClass);


        backkk = findViewById(R.id.BackButtonControllingSectin);
        backkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadClassTimeEnters = findViewById(R.id.uploadClassTimeEnter);
        uploadClassTimeEnters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreeClassRoomHome.this, UploadRoutineClasses.class));
            }
        });


        freeClassBtns = findViewById(R.id.freeClassBtn);
        freeClassBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreeClassRoomHome.this, SearchingFreeClassRoom.class));

            }
        });

        blockClassBtns = findViewById(R.id.blockClassBtn);
        blockClassBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreeClassRoomHome.this,SearchingBlockClassRoom.class));
            }
        });



    }

    private void usersType() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(FreeClassRoomHome.this, "No user logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User = ""+snapshot.child("userType").getValue();
                        User = User.toString().toUpperCase();
                        System.out.println("Users : "+ User);
                        if(User.equals("ADMIN_OR_CR") || User.equals("ADMIN") || User.equals("CR")){
//                        if(User.equals("USER")){
                            uploadClassTimeEnters.setVisibility(View.VISIBLE);
                            blockClassBtns.setVisibility(View.VISIBLE);
                        }else{
                            uploadClassTimeEnters.setVisibility(View.INVISIBLE);
                            blockClassBtns.setVisibility(View.INVISIBLE);
                            classtime.setVisibility(View.GONE);
                            blockClasss.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}