package com.example.myapplication.dashboardadmin.routinepart;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterModelClass extends Filter {

    ArrayList<ModelRoutine> FilterList = new ArrayList<>();
    public com.example.myapplication.dashboardadmin.routinepart.AdapterRoutine adapterRoutine;

    public FilterModelClass(ArrayList<ModelRoutine> FilterList, AdapterRoutine adapterRoutine) {
        this.FilterList = FilterList;
        this.adapterRoutine = adapterRoutine;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results  = new FilterResults();
        ArrayList<ModelRoutine> FilterModel = new ArrayList<>();
        charSequence = charSequence.toString().toUpperCase();

        if(charSequence != null && charSequence.length()>0){
            for(int i = 0; i< FilterList.size();i++){
                if(FilterList.get(i).getBatch().toUpperCase().contains(charSequence)){
                    FilterModel.add(FilterList.get(i));
                }
            }

            results.count = FilterModel.size();
            results.values = FilterModel;
        }else{
            results.count = FilterList.size();
            results.values = FilterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterRoutine.categoryBatch = (ArrayList<ModelRoutine>) filterResults.values;
        adapterRoutine.notifyDataSetChanged();
    }
}
