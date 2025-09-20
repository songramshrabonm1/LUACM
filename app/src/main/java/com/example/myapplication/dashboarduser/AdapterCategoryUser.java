package com.example.myapplication.dashboarduser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.PdfListAdminActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCategoryUser extends RecyclerView.Adapter<AdapterCategoryUser.ViewHolder> implements Filterable {
    //এই AdapterCategory class এ আমরা RecyclerView.Adapter class এর property কে implement করার জন্যে extend করেছি ।

    // Context object তৈরি করা হয়েছে যা অ্যাপ্লিকেশনের বর্তমান অবস্থা বা Activity বোঝায়। // আমাদের context টা  DashboardAdmin.class এর থেকে পাঠানো হচ্ছে ।
    private Context context;

    // categoryArrayList এবং filterList দুটি ArrayList, যেগুলো RecyclerView-এ ডেটা দেখানোর জন্য এবং ফিল্টার করার জন্য ব্যবহার হয়।
    public ArrayList<ModelCategoryUser> categoryArrayList ,filterList;


//    private com.example.myapplication.dashboardadmin.FilterCategory filter;
    private com.example.myapplication.dashboarduser.FilterCategoryUser filter;

    public AdapterCategoryUser(Context context, ArrayList<ModelCategoryUser> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList; //data component যা আমরা recyclerView object এর মাধ্যমে পাস করলাম ।
        this.filterList = categoryArrayList;
    }

    @NonNull
    @Override

    // onCreateViewHolder মেথডটি একটি নতুন View তৈরি করে, যা RecyclerView-এর একেকটি আইটেম উপস্থাপন করে।
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater ব্যবহার করে row_category লেআউটকে inflate করা হলো, যা RecyclerView-এর একেকটি row ডিজাইন করে। যা পরে রিটার্ন করে দেয় ।
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_category, parent, false);
        // ViewHolder ক্লাসের একটি নতুন অবজেক্ট তৈরি করা হলো, যা row-এর ভিউ ধরে রাখে।
        ViewHolder vh = new ViewHolder(view);
        // ViewHolder রিটার্ন করা হলো।
        return vh;
    }

    @Override
    // onBindViewHolder মেথডটি RecyclerView-এর প্রতিটি row-এর ডেটা সেট করার জন্য ব্যবহার হয়।
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ডেটাবেস থেকে নির্দিষ্ট পজিশনের ডেটা categoryArrayList থেকে আনা হলো।
        ModelCategoryUser modelCategory = categoryArrayList.get(position);
        String id = modelCategory.getId();
        String category = modelCategory.getCategory();
        String uniqueId = modelCategory.getUid();
        long timeStamp = modelCategory.getTimestamp();

        // row-এর TextView-তে ক্যাটাগরির নাম সেট করা হলো।
        holder.textView.setText(category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfListUserActivity.class);
                intent.putExtra("categoryId", id); // Data pass kortesi
                intent.putExtra("categoryTitle", category);
                context.startActivity(intent); // Corrected line
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){ //filter variable decalare 32 no line
            filter = new FilterCategoryUser(filterList, AdapterCategoryUser.this);
        }
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_titleUser);
        }
    }
}