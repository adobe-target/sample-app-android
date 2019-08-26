package com.wetravel.Controller;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.DealOfferAdapter;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Models.Deal;
import com.wetravel.Models.Seat;
import com.wetravel.Models.SeatingResponse;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class SeatingActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvHeading;
    ScrollView svSeats;

    TextView tvDateTime,tvBusInfo;
    String travelName,travelDate,departureTime,busInfo;
    RelativeLayout rlTxtLower,rlTxtUpper,rlLower,rlUpper,rlOfferPopup;
    TextView tvLower,tvUpper;
    View viewLower,viewUpper;
    TextView tvAll,tvSitingPrice,tvSleeperPrice,tvOfferTxt;
    Button btnCheckout;

    ImageView imgLowerSeat1,imgLowerSeat2,imgLowerSeat3,imgLowerSeat4,imgLowerSeat5,imgLowerSeat6,imgLowerSeat7,imgLowerSeat8,
     imgLowerSeat9,imgLowerSeat10,imgLowerSeat11,imgLowerSeat12,imgLowerSeat13,imgLowerSeat14,imgLowerSeat15,imgLowerSeat16,
     imgLowerSeat17,imgLowerSeat18,imgLowerSeat19,imgLowerSeat20,imgLowerSeat21,imgLowerSeat22,imgLowerSeat23;
    ArrayList<Seat> lowerSeats;

    ImageView imgUpperSeat1,imgUpperSeat2,imgUpperSeat3,imgUpperSeat4,imgUpperSeat5,imgUpperSeat6,imgUpperSeat7,imgUpperSeat8,
            imgUpperSeat9,imgUpperSeat10,imgUpperSeat11,imgUpperSeat12,imgUpperSeat13,imgUpperSeat14,imgUpperSeat15,imgUpperSeat16,
            imgUpperSeat17,imgUpperSeat18,imgUpperSeat19,imgUpperSeat20,imgUpperSeat21,imgUpperSeat22,imgUpperSeat23;
    ArrayList<Seat> upperSeats;

    int filterType = 1; //1 for all,2 for 399 and 3 for 550
    int lowerUpperSelect = 1; // 1 for lowwer and 2 for upper

    ArrayList<Deal> dealOfferList;
    TextView tvSeatNumbers,tvAmount;
    String selectedSeatNumber = "",checkoutPrice = "0",dealAmount="0";
    int offerAdded = 0;

    RecyclerView rvDealOffer;
    DealOfferAdapter dealOfferAdapter;
    ImageView imgCircle;
    TextView tvDealAmount;

    RelativeLayout rlA,rlB,rlC;
    boolean isOpen = false;
    float animContainerHeight = Utility.deviceHeight*330/1000;
    View viewOffer;
    String sitingPrice="",sleeperPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_seating);

        initLayouts();
        setParamValue();

        AppDialogs.dialogLoaderShow(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSeatingDetails();
            }
        },Constant.delay_api);
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
        imgBack.getLayoutParams().width = Utility.deviceWidth*6/100;
        imgBack.setPadding(0,Utility.deviceWidth*22/1000,0,0);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);
        tvHeading.setTypeface(Utility.font_roboto_bold);

        tvDateTime = findViewById(R.id.tvDateTime);
        tvDateTime.setTextSize(Utility.txtSize_15dp);
        tvDateTime.setTypeface(Utility.font_roboto_regular);

        tvBusInfo = findViewById(R.id.tvBusInfo);
        tvBusInfo.setTextSize(Utility.txtSize_12dp);
        tvBusInfo.setTypeface(Utility.font_roboto_light);

        rlTxtLower = findViewById(R.id.rlTxtLower);
        rlTxtLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowerUpperSelect = 1;
                showLowerUpper(lowerUpperSelect);
            }
        });

        rlTxtUpper = findViewById(R.id.rlTxtUpper);
        rlTxtUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowerUpperSelect = 2;
                showLowerUpper(lowerUpperSelect);
            }
        });

        viewLower = findViewById(R.id.viewLower);
        viewUpper = findViewById(R.id.viewUpper);


        rlOfferPopup = findViewById(R.id.rlOfferPopup);
        RelativeLayout.LayoutParams paramstvtvSeeMore = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramstvtvSeeMore.setMargins(Utility.deviceWidth*5/100,0,0,Utility.deviceWidth*1/100);
        rlOfferPopup.setLayoutParams(paramstvtvSeeMore);
        rlOfferPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    animationFromTopToBottom(rlA);
                }else{
                    animationFromBottomToTop(rlA);
                }
            }
        });

        rlLower = findViewById(R.id.rlLower);
        rlLower.setVisibility(View.GONE);
        rlUpper = findViewById(R.id.rlUpper);
        rlUpper.setVisibility(View.GONE);

        tvLower = findViewById(R.id.tvLower);
        tvLower.setTextSize(Utility.txtSize_15dp);
        tvLower.setTypeface(Utility.font_roboto_regular);

        tvUpper = findViewById(R.id.tvUpper);
        tvUpper.setTextSize(Utility.txtSize_15dp);
        tvUpper.setTypeface(Utility.font_roboto_regular);

        tvAll = findViewById(R.id.tvAll);
        tvAll.setTextSize(Utility.txtSize_12dp);
        tvAll.setTypeface(Utility.font_roboto_regular);
        tvAll.setTextColor(getResources().getColor(R.color.color_1473E6));
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterType = 1;
                if(lowerUpperSelect==1){
                    initLowerType();
                }else{
                    initUpperType();
                }
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
                filterType = 2;
                if(lowerUpperSelect==1){
                    initLowerType();
                }else{
                    initUpperType();
                }
                tvAll.setTextColor(getResources().getColor(R.color.color_919191));
                tvSitingPrice.setTextColor(getResources().getColor(R.color.color_1473E6));
                tvSleeperPrice.setTextColor(getResources().getColor(R.color.color_919191));
            }
        });
        tvSitingPrice.setTypeface(Utility.font_roboto_regular);

        tvSleeperPrice = findViewById(R.id.tvSleeperPrice);
        tvSleeperPrice.setTextSize(Utility.txtSize_12dp);
        tvSleeperPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterType = 3;
                if(lowerUpperSelect==1){
                    initLowerType();
                }else{
                    initUpperType();
                }
                tvAll.setTextColor(getResources().getColor(R.color.color_919191));
                tvSitingPrice.setTextColor(getResources().getColor(R.color.color_919191));
                tvSleeperPrice.setTextColor(getResources().getColor(R.color.color_1473E6));
            }
        });
        tvSleeperPrice.setTypeface(Utility.font_roboto_regular);

        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setTextSize(Utility.txtSize_12dp);
        btnCheckout.setTypeface(Utility.font_roboto_bold);

        btnCheckout.setTextColor(getResources().getColor(R.color.color_BCBCBC));
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.dialogLoaderShow(SeatingActivity.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppDialogs.dialogLoaderHide();
                        startActivity(new Intent(SeatingActivity.this,PaymentActivity.class));
                    }
                },Constant.delay_api);
            }
        });
        btnCheckout.setClickable(false);

        tvOfferTxt = findViewById(R.id.tvOfferTxt);
        tvOfferTxt.setTextSize(Utility.txtSize_9dp);
        tvOfferTxt.setText(Constant.msg_Offer_Deal);
        tvOfferTxt.setTypeface(Utility.font_roboto_regular_italic);

        tvSeatNumbers = findViewById(R.id.tvSeatNumbers);
        tvSeatNumbers.setTextSize(Utility.txtSize_12dp);
        tvSeatNumbers.setTypeface(Utility.font_roboto_regular);

        tvAmount = findViewById(R.id.tvAmount);
        tvAmount.setTextSize(Utility.txtSize_14dp);
        tvAmount.setTypeface(Utility.font_roboto_bold);

        svSeats = findViewById(R.id.svSeats);
        svSeats.setPadding(0,0,0,Utility.deviceWidth*42/100);

        rvDealOffer = findViewById(R.id.rvDealOffer);
//        rvDealOffer.getLayoutParams().height = Utility.deviceWidth*56/100;
        rvDealOffer.getLayoutParams().height = (int)animContainerHeight+Utility.deviceHeight*2/1000;

        tvDealAmount = findViewById(R.id.tvDealAmount);
        tvDealAmount.setTextSize(Utility.txtSize_11dp);
        tvDealAmount.setTypeface(Utility.font_roboto_regular_italic);

        rlA = findViewById(R.id.rlA);
        rlA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlC = findViewById(R.id.rlC);
        rlC.setVisibility(View.GONE);
        rlC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    animationFromTopToBottom(rlA);
            }
        });

        rlB = findViewById(R.id.rlB);
        rlA.setY(animContainerHeight);

        imgCircle = findViewById(R.id.imgCircle);
        imgCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    animationFromTopToBottom(rlA);
                }else{
                    animationFromBottomToTop(rlA);
                }
            }
        });

        viewOffer = findViewById(R.id.viewOffer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getSeatingDetails(){
        GetJSON getJSON = new GetJSON(this,Constant.json_seating) {
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
                    setDealAdapter();
                    showLowerUpper(1);

                    sitingPrice = seatingResponse.getSiting_price();
                    sleeperPrice = seatingResponse.getSleeper_price();
                    tvSitingPrice.setText("$"+seatingResponse.getSiting_price());
                    tvSleeperPrice.setText("$"+seatingResponse.getSleeper_price());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setDealAdapter(){
        dealOfferAdapter = new DealOfferAdapter(this,dealOfferList,selectedSeatNumber);
        RecyclerView.LayoutManager layoutManagerTravels = new LinearLayoutManager(this);
        rvDealOffer.setLayoutManager(layoutManagerTravels);
        rvDealOffer.setItemAnimator(new DefaultItemAnimator());
        rvDealOffer.setAdapter(dealOfferAdapter);
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
        setSeatPositions(imgLowerSeat1,0,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgLowerSeat1,0,lowerSeats);

        imgLowerSeat2 = findViewById(R.id.imgLowerSeat2);
        setSeatPositions(imgLowerSeat2,R.id.imgLowerSeat1,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgLowerSeat2,1,lowerSeats);

        imgLowerSeat3 = findViewById(R.id.imgLowerSeat3);
        setSeatPositions(imgLowerSeat3,R.id.imgLowerSeat2,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgLowerSeat3,2,lowerSeats);

        imgLowerSeat4 = findViewById(R.id.imgLowerSeat4);
        setSeatPositions(imgLowerSeat4,R.id.imgLowerSeat3,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*40/1000);
        setupClickEvent(imgLowerSeat4,3,lowerSeats);

        imgLowerSeat5 = findViewById(R.id.imgLowerSeat5);
        setSeatPositions(imgLowerSeat5,R.id.imgLowerSeat4,Utility.deviceWidth*25/100,0,0,0);
        setupClickEvent(imgLowerSeat5,4,lowerSeats);

        imgLowerSeat6 = findViewById(R.id.imgLowerSeat6);
        setSeatPositions(imgLowerSeat6,R.id.imgLowerSeat4,Utility.deviceWidth*41/100,0,0,0);
        setupClickEvent(imgLowerSeat6,5,lowerSeats);

        imgLowerSeat7 = findViewById(R.id.imgLowerSeat7);
        setSeatPositions(imgLowerSeat7,0,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat7,6,lowerSeats);

        imgLowerSeat8 = findViewById(R.id.imgLowerSeat8);
        setSeatPositions(imgLowerSeat8,0,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat8,7,lowerSeats);

        imgLowerSeat9 = findViewById(R.id.imgLowerSeat9);
        setSeatPositions(imgLowerSeat9,R.id.imgLowerSeat7,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat9,8,lowerSeats);

        imgLowerSeat10 = findViewById(R.id.imgLowerSeat10);
        setSeatPositions(imgLowerSeat10,R.id.imgLowerSeat8,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat10,9,lowerSeats);

        imgLowerSeat11 = findViewById(R.id.imgLowerSeat11);
        setSeatPositions(imgLowerSeat11,R.id.imgLowerSeat9,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat11,10,lowerSeats);

        imgLowerSeat12 = findViewById(R.id.imgLowerSeat12);
        setSeatPositions(imgLowerSeat12,R.id.imgLowerSeat10,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat12,11,lowerSeats);

        imgLowerSeat13 = findViewById(R.id.imgLowerSeat13);
        setSeatPositions(imgLowerSeat13,R.id.imgLowerSeat11,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat13,12,lowerSeats);

        imgLowerSeat14 = findViewById(R.id.imgLowerSeat14);
        setSeatPositions(imgLowerSeat14,R.id.imgLowerSeat12,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat14,13,lowerSeats);

        imgLowerSeat15 = findViewById(R.id.imgLowerSeat15);
        setSeatPositions(imgLowerSeat15,R.id.imgLowerSeat13,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat15,14,lowerSeats);

        imgLowerSeat16 = findViewById(R.id.imgLowerSeat16);
        setSeatPositions(imgLowerSeat16,R.id.imgLowerSeat14,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat16,15,lowerSeats);

        imgLowerSeat17 = findViewById(R.id.imgLowerSeat17);
        setSeatPositions(imgLowerSeat17,R.id.imgLowerSeat15,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat17,16,lowerSeats);

        imgLowerSeat18 = findViewById(R.id.imgLowerSeat18);
        setSeatPositions(imgLowerSeat18,R.id.imgLowerSeat16,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat18,17,lowerSeats);

        imgLowerSeat19 = findViewById(R.id.imgLowerSeat19);
        setSeatPositions(imgLowerSeat19,R.id.imgLowerSeat17,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat19,18,lowerSeats);

        imgLowerSeat20 = findViewById(R.id.imgLowerSeat20);
        setSeatPositions(imgLowerSeat20,R.id.imgLowerSeat18,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgLowerSeat20,19,lowerSeats);

        imgLowerSeat21 = findViewById(R.id.imgLowerSeat21);
        setSeatPositions(imgLowerSeat21,R.id.imgLowerSeat19,Utility.deviceWidth*6/100,0,0,0);
        setupClickEvent(imgLowerSeat21,20,lowerSeats);

        imgLowerSeat22 = findViewById(R.id.imgLowerSeat22);
        setSeatPositions(imgLowerSeat22,R.id.imgLowerSeat19,Utility.deviceWidth*15/100,0,0,0);
        setupClickEvent(imgLowerSeat22,21,lowerSeats);

        imgLowerSeat23 = findViewById(R.id.imgLowerSeat23);
        setSeatPositions(imgLowerSeat23,R.id.imgLowerSeat20,Utility.deviceWidth*24/100,0,0,0);
        setupClickEvent(imgLowerSeat23,22,lowerSeats);
    }

    public void initUpperType(){
        imgUpperSeat1 = findViewById(R.id.imgUpperSeat1);
        setSeatPositions(imgUpperSeat1,0,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgUpperSeat1,0,upperSeats);

        imgUpperSeat2 = findViewById(R.id.imgUpperSeat2);
        setSeatPositions(imgUpperSeat2,R.id.imgUpperSeat1,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgUpperSeat2,1,upperSeats);

        imgUpperSeat3 = findViewById(R.id.imgUpperSeat3);
        setSeatPositions(imgUpperSeat3,R.id.imgUpperSeat2,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*2/100);
        setupClickEvent(imgUpperSeat3,2,upperSeats);

        imgUpperSeat4 = findViewById(R.id.imgUpperSeat4);
        setSeatPositions(imgUpperSeat4,R.id.imgUpperSeat3,Utility.deviceWidth*25/100,0,0,Utility.deviceWidth*40/1000);
        setupClickEvent(imgUpperSeat4,3,upperSeats);

        imgUpperSeat5 = findViewById(R.id.imgUpperSeat5);
        setSeatPositions(imgUpperSeat5,R.id.imgUpperSeat4,Utility.deviceWidth*25/100,0,0,0);
        setupClickEvent(imgUpperSeat5,4,upperSeats);

        imgUpperSeat6 = findViewById(R.id.imgUpperSeat6);
        setSeatPositions(imgUpperSeat6,R.id.imgUpperSeat4,Utility.deviceWidth*41/100,0,0,0);
        setupClickEvent(imgUpperSeat6,5,upperSeats);

        imgUpperSeat7 = findViewById(R.id.imgUpperSeat7);
        setSeatPositions(imgUpperSeat7,0,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat7,6,upperSeats);

        imgUpperSeat8 = findViewById(R.id.imgUpperSeat8);
        setSeatPositions(imgUpperSeat8,0,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat8,7,upperSeats);

        imgUpperSeat9 = findViewById(R.id.imgUpperSeat9);
        setSeatPositions(imgUpperSeat9,R.id.imgUpperSeat7,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat9,8,upperSeats);

        imgUpperSeat10 = findViewById(R.id.imgUpperSeat10);
        setSeatPositions(imgUpperSeat10,R.id.imgUpperSeat8,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat10,9,upperSeats);

        imgUpperSeat11 = findViewById(R.id.imgUpperSeat11);
        setSeatPositions(imgUpperSeat11,R.id.imgUpperSeat9,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat11,10,upperSeats);

        imgUpperSeat12 = findViewById(R.id.imgUpperSeat12);
        setSeatPositions(imgUpperSeat12,R.id.imgUpperSeat10,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat12,11,upperSeats);

        imgUpperSeat13 = findViewById(R.id.imgUpperSeat13);
        setSeatPositions(imgUpperSeat13,R.id.imgUpperSeat11,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat13,12,upperSeats);

        imgUpperSeat14 = findViewById(R.id.imgUpperSeat14);
        setSeatPositions(imgUpperSeat14,R.id.imgUpperSeat12,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat14,13,upperSeats);

        imgUpperSeat15 = findViewById(R.id.imgUpperSeat15);
        setSeatPositions(imgUpperSeat15,R.id.imgUpperSeat13,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat15,14,upperSeats);

        imgUpperSeat16 = findViewById(R.id.imgUpperSeat16);
        setSeatPositions(imgUpperSeat16,R.id.imgUpperSeat14,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat16,15,upperSeats);

        imgUpperSeat17 = findViewById(R.id.imgUpperSeat17);
        setSeatPositions(imgUpperSeat17,R.id.imgUpperSeat15,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat17,16,upperSeats);

        imgUpperSeat18 = findViewById(R.id.imgUpperSeat18);
        setSeatPositions(imgUpperSeat18,R.id.imgUpperSeat16,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat18,17,upperSeats);

        imgUpperSeat19 = findViewById(R.id.imgUpperSeat19);
        setSeatPositions(imgUpperSeat19,R.id.imgUpperSeat17,Utility.deviceWidth*15/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat19,18,upperSeats);

        imgUpperSeat20 = findViewById(R.id.imgUpperSeat20);
        setSeatPositions(imgUpperSeat20,R.id.imgUpperSeat18,Utility.deviceWidth*24/100,0,0,Utility.deviceWidth*35/1000);
        setupClickEvent(imgUpperSeat20,19,upperSeats);

        imgUpperSeat21 = findViewById(R.id.imgUpperSeat21);
        setSeatPositions(imgUpperSeat21,R.id.imgUpperSeat19,Utility.deviceWidth*6/100,0,0,0);
        setupClickEvent(imgUpperSeat21,20,upperSeats);

        imgUpperSeat22 = findViewById(R.id.imgUpperSeat22);
        setSeatPositions(imgUpperSeat22,R.id.imgUpperSeat19,Utility.deviceWidth*15/100,0,0,0);
        setupClickEvent(imgUpperSeat22,21,upperSeats);

        imgUpperSeat23 = findViewById(R.id.imgUpperSeat23);
        setSeatPositions(imgUpperSeat23,R.id.imgUpperSeat20,Utility.deviceWidth*24/100,0,0,0);
        setupClickEvent(imgUpperSeat23,22,upperSeats);
    }

    public void setSeatPositions(ImageView img,int viewId,int left,int top,int right,int bottom){
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(left,top,right,bottom);
        if(viewId!=0) {
            params1.addRule(RelativeLayout.BELOW, viewId);
        }
        img.setLayoutParams(params1);
    }

    public void setupClickEvent(final ImageView img, final int pos, final ArrayList<Seat> list){
        img.getLayoutParams().width = Utility.deviceWidth*7/100;
        if(list.size()>pos) {
            setLowerSetsDefault(img, list.get(pos).getStatus(), list.get(pos).getType());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLowerSetsSelected(img, pos, list);
                }
            });

            if(filterType == 2){
                if(list.get(pos).getPrice().equalsIgnoreCase(sitingPrice)){
                    img.setVisibility(View.VISIBLE);
                }else{
                    img.setVisibility(View.INVISIBLE);
                }
            }else if(filterType == 3){
                if(list.get(pos).getPrice().equalsIgnoreCase(sleeperPrice)){
                    img.setVisibility(View.VISIBLE);
                }else{
                    img.setVisibility(View.INVISIBLE);
                }
            }else{
                img.setVisibility(View.VISIBLE);
            }
        }else{
            img.setVisibility(View.INVISIBLE);
        }
    }

    public void setLowerSetsDefault(ImageView img, String status,String type){
        if(type.equalsIgnoreCase("sleeper")) {
            if (status.equalsIgnoreCase("1")) {
                img.setImageResource(R.drawable.sleeper_seat_filed);
            }else if (status.equalsIgnoreCase("2")) {
                img.setImageResource(R.drawable.sleeper_seat_selected);
            } else {
                img.setImageResource(R.drawable.sleeper_seat_unselected);
            }
        }else{
            if (status.equalsIgnoreCase("1")) {
                img.setImageResource(R.drawable.siting_seat_filed);
            }else if (status.equalsIgnoreCase("2")) {
                img.setImageResource(R.drawable.siting_seat_selected);
            } else {
                img.setImageResource(R.drawable.siting_seat_unselected);
            }
        }
    }

    public void setLowerSetsSelected(ImageView img, int pos,ArrayList<Seat> list){
        String type = list.get(pos).getType();
        if(type.equalsIgnoreCase("sleeper")) {
            if (list.get(pos).getStatus().equalsIgnoreCase("0")) {
                img.setImageResource(R.drawable.sleeper_seat_selected);
                list.get(pos).setStatus("2");
            } else if (list.get(pos).getStatus().equalsIgnoreCase("2")) {
                img.setImageResource(R.drawable.sleeper_seat_unselected);
                list.get(pos).setStatus("0");
            }
        }else{
            if (list.get(pos).getStatus().equalsIgnoreCase("0")) {
                img.setImageResource(R.drawable.siting_seat_selected);
                list.get(pos).setStatus("2");
            } else if (list.get(pos).getStatus().equalsIgnoreCase("2")) {
                img.setImageResource(R.drawable.siting_seat_unselected);
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
        if(selectedSeatNumber.equalsIgnoreCase("")){
            btnCheckout.setClickable(false);
            btnCheckout.setBackground(getResources().getDrawable(R.drawable.grey_round_button));
            btnCheckout.setTextColor(getResources().getColor(R.color.color_BCBCBC));
        }else{
            btnCheckout.setClickable(true);
            btnCheckout.setBackground(getResources().getDrawable(R.drawable.blue_round_button));
            btnCheckout.setTextColor(getResources().getColor(R.color.color_ffffff));
        }
        checkoutPrice = String.format("%.0f",finalPrice);
        setDealAdapter();
        setDealOfferPrice("");
    }

    public void setDealOfferPrice(String operation){
        if(operation.equalsIgnoreCase("add")) {
            offerAdded = offerAdded+1;
        }else if(operation.equalsIgnoreCase("minus")) {
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

        tvDealAmount.setText("$ "+String.format("%.0f", (Double.parseDouble(checkoutPrice)+Double.parseDouble(dealAmount))));
        tvAmount.setText("$ "+String.format("%.0f", (Double.parseDouble(checkoutPrice)+Double.parseDouble(dealAmount))));
        if(Double.parseDouble(dealAmount)>0){
            tvDealAmount.setVisibility(View.VISIBLE);
            tvDealAmount.setText("(Including $"+dealAmount+" Deals)");
            tvOfferTxt.setText("Added "+offerAdded+" deals");
        }else{
            tvDealAmount.setVisibility(View.GONE);
            tvOfferTxt.setText(Constant.msg_Offer_Deal);
        }
    }

    public void animClickFalse(){
        rlOfferPopup.setClickable(false);
        imgCircle.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlOfferPopup.setClickable(true);
                imgCircle.setClickable(true);
                if(isOpen) {
                    rlC.setClickable(true);
                }else{
                    rlC.setClickable(false);
                }
            }
        },Constant.animation_duration);
    }

    public void animationFromBottomToTop(final View view){
        isOpen = true;
        viewOffer.setVisibility(View.GONE);
        rlC.setClickable(false);
        animClickFalse();

        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 0.5f);
        animation1.setDuration(Constant.animation_duration);
//        animation1.setStartOffset(5000);
        animation1.setFillAfter(true);
        rlC.startAnimation(animation1);

        rlC.setVisibility(View.VISIBLE);
        float y1 = rlA.getY();
        ObjectAnimator anim = ObjectAnimator.ofFloat(view,"y",y1,y1-animContainerHeight);
        anim.setDuration(Constant.animation_duration);
        anim.start();

        imgCircle.animate().setDuration(Constant.animation_duration).rotation(180).start();
    }

    public void animationFromTopToBottom(final View view) {
        isOpen = false;
        viewOffer.setVisibility(View.VISIBLE);
        rlC.setClickable(false);
        animClickFalse();

        AlphaAnimation animation1 = new AlphaAnimation(0.5f, 0.0f);
        animation1.setDuration(Constant.animation_duration);
//        animation1.setStartOffset(5000);
        animation1.setFillAfter(true);
        rlC.startAnimation(animation1);

        rlC.setVisibility(View.GONE);
        float y1 = rlA.getY();
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "y",  y1,y1+animContainerHeight);
        anim.setDuration(Constant.animation_duration);
        anim.start();

        rlC.setVisibility(View.GONE);
        imgCircle.animate().setDuration(Constant.animation_duration).rotation(360).start();
    }
}
