package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.dashboardadmin.addAdminCr.AddingSection;
import com.example.myapplication.dashboardadmin.addAdminCr.showInformation;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.MainHomeCourseSection;
import com.example.myapplication.register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentAdminCr extends Fragment {

    private ImageButton BackButtonControllingSectin;
    private AppCompatButton logout, CrAddAdmins, AddEmailRegisterStudents, showAdmin, showRegisterStudent;

    FirebaseAuth firebaseAuth;

    public FragmentAdminCr() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_cr, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        requireActivity().getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        BackButtonControllingSectin = view.findViewById(R.id.BackButtonControllingSectin);
        BackButtonControllingSectin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        showAdmin = view.findViewById(R.id.ShowEmailRegisterADMINCR);
        showAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), showInformation.class);
                intent.putExtra("DETAILS", "CRADDADMINADD");
                startActivity(intent);
            }
        });

        showRegisterStudent = view.findViewById(R.id.ShowEmailRegisterStudent);
        showRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), showInformation.class);
                intent.putExtra("DETAILS", "ADDEMAILREGISTERSTUDENT");
                startActivity(intent);
            }
        });

        CrAddAdmins = view.findViewById(R.id.crAdminAdd);
        CrAddAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddingSection.class);
                intent.putExtra("Type", "CRADDADMINADD");
                startActivity(intent);
            }
        });

        AddEmailRegisterStudents = view.findViewById(R.id.AddEmailRegisterStudent);
        AddEmailRegisterStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddingSection.class);
                intent.putExtra("Type", "ADDEMAILREGISTERSTUDENT");
                startActivity(intent);
            }
        });

        logout =view.findViewById(R.id.logoutAdmin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
}
