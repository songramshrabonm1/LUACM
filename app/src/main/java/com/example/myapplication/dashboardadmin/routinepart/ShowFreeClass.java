package com.example.myapplication.dashboardadmin.routinepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.DashboardAdmin;
import com.example.myapplication.register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ShowFreeClass extends AppCompatActivity {

    private ImageButton backBtnnccc;
    private RecyclerView classessFreeRecyclerView;
    private String day = "" , time = "" , UserType = "";
    private FirebaseAuth firebaseAuth;
    AdapterFreeClass adapterFreeClass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_free_class);

        firebaseAuth = FirebaseAuth.getInstance();



//        Intent inte = new Intent(SearchingFreeClassRoom.this, ShowFreeClass.class);
//        inte.putExtra("day" , day );
//        inte.putExtra("time" ,time);
//        startActivity(inte);

         day = getIntent().getStringExtra("day");
         time = getIntent().getStringExtra("time");

         System.out.println("day ----->" + day);
         System.out.println("time ----->" + time);

         loadFreeRoom();


        backBtnnccc = findViewById(R.id.backBtnnccc);
        classessFreeRecyclerView = findViewById(R.id.classessFree);
        backBtnnccc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

//    private void loadFreeRoom() {
//        ArrayList<ModelFreeClassShow> freeClassShowsArrayList = new ArrayList<>();
//        ArrayList<String>FreeClassList = new ArrayList<>();
//        ArrayList<String>BlockClassList = new ArrayList<>();
//
//        String [] weekensday = {"SATURDAY" , "SUNDAY" , "MONDAY" , "TUESDAY" , "WEDNESDAY" , "THURSDAY" ,"FRIDAY"};
//        String [] Alltimes = {"8:00-8:59" , "9:00-9:59" , "10:00-10:59", "11:00-11:59" , "12:00-12:59" , "1:00-1:59" , "2:00-2:59" ,"3:00-3:59", "4:00-4:59"};
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");
//
//
//
//        for(int Dayi = 0 ; Dayi<weekensday.length; Dayi++){
//            if(day.equals(weekensday[Dayi])){
//                for(int timesi = 0 ; timesi < Alltimes.length ; timesi++){
//                    if(time.equals(Alltimes[timesi])){
//                        ref.child(day)
//                                .child(time)
//                                .child("classRoom")//ধরি এই ক্লাসরাম চাইল্ড এর ভিতরে আর অনেক ক্লাসের নাম অনুজয়ী  চাইল্ড আছে এবং তাদের ভিতর আরো  চাইল্ড আছে
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        for(DataSnapshot ds : snapshot.getChildren()){
////                                            String className = ds.getValue().toString();
//                                            String className = ds.getKey().toString();
//                                            ref.child(className)
//                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                            String classType = ""+snapshot.child("classType").getValue();
//                                                            if(classType.equals("FREE")){
////                                                                FreeClassList.add("FREE");
//                                                                FreeClassList.add(className);
//
//                                                            }else if(classType.equals("BLOCK")){
//                                                                BlockClassList.add("BLOCK");
//                                                            }
//
//                                                            updateRecyclerView(FreeClassList);
//
//                                                        }
//
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                                        }
//                                                    });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//
//                        break;
//                    }
//                }
//            }
//        }
//
//
//
////        classessFreeRecyclerView.setLayoutManager(new LinearLayoutManager(ShowFreeClass.this));
////        AdapterFreeClass adapterFreeClass = new AdapterFreeClass(FreeClassList, ShowFreeClass.this  , UserType , day , time);
////        classessFreeRecyclerView.setAdapter(adapterFreeClass);
//
//        // Separate method to update RecyclerView
//
//    }


    private void loadFreeRoom() {
        ArrayList<ModelFreeClassShow> freeClassShowsArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");

        ref.child(day)
                .child(time)
                .child("classRoom")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String classType = ""+ ds.child("classType").getValue(String.class);
                            if(classType.equals("FREE")){

                                classessFreeRecyclerView.setLayoutManager(new LinearLayoutManager(ShowFreeClass.this));
                                ModelFreeClassShow modelFreeClassShow = ds.getValue(ModelFreeClassShow.class);
                                freeClassShowsArrayList.add(modelFreeClassShow);
                            }
                        }
                        adapterFreeClass = new AdapterFreeClass(freeClassShowsArrayList, ShowFreeClass.this , UserType);
                        classessFreeRecyclerView.setAdapter(adapterFreeClass);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


//    private void updateRecyclerView(ArrayList<String> freeClassList) {
////        classessFreeRecyclerView.setLayoutManager(new LinearLayoutManager(ShowFreeClass.this));
////        AdapterFreeClass adapterFreeClass = new AdapterFreeClass(freeClassList, ShowFreeClass.this, UserType, day, time);
////        classessFreeRecyclerView.setAdapter(adapterFreeClass);
////        adapterFreeClass.notifyDataSetChanged(); // Add this line to refresh the data
//
//
//    }

    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//            reference.child(currentUser.getUid())
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Name = "" + snapshot.child("Name").getValue();
                            String email = "" + snapshot.child("email").getValue();
                            UserType = ""+snapshot.child("userType").getValue();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
    }

}