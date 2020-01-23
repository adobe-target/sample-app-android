package com.wetravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Controller.ThankYouActivity;
import com.wetravel.Models.MyTickets;
import com.wetravel.R;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.OfferViewHolder> {
    Context context;
    ArrayList<MyTickets> myTickets;

    public MyTicketsAdapter(Context context, ArrayList<MyTickets> myTickets){
        this.context = context;
        this.myTickets = myTickets;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llHistory;
        RelativeLayout rlRight;
        TextView tvLocation,tvSeat,tvTravel;
        TextView tvDate,tvTime,tvHour;

        public OfferViewHolder(View itemView) {
            super(itemView);

            llHistory = itemView.findViewById(R.id.llHistory);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utility.deviceWidth*55/1000, Utility.deviceWidth*4/100, Utility.deviceWidth*55/1000,0);
            llHistory.setLayoutParams(params);

            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvLocation.setTextSize(Utility.txtSize_10dp);
            tvLocation.setTypeface(Utility.font_roboto_bold);

            tvSeat = itemView.findViewById(R.id.tvSeat);
            tvSeat.setTextSize(Utility.txtSize_10dp);
            tvSeat.setTypeface(Utility.font_roboto_regular);

            tvTravel = itemView.findViewById(R.id.tvTravel);
            tvTravel.setTextSize(Utility.txtSize_10dp);
            tvTravel.setTypeface(Utility.font_roboto_regular);

            rlRight = itemView.findViewById(R.id.rlRight);
            rlRight.setPadding(Utility.deviceWidth*8/100,0,0,0);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvDate.setTextSize(Utility.txtSize_10dp);
            tvDate.setTypeface(Utility.font_roboto_bold);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvTime.setTextSize(Utility.txtSize_10dp);
            tvTime.setTypeface(Utility.font_roboto_regular);

            tvHour = itemView.findViewById(R.id.tvHour);
            tvHour.setTextSize(Utility.txtSize_10dp);
            tvHour.setTypeface(Utility.font_roboto_regular);

        }
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_tickets,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {
        holder.tvLocation.setText(""+myTickets.get(position).getDeparture()+" to "+myTickets.get(position).getDestination());
        holder.tvSeat.setText("Seat Number ("+myTickets.get(position).getSeat()+") | "+myTickets.get(position).getCurrency_symbol()+myTickets.get(position).getAmount());
        holder.tvTravel.setText(""+myTickets.get(position).getTravel_name()+"("+myTickets.get(position).getBus_info()+")");

        holder.tvDate.setText("on "+ Utility.changeDateFormat(Constant.date_ddmyyyy, Constant.date_ddmmmyyyy,myTickets.get(position).getDeparture_date()));
        holder.tvTime.setText("at "+myTickets.get(position).getDeparture_time());
        holder.tvHour.setText("For "+myTickets.get(position).getDuration()+" hrs");

        holder.llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThankYouActivity.class);
                intent.putExtra("departure",""+myTickets.get(position).getDeparture());
                intent.putExtra("destination",""+myTickets.get(position).getDestination());
                intent.putExtra("status",""+myTickets.get(position).getStatus());
                intent.putExtra("amount",""+myTickets.get(position).getAmount());
                intent.putExtra("curSymbol",""+myTickets.get(position).getCurrency_symbol());
                intent.putExtra("date",""+myTickets.get(position).getDeparture_date());
                intent.putExtra("time",""+myTickets.get(position).getDeparture_time());
                intent.putExtra("seat",""+myTickets.get(position).getSeat());
                intent.putExtra("hour",""+myTickets.get(position).getDuration());
                intent.putExtra("travelName",""+myTickets.get(position).getTravel_name());
                intent.putExtra("busInfo",""+myTickets.get(position).getBus_info());
                ((Activity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }
}
