package com.wetravel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Fragments.BannerDialogFragment;
import com.wetravel.Models.Offer;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Offer> offerList;

    public OffersAdapter(Context context, ArrayList<Offer> offerList){
        this.context = context;
        this.offerList = offerList;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout rlItem;
        public TextView tvName,tvCode,tvSeeMore;
        ImageView img;

        public OfferViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

            //Banner Property
            rlItem = itemView.findViewById(R.id.rlItem);

            ////Description layer Property
            tvName = itemView.findViewById(R.id.tvName);
            tvName.setTextSize(Utility.txtSize_15dp);
            tvName.setTypeface(Utility.font_roboto_regular);
            tvName.setPadding(Utility.deviceWidth*15/100, Utility.deviceWidth*12/100,0,0);

            //Code layer Property
            tvCode = itemView.findViewById(R.id.tvCode);
            tvCode.setTextSize(Utility.txtSize_14dp);
            tvCode.setTypeface(Utility.font_roboto_regular);

            RelativeLayout.LayoutParams paramstvtvCode = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramstvtvCode.setMargins(Utility.deviceWidth*15/100, Utility.deviceWidth*27/100,0,0);
            tvCode.setLayoutParams(paramstvtvCode);

            tvCode.getLayoutParams().width = Utility.deviceWidth*20/100;
            tvCode.getLayoutParams().height = Utility.deviceWidth*8/100;

            //See more layer Property
            tvSeeMore = itemView.findViewById(R.id.tvSeeMore);
            tvSeeMore.setTextSize(Utility.txtSize_12dp);
            tvSeeMore.setTypeface(Utility.font_roboto_regular);
            tvSeeMore.setPadding(0,0, Utility.deviceWidth*6/100,0);

            RelativeLayout.LayoutParams paramstvtvSeeMore = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramstvtvSeeMore.setMargins(Utility.deviceWidth*15/100, Utility.deviceWidth*38/100,0,0);
            tvSeeMore.setLayoutParams(paramstvtvSeeMore);

            tvSeeMore.getLayoutParams().width = Utility.deviceWidth*30/100;
            tvSeeMore.getLayoutParams().height = Utility.deviceWidth*10/100;

        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {

        if(Utility.getDrawableFromAssets(context,"IMAGES/"+ Utility.deviceDensityImage + "/" +offerList.get(position).getOffer_banner()) != null) {
            Bitmap bitmap = Utility.drawableToBitmap(context, (Utility.getDrawableFromAssets(context, "IMAGES/" + Utility.deviceDensityImage + "/" + offerList.get(position).getOffer_banner())));
            holder.img.setImageBitmap(bitmap);
        }

        holder.tvName.setText(offerList.get(position).getOffer_desc()+" "+context.getResources().getString(R.string.str_rupee)+offerList.get(position).getOffer_price()+".");

//        holder.rlItem.setBackground(Utility.getDrawableFromAssets(context, "IMAGES/"+Utility.deviceDensityImage+"/"+ offerList.get(position).getOffer_banner()));
//      holder.rlItem.setBackgroundResource(offers[position]);

        holder.tvCode.setText(offerList.get(position).getOffer_code());

        holder.rlItem.setTag(position);

        if(offerList.get(position).getFlag_banner().equalsIgnoreCase("1")){
            holder.tvName.setVisibility(View.GONE);
            holder.tvCode.setVisibility(View.GONE);
            holder.tvSeeMore.setVisibility(View.GONE);
        }

        RelativeLayout.LayoutParams paramsrlItem = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(Integer.parseInt(holder.rlItem.getTag().toString()) == offerList.size()-1) {
            paramsrlItem.setMargins(-Utility.deviceWidth * 2 / 100, -Utility.deviceWidth * 5 / 100,-Utility.deviceWidth * 1 / 100, -Utility.deviceWidth * 5 / 100);
        }else{
            paramsrlItem.setMargins(-Utility.deviceWidth * 2 / 100, -Utility.deviceWidth * 5 / 100,-Utility.deviceWidth *10  / 100, -Utility.deviceWidth * 5 / 100);
        }
        holder.rlItem.setLayoutParams(paramsrlItem);
        holder.rlItem.getLayoutParams().width = Utility.deviceWidth * 103 / 100;

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppDialogs.dialogBannerShow(context,"IMAGES/"+Utility.deviceDensityImage+"/"+ offerList.get(position).getAction_banner());

                FragmentActivity activity = (FragmentActivity)context;

                DialogFragment bannerDialogFragment = BannerDialogFragment.getInstance(context, "IMAGES/"+ Utility.deviceDensityImage+"/"+ offerList.get(position).getAction_banner());

                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

                Fragment prevFragment = activity.getSupportFragmentManager().findFragmentByTag("bannerDialogView");

                if (prevFragment != null) {

                    fragmentTransaction.remove(prevFragment);

                }

                fragmentTransaction.addToBackStack(null);

                bannerDialogFragment.show(fragmentTransaction, "bannerDialogView");

            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }


}
