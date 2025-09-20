package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure.DsprogrammingUploadCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProblemSolvingUploadCategory extends AppCompatActivity {

    ImageButton backButton;
    AppCompatButton createModuleBtn ;
    EditText contestName , InstructorName , TotalVideo , DivisionName  ;
    TextView platformName;
    private ProgressDialog progressDialog;
    private ArrayList<String> ContestSiteName = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    private HashMap<String,String> categorySiteNameAndPicture = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_solving_upload_category);

        backButton = findViewById(R.id.backBtnnc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading ....");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();


        contestName = findViewById(R.id.contestName);
        InstructorName = findViewById(R.id.InstructorNamee);
        TotalVideo = findViewById(R.id.totalVideoCount);
        DivisionName = findViewById(R.id.divisionName);
        platformName = findViewById(R.id.PlatformName);
        createModuleBtn = findViewById(R.id.CreateModuleBtn);



        categorySiteNameAndPicture.put("Lu" , "https://firebasestorage.googleapis.com/v0/b/lu-pdf.appspot.com/o/ProblemSolvingDailyContestPlatformPicture%2Flu.jpeg?alt=media&token=e089ad7d-570c-4d52-ad3c-029ede61e2b7");
        categorySiteNameAndPicture.put("CodeForces" ,"https://firebasestorage.googleapis.com/v0/b/lu-pdf.appspot.com/o/ProblemSolvingDailyContestPlatformPicture%2Fcodeforces.jpeg?alt=media&token=c2916487-6f66-476c-a998-36adba2a263e");
        categorySiteNameAndPicture.put("Codechef", "https://firebasestorage.googleapis.com/v0/b/lu-pdf.appspot.com/o/ProblemSolvingDailyContestPlatformPicture%2Fcodechef.jpeg?alt=media&token=70e4bdfc-1091-4828-9f99-2203d1393a4c");
        categorySiteNameAndPicture.put("Leetcode", "https://firebasestorage.googleapis.com/v0/b/lu-pdf.appspot.com/o/ProblemSolvingDailyContestPlatformPicture%2FleetCode.png?alt=media&token=7ef3dcdd-2232-470f-abc6-ac307fa6a2a8");
        categorySiteNameAndPicture.put("HackerRank" , "https://firebasestorage.googleapis.com/v0/b/lu-pdf.appspot.com/o/ProblemSolvingDailyContestPlatformPicture%2Fhackerrank-software.png?alt=media&token=50725e58-ec0a-4739-954d-e36f56a3bafa");


        ContestSiteName.add("Lu");
        ContestSiteName.add("CodeForces");
        ContestSiteName.add("Codechef");
        ContestSiteName.add("Leetcode");
        ContestSiteName.add("HackerRank");


        platformName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContestSide();

            }
        });

        createModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    String SelectedContestSiteName = "";
    String value = "";

    String ContestName = "" , InstructorNameString = "" , TodayTotalVideo = "" , DivisionNameString = "" ;

    private void pickContestSide() {
        String [] name = new String[ContestSiteName.size()];
        for(int i =0 ; i<ContestSiteName.size() ; i++){
            name[i] = ContestSiteName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("problem solving Site")
                .setItems(name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SelectedContestSiteName = ContestSiteName.get(i);
                        platformName.setText(SelectedContestSiteName);
                        value = categorySiteNameAndPicture.get(ContestSiteName.get(i));
                    }
                }).show();

    }



    private void validateData() {
        ContestName =  contestName.getText().toString().trim();
        InstructorNameString = InstructorName.getText().toString().trim();
        TodayTotalVideo = TotalVideo.getText().toString().trim();
        DivisionNameString = DivisionName.getText().toString().trim();

        if(TextUtils.isEmpty(ContestName)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "Constest Name Empty" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(InstructorNameString)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "Instructor Name Empty" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(TodayTotalVideo)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "Today Total Video Number Empty" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(DivisionNameString)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "Division Name Empty" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(SelectedContestSiteName)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "Select Contest Site Name" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(value)){
            Toast.makeText(ProblemSolvingUploadCategory.this, "VAlue not store", Toast.LENGTH_SHORT).show();
        }
        else{
            uploadDatabase();
        }

    }

    private void uploadDatabase() {
        progressDialog.setMessage("Uploading Contest ....");
        progressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        progressDialog.setMessage("UpLoading Module");
        progressDialog.show();

        long timeStamp = System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("ContestName" , ContestName);
        hashMap.put("InstructorName", InstructorNameString);
        hashMap.put("TotalVideo", TodayTotalVideo);
        hashMap.put("Division", DivisionNameString);
        hashMap.put("SelectContestSite" ,  SelectedContestSiteName);
        hashMap.put("value", value);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("id",""+timeStamp);
        hashMap.put("uid", firebaseAuth.getUid());



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Video");
        reference.child("DailyContest")
                .child(""+timeStamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(ProblemSolvingUploadCategory.this, "Uploaded Module Successfully ", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProblemSolvingUploadCategory.this,  ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }


}