package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dashboardadmin.AdapterCategory;
import com.example.myapplication.dashboardadmin.CategoryAddActivity;
import com.example.myapplication.dashboardadmin.ModelCategory;
import com.example.myapplication.dashboardadmin.pdfActivity;
import com.example.myapplication.register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreviousQuestionFragment extends Fragment {


    private EditText search;
    private TextView namee, emaill;
    private FirebaseAuth firebaseAuth;
    private AdapterCategory adapterCategory;
    private AppCompatButton addCategory, pdf;
    private ImageButton logout, backk;
    private RecyclerView recyclerView;
    private String UserType = "";

    public PreviousQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_previous_question, container, false);

        View view = inflater.inflate(R.layout.fragment_previous_question, container, false);


        namee = view.findViewById(R.id.textTv);
        emaill = view.findViewById(R.id.emailidd);
        recyclerView = view.findViewById(R.id.category_List);
        logout = view.findViewById(R.id.logOut);
        pdf = view.findViewById(R.id.addpdffb);
        backk = view.findViewById(R.id.BackButton);
        addCategory = view.findViewById(R.id.addcategoryBtn);
        search = view.findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();

        checkUser(); // Set userName & email
        loadCategory(); // Load category into RecyclerView

        // Logout button action
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });


        // Back button action
        backk.setOnClickListener(v -> getActivity().onBackPressed());

        // PDF button action
        pdf.setOnClickListener(v -> startActivity(new Intent(getActivity(), pdfActivity.class)));

        // Add Category button action
        addCategory.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CategoryAddActivity.class));
            getActivity().finish();
        });


        // Search filter
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    adapterCategory.getFilter().filter(charSequence);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;

    }


    //************************************** Load Categories into RecyclerView **************************************
    private void loadCategory() {
        ArrayList<ModelCategory> categoryArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    ModelCategory model = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(model);
                }
                adapterCategory = new AdapterCategory(getContext(), categoryArrayList, UserType);
                recyclerView.setAdapter(adapterCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //********************************************** Set Username **********************************************
    private void checkUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            UserType = "ADMIN";
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            getActivity().finish();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Name = "" + snapshot.child("Name").getValue();
                            String email = "" + snapshot.child("email").getValue();
                            UserType = "" + snapshot.child("userType").getValue(String.class);

                            if (!UserType.equals("ADMIN") && !UserType.equals("CR") && !UserType.equals("ADMIN_OR_CR")) {
                                addCategory.setVisibility(View.INVISIBLE);
                                pdf.setVisibility(View.INVISIBLE);
                            }

                            namee.setText(Name);
                            emaill.setText(email);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
        }
    }
}