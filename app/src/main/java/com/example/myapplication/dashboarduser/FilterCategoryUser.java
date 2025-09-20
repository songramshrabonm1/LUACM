package com.example.myapplication.dashboarduser;


import android.widget.Filter;

import java.util.ArrayList;

public class FilterCategoryUser extends Filter {
    private ArrayList<ModelCategoryUser> FilterList;
//    public com.example.myapplication.dashboardadmin.AdapterCategoryUser adapterCategory;
    public com.example.myapplication.dashboarduser.AdapterCategoryUser adapterCategory;

    public FilterCategoryUser(ArrayList<ModelCategoryUser> FilterList, AdapterCategoryUser adapterCategory) {
        this.FilterList = FilterList;
        this.adapterCategory = adapterCategory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        // FilterResults অবজেক্ট তৈরি করা হচ্ছে, যা ফিল্টারের ফলাফল ধারণ করবে।
        FilterResults results = new FilterResults();

        //(যে টেক্সট দিয়ে সার্চ করা হচ্ছে) null না হয় এবং তার length 0 এর থেকে বেশি হয়, তখন নিচের কাজগুলো হবে।
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCategoryUser> filteredModels = new ArrayList<>();
            for (int i = 0; i < FilterList.size(); i++) {
                if (FilterList.get(i).getCategory().toUpperCase().contains(constraint)) {
                    filteredModels.add(FilterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        } else {
            results.count = FilterList.size();
            results.values = FilterList;
        }
        return results;
    }
    @Override
// publishResults মেথডটি UI থ্রেডে চলে এবং এখানে ফিল্টারিংয়ের ফলাফল দেখানো হয়।
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        // ফিল্টার করা লিস্টটি অ্যাডাপ্টারের categoryArrayList এ সেট করা হচ্ছে।
        adapterCategory.categoryArrayList = (ArrayList<ModelCategoryUser>) filterResults.values;
        // অ্যাডাপ্টারকে জানানো হচ্ছে যে ডেটা পরিবর্তন হয়েছে এবং রিসাইক্লার ভিউ আপডেট করতে হবে।
        adapterCategory.notifyDataSetChanged();
    }
}