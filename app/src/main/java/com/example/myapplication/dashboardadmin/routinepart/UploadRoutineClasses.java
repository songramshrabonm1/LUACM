package com.example.myapplication.dashboardadmin.routinepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
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
import com.example.myapplication.dashboardadmin.DashboardAdmin;
import com.example.myapplication.register.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadRoutineClasses extends AppCompatActivity {

    private TextView Days , Timee , classes , Batch , Section  ,Department;
//    private TextInputLayout Days , Timee , classes , Batch , Section  ,Department;
    private ImageButton backButton;
    private AppCompatButton BlockClassBtn , ResetFreeClasBtn ;
    ArrayList<String> timess, dayss;
    private String Selectday = "", Selecttime = "" , Selectclass = "" , SelectBatch = "", SelectSection = "" ,SelectDepartment = "";
    private FirebaseAuth firebaseAuth;
    private String UserType = "";
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_routine_classes);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        BlockClassBtn = findViewById(R.id.uploadBlockClass);
        ResetFreeClasBtn = findViewById(R.id.ResetClasFree);
//        ResetFreeClasBtn.setVisibility(View.GONE);
        firebaseAuth = FirebaseAuth.getInstance();
        Days = findViewById(R.id.Days);
        Timee  = findViewById(R.id.Timeess);
        classes = findViewById(R.id.ClassRoom);
        backButton = findViewById(R.id.backBtnnc);
        Batch = findViewById(R.id.SelectBatch);
        Section = findViewById(R.id.SelectSection);
        Department = findViewById(R.id.SelectDepartment);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("dialog");
        progressDialog.setCanceledOnTouchOutside(false);

        checkUser();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDay();
            }
        });

        Timee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickClass();
            }
        });
        Batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBatches();
            }
        });
        Section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSection();
            }
        });
        Department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDepartment();
            }
        });




        BlockClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateClassInput();
            }
        });



        ResetFreeClasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadRoutineClasses.this);
                builder.setTitle("Free Class")
                        .setMessage("Are You Want To Sure Reset Class Free ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SetClassAllFree();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }

    private void selectDepartment() {
        String[] departments = {
                "Department of English",
                "Department of Computer Science & Engineering (CSE)",
                "Department of Business Administration (BuA)",
                "Department of Architecture (Arch)",
                "Department of Civil Engineering (CE)",
                "Department of Electrical & Electronic Engineering (EEE)",
                "Department of Public Health (PH)",
                "Department of Law",
                "Department of Islamic Studies (IS)",
                "Department of Bangla",
                "Department of Tourism and Hospitality Management (THM)"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Section")
                .setItems(departments, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectDepartment = departments[which];
                        Department.setText(departments[which]);
                    }
                }).show();


    }


    private void pickClass() {

        String [] Classes ={"RAB-401", "RAB-402", "RAB-403", "RAB-404", "RAB-405", "RAB-406", "RAB-407", "RAB-408", "RAB-409", "RAB-410", "RAB-301", "RAB-302", "RAB-303", "RAB-304", "RAB-305", "RAB-306", "RAB-307", "RAB-308", "RAB-309", "RAB-310", "RKB-402", "RKB-403", "RKB-404", "RKB-405", "RKB-406", "RKB-407", "RKB-104", "RKB-103", "RKB-106", "RKB-304", "RKB-303", "RKB-306", "RKB-307", "RAB-202", "RAB-205", "RAB-206", "RAB-108-B", "RAB-112", "RAB-109 A", "RAB-109 B", "G1", "G2", "G3", "ACL-1", "ACL-2", "ACL-3", "NL", "GL"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Times")
                .setItems(Classes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Selectclass = Classes[i];
                        classes.setText(Selectclass);

                    }
                }).show();

    }

    private void pickTime() {

//        timess.add("8:00-8:59");
//        timess.add("9:00-9:59");
//        timess.add("10:00-10:59");
//        timess.add("11:00-11:59");
//        timess.add("12:00-12:59");
//        timess.add("1:00-1:59");
//        timess.add("2:00-2:59");
//        timess.add("3:00-3:59");
//        timess.add("4:00-4:59");

//        String [] times = new String[timess.size()];
//        for(int i = 0 ;i<timess.size(); i++){
//            times[i] = timess.get(i);
//        }

        String[] times = {
                "8:00-8:59", "9:00-9:59", "10:00-10:59", "11:00-11:59",
                "12:00-12:59", "1:00-1:59", "2:00-2:59", "3:00-3:59", "4:00-4:59"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Times")
                .setItems(times, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Selecttime = times[i];
                        Timee.setText(Selecttime);

                    }
                }).show();
    }

    private void pickDay() {
//        dayss.add("SATURDAY");
//        dayss.add("SUNDAY");
//        dayss.add("MONDAY");
//        dayss.add("TUESDAY");
//        dayss.add("WEDNESDAY");
//        dayss.add("THURSDAY");
//        dayss.add("FRIDAY");

//        String [] daysss = new String[dayss.size()];

        String [] daysss = {"SATURDAY", "SUNDAY" , "MONDAY" , "TUESDAY" , "WEDNESDAY" , "THURSDAY" , "FRIDAY" };

//        for(int i = 0 ;i<dayss.size(); i++){
//            daysss[i] = dayss.get(i);
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Day")
                .setItems(daysss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Selectday = daysss[i];
                        Days.setText(Selectday);

                    }
                }).show();
    }

    private void selectSection() {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Section")
                .setItems(alphabet, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectSection = alphabet[which];
                        Section.setText(alphabet[which]);
                    }
                }).show();

    }


    private void selectBatches() {
        String [] Batches = new String[101];
        int ii = 0 ;
        for(int i = 50 ; i<=150; i++){
            Batches[ii] = ""+i;
            ii++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Batch")
                .setItems(Batches, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SelectBatch = Batches[i];
                        Batch.setText(Batches[i]);
                    }
                }).show();
    }



    private void validateClassInput() {
        if(TextUtils.isEmpty(Selectclass)){
            Toast.makeText(UploadRoutineClasses.this, "Select class" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Selectday)){
            Toast.makeText(UploadRoutineClasses.this, "Select Day" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Selecttime)){
            Toast.makeText(UploadRoutineClasses.this, "Select Time" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(SelectBatch)){
            Toast.makeText(UploadRoutineClasses.this, "Select Batch" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(SelectSection)) {
            Toast.makeText(UploadRoutineClasses.this, "Select Section" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(SelectDepartment)){
            Toast.makeText(UploadRoutineClasses.this, "Select Department" , Toast.LENGTH_SHORT).show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Block Class")
                            .setMessage("Are You Sure this class is Block ... Please add Carefully")
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            UploadedBlockClass();

                                        }
                                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }
    private void UploadedBlockClass() {
        progressDialog.setMessage("Set classes Block");
        progressDialog.show();
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("Batch" , SelectBatch);
        hashMap.put("Section" , SelectSection);
        hashMap.put("Department" , SelectDepartment);
        hashMap.put("classType" , "BLOCK");
        hashMap.put("className" , Selectclass);
        hashMap.put("classTime" , Selecttime);
        hashMap.put("day" , Selectday);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");
        ref.child(Selectday)
                .child(Selecttime)
                .child("classRoom")
                .child(Selectclass)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineClasses.this, "set "+Selectclass +" class Block Now", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineClasses.this, "Failing to set the value", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void SetClassAllFree(){

        progressDialog.setMessage("Set All Classes Free Please Wait.....");
        progressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadRoutineClasses.this);
        builder.setTitle("Free Class")
                .setMessage("New Semester Start.....")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SetClassAllFree();

                        HashMap<String , Object> hashMap = new HashMap<>();
                        hashMap.put("Batch" , "");
                        hashMap.put("Section" , "");
                        hashMap.put("Department" , "");
                        hashMap.put("classType" , "FREE");
//                        hashMap.put("classroom" , "");

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");
                        String [] weekensday = {"SATURDAY" , "SUNDAY" , "MONDAY" , "TUESDAY" , "WEDNESDAY" , "THURSDAY" ,"FRIDAY"};
                        String [] Alltimes = {"8:00-8:59" , "9:00-9:59" , "10:00-10:59", "11:00-11:59" , "12:00-12:59" , "1:00-1:59" , "2:00-2:59" ,"3:00-3:59", "4:00-4:59"};
                        String [] Classes ={"RAB-401", "RAB-402", "RAB-403", "RAB-404", "RAB-405", "RAB-406", "RAB-407", "RAB-408", "RAB-409", "RAB-410", "RAB-301", "RAB-302", "RAB-303", "RAB-304", "RAB-305", "RAB-306", "RAB-307", "RAB-308", "RAB-309", "RAB-310", "RKB-402", "RKB-403", "RKB-404", "RKB-405", "RKB-406", "RKB-407", "RKB-104", "RKB-103", "RKB-106", "RKB-304", "RKB-303", "RKB-306", "RKB-307", "RAB-202", "RAB-205", "RAB-206", "RAB-108-B", "RAB-112", "RAB-109 A", "RAB-109 B", "G1", "G2", "G3", "ACL-1", "ACL-2", "ACL-3", "NL", "GL"};
                        for(int iday = 0 ; iday<weekensday.length ; iday++){
                            for(int timesi = 0 ; timesi<Alltimes.length; timesi++){
                                for(int classesi = 0 ; classesi<Classes.length ; classesi++){


                                    hashMap.put("day" , weekensday[iday]);
                                    hashMap.put("classTime", Alltimes[timesi]);
                                    hashMap.put("className",Classes[classesi]);
                                    ref.child(weekensday[iday])
                                            .child(Alltimes[timesi])
                                            .child("classRoom")
                                            .child(Classes[classesi])

                                            .setValue(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(UploadRoutineClasses.this, "set free class", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(UploadRoutineClasses.this, "Failing to set the value", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            }
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return  ;
                    }
                }).show();
    }


    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//            reference.child(currentUser.getUid())
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            UserType = ""+snapshot.child("userType");
                            System.out.println("UserType checkUser Method ---> " +UserType);
                            UserType = ""+snapshot.child("userType").getValue();
                            System.out.println("UserTupe check method: -----> " + UserType);
//                            if(UserType.equals("ADMIN_OR_CR")) {
                            if(UserType.equals("USER")){

                                ResetFreeClasBtn.setVisibility(View.VISIBLE);
                            }else{
//                                ResetFreeClasBtn.setVisibility(View.INVISIBLE);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

    }


}