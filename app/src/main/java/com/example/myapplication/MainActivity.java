package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.window.SplashScreen;

import com.example.myapplication.dashboardadmin.AdminDashboardHome;
import com.example.myapplication.dashboardadmin.DashboardAdmin;
import com.example.myapplication.dashboarduser.UserDashboarHome;
import com.example.myapplication.dashboarduser.UserDashboardActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.myapplication.register.LoginActivity;  // Import the LoginActivity from register package
import com.example.myapplication.register.RegisterActivity;

public class
MainActivity extends AppCompatActivity {

    Button login ,skip;
    BottomNavigationView bottomNavigationView ;
    ViewPager2 viewPager ;
    TextView textView;
    private String UserType = "";
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SplashScreen.installSplashScreen(this);
        //******************************************BUTTON INITIALIZE*********************************************
        login = findViewById(R.id.login);
        skip = findViewById(R.id.skip);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        viewPager = findViewById(R.id.imagesViewPager);
        textView = findViewById(R.id.text);



        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        // এই টা  করতি হয় নিচের bottom layout এর জন্য
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        menu = bottomNavigationView.getMenu();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserType = "" + snapshot.child("userType").getValue();

                    if (UserType.equals("USER")) {
                        menu.findItem(R.id.navProblem).setVisible(false);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navVideo) {
                    viewPager.setCurrentItem(0, true);
                } else if (itemId == R.id.navProblem) {
                    viewPager.setCurrentItem(1, true);
                } else if (itemId == R.id.navRoutine) {
                    viewPager.setCurrentItem(2, true);
                } else if (itemId == R.id.nvaQuestion) {
                    viewPager.setCurrentItem(3, true);
                } else if (itemId == R.id.profile) {
                    viewPager.setCurrentItem(4, true);
                }

                return true; // **এটা TRUE রাখতে হবে, নাহলে কাজ করে  না!**
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    bottomNavigationView.setSelectedItemId(R.id.navVideo);
                    textView.setText("Home");
                }
                else if(position == 1){
                    bottomNavigationView.setSelectedItemId(R.id.navProblem);
                    textView.setText("Problem Solving");
                }
                else if(position == 2){
                    bottomNavigationView.setSelectedItemId(R.id.navRoutine);
                    textView.setText("Lu Student");
                }else if(position == 3){
                    bottomNavigationView.setSelectedItemId(R.id.nvaQuestion);
                    textView.setText("Question Set");
                }else if(position == 4){
                    bottomNavigationView.setSelectedItemId(R.id.profile);
                    textView.setText("Profile");
                }
            }
        });
        //******************************************BUTTON INITIALIZE*********************************************

    }


}