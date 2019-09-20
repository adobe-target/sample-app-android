package com.wetravel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Models.Offer;
import com.wetravel.Models.Recommandation;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class HistoryRecommendationsAdapter extends RecyclerView.Adapter<HistoryRecommendationsAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Recommandation> recommandations;

    public HistoryRecommendationsAdapter(Context context, ArrayList<Recommandation> recommandations){
        this.context = context;
        this.recommandations = recommandations;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout rlItem;
        ImageView img;

        public OfferViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

            //Banner Property
            rlItem = itemView.findViewById(R.id.rlItem);

        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historey_recommendations,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {
        holder.rlItem.setTag(position);

        if(Utility.getDrawableFromAssets(context,"IMAGES/"+ Utility.deviceDensityImage + "/" +recommandations.get(position).getBanner()) != null) {
            Bitmap bitmap = Utility.drawableToBitmap(context, (Utility.getDrawableFromAssets(context, "IMAGES/" + Utility.deviceDensityImage + "/" + recommandations.get(position).getBanner())));
            holder.img.setImageBitmap(bitmap);
        }

        RelativeLayout.LayoutParams paramsrlItem = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
       /* if(Integer.parseInt(holder.rlItem.getTag().toString()) == recommandations.size()-1) {
            paramsrlItem.setMargins(-Utility.deviceWidth * 2 / 100, -Utility.deviceWidth * 5 / 100,-Utility.deviceWidth * 1 / 100, -Utility.deviceWidth * 5 / 100);
        }else{
            paramsrlItem.setMargins(-Utility.deviceWidth * 2 / 100, -Utility.deviceWidth * 5 / 100,-Utility.deviceWidth *10  / 100, -Utility.deviceWidth * 5 / 100);
        }*/
        paramsrlItem.setMargins(0, 0,Utility.deviceWidth * 5 / 100,Utility.deviceWidth * 5 / 100);
        holder.rlItem.setLayoutParams(paramsrlItem);
        holder.rlItem.getLayoutParams().width = Utility.deviceWidth * 85 / 100;
    }

    @Override
    public int getItemCount() {
        return recommandations.size();
    }


}
