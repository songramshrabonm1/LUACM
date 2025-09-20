package com.example.myapplication.dashboardadmin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
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

public class DashboardAdmin extends AppCompatActivity {

    EditText search;
    TextView namee, emaill;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    //    private ArrayList<ModelCategory> categoryArrayList;
    private AdapterCategory adapterCategory;
    AppCompatButton addCategory, pdf;
    ImageButton logout;
    ImageButton backk;
    String UserType = "";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);



        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        //****************************************** initialization part **********************************\\
        namee = findViewById(R.id.textTv);
        emaill = findViewById(R.id.emailidd);
        recyclerView = findViewById(R.id.category_List);
        logout = findViewById(R.id.logOut);
        pdf = findViewById(R.id.addpdffb);
        backk = findViewById(R.id.BackButton);
        firebaseAuth = FirebaseAuth.getInstance();
        //******************************************initialization part **********************************\\

        checkUser();//this method created for set userName & email



        //***************************************LogOUt a user *********************************************
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardAdmin.this, LoginActivity.class));
                    finish();
            }
        });
        //***************************************LdogOUt a user *********************************************


        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashboardAdmin.this, MainActivity.class));
//                finish();
                finish();
            }
        });


        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdmin.this, pdfActivity.class));
//                finish();
            }
        });


        //******************************************Method & initialization part **********************************\\
        addCategory = findViewById(R.id.addcategoryBtn);
        search = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
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
                    Toast.makeText(DashboardAdmin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // ************************************* This part created for --> searching category *************************************


        //************************* Goto CategoryAddActivity Class *************************
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdmin.this, CategoryAddActivity.class));
                finish();
            }
        });
        //************************************************* Goto CategoryActivity Class *************************************************


    }


    //************************************** SET DATA INTO THE RECYCLERVIEW PART ********************************************
    private void loadCategory() {
        ArrayList<ModelCategory> categoryArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                // DataSnapshot হলো সেই অবজেক্ট যা ডেটাবেসের নির্দিষ্ট অংশের ডেটাকে ধরে রাখে। এটি Firebase থেকে ফেচ করা ডেটার রেফারেন্স হিসেবে কাজ করে।
                // Firebase ডাটাবেসের প্রতিটি চাইল্ড নোডের জন্য লুপ চালানো।
                for (DataSnapshot ds : snapshot.getChildren()) {//এটি একটি DataSnapshot অবজেক্ট, যা Firebase Realtime Database থেকে ডেটা পড়তে ব্যবহার করা হয়।
                    //     getValue() মেথডটি DataSnapshot থেকে ডেটা রিটার্ন করে।
                    recyclerView.setLayoutManager(new LinearLayoutManager(DashboardAdmin.this));
                    ModelCategory model = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(model);
                }
                adapterCategory = new AdapterCategory(DashboardAdmin.this, categoryArrayList , UserType);
                recyclerView.setAdapter(adapterCategory);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //************************************** SET DATA INTO THE RECYCLERVIEW PART END ********************************************







//    **********************************************SET USERNAME**********************************************
    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(DashboardAdmin.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//            reference.child(currentUser.getUid())
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Name = "" + snapshot.child("Name").getValue();
                            String email = "" + snapshot.child("email").getValue();
                            UserType = ""+snapshot.child("userType").getValue(String.class);
                            if(UserType.equals("ADMIN") || UserType.equals("CR") || UserType.equals("ADMIN_OR_CR")) {

                            }else{
                                addCategory.setVisibility(View.INVISIBLE);
                                pdf.setVisibility(View.INVISIBLE);
                            }
                            namee.setText(Name);
                            emaill.setText(email);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }
    //    **********************************************SET USERNAME**********************************************

}