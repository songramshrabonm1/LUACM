package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.ModelCppModule;

import java.util.ArrayList;

public class FilterDsModule extends Filter {

    ArrayList<ModelDsModule> filterList = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure.AdapterDSModule adapterDSModule;

    public FilterDsModule(ArrayList<ModelDsModule> filterList, AdapterDSModule adapterDSModule) {
        this.filterList = filterList;
        this.adapterDSModule = adapterDSModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if(charSequence != null && charSequence.length()>0){
            ArrayList<ModelDsModule> filterModel = new ArrayList<>();
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
        return  results;    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        // ফিল্টার করা লিস্টটি অ্যাডাপ্টারের categoryArrayList এ সেট করা হচ্ছে।
        adapterDSModule.categoryModule = (ArrayList<ModelDsModule>) filterResults.values;
        // অ্যাডাপ্টারকে জানানো হচ্ছে যে ডেটা পরিবর্তন হয়েছে এবং রিসাইক্লার ভিউ আপডেট করতে হবে।
        adapterDSModule.notifyDataSetChanged();
    }
}
