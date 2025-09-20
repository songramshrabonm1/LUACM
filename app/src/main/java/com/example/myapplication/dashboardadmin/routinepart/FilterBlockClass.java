package com.example.myapplication.dashboardadmin.routinepart;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.ModelCategory;

import java.util.ArrayList;

public class FilterBlockClass extends Filter {
    ArrayList<ModelFreeClassShow> filterList ;
    public com.example.myapplication.dashboardadmin.routinepart.AdapterBlockClass adapterBlockClass;

    public FilterBlockClass(ArrayList<ModelFreeClassShow> filterList, AdapterBlockClass adapterBlockClass) {
        this.filterList = filterList;
        this.adapterBlockClass = adapterBlockClass;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint!= null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelFreeClassShow> filteredModels = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getBatch().toUpperCase().contains(constraint)) {
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;


        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;


    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterBlockClass.blockArrayList = (ArrayList<ModelFreeClassShow>) results.values;
        adapterBlockClass.notifyDataSetChanged();
    }
}
