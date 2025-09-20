package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.dashboardadmin.addAdminCr.SetAdminCrHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPagerAdapter  extends FragmentStateAdapter {

    private String userType = ""; // Default user type
    private FirebaseAuth firebaseAuth;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        firebaseAuth = FirebaseAuth.getInstance();
        fetchUserType();
        
    }

    private void fetchUserType() {
        if (firebaseAuth.getCurrentUser() != null) {
            String userEmail = firebaseAuth.getCurrentUser().getEmail();
            if (userEmail != null) {
                String safeEmail = userEmail.replace(".", ","); // Firebase key sanitization
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

                ref.child(safeEmail).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userType = snapshot.getValue(String.class);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        userType = "USER"; // Default fallback
                    }
                });
            }
        }
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new HomeFragment();
        }
        else if(position == 1){
            return new ProblemSolving();
        }
        else if(position == 2){
            return new RoomFrament();
        }
        else if(position == 3){
            if(userType.isEmpty()){
                return new ProfileFragment();
            }else{
                return new PreviousQuestionFragment();
            }
        }else if(position == 4){
            if (userType.equals("ADMIN") || userType.equals("ADMIN_OR_CR")) {
                return new FragmentAdminCr();
            } else {
                return new ProfileFragment();
            }
        }else{
            return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
