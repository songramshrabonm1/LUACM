package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;

public class UploadRatingSet extends AppCompatActivity {

    private String platform = "";
    private TextView Selectrating ;
    private String Rating = "";
    private AppCompatButton uploadRatingButton ;
    private EditText link , pass , AnotherRating;
    private ProgressDialog progressDialog;
    private ImageButton BackButtonRatingUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rating_set);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


//        ratinggs

         progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        BackButtonRatingUploads = findViewById(R.id.BackButtonRatingUpload);

        BackButtonRatingUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        platform = getIntent().getStringExtra("PlatformName");

//        CATEGORYWISE

        if(platform.equals("CODEFORCES") || platform.equals("CODECHEF")) {
            Selectrating = findViewById(R.id.Select_Rating);
            Selectrating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (platform.equals("CODEFORCES"))
                        selectRatingFORCODEFORCES();
                    else if (platform.equals("CODECHEF")) {
                        sELECTRATINGCODECHEF();
                    }

                }
            });
        }else if(platform.equals("CATEGORYWISE")){
            Selectrating = findViewById(R.id.Select_Rating);
            Selectrating.setText("Select Category");
            Selectrating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectRatingForCategory(platform);

                }
            });
        }else{
//            ratingg
            Selectrating = findViewById(R.id.Select_Rating);
            Selectrating.setVisibility(View.GONE);
            AnotherRating = findViewById(R.id.ratingg);
            AnotherRating.setVisibility(View.VISIBLE);
            AnotherRating.setText("Write Here...");

//            selectRatingForCategory(platform);


        }

        link = findViewById(R.id.vjudgelink);


        pass = findViewById(R.id.Vjudge_PasswordSet);

        uploadRatingButton = findViewById(R.id.UploadRating);
        uploadRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }



    String linkString = "" , passwordVjudge = "";
    private void validateData() {
        linkString = link.getText().toString().trim();
        passwordVjudge = pass.getText().toString().trim();
//        Rating = AnotherRating.getText().toString().trim();


        if(TextUtils.isEmpty(Rating)){
            Toast.makeText(UploadRatingSet.this, "Pick Rating", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(linkString)){
            Toast.makeText(UploadRatingSet.this, "Upload Link", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(passwordVjudge)){
            Toast.makeText(UploadRatingSet.this, "Upload password", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(platform)){
            Toast.makeText(UploadRatingSet.this, "Platform Null" , Toast.LENGTH_SHORT).show();
        }
        else{
            if(platform.equals("CODEFORCES") || platform.equals("CODECHEF")) {
                uploadDatabase();
            }
//            else if(platform.equals("CATEGORYWISE")){
//                uploadDatabaseCategory();
//            }
            else{
                uploadDatabaseCategory();
            }
        }
    }



    private void sELECTRATINGCODECHEF() {
        String[] ratingss = {"0_1000" , "1000_1200" , "1200_1400" , "1400_1500" , "1500_1600" , "1600_1700" , "1700_1800" , "1800_2000" , "2000_2200" , "2200_2500" , "2500_5001"};
//        for(int i =0 ;i<ratingss.length; i++){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Rating")
                    .setItems(ratingss, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Rating = ratingss[i];
                            Selectrating.setText(Rating);
                        }
                    }).show();
//        }
    }

    private void selectRatingFORCODEFORCES() {
        String[] ratings = {"800" , "900" , "1000" , "1100" , "1200" , "1300" , "1400" , "1500" , "1600" , "1700" , "1800" , "1900" , "2000" , "2100" , "2200" , "2300" , "2400", "2500"};
//        for(int i =0 ;i<ratings.length; i++){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Rating")
                    .setItems(ratings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Rating = ratings[i];
                            Selectrating.setText(Rating);
                        }
                    }).show();
//        }
    }

    private void selectRatingForCategory(String platform) {
        if(platform.equals("CATEGORYWISE")){
            String[] ratings = {"Array", "String", "Hash Table", "Dynamic Programming", "Math", "Sorting", "Greedy", "Depth-First Search", "Database", "Binary Search", "Matrix", "Tree", "Breadth-First Search", "Bit Manipulation", "Two Pointers", "Prefix Sum", "Heap (Priority Queue)", "Binary Tree", "Simulation", "Stack", "Graph", "Counting", "Sliding Window", "Design", "Backtracking", "Enumeration", "Union Find", "Linked List", "Ordered Set", "Number Theory", "Monotonic Stack", "Segment Tree", "Trie", "Combinatorics", "Bitmask", "Queue", "Divide and Conquer", "Recursion", "Binary Indexed Tree", "Geometry", "Memoization", "Binary Search Tree", "Hash Function", "String Matching", "Topological Sort", "Shortest Path", "Rolling Hash", "Game Theory", "Interactive", "Data Stream", "Monotonic Queue", "Brainteaser", "Randomized", "Merge Sort", "Doubly-Linked List", "Counting Sort", "Iterator", "Concurrency", "Probability and Statistics", "Quickselect", "Suffix Array", "Bucket Sort", "Minimum Spanning Tree", "Line Sweep", "Shell", "Reservoir Sampling", "Strongly Connected Component", "Eulerian Circuit", "Radix Sort", "Rejection Sampling", "Biconnected Component"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Category")
                    .setItems(ratings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Rating = ratings[i];
                            Selectrating.setText(Rating);
                        }
                    }).show();
        }
    }



    private void uploadDatabase() {
//        HashMap<String , Object> hashMap = new HashMap<>();
//        hashMap.put("rating" ,Rating );
//        hashMap.put("link" , linkString);
//        hashMap.put("password" , passwordVjudge);
//        hashMap.put("platform", platform);
        progressDialog.setMessage("Uplading.......");
        progressDialog.show();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child("RatingWise")
                .child(platform)
                .child(Rating)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            updatedDatabase();
                        }else{
                            createdDatabases();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, ""+ error.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createdDatabases() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("rating" ,Rating );
        hashMap.put("link" , linkString);
        hashMap.put("password" , passwordVjudge);
        hashMap.put("platform", platform);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child("RatingWise")
                .child(platform)
                .child(Rating)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, "Uploaded Succefully" , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, "Uploaded not Succefully"+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updatedDatabase() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("rating" ,Rating );
        hashMap.put("link" , linkString);
        hashMap.put("password" , passwordVjudge);
        hashMap.put("platform", platform);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child("RatingWise")
                .child(platform)
                .child(Rating)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this , "This rating already exist so,Updated Database successfully " , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this , "This rating already exist , Updated Database is not successfull"+e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void uploadDatabaseCategory() {
        progressDialog.setMessage("Uplading.......");
        progressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child(""+platform)
                .child(Rating)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            updatedDatabaseAnother();
                        }else{
                            createdDatabasesAnother();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, ""+ error.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void createdDatabasesAnother() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("rating" ,Rating );
        hashMap.put("link" , linkString);
        hashMap.put("password" , passwordVjudge);
        hashMap.put("platform", platform);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child(""+platform)
                .child(Rating) // ekhane rating mane select kora category
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, "Uploaded Succefully" , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this, "Uploaded not Succefully"+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updatedDatabaseAnother() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("rating" ,Rating );
        hashMap.put("link" , linkString);
        hashMap.put("password" , passwordVjudge);
        hashMap.put("platform", platform);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet"); //platform ekhane category wise set hoche ar rating ekhane selected category set hoche
        ref.child(""+platform)
                .child(Rating)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this , "This rating already exist so,Updated Database successfully " , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRatingSet.this , "This rating already exist , Updated Database is not successfull"+e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

