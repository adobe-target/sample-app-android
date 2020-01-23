package com.wetravel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wetravel.Controller.SearchBusActivity;
import com.wetravel.Models.Offer;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class SearchOffersAdapter extends RecyclerView.Adapter<SearchOffersAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Offer> offerList;

    public SearchOffersAdapter(Context context, ArrayList<Offer> offerList){
        this.context = context;
        this.offerList = offerList;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgBanner;


        public OfferViewHolder(View itemView) {
            super(itemView);

            //Banner Property
            imgBanner = itemView.findViewById(R.id.imgBanner);
            imgBanner.getLayoutParams().width = Utility.deviceWidth*40/100;
        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_offer,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {
        holder.imgBanner.setTag(position);

        if(Utility.getDrawableFromAssets(context,"IMAGES/"+ Utility.deviceDensityImage + "/" +offerList.get(position).getOffer_banner()) != null) {
            Bitmap bitmap = Utility.drawableToBitmap(context,(Utility.getDrawableFromAssets(context, "IMAGES/"+ Utility.deviceDensityImage+"/"+ offerList.get(position).getOffer_banner())));
            holder.imgBanner.setImageBitmap(bitmap);
        }

        holder.imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchBusActivity)context).setFilterForOffers(offerList.get(position).getTag_filter(),offerList.get(position).getFull_offer_banner());
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
