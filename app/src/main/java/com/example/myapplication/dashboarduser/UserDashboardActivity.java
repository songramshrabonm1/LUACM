package com.example.myapplication.dashboarduser;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.AdapterCategory;
import com.example.myapplication.dashboardadmin.DashboardAdmin;
import com.example.myapplication.dashboardadmin.pdfActivity;
import com.example.myapplication.register.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserDashboardActivity extends AppCompatActivity {

    EditText search;
    TextView namee, emaill;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    //    private ArrayList<ModelCategory> categoryArrayList;
    private AdapterCategoryUser adapterCategory;
    Button addCategory, pdf;
        ImageButton logoutUser;
    ImageButton backk;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);





        //****************************************** initialization part **********************************\\
        namee = findViewById(R.id.textTv);
        emaill = findViewById(R.id.emailidd);
        recyclerView = findViewById(R.id.category_List);
        logoutUser = findViewById(R.id.logOutUser);
        backk = findViewById(R.id.BackButton);
        //******************************************initialization part **********************************\\

        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(UserDashboardActivity.this, LoginActivity.class));
                        finish();

            }
        });

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                startActivity(new Intent(UserDashboardActivity.this, MainActivity.class));
                finish();
            }
        });

        //******************************************Method & initialization part **********************************\\
        search = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();//this method created for set userName & email
        loadCategory();// this method created for set category into the recyclerView .
        //******************************************Method & initialization part **********************************\\


        // ************************************* This part created for --> searching category(Filter) *************************************
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    adapterCategory.getFilter().filter(charSequence);
                } catch (Exception e) {
                    Toast.makeText(UserDashboardActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //**************************************LoadCategory SET DATA INTO THE RECYCLERVIEW PART ********************************************
    private void loadCategory() {
        ArrayList<ModelCategoryUser> categoryArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                // DataSnapshot হলো সেই অবজেক্ট যা ডেটাবেসের নির্দিষ্ট অংশের ডেটাকে ধরে রাখে। এটি Firebase থেকে ফেচ করা ডেটার রেফারেন্স হিসেবে কাজ করে।
                // Firebase ডাটাবেসের প্রতিটি চাইল্ড নোডের জন্য লুপ চালানো।
                for (DataSnapshot ds : snapshot.getChildren()) {//এটি একটি DataSnapshot অবজেক্ট, যা Firebase Realtime Database থেকে ডেটা পড়তে ব্যবহার করা হয়।
                    //     getValue() মেথডটি DataSnapshot থেকে ডেটা রিটার্ন করে।
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserDashboardActivity.this));
                    ModelCategoryUser model = ds.getValue(ModelCategoryUser.class);
                    categoryArrayList.add(model);
                }
                adapterCategory = new AdapterCategoryUser(UserDashboardActivity.this, categoryArrayList);
                recyclerView.setAdapter(adapterCategory);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //**************************************LoadCategory SET DATA INTO THE RECYCLERVIEW PART END ********************************************







    //    **********************************************SET USERNAME**********************************************
    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(UserDashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//            reference.child(currentUser.getUid())
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".","," ))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Name = "" + snapshot.child("Name").getValue();
                            String email = "" + snapshot.child("email").getValue();
//                            namee.setText(Name);
//                            emaill.setText(email);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }
    //    **********************************************SET USERNAME**********************************************

}