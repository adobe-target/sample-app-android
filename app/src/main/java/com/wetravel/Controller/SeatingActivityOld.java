package com.wetravel.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.DealOfferAdapterOld;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Models.Deal;
import com.wetravel.Models.Seat;
import com.wetravel.Models.SeatingResponse;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class SeatingActivityOld extends AppCompatActivity {
    ImageView imgBack;
    TextView tvHeading;
    ScrollView svSeats;

    TextView tvDateTime,tvBusInfo;
    String travelName,travelDate,departureTime,busInfo;
    RelativeLayout rlTxtLower,rlTxtUpper,rlLower,rlUpper,rlCheckout,rlOfferPopup;
    TextView tvLower,tvUpper;
    View viewLower,viewUpper,viewPopUp;
    TextView tvAll,tvSitingPrice,tvSleeperPrice,tvOfferTxt;

    ImageView imgLowerSeat1,imgLowerSeat2,imgLowerSeat3,imgLowerSeat4,imgLowerSeat5,imgLowerSeat6,imgLowerSeat7,imgLowerSeat8,
     imgLowerSeat9,imgLowerSeat10,imgLowerSeat11,imgLowerSeat12,imgLowerSeat13,imgLowerSeat14,imgLowerSeat15,imgLowerSeat16,
     imgLowerSeat17,imgLowerSeat18,imgLowerSeat19,imgLowerSeat20,imgLowerSeat21,imgLowerSeat22,imgLowerSeat23;
    ArrayList<Seat> lowerSeats;

    ImageView imgUpperSeat1,imgUpperSeat2,imgUpperSeat3,imgUpperSeat4,imgUpperSeat5,imgUpperSeat6,imgUpperSeat7,imgUpperSeat8,
            imgUpperSeat9,imgUpperSeat10,imgUpperSeat11,imgUpperSeat12,imgUpperSeat13,imgUpperSeat14,imgUpperSeat15,imgUpperSeat16,
            imgUpperSeat17,imgUpperSeat18,imgUpperSeat19,imgUpperSeat20,imgUpperSeat21,imgUpperSeat22,imgUpperSeat23;
    ArrayList<Seat> upperSeats;

    ArrayList<Deal> dealOfferList;
    TextView tvSeatNumbers,tvAmount;
    TextView tvSeatNumbersDialog,tvAmountDialog,tvDealAmountDialog;
    String selectedSeatNumber = "",checkoutPrice = "0",dealAmount="0";
    int offerAdded = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_seating_old);

        initLayouts();
        setParamValue();
        showLowerUpper(1);

        AppDialogs.dialogLoaderShow(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSeatingDetails();
            }
        }, Constant.delay_api);
    }

    public void setParamValue(){
        travelName = getIntent().getExtras().getString("travelName");
        travelDate = getIntent().getExtras().getString("travelDate");
        departureTime = getIntent().getExtras().getString("departureTime");
        busInfo = getIntent().getExtras().getString("busInfo");

        tvHeading.setText(travelName);
        tvDateTime.setText(travelDate+" | "+departureTime);
        tvBusInfo.setText(busInfo);
    }

    //View initialization
    public void initLayouts(){
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);

        tvDateTime = findViewById(R.id.tvDateTime);
        tvDateTime.setTextSize(Utility.txtSize_15dp);

        tvBusInfo = findViewById(R.id.tvBusInfo);
        tvBusInfo.setTextSize(Utility.txtSize_12dp);

        rlTxtLower = findViewById(R.id.rlTxtLower);
        rlTxtLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLowerUpper(1);
            }
        });

        rlTxtUpper = findViewById(R.id.rlTxtUpper);
        rlTxtUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLowerUpper(2);
            }
        });

        viewLower = findViewById(R.id.viewLower);
        viewUpper = findViewById(R.id.viewUpper);
        viewPopUp = findViewById(R.id.viewPopUp);
        viewPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDeals();
            }
        });

        rlOfferPopup = findViewById(R.id.rlOfferPopup);
        rlOfferPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDeals();
            }
        });

        rlLower = findViewById(R.id.rlLower);
        rlUpper = findViewById(R.id.rlUpper);

        tvLower = findViewById(R.id.tvLower);
        tvLower.setTextSize(Utility.txtSize_15dp);

        tvUpper = findViewById(R.id.tvUpper);
        tvUpper.setPadding(0,0, Utility.deviceWidth*8/100,0);
        tvUpper.setTextSize(Utility.txtSize_15dp);

        tvAll = findViewById(R.id.tvAll);
        tvAll.setTextSize(Utility.txtSize_12dp);
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAll.setTextColor(getResources().getColor(R.color.color_1473E6));
                tvSitingPrice.setTextColor(getResources().getColor(R.color.color_919191));
                tvSleeperPrice.setTextColor(getResources().getColor(R.color.color_919191));
            }
        });

        tvSitingPrice = findViewById(R.id.tvSitingPrice);
        tvSitingPrice.setTextSize(Utility.txtSize_12dp);
        tvSitingPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAll.setTextColor(getResources().getColor(R.color.color_919191));
                tvSitingPrice.setTextColor(getResources().getColor(R.color.color_1473E6));
                tvSleeperPrice.setTextColor(getResources().getColor(R.color.color_919191));
            }
        });

        tvSleeperPrice = findViewById(R.id.tvSleeperPrice);
        tvSleeperPrice.setTextSize(Utility.txtSize_12dp);
        tvSleeperPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAll.setTextColor(getResources().getColor(R.color.color_919191));
                tvSitingPrice.setTextColor(getResources().getColor(R.color.color_919191));
                tvSleeperPrice.setTextColor(getResources().getColor(R.color.color_1473E6));
            }
        });

        rlCheckout = findViewById(R.id.rlCheckout);
        rlCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeatingActivityOld.this,PaymentActivity.class));
            }
        });

        tvOfferTxt = findViewById(R.id.tvOfferTxt);
        tvOfferTxt.setTextSize(Utility.txtSize_9dp);
        tvOfferTxt.setText(Constant.msg_Offer_Deal);

        tvSeatNumbers = findViewById(R.id.tvSeatNumbers);
        tvSeatNumbers.setTextSize(Utility.txtSize_12dp);

        tvAmount = findViewById(R.id.tvAmount);
        tvAmount.setTextSize(Utility.txtSize_12dp);

        svSeats = findViewById(R.id.svSeats);
        svSeats.setPadding(0,0,0, Utility.deviceWidth*42/100);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getSeatingDetails(){
        GetJSON getJSON = new GetJSON(this, Constant.json_seating) {
            @Override
            public void response(String response) {
                AppDialogs.dialogLoaderHide();
                try {
                    Gson gson = new Gson();
                    SeatingResponse seatingResponse = gson.fromJson(response,SeatingResponse.class);
                    lowerSeats = seatingResponse.seats_lower;
                    upperSeats = seatingResponse.seats_upper;
                    dealOfferList = seatingResponse.deal_offer;

                    initLowerType();
                    initUpperType();

                    tvSitingPrice.setText("$"+seatingResponse.getSiting_price());
                    tvSleeperPrice.setText("$"+seatingResponse.getSleeper_price());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void showLowerUpper(int pos){
        if(pos == 1){
            rlLower.setVisibility(View.VISIBLE);
            rlUpper.setVisibility(View.GONE);

            viewLower.setVisibility(View.VISIBLE);
            viewUpper.setVisibility(View.GONE);

            tvLower.setTextColor(getResources().getColor(R.color.color_003232));
            tvUpper.setTextColor(getResources().getColor(R.color.color_919191));
        }else{
            rlLower.setVisibility(View.GONE);
            rlUpper.setVisibility(View.VISIBLE);

            viewLower.setVisibility(View.GONE);
            viewUpper.setVisibility(View.VISIBLE);

            tvLower.setTextColor(getResources().getColor(R.color.color_919191));
            tvUpper.setTextColor(getResources().getColor(R.color.color_003232));
        }
    }

    public void initLowerType(){
        imgLowerSeat1 = findViewById(R.id.imgLowerSeat1);
        setupClickEvent(imgLowerSeat1,0,lowerSeats);

        imgLowerSeat2 = findViewById(R.id.imgLowerSeat2);
        setupClickEvent(imgLowerSeat2,1,lowerSeats);

        imgLowerSeat3 = findViewById(R.id.imgLowerSeat3);
        setupClickEvent(imgLowerSeat3,2,lowerSeats);

        imgLowerSeat4 = findViewById(R.id.imgLowerSeat4);
        setupClickEvent(imgLowerSeat4,3,lowerSeats);

        imgLowerSeat5 = findViewById(R.id.imgLowerSeat5);
        setupClickEvent(imgLowerSeat5,4,lowerSeats);

        imgLowerSeat6 = findViewById(R.id.imgLowerSeat6);
        setupClickEvent(imgLowerSeat6,5,lowerSeats);

        imgLowerSeat7 = findViewById(R.id.imgLowerSeat7);
        setupClickEvent(imgLowerSeat7,6,lowerSeats);

        imgLowerSeat8 = findViewById(R.id.imgLowerSeat8);
        setupClickEvent(imgLowerSeat8,7,lowerSeats);

        imgLowerSeat9 = findViewById(R.id.imgLowerSeat9);
        setupClickEvent(imgLowerSeat9,8,lowerSeats);

        imgLowerSeat10 = findViewById(R.id.imgLowerSeat10);
        setupClickEvent(imgLowerSeat10,9,lowerSeats);

        imgLowerSeat11 = findViewById(R.id.imgLowerSeat11);
        setupClickEvent(imgLowerSeat11,10,lowerSeats);

        imgLowerSeat12 = findViewById(R.id.imgLowerSeat12);
        setupClickEvent(imgLowerSeat12,11,lowerSeats);

        imgLowerSeat13 = findViewById(R.id.imgLowerSeat13);
        setupClickEvent(imgLowerSeat13,12,lowerSeats);

        imgLowerSeat14 = findViewById(R.id.imgLowerSeat14);
        setupClickEvent(imgLowerSeat14,13,lowerSeats);

        imgLowerSeat15 = findViewById(R.id.imgLowerSeat15);
        setupClickEvent(imgLowerSeat15,14,lowerSeats);

        imgLowerSeat16 = findViewById(R.id.imgLowerSeat16);
        setupClickEvent(imgLowerSeat16,15,lowerSeats);

        imgLowerSeat17 = findViewById(R.id.imgLowerSeat17);
        setupClickEvent(imgLowerSeat17,16,lowerSeats);

        imgLowerSeat18 = findViewById(R.id.imgLowerSeat18);
        setupClickEvent(imgLowerSeat18,17,lowerSeats);

        imgLowerSeat19 = findViewById(R.id.imgLowerSeat19);
        setupClickEvent(imgLowerSeat19,18,lowerSeats);

        imgLowerSeat20 = findViewById(R.id.imgLowerSeat20);
        setupClickEvent(imgLowerSeat20,19,lowerSeats);

        imgLowerSeat21 = findViewById(R.id.imgLowerSeat21);
        setupClickEvent(imgLowerSeat21,20,lowerSeats);

        imgLowerSeat22 = findViewById(R.id.imgLowerSeat22);
        setupClickEvent(imgLowerSeat22,21,lowerSeats);

        imgLowerSeat23 = findViewById(R.id.imgLowerSeat23);
        setupClickEvent(imgLowerSeat23,22,lowerSeats);
    }

    public void initUpperType(){
        imgUpperSeat1 = findViewById(R.id.imgUpperSeat1);
        setupClickEvent(imgUpperSeat1,0,upperSeats);

        imgUpperSeat2 = findViewById(R.id.imgUpperSeat2);
        setupClickEvent(imgUpperSeat2,1,upperSeats);

        imgUpperSeat3 = findViewById(R.id.imgUpperSeat3);
        setupClickEvent(imgUpperSeat3,2,upperSeats);

        imgUpperSeat4 = findViewById(R.id.imgUpperSeat4);
        setupClickEvent(imgUpperSeat4,3,upperSeats);

        imgUpperSeat5 = findViewById(R.id.imgUpperSeat5);
        setupClickEvent(imgUpperSeat5,4,upperSeats);

        imgUpperSeat6 = findViewById(R.id.imgUpperSeat6);
        setupClickEvent(imgUpperSeat6,5,upperSeats);

        imgUpperSeat7 = findViewById(R.id.imgUpperSeat7);
        setupClickEvent(imgUpperSeat7,6,upperSeats);

        imgUpperSeat8 = findViewById(R.id.imgUpperSeat8);
        setupClickEvent(imgUpperSeat8,7,upperSeats);

        imgUpperSeat9 = findViewById(R.id.imgUpperSeat9);
        setupClickEvent(imgUpperSeat9,8,upperSeats);

        imgUpperSeat10 = findViewById(R.id.imgUpperSeat10);
        setupClickEvent(imgUpperSeat10,9,upperSeats);

        imgUpperSeat11 = findViewById(R.id.imgUpperSeat11);
        setupClickEvent(imgUpperSeat11,10,upperSeats);

        imgUpperSeat12 = findViewById(R.id.imgUpperSeat12);
        setupClickEvent(imgUpperSeat12,11,upperSeats);

        imgUpperSeat13 = findViewById(R.id.imgUpperSeat13);
        setupClickEvent(imgUpperSeat13,12,upperSeats);

        imgUpperSeat14 = findViewById(R.id.imgUpperSeat14);
        setupClickEvent(imgUpperSeat14,13,upperSeats);

        imgUpperSeat15 = findViewById(R.id.imgUpperSeat15);
        setupClickEvent(imgUpperSeat15,14,upperSeats);

        imgUpperSeat16 = findViewById(R.id.imgUpperSeat16);
        setupClickEvent(imgUpperSeat16,15,upperSeats);

        imgUpperSeat17 = findViewById(R.id.imgUpperSeat17);
        setupClickEvent(imgUpperSeat17,16,upperSeats);

        imgUpperSeat18 = findViewById(R.id.imgUpperSeat18);
        setupClickEvent(imgUpperSeat18,17,upperSeats);

        imgUpperSeat19 = findViewById(R.id.imgUpperSeat19);
        setupClickEvent(imgUpperSeat19,18,upperSeats);

        imgUpperSeat20 = findViewById(R.id.imgUpperSeat20);
        setupClickEvent(imgUpperSeat20,19,upperSeats);

        imgUpperSeat21 = findViewById(R.id.imgUpperSeat21);
        setupClickEvent(imgUpperSeat21,20,upperSeats);

        imgUpperSeat22 = findViewById(R.id.imgUpperSeat22);
        setupClickEvent(imgUpperSeat22,21,upperSeats);

        imgUpperSeat23 = findViewById(R.id.imgUpperSeat23);
        setupClickEvent(imgUpperSeat23,22,upperSeats);
    }

    public void setupClickEvent(final ImageView img, final int pos, final ArrayList<Seat> list){
        setLowerSetsDefault(img,list.get(pos).getStatus(),list.get(pos).getType());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLowerSetsSelected(img,pos,list);
            }
        });
    }

    public void setLowerSetsDefault(ImageView img, String status,String type){
        if(type.equalsIgnoreCase("sleeper")) {
            if (status.equalsIgnoreCase("1")) {
                img.setBackgroundResource(R.drawable.sleeper_seat_filed);
            } else {
                img.setBackgroundResource(R.drawable.sleeper_seat_unselected);
            }
        }else{
            if (status.equalsIgnoreCase("1")) {
                img.setBackgroundResource(R.drawable.siting_seat_filed);
            } else {
                img.setBackgroundResource(R.drawable.siting_seat_unselected);
            }
        }
    }

    public void setLowerSetsSelected(ImageView img, int pos,ArrayList<Seat> list){
        String type = list.get(pos).getType();
        if(type.equalsIgnoreCase("sleeper")) {
            if (list.get(pos).getStatus().equalsIgnoreCase("0")) {
                img.setBackgroundResource(R.drawable.sleeper_seat_selected);
                list.get(pos).setStatus("2");
            } else if (list.get(pos).getStatus().equalsIgnoreCase("2")) {
                img.setBackgroundResource(R.drawable.sleeper_seat_unselected);
                list.get(pos).setStatus("0");
            }
        }else{
            if (list.get(pos).getStatus().equalsIgnoreCase("0")) {
                img.setBackgroundResource(R.drawable.siting_seat_selected);
                list.get(pos).setStatus("2");
            } else if (list.get(pos).getStatus().equalsIgnoreCase("2")) {
                img.setBackgroundResource(R.drawable.siting_seat_unselected);
                list.get(pos).setStatus("0");
            }
        }
        setSeatNumber();
    }

    public void setSeatNumber(){
        StringBuilder sbSeat = new StringBuilder();
        double finalPrice = 0;

        for(int i = 0;i<lowerSeats.size();i++) {
            if(lowerSeats.get(i).getStatus().equalsIgnoreCase("2")){
                if(sbSeat.toString().equalsIgnoreCase("")){
                    sbSeat.append(lowerSeats.get(i).getSeat_no());
                }else{
                    sbSeat.append(",");
                    sbSeat.append(lowerSeats.get(i).getSeat_no());
                }
                finalPrice = finalPrice + Double.parseDouble(lowerSeats.get(i).getPrice());
            }
        }

        for(int i = 0;i<upperSeats.size();i++) {
            if(upperSeats.get(i).getStatus().equalsIgnoreCase("2")){
                if(sbSeat.toString().equalsIgnoreCase("")){
                    sbSeat.append(upperSeats.get(i).getSeat_no());
                }else{
                    sbSeat.append(",");
                    sbSeat.append(upperSeats.get(i).getSeat_no());
                }
                finalPrice = finalPrice + Double.parseDouble(upperSeats.get(i).getPrice());
            }
        }

        tvSeatNumbers.setText(sbSeat.toString());
        tvAmount.setText("$ "+String.format("%.0f",finalPrice+Double.parseDouble(dealAmount)));

        selectedSeatNumber = sbSeat.toString();
        checkoutPrice = String.format("%.0f",finalPrice);

    }

    public void dialogDeals() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_check_out, null);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);

        mBottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog bsd = (BottomSheetDialog)dialog;
                FrameLayout frameLayout = (FrameLayout) bsd.findViewById(android.support.design.R.id.design_bottom_sheet);
                frameLayout.setBackgroundResource(R.color.color_transparent);
                BottomSheetBehavior.from(frameLayout).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        TextView tvOfferTxtDialog = view.findViewById(R.id.tvOfferTxtDialog);
        tvOfferTxtDialog.setTextSize(Utility.txtSize_9dp);
        tvOfferTxtDialog.setText(Constant.msg_Offer_Deal);

        RecyclerView rvDealOffer = view.findViewById(R.id.rvDealOffer);
        rvDealOffer.getLayoutParams().height = Utility.deviceWidth*55/100;
        DealOfferAdapterOld dealOfferAdapter = new DealOfferAdapterOld(this,dealOfferList,selectedSeatNumber);
        RecyclerView.LayoutManager layoutManagerTravels = new LinearLayoutManager(this);
        rvDealOffer.setLayoutManager(layoutManagerTravels);
        rvDealOffer.setItemAnimator(new DefaultItemAnimator());
        rvDealOffer.setAdapter(dealOfferAdapter);

        RelativeLayout rlDismiss = view.findViewById(R.id.rlDismiss);
        rlDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        RelativeLayout rlDialogOfferPopup = view.findViewById(R.id.rlDialogOfferPopup);
        rlDialogOfferPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        tvSeatNumbersDialog = view.findViewById(R.id.tvSeatNumbersDialog);
        tvSeatNumbersDialog.setTextSize(Utility.txtSize_12dp);
        tvSeatNumbersDialog.setText(selectedSeatNumber);

        tvAmountDialog = view.findViewById(R.id.tvAmountDialog);
        tvAmountDialog.setTextSize(Utility.txtSize_12dp);
        tvAmountDialog.setText("$ "+String.format("%.0f",Double.parseDouble(checkoutPrice)+Double.parseDouble(dealAmount)));

        tvDealAmountDialog = view.findViewById(R.id.tvDealAmountDialog);
        tvDealAmountDialog.setTextSize(Utility.txtSize_12dp);
        if(Double.parseDouble(dealAmount)>0){
            tvDealAmountDialog.setVisibility(View.VISIBLE);
            tvDealAmountDialog.setText("(Including $"+dealAmount+" Deals)");
        }else{
            tvDealAmountDialog.setVisibility(View.GONE);
        }

        RelativeLayout rlCheckout1 = view.findViewById(R.id.rlCheckout);
        rlCheckout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeatingActivityOld.this,PaymentActivity.class));
            }
        });

        mBottomSheetDialog.show();
    }

    public void setDealOfferPrice(String operation){
        if(operation.equalsIgnoreCase("add")) {
            offerAdded = offerAdded+1;
        }else{
            offerAdded = offerAdded-1;
        }

        int count = 1;
        String[] split = selectedSeatNumber.split(",");
        if(split.length>0){
            count = split.length;
        }

        dealAmount = "0";
        for(int i = 0;i<dealOfferList.size();i++) {
            if(dealOfferList.get(i).getStatus() == 1){
                if(Constant.deal_multiply_flag) {
                    dealAmount = String.format("%.0f", (Double.parseDouble(dealAmount) + count * Double.parseDouble(dealOfferList.get(i).getDeal_price())));
                }else{
                    dealAmount = String.format("%.0f", (Double.parseDouble(dealAmount) + Double.parseDouble(dealOfferList.get(i).getDeal_price())));
                }
            }
        }

        tvAmountDialog.setText("$ "+String.format("%.0f", (Double.parseDouble(checkoutPrice)+Double.parseDouble(dealAmount))));
        tvAmount.setText("$ "+String.format("%.0f", (Double.parseDouble(checkoutPrice)+Double.parseDouble(dealAmount))));
        if(Double.parseDouble(dealAmount)>0){
            tvDealAmountDialog.setVisibility(View.VISIBLE);
            tvDealAmountDialog.setText("(Including $"+dealAmount+" Deals)");
        }else{
            tvDealAmountDialog.setVisibility(View.GONE);
        }
    }
}
