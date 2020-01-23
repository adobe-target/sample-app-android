package com.wetravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.wetravel.Models.City;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter {
    private LayoutInflater layoutInflater;
    ArrayList<City> cityList;
    Context mContext;

    FilterList filterList = new FilterList();
    ArrayList<City> filterArray;

    public CityAdapter(Context context, ArrayList<City> cityList){
        super(context, R.layout.item_city);
        this.mContext = context;
        this.cityList = cityList;

        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int pos, View mView, ViewGroup viewGroup){
        if(mView == null){
            mView = layoutInflater.inflate(R.layout.item_city,viewGroup,false);
        }

        TextView tvCity = mView.findViewById(R.id.tvCity);
        tvCity.setTextSize(Utility.txtSize_14dp);
        tvCity.setTypeface(Utility.font_roboto_regular);
        tvCity.setText(cityList.get(pos).getCity_name());

        return  mView;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return cityList.indexOf(item);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filterList;
    }

    public class FilterList extends Filter{
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if(filterArray == null){
                    synchronized (lock){
                        filterArray = new ArrayList<>(cityList);
                    }
            }

            if(prefix == null || prefix.length() == 0){
                synchronized (lock){
                    results.values = filterArray;
                    results.count = filterArray.size();
                }
            }else{
                String searchLowewrCase = prefix.toString().toLowerCase();

                ArrayList<City> matchValues = new ArrayList<>();

                for(City city : filterArray){
                    if(city.getCity_name().toLowerCase().startsWith(searchLowewrCase)){
                        matchValues.add(city);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values != null){
                    cityList = (ArrayList<City>) results.values;
                }else{
                    cityList = null;
                }
                if(results.count>0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
        }
    }
}
