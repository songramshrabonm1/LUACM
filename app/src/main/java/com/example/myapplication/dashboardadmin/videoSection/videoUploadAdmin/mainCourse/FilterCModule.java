package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.ModelCategory;

import java.util.ArrayList;

public class FilterCModule extends Filter {

    ArrayList<ModelCModule> filterList  = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.AdapterCModule adapterCModule;

    public FilterCModule(ArrayList<ModelCModule> filterList, AdapterCModule adapterCModule) {
        this.filterList = filterList;
        this.adapterCModule = adapterCModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();
        ArrayList<ModelCModule> filterModel = new ArrayList<>();
        charSequence = charSequence.toString().toUpperCase();

        if(charSequence != null && charSequence.length() > 0){
            for(int i = 0 ; i<filterList.size() ; i++){
                if(filterList.get(i).getVideoTitle().toUpperCase().contains(charSequence)){
                    filterModel.add(filterList.get(i));
                }
            }

            filterResults.count = filterModel.size();
            filterResults.values = filterModel;
        }else{
            filterResults.count = filterList.size();
            filterResults.values = filterList;
        }
        return  filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        // ফিল্টার করা লিস্টটি অ্যাডাপ্টারের categoryArrayList এ সেট করা হচ্ছে।
        adapterCModule.categoryModule = (ArrayList<ModelCModule>) filterResults.values;
        // অ্যাডাপ্টারকে জানানো হচ্ছে যে ডেটা পরিবর্তন হয়েছে এবং রিসাইক্লার ভিউ আপডেট করতে হবে।
        adapterCModule.notifyDataSetChanged();

    }
}
