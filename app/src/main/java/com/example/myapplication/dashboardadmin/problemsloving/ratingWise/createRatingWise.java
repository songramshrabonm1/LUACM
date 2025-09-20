package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.routinepart.RoutineShow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class createRatingWise extends AppCompatActivity {

    private ImageButton backButtonRating;
    private RecyclerView recyclerViewRating;
    private AppCompatButton uploadRatingButton;
    private String Platform = "";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private AdapterRating adapterRating ;
    private EditText searchText ;
    private TextView platformNameTextView ;
    String UserType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rating_wise);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not signed in! Please log in.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        userType();
        if (UserType.equals("USER")) {

          Toast.makeText(this, "You are not registered student", Toast.LENGTH_SHORT).show();
            finish();
            return ;
//            uploadRatingButton.setVisibility(View.INVISIBLE);
        }


//        PlatformName
        Platform = getIntent().getStringExtra("PlatformName");
        if(Platform.equals("CATEGORYWISE")){
            platformNameTextView = findViewById(R.id.choseSection);
            platformNameTextView.setText("CATEGORYWISE PROBLEM SET");
            loadingCategory();

        }else if(Platform.equals("CODEFORCES") || Platform.equals("CODECHEF")){
            Platform = Platform.toUpperCase();
            platformNameTextView = findViewById(R.id.choseSection);
            platformNameTextView.setText(Platform);
            loadingRating();

        }else if(Platform.equals("LUACM")){
//            LUACM
            platformNameTextView = findViewById(R.id.choseSection);
            platformNameTextView.setText("LUACM PROBLEM SET");
            loadingCategory();
        }else if(Platform.equals("EXTRA")){
            platformNameTextView = findViewById(R.id.choseSection);
            platformNameTextView.setText("CONCEPTUAL SESSION PROBLEM SET");
            loadingCategory();
        }



        searchText = findViewById(R.id.search_EtRating);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterRating.getFilter().filter(charSequence);

                }catch (Exception e){
                    Toast.makeText(createRatingWise.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//

        backButtonRating = findViewById(R.id.BackButtonRating);
        backButtonRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerViewRating = findViewById(R.id.RatingWiseRecyclerView);

        if(Platform.equals("CODEFORCES") || Platform.equals("CODECHEF")) {
            uploadRatingButton = findViewById(R.id.CreateRatingBtn);
            uploadRatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(createRatingWise.this, UploadRatingSet.class);
                    intent.putExtra("PlatformName", Platform);
                    startActivity(intent);
                }
            });
        }else{
            uploadRatingButton = findViewById(R.id.CreateRatingBtn);
            uploadRatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(createRatingWise.this, UploadRatingSet.class);
                    intent.putExtra("PlatformName", Platform);
                    startActivity(intent);
                }
            });

        }
//        }else if(Platform.equals("CATEGORYWISE")){
//            uploadRatingButton = findViewById(R.id.CreateRatingBtn);
//            uploadRatingButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(createRatingWise.this, UploadRatingSet.class);
//                    intent.putExtra("PlatformName", Platform);
//                    startActivity(intent);
//                }
//            });
//        }else if(Platform.equals("LUACM")){
////            LUACM
//        }else if(Platform.equals("EXTRA")){
////            EXTRA
//        }
    }
//    private void userType() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(firebaseAuth.getCurrentUser().getEmail().replace("." , ","))
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        UserType = ""+snapshot.child("userType").getValue(String.class);
//                        UserType = UserType.toString().toUpperCase().trim();
//                        if(UserType.equals("USER")){
//                            uploadRatingButton.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }


    private void userType() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) return;

        String email = user.getEmail();
        if (email == null) {
            Toast.makeText(this, "User email not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(email.replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserType = snapshot.child("userType").getValue(String.class);
                        if (UserType != null) {
                            UserType = UserType.toUpperCase().trim();

                            if (UserType.equals("USSERTYPE")) {
//                                Toast.makeText(this, "You are not registered student", Toast.LENGTH_SHORT).show();
//                                return ;
                                uploadRatingButton.setVisibility(View.INVISIBLE);
                            }else if(UserType.equals("USER")){
                                Toast.makeText(createRatingWise.this, "You are not registered student", Toast.LENGTH_SHORT).show();
                                finish();
                                return ;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(createRatingWise.this, "Failed to load user type", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadingRating() {
        /*
           DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child("RatingWise")
                .child(platform)
                .child(Rating)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
         */
        ArrayList<ModelRating> ratingsCateogyArraylist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child("RatingWise")
                .child(Platform)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ratingsCateogyArraylist.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerViewRating.setLayoutManager(new LinearLayoutManager(createRatingWise.this));
                            ModelRating modelRating = ds.getValue(ModelRating.class);
                            ratingsCateogyArraylist.add(modelRating);
                        }
                        adapterRating = new AdapterRating(createRatingWise.this , ratingsCateogyArraylist, Platform , UserType);
                          recyclerViewRating.setAdapter(adapterRating);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadingCategory() {
        ArrayList<ModelRating> ratingsCateogyArraylist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child(""+Platform)
//                .child(Platform)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ratingsCateogyArraylist.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerViewRating.setLayoutManager(new LinearLayoutManager(createRatingWise.this));
                            ModelRating modelRating = ds.getValue(ModelRating.class);
                            ratingsCateogyArraylist.add(modelRating);
                        }
                        adapterRating = new AdapterRating(createRatingWise.this , ratingsCateogyArraylist, Platform , UserType);
                        recyclerViewRating.setAdapter(adapterRating);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}