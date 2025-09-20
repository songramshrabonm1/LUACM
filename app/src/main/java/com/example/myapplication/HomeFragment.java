package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.MainHomeCourseSection;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual.ConceptualSessionAdmin;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.probleSolvingUpload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    ImageButton backBtn;
    AppCompatButton mainCrsBtnContinue1, probleSolvingBtn, conceptualBtn;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String UserType = "";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance();
        loadUser();

        // Back Button
        backBtn = view.findViewById(R.id.BackButton);
        backBtn.setOnClickListener(v -> {
            // Fragment হলে `onBackPressed()` সরাসরি কাজ করবে না, `getActivity().onBackPressed()` ব্যবহার করতে পারেন
            requireActivity().onBackPressed();
        });

        // Main Course Button
        mainCrsBtnContinue1 = view.findViewById(R.id.continue_buttonFundamental);
        mainCrsBtnContinue1.setOnClickListener(v -> {
            UserType = UserType.toUpperCase().trim();
            System.out.println("Main Course Button e UserType: " + UserType);
            Intent intent = new Intent(getActivity(), MainHomeCourseSection.class);
            startActivity(intent);
        });

        // Problem Solving Button
        probleSolvingBtn = view.findViewById(R.id.continue_buttonCodeForcestwo);
        probleSolvingBtn.setOnClickListener(v -> {
            UserType = UserType.toUpperCase().trim();
            System.out.println("Problem Solving Button e UserType: " + UserType);
            Intent intent = new Intent(getActivity(), probleSolvingUpload.class);
            intent.putExtra("UserType", UserType);
            startActivity(intent);
        });

        // Conceptual Session Button
        conceptualBtn = view.findViewById(R.id.continue_buttonCodechefthree);
        conceptualBtn.setOnClickListener(v -> {
            UserType = UserType.toUpperCase().trim();
            System.out.println("Conceptual Course Button e UserType: " + UserType);
            Intent intent = new Intent(getActivity(), ConceptualSessionAdmin.class);
            intent.putExtra("UserType", UserType);
            startActivity(intent);
        });
    }

    private void loadUser() {
        if (firebaseAuth.getCurrentUser() != null) { // Check if user is logged in
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserType = "" + snapshot.child("userType").getValue();
                                System.out.println("LoadUser এর ভিতরে: " + UserType);
                            } else {
                                System.out.println("User data not found in database.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Database Error: " + error.getMessage());
                        }
                    });
        } else {
            System.out.println("User not logged in. Redirecting to Login.");
            // এখানে আপনি Login Activity-তে পাঠাতে পারেন
        }
    }

}
