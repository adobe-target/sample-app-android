package com.wetravel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wetravel.Models.Loyalty;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class LoyaltyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    ArrayList<Loyalty> loyalties;
    Context mContext;

    public LoyaltyAdapter(Context context, ArrayList<Loyalty> loyalties){
        this.mContext = context;
        this.loyalties = loyalties;

        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int pos, View mView, ViewGroup viewGroup){
        if(mView == null){
            mView = layoutInflater.inflate(R.layout.item_city,viewGroup,false);
        }

        TextView tvLoyalty = mView.findViewById(R.id.tvCity);
        tvLoyalty.setTextSize(Utility.txtSize_14dp);
        tvLoyalty.setTypeface(Utility.font_roboto_regular_italic);
        tvLoyalty.setTextColor(mContext.getResources().getColor(R.color.color_505050));
        tvLoyalty.setText(loyalties.get(pos).getName());

        return  mView;
    }

    @Override
    public int getCount() {
        return loyalties.size();
    }

    @Override
    public Object getItem(int position) {
        return loyalties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
