package com.example.myapplication.dashboardadmin.addAdminCr;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class SetAdminCrHome extends AppCompatActivity {

   private  ImageButton BackButtonControllingSectin;
    private AppCompatButton CrAddAdmins, AddEmailRegisterStudents, showAdmin , showREgisterStuden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_admin_cr_home);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        BackButtonControllingSectin = findViewById(R.id.BackButtonControllingSectin);
        BackButtonControllingSectin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showAdmin = findViewById(R.id.ShowEmailRegisterADMINCR);
        showAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAdminCrHome.this, showInformation.class);
                intent.putExtra("DETAILS","CRADDADMINADD");
                startActivity(intent);
            }
        });
//        CRADDADMINADD ADDEMAILREGISTERSTUDENT
        showREgisterStuden = findViewById(R.id.ShowEmailRegisterStudent);
        showREgisterStuden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAdminCrHome.this, showInformation.class);
                intent.putExtra("DETAILS","ADDEMAILREGISTERSTUDENT");
                startActivity(intent);

            }
        });

        CrAddAdmins = findViewById(R.id.crAdminAdd);

        CrAddAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAdminCrHome.this, AddingSection.class);
                intent.putExtra("Type" , "CRADDADMINADD");
                startActivity(intent);
            }
        });

        AddEmailRegisterStudents = findViewById(R.id.AddEmailRegisterStudent);
        AddEmailRegisterStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAdminCrHome.this, AddingSection.class);
                intent.putExtra("Type" , "ADDEMAILREGISTERSTUDENT");
                startActivity(intent);
            }
        });

    }
}

