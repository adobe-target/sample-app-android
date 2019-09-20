package com.wetravel.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.R;
import com.wetravel.Utils.Utility;

public class PaymentActivityOld extends AppCompatActivity {
    ImageView imgBack;
    TextView tvHeading;

    TextView tvLocation,tvAmount,tvDayInfo,tvBusInfo,tvTravelInfo;


    RelativeLayout rlCreditCard;
    TextView txtCreditCard;
    ImageView imgCreditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_payment);

        initLayouts();
    }

    //View initialization
    public void initLayouts(){
        imgBack = findViewById(R.id.imgBack);
        imgBack.getLayoutParams().width = Utility.deviceWidth*5/100;
        imgBack.getLayoutParams().height = Utility.deviceWidth*5/100;

        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);

        tvLocation = findViewById(R.id.tvLocation);
        tvLocation.setTextSize(Utility.txtSize_15dp);

        tvAmount = findViewById(R.id.tvAmount);
        tvAmount.setTextSize(Utility.txtSize_15dp);

        tvDayInfo = findViewById(R.id.tvDayInfo);
        tvDayInfo.setTextSize(Utility.txtSize_15dp);

        tvBusInfo = findViewById(R.id.tvBusInfo);
        tvBusInfo.setTextSize(Utility.txtSize_15dp);

        tvTravelInfo = findViewById(R.id.tvTravelInfo);
        tvTravelInfo.setTextSize(Utility.txtSize_12dp);

        rlCreditCard = findViewById(R.id.rlCreditCard);

        imgCreditCard = findViewById(R.id.imgCreditCard);

        txtCreditCard = findViewById(R.id.txtCreditCard);
        txtCreditCard.setTextSize(Utility.txtSize_15dp);

        openCreditCardInfo(true);
    }

    public void openCreditCardInfo(boolean status){
        if(status){
            rlCreditCard.setBackgroundResource(R.drawable.bg_payment_selected);
            imgCreditCard.setBackgroundResource(R.drawable.radio_select);
        }else{
            rlCreditCard.setBackgroundResource(R.drawable.bg_payment_unselected);
            imgCreditCard.setBackgroundResource(R.drawable.radio_without_select);
        }
    }
}
