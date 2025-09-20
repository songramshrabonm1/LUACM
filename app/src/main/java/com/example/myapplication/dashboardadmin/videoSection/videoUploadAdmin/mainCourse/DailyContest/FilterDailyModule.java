package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.ModelAlgoModule;

import java.util.ArrayList;

public class FilterDailyModule extends Filter {

    ArrayList<ModelDailyContestModule> filterList = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.AdapterContestModule adapterContestModule;

    public FilterDailyModule(ArrayList<ModelDailyContestModule> filterList, AdapterContestModule adapterContestModule) {
        this.filterList = filterList;
        this.adapterContestModule = adapterContestModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() >0){
            ArrayList<ModelDailyContestModule> filterModel = new ArrayList<>();
            charSequence = charSequence.toString().toUpperCase();
            for(int i = 0 ;i<filterList.size(); i++){
                if(filterList.get(i).getContestName().contains(charSequence)){
                    filterModel.add(filterList.get(i));

                }
            }
            results.count = filterModel.size();
            results.values = filterModel;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterContestModule.categoryModule = (ArrayList<ModelDailyContestModule>) filterResults.values;
        adapterContestModule.notifyDataSetChanged();
    }
}
