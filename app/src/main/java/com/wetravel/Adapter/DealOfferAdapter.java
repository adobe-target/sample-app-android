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

import com.wetravel.Controller.SearchBusActivity;
import com.wetravel.Controller.SeatingActivity;
import com.wetravel.Models.Deal;
import com.wetravel.Models.Offer;
import com.wetravel.R;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class DealOfferAdapter extends RecyclerView.Adapter<DealOfferAdapter.OfferViewHolder> {
    Context context;
    ArrayList<Deal> deals;
    int selectedSeatNumber;

    public DealOfferAdapter(Context context, ArrayList<Deal> deals, String selectedSeatNumber){
        this.context = context;
        this.deals = deals;

        String[] split = selectedSeatNumber.split(",");
        if(split.length==0){
            this.selectedSeatNumber = 1;
        }else{
            this.selectedSeatNumber = split.length;
        }
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgDeal,imgAdd;
        public TextView tvDealName,tvForJust,tvPrice;
        RelativeLayout rl;

        public OfferViewHolder(View itemView) {
            super(itemView);

            imgDeal = itemView.findViewById(R.id.imgDeal);
            imgDeal.getLayoutParams().width = Utility.deviceWidth*9/100;

            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgAdd.getLayoutParams().width = Utility.deviceWidth*6/100;
            imgAdd.getLayoutParams().height = Utility.deviceWidth*6/100;

            tvDealName = itemView.findViewById(R.id.tvDealName);
            tvDealName.setTextSize(Utility.txtSize_11dp);

            tvForJust = itemView.findViewById(R.id.tvForJust);
            tvForJust.setTextSize(Utility.txtSize_10dp);

            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvPrice.setTextSize(Utility.txtSize_11dp);

            rl = itemView.findViewById(R.id.rl);
        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_offer,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, final int position) {

        if(deals.get(position).getStatus() == 1){
            holder.imgAdd.setBackgroundResource(R.drawable.cancel_deal_offer);
            holder.imgAdd.setTag("cancel_deal_offer");
        }else{
            holder.imgAdd.setBackgroundResource(R.drawable.add_deal_offer);
            holder.imgAdd.setTag("add_deal_offer");
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equalsIgnoreCase("add_deal_offer")){
                    holder.imgAdd.setBackgroundResource(R.drawable.cancel_deal_offer);
                    v.setTag("cancel_deal_offer");
                    deals.get(position).setStatus(1);
                    ((SeatingActivity)context).setDealOfferPrice("add");
                }else{
                    holder.imgAdd.setBackgroundResource(R.drawable.add_deal_offer);
                    v.setTag("add_deal_offer");
                    deals.get(position).setStatus(0);
                    ((SeatingActivity)context).setDealOfferPrice("minus");
                }
            }
        });

        if(Utility.getDrawableFromAssets(context,"IMAGES/"+ Utility.deviceDensityImage + "/" +deals.get(position).getDeal_image()) != null) {
            Bitmap bitmap = Utility.drawableToBitmap(context,(Utility.getDrawableFromAssets(context, "IMAGES/"+Utility.deviceDensityImage+"/"+ deals.get(position).getDeal_image())));
            holder.imgDeal.setImageBitmap(bitmap);
        }

        holder.tvDealName.setText(""+deals.get(position).getDeal_name());
        if(Constant.deal_multiply_flag) {
            holder.tvPrice.setText("$" + selectedSeatNumber * Integer.parseInt(deals.get(position).getDeal_price()));
        }else{
            holder.tvPrice.setText("$" + Integer.parseInt(deals.get(position).getDeal_price()));
        }

        holder.rl.setPadding(0,Utility.deviceHeight*1/100,0,Utility.deviceHeight*1/100);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }
}
