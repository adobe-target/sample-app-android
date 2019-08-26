package com.wetravel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Controller.SearchBusActivity;
import com.wetravel.Models.Offer;
import com.wetravel.Models.Travel;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class SearchTravelsAdapter extends RecyclerView.Adapter<SearchTravelsAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Travel> serachTravelsList;
    String selectTravelId = "";

    public SearchTravelsAdapter(Context context, ArrayList<Travel> serachTravelsList){
        this.context = context;
        this.serachTravelsList = serachTravelsList;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout rlItem,rlOffers,rlTravelDetails;
        ImageView imgDeal,imgCheck;
        TextView tvOfferName,tvTravelName,tvTravelTime,tvBusInfo,tvBusDetails,tvRatings,tvPriceFrom;
        ImageView imgRatingOne,imgRatingtwo,imgRatingThree,imgRatingFour,imgRatingFive;


        public OfferViewHolder(View itemView) {
            super(itemView);

            rlOffers = itemView.findViewById(R.id.rlOffers);
            rlOffers.setPadding(Utility.deviceWidth*3/100,Utility.deviceWidth*1/100,0,0);

            rlItem = itemView.findViewById(R.id.rlItem);
            RelativeLayout.LayoutParams paramsrlItem = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsrlItem.setMargins(Utility.deviceWidth*5/100,0,Utility.deviceWidth*5/100,Utility.deviceWidth*3/100);
            rlItem.setLayoutParams(paramsrlItem);
            rlItem.setPadding(Utility.deviceWidth*2/100,0,Utility.deviceWidth*2/100,Utility.deviceWidth*2/100);

            imgDeal = itemView.findViewById(R.id.imgDeal);

            tvOfferName = itemView.findViewById(R.id.tvOfferName);
            tvOfferName.setTextSize(Utility.txtSize_8dp);
            tvOfferName.setTypeface(Utility.font_roboto_regular);

            rlTravelDetails = itemView.findViewById(R.id.rlTravelDetails);
            rlTravelDetails.setPadding(Utility.deviceWidth*2/100,Utility.deviceWidth*2/100,Utility.deviceWidth*2/100,Utility.deviceWidth*2/100);

            imgCheck = itemView.findViewById(R.id.imgCheck);
            RelativeLayout.LayoutParams paramsimgCheck= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsimgCheck.setMargins(0,Utility.deviceWidth*15/1000,0,0);
            imgCheck.setLayoutParams(paramsimgCheck);

            tvTravelName = itemView.findViewById(R.id.tvTravelName);
            tvTravelName.setTextSize(Utility.txtSize_15dp);
            tvTravelName.setTypeface(Utility.font_roboto_regular);

            tvTravelTime = itemView.findViewById(R.id.tvTravelTime);
            tvTravelTime.setTextSize(Utility.txtSize_15dp);
            tvTravelTime.setTypeface(Utility.font_roboto_regular);

            tvBusInfo = itemView.findViewById(R.id.tvBusInfo);
            tvBusInfo.setTextSize(Utility.txtSize_12dp);
            tvBusInfo.setTypeface(Utility.font_roboto_light);

            tvBusDetails = itemView.findViewById(R.id.tvBusDetails);
            tvBusDetails.setTextSize(Utility.txtSize_10dp);
            tvBusDetails.setTypeface(Utility.font_roboto_light);

            tvRatings = itemView.findViewById(R.id.tvRatings);
            tvRatings.setTextSize(Utility.txtSize_7dp);

            tvPriceFrom = itemView.findViewById(R.id.tvPriceFrom);
            tvPriceFrom.setTextSize(Utility.txtSize_15dp);
            tvPriceFrom.setTypeface(Utility.font_roboto_bold);

            imgRatingOne = itemView.findViewById(R.id.imgRatingOne);
            imgRatingOne.getLayoutParams().width = Utility.deviceWidth*3/100;

            imgRatingtwo = itemView.findViewById(R.id.imgRatingtwo);
            imgRatingtwo.getLayoutParams().width = Utility.deviceWidth*3/100;

            imgRatingThree = itemView.findViewById(R.id.imgRatingThree);
            imgRatingThree.getLayoutParams().width = Utility.deviceWidth*3/100;

            imgRatingFour = itemView.findViewById(R.id.imgRatingFour);
            imgRatingFour.getLayoutParams().width = Utility.deviceWidth*3/100;

            imgRatingFive = itemView.findViewById(R.id.imgRatingFive);
            imgRatingFive.getLayoutParams().width = Utility.deviceWidth*3/100;
        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_travels,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, final int position) {
        holder.tvTravelName.setText(serachTravelsList.get(position).getTravel_name());
        holder.tvTravelTime.setText(serachTravelsList.get(position).getDeparture_time());
        holder.tvPriceFrom.setText("From $"+serachTravelsList.get(position).getTravel_price());
        holder.tvBusInfo.setText(serachTravelsList.get(position).getBus_info());
        holder.tvBusDetails.setText(serachTravelsList.get(position).getBus_sets()+" Seats | "+serachTravelsList.get(position).getJourney_time()+" hrs | "+serachTravelsList.get(position).getBus_stop()+" rest stop");
        holder.tvRatings.setText(serachTravelsList.get(position).getCount_ratings()+" Ratings");

        if(!serachTravelsList.get(position).getOffer_text().equalsIgnoreCase("")){
            holder.rlItem.setBackgroundResource(R.drawable.bg_search_result_offer);
            holder.rlOffers.setVisibility(View.VISIBLE);
            holder.imgCheck.setBackgroundResource(R.drawable.radio_without_select);
            holder.tvOfferName.setText(serachTravelsList.get(position).getOffer_text());
        }else{
            holder.rlItem.setBackgroundResource(R.drawable.bg_search_result);
            holder.rlOffers.setVisibility(View.GONE);
            holder.imgCheck.setBackgroundResource(R.drawable.radio_without_select);
        }

        if(selectTravelId.equalsIgnoreCase(serachTravelsList.get(position).getId())){
            if(!serachTravelsList.get(position).getOffer_text().equalsIgnoreCase("")){
                holder.rlItem.setBackgroundResource(R.drawable.bg_search_result_offer_blue);
                holder.rlOffers.setVisibility(View.VISIBLE);
                holder.imgCheck.setBackgroundResource(R.drawable.radio_select);
                holder.tvOfferName.setText(serachTravelsList.get(position).getOffer_text());
            }else{
                holder.rlItem.setBackgroundResource(R.drawable.bg_search_result_blue);
                holder.rlOffers.setVisibility(View.GONE);
                holder.imgCheck.setBackgroundResource(R.drawable.radio_select);
            }
        }

        if(Integer.parseInt(serachTravelsList.get(position).getAverage_rating())==1){
            holder.imgRatingOne.setVisibility(View.VISIBLE);
            holder.imgRatingtwo.setVisibility(View.GONE);
            holder.imgRatingThree.setVisibility(View.GONE);
            holder.imgRatingFour.setVisibility(View.GONE);
            holder.imgRatingFive.setVisibility(View.GONE);
        }else if(Integer.parseInt(serachTravelsList.get(position).getAverage_rating())==2){
            holder.imgRatingOne.setVisibility(View.VISIBLE);
            holder.imgRatingtwo.setVisibility(View.VISIBLE);
            holder.imgRatingThree.setVisibility(View.GONE);
            holder.imgRatingFour.setVisibility(View.GONE);
            holder.imgRatingFive.setVisibility(View.GONE);
        }else if(Integer.parseInt(serachTravelsList.get(position).getAverage_rating())==3){
            holder.imgRatingOne.setVisibility(View.VISIBLE);
            holder.imgRatingtwo.setVisibility(View.VISIBLE);
            holder.imgRatingThree.setVisibility(View.VISIBLE);
            holder.imgRatingFour.setVisibility(View.GONE);
            holder.imgRatingFive.setVisibility(View.GONE);
        }else if(Integer.parseInt(serachTravelsList.get(position).getAverage_rating())==4){
            holder.imgRatingOne.setVisibility(View.VISIBLE);
            holder.imgRatingtwo.setVisibility(View.VISIBLE);
            holder.imgRatingThree.setVisibility(View.VISIBLE);
            holder.imgRatingFour.setVisibility(View.VISIBLE);
            holder.imgRatingFive.setVisibility(View.GONE);
        }else if(Integer.parseInt(serachTravelsList.get(position).getAverage_rating())==5){
            holder.imgRatingOne.setVisibility(View.VISIBLE);
            holder.imgRatingtwo.setVisibility(View.VISIBLE);
            holder.imgRatingThree.setVisibility(View.VISIBLE);
            holder.imgRatingFour.setVisibility(View.VISIBLE);
            holder.imgRatingFive.setVisibility(View.VISIBLE);
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectTravelId = serachTravelsList.get(position).getId();
                 String travelName = serachTravelsList.get(position).getTravel_name();
                 String departureTime = serachTravelsList.get(position).getDeparture_time();
                 String busInfo = serachTravelsList.get(position).getBus_info();
                ((SearchBusActivity)context).setTravelSelectable(selectTravelId,travelName,departureTime,busInfo);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return serachTravelsList.size();
    }

    public void gotoupdate(ArrayList<Travel> serachTravelsList){
        this.serachTravelsList = serachTravelsList;
        notifyDataSetChanged();
    }
}
