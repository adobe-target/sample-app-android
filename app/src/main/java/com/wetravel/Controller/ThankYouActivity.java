package com.wetravel.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.RecommandationAdapter;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Fragments.MyProfileFragment;
import com.wetravel.Models.Recommandation;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class ThankYouActivity extends AppCompatActivity {
    TextView tvHeading;
    Button btnDone;
    TextView tvLocation,tvDayInfo,tvBusInfo,tvTravelInfo;
    ImageView ivProfile;

    RecyclerView rvRecommandations;
    RecommandationAdapter recommandationbAdapter;
    ArrayList<Recommandation> recommandations = new ArrayList<>();

    TextView txtRecommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_thank_you);

        initLayouts();
        setExtraValue();
        AppDialogs.dialogLoaderShow(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecommandations();
            }
        },Constant.delay_api);
    }

    //View initialization
    public void initLayouts(){
        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);
        tvHeading.setTypeface(Utility.font_roboto_bold);

        tvLocation = findViewById(R.id.tvLocation);
        tvLocation.setTextSize(Utility.txtSize_12dp);
        tvLocation.setTypeface(Utility.font_roboto_bold);

        tvDayInfo = findViewById(R.id.tvDayInfo);
        tvDayInfo.setTextSize(Utility.txtSize_12dp);
        tvDayInfo.setTypeface(Utility.font_roboto_regular);

        tvBusInfo = findViewById(R.id.tvBusInfo);
        tvBusInfo.setTextSize(Utility.txtSize_12dp);
        tvBusInfo.setTypeface(Utility.font_roboto_regular);

        tvTravelInfo = findViewById(R.id.tvTravelInfo);
        tvTravelInfo.setTextSize(Utility.txtSize_11dp);
        tvTravelInfo.setTypeface(Utility.font_roboto_regular);

        rvRecommandations = findViewById(R.id.rvRecommandations);
        recommandationbAdapter = new RecommandationAdapter(this,recommandations);
        RecyclerView.LayoutManager layoutManagerTravels = new LinearLayoutManager(this);
        rvRecommandations.setLayoutManager(layoutManagerTravels);
        rvRecommandations.setItemAnimator(new DefaultItemAnimator());
        rvRecommandations.setAdapter(recommandationbAdapter);

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras() != null) {
                    ThankYouActivity.this.finish();
                }else{
                    Intent intent = new Intent(ThankYouActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        btnDone.setTextSize(Utility.txtSize_12dp);
        btnDone.setTypeface(Utility.font_roboto_bold);

        txtRecommendation = findViewById(R.id.txtRecommendation);
        txtRecommendation.setTextSize(Utility.txtSize_12dp);
        txtRecommendation.setTypeface(Utility.font_roboto_regular);

        ivProfile = findViewById(R.id.ivProfile);
        ivProfile.getLayoutParams().height = Utility.deviceWidth*6/100;
        ivProfile.getLayoutParams().width = Utility.deviceWidth*6/100;
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankYouActivity.this, HomeActivity.class);
                intent.putExtra("navigate","MyProfile");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void setExtraValue(){
        if(getIntent().getExtras() != null){
            tvHeading.setText(""+getIntent().getExtras().getString("departure")+" to "+getIntent().getExtras().getString("destination"));
            tvLocation.setText("Status "+getIntent().getExtras().getString("status")+" | "+getIntent().getExtras().getString("curSymbol")+getIntent().getExtras().getString("amount"));
            tvDayInfo.setText("On Wednesday, "+Utility.changeDateFormat(Constant.date_ddmyyyy,Constant.date_ddmmmyyyy,getIntent().getExtras().getString("date"))+", "+getIntent().getExtras().getString("time"));
            tvBusInfo.setText("Seat No.("+getIntent().getExtras().getString("seat")+") | Duration("+getIntent().getExtras().getString("hour")+" hrs)");
            tvTravelInfo.setText(""+getIntent().getExtras().getString("travelName")+"("+getIntent().getExtras().getString("busInfo")+")");
        }
    }

    public void getRecommandations(){
        GetJSON getJSON = new GetJSON(this,Constant.json_recommandation) {
            @Override
            public void response(String response) {
                AppDialogs.dialogLoaderHide();
                try {
                    Gson gson = new Gson();
                    Recommandation recommandation = gson.fromJson(response,Recommandation.class);

                    recommandations.addAll(recommandation.recommandations);
                    recommandationbAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
