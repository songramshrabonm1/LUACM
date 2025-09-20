package com.example.myapplication.dashboardadmin;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> implements Filterable {
    //এই AdapterCategory class এ আমরা RecyclerView.Adapter class এর property কে implement করার জন্যে extend করেছি ।


    // Context object তৈরি করা হয়েছে যা অ্যাপ্লিকেশনের বর্তমান অবস্থা বা Activity বোঝায়। // আমাদের context টা  DashboardAdmin.class এর থেকে পাঠানো হচ্ছে ।
    private Context context;

    // categoryArrayList এবং filterList দুটি ArrayList, যেগুলো RecyclerView-এ ডেটা দেখানোর জন্য এবং ফিল্টার করার জন্য ব্যবহার হয়।
    public ArrayList<ModelCategory> categoryArrayList ,filterList;

    private String UserType = "";
    private com.example.myapplication.dashboardadmin.FilterCategory filter;

    public AdapterCategory(Context context, ArrayList<ModelCategory> categoryArrayList , String UserType) {
        this.context = context;
        this.categoryArrayList = categoryArrayList; //data component যা আমরা recyclerView object এর মাধ্যমে পাস করলাম ।
        this.filterList = categoryArrayList;
        this.UserType = UserType;
    }

    @NonNull
    @Override

    // onCreateViewHolder মেথডটি একটি নতুন View তৈরি করে, যা RecyclerView-এর একেকটি আইটেম উপস্থাপন করে।
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater ব্যবহার করে row_category লেআউটকে inflate করা হলো, যা RecyclerView-এর একেকটি row ডিজাইন করে। যা পরে রিটার্ন করে দেয় ।
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        // ViewHolder ক্লাসের একটি নতুন অবজেক্ট তৈরি করা হলো, যা row-এর ভিউ ধরে রাখে।
        ViewHolder vh = new ViewHolder(view);

        // ViewHolder রিটার্ন করা হলো।
        return vh;
    }

    @Override
    // onBindViewHolder মেথডটি RecyclerView-এর প্রতিটি row-এর ডেটা সেট করার জন্য ব্যবহার হয়।
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ডেটাবেস থেকে নির্দিষ্ট পজিশনের ডেটা categoryArrayList থেকে আনা হলো।
        ModelCategory modelCategory = categoryArrayList.get(position);
        String id = modelCategory.getId();
        String category = modelCategory.getCategory();
        String uniqueId = modelCategory.getUid();
        long timeStamp = modelCategory.getTimestamp();

        // row-এর TextView-তে ক্যাটাগরির নাম সেট করা হলো।
        holder.textView.setText(category);

        if(UserType.equals("ADMIN") || UserType.equals("CR") || UserType.equals("ADMIN_OR_CR")) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete বাটন ক্লিক করলে AlertDialog দেখানো হবে।
                    // AlertDialog তৈরি করা হলো।
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // ডিলিট আইকন সেট করা হলো।
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("Delete")
                            // মেসেজ এবং টাইটেল সেট করা হলো।
                            .setMessage("Are you sure you want to delete the category")
                            //dialgoue er vitor click korbo tai DialogueInterface.OnclickListener()
                            .setPositiveButton("Confirmed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteCategory(modelCategory, holder);
                                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
            });
        }else{
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfListAdminActivity.class);
                intent.putExtra("categoryId", id); // Data pass kortesi
                intent.putExtra("categoryTitle", category);
                intent.putExtra("UserType" , UserType);
                context.startActivity(intent); // Corrected line

            }
        });



    }


//    DeleteItem --> part
    private void deleteCategory(ModelCategory modelCategory, ViewHolder holder) {
//এখানে modelCategory অবজেক্ট থেকে id নেওয়া হচ্ছে, যা ডাটাবেসে ক্যাটাগরির ইউনিক আইডি হিসেবে ব্যবহৃত হয়। এই আইডির সাহায্যে আমরা ডাটাবেসে নির্দিষ্ট ক্যাটাগরিকে সনাক্ত করতে পারবো।
        String id = modelCategory.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Category").child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//এখানে runOnUiThread ব্যবহার করা হয়েছে, কারণ Firebase এর onSuccess মেথডটি ব্যাকগ্রাউন্ড থ্রেডে চলে, এবং UI আপডেট করার জন্য মূল থ্রেডে যেতে হয়।
// ((Activity) context) এর মাধ্যমে context-কে Activity হিসেবে কাস্ট করা হচ্ছে, যাতে UI থ্রেডে রান করতে পারে।
// UI থ্রেডে ফিরে এসে UI আপডেট করতে এই লাইনটি ব্যবহার করা হয়েছে। context কে Activity হিসেবে কাস্ট করা হয়েছে।
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Remove the item from the list and notify adapter
                        // ViewHolder থেকে রিসাইক্লার ভিউর বর্তমান আইটেমের পজিশন পাওয়া হচ্ছে।
                        int position = holder.getAdapterPosition();

                        // চেক করা হচ্ছে পজিশনটি বৈধ কিনা। NO_POSITION নয় এবং ০ বা তার বেশি হলে বৈধ।
                        if (position != RecyclerView.NO_POSITION && position >= 0) {
                            categoryArrayList.remove(position);
                            // পজিশন অনুযায়ী নির্দিষ্ট আইটেমটি ArrayList থেকে সরিয়ে ফেলা হচ্ছে।
                            notifyItemRemoved(position);
                            // রিসাইক্লার ভিউকে জানানো হচ্ছে যে, এই পজিশনের আইটেম রিমুভ হয়েছে।// update করা হচ্ছে বাকি আইটেম গুলো কে ।
                            notifyItemRangeChanged(position, categoryArrayList.size());
                        }
//                        categoryArrayList.remove(position);
//                        notifyItemRemoved(position);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            filter = new FilterCategory(filterList, AdapterCategory.this);
        }
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_title);
            deleteButton = itemView.findViewById(R.id.delete_icon);
        }
    }
}