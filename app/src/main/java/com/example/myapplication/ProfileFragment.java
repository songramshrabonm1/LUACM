package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.dashboardadmin.CategoryAddActivity;
import com.example.myapplication.register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {


    FirebaseAuth firebaseAuth;
    TextView userName , userEmail , user_types;
    String name , email , userType;
    AppCompatButton logoutButton ;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        logoutButton = view.findViewById(R.id.logout_button);
        user_types = view.findViewById(R.id.user_type);
        checkUser();



        logoutButton.setOnClickListener(v -> {
            firebaseAuth.signOut();

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }


    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
//            UserType = "ADMIN";
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                             name = "" + snapshot.child("Name").getValue();
                             email = "" + snapshot.child("email").getValue();
                             userType = "" + snapshot.child("userType").getValue();

                             userName.setText(name);
                             userEmail.setText(email);
                             user_types.setText(userType);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
        }
    }
}