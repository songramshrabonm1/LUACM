package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myapplication.dashboardadmin.CategoryAddActivity;
import com.example.myapplication.dashboardadmin.routinepart.FreeClassRoomHome;
import com.example.myapplication.dashboardadmin.routinepart.UploadRoutine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomFrament extends Fragment {

    Button routine , freeclass ;
    RelativeLayout relative;

    private FirebaseAuth firebaseAuth;

    public RoomFrament() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_frament, container, false);

        relative = view.findViewById(R.id.searchingRealtiveLayout);
        routine = view.findViewById(R.id.seeRoutine);
        freeclass = view.findViewById(R.id.freeClassBtn);
        firebaseAuth = FirebaseAuth.getInstance();
//        checkUserType();

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadRoutine.class));
//                getActivity().finish();
            }
        });

        freeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FreeClassRoomHome.class));
//                getActivity().finish();
            }
        });



        return view;
    }

//    private void checkUserType() {
//        if (firebaseAuth.getCurrentUser() != null) {
//            String userEmail = firebaseAuth.getCurrentUser().getEmail();
//            if (userEmail != null) {
//                String safeEmail = userEmail.replace(".", ","); // Firebase key sanitization
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//
//                ref.child(safeEmail).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            String userType = snapshot.getValue(String.class);
//
//                            if (!userType.equals("ADMIN") && !userType.equals("ADMIN_OR_CR")) {
//                                relative.setVisibility(View.GONE); // Hide button for others
//                            } else {
//                                relative.setVisibility(View.VISIBLE); // Show button for admins
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        freeclass.setVisibility(View.GONE); // Default: Hide if error occurs
//                    }
//                });
//            }
//        } else {
//            relative.setVisibility(View.GONE); // Hide if no user is logged in
//        }
//    }
}