package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.ModelCModule;

import java.util.ArrayList;

public class FilterCppModule extends Filter {

    ArrayList<ModelCppModule> filterList  = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.AdapterCppModule adapterCppModule;

    public FilterCppModule(ArrayList<ModelCppModule> filterList, AdapterCppModule adapterCppModule) {
        this.filterList = filterList;
        this.adapterCppModule = adapterCppModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if(charSequence != null && charSequence.length()>0){
                ArrayList<ModelCppModule> filterModel = new ArrayList<>();
                charSequence = charSequence.toString().toUpperCase();
                for(int i = 0 ; i<filterList.size();i++){
                    if(filterList.get(i).getVideoTitle().toUpperCase().contains(charSequence)){
                        filterModel.add(filterList.get(i));
                    }
                }
                results.count = filterModel.size();
                results.values = filterModel;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return  results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        // ফিল্টার করা লিস্টটি অ্যাডাপ্টারের categoryArrayList এ সেট করা হচ্ছে।
        adapterCppModule.categoryModule = (ArrayList<ModelCppModule>) filterResults.values;
        // অ্যাডাপ্টারকে জানানো হচ্ছে যে ডেটা পরিবর্তন হয়েছে এবং রিসাইক্লার ভিউ আপডেট করতে হবে।
        adapterCppModule.notifyDataSetChanged();

    }
}
