package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.RatingWiseProblemHome;
import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.createRatingWise;


public class ProblemSolving extends Fragment {

    private AppCompatButton categoryWise, ratingWiseBtn, luAcm, anotherBtn;


    public ProblemSolving() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_solving, container, false);

        // Set status bar color (only works in activity, for fragments handle in parent activity)
        requireActivity().getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        // Initialize buttons
        categoryWise = view.findViewById(R.id.Categorywist);
        ratingWiseBtn = view.findViewById(R.id.RatingWise);
        luAcm = view.findViewById(R.id.LuAcm);
        anotherBtn = view.findViewById(R.id.AnotherBtn);

        // Set click listeners
        categoryWise.setOnClickListener(v -> openCreateRatingWise("CATEGORYWISE"));
        ratingWiseBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), RatingWiseProblemHome.class)));
        luAcm.setOnClickListener(v -> openCreateRatingWise("LUACM"));
        anotherBtn.setOnClickListener(v -> openCreateRatingWise("EXTRA"));

        return view;
    }

    private void openCreateRatingWise(String platformName) {
        Intent intent = new Intent(getActivity(), createRatingWise.class);
        intent.putExtra("PlatformName", platformName);
        startActivity(intent);
    }
}