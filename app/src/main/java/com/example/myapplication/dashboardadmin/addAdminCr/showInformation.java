package com.example.myapplication.dashboardadmin.addAdminCr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showInformation extends AppCompatActivity {

    private EditText searchInfo ;
    private RecyclerView recyclerView ;
//    private ArrayList<ModelShowInfo> categoryInfoArrayList ;
    private String info = "";
    FirebaseAuth firebaseAuth ;
    String User = "";
    public com.example.myapplication.dashboardadmin.addAdminCr.AdapterInformation adapterInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);


        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        firebaseAuth = FirebaseAuth.getInstance();
        usersType();
        info = getIntent().getStringExtra("DETAILS");

        searchInfo = findViewById(R.id.inforsearchText);

        recyclerView = findViewById(R.id.informationRecyclerView);
        loadCategoryInfo();




        searchInfo = findViewById(R.id.inforsearchText);
        searchInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try{
                    adapterInformation.getFilter().filter(charSequence);

                }catch (Exception e){
                    Toast.makeText(showInformation.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }





    private void loadCategoryInfo() {

        ArrayList<ModelShowInfo> categoryInfoArrayList  = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminAddingSection");
        ref.child(info)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        categoryInfoArrayList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){

                            recyclerView.setLayoutManager(new LinearLayoutManager(showInformation.this));
                            ModelShowInfo modelShowInfo = ds.getValue(ModelShowInfo.class);
                            categoryInfoArrayList.add(modelShowInfo);
//                            AdapterInformation adapterInformation = new AdapterInformation()
                        }
                  adapterInformation = new AdapterInformation(showInformation.this ,categoryInfoArrayList , info , User);
                        recyclerView.setAdapter(adapterInformation);
                    }
//                    ADMIN OR CR
//                    USERTYPE

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(showInformation.this, "Failed ", Toast.LENGTH_SHORT).show();
                    }
                });



    }


    private void usersType() {

        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User = ""+snapshot.child("userType").getValue();
                        User = User.toString().toUpperCase();
                        System.out.println("Users : "+ User);
//                        if(User.equals("ADMIN")){
////                            uploadRoutineButton.setVisibility(View.VISIBLE);
//                        }else{
////                            uploadRoutineButton.setVisibility(View.INVISIBLE);
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}