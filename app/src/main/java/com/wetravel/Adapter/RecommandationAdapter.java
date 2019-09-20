package com.wetravel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wetravel.Models.Recommandation;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class RecommandationAdapter extends RecyclerView.Adapter<RecommandationAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Recommandation> recommandations;

    public RecommandationAdapter(Context context, ArrayList<Recommandation> recommandations){
        this.context = context;
        this.recommandations = recommandations;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgBanner;


        public OfferViewHolder(View itemView) {
            super(itemView);

            //Banner Property
            imgBanner = itemView.findViewById(R.id.imgBanner);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utility.deviceWidth*4/100,Utility.deviceWidth*4/100,Utility.deviceWidth*4/100,0);
            imgBanner.setLayoutParams(params);

        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommandations,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {
        holder.imgBanner.setTag(position);

        if(Utility.getDrawableFromAssets(context,"IMAGES/"+ Utility.deviceDensityImage + "/" +recommandations.get(position).getBanner()) != null) {
            Bitmap bitmap = Utility.drawableToBitmap(context,(Utility.getDrawableFromAssets(context, "IMAGES/"+Utility.deviceDensityImage+"/"+ recommandations.get(position).getBanner())));
            holder.imgBanner.setImageBitmap(bitmap);

//            BitmapDrawable b = new BitmapDrawable(context.getResources(),bitmap);
//            holder.imgBanner.setBackgroundDrawable(b);
        }
    }

    @Override
    public int getItemCount() {
        return recommandations.size();
    }
}
