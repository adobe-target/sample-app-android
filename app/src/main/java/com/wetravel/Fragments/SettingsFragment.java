package com.wetravel.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Controller.HomeActivity;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

public class SettingsFragment extends Fragment {
    View rootView;

    RelativeLayout rlLaunchID,rlMboxInformations,rlHomeOffers,rlSearchOffers,rlSeatingDeals,rlPaymentsOffers;
    TextView txtLaunchID,txtMboxInformations,txtHomeOffers,txtSearchOffers,txtSeatingDeals,txtPaymentsOffers;
    EditText edtLaunchID,edtHomeOffers,edtSearchOffers,edtSeatingDeals,edtPaymentsOffers;

    ImageView imgSave;
    RelativeLayout rlSave;
    TextView tvSave;

    RelativeLayout rlMain;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings,container,false);

        ((HomeActivity)getActivity()).setHeader("Settings");
        initLayout();

        return rootView;
    }

    //View initialization
    public void initLayout(){
        rlMain = rootView.findViewById(R.id.rlMain);
        progressBar = rootView.findViewById(R.id.progressBar);

        rlLaunchID = rootView.findViewById(R.id.rlLaunchID);
        txtLaunchID = rootView.findViewById(R.id.txtLaunchID);
        edtLaunchID = rootView.findViewById(R.id.edtLaunchID);
        setProperty(rlLaunchID,txtLaunchID,edtLaunchID);

        rlMboxInformations = rootView.findViewById(R.id.rlMboxInformations);
        rlMboxInformations.setPadding(Utility.deviceWidth*60/1000,Utility.deviceWidth*3/100,Utility.deviceWidth*60/1000,0);

        txtMboxInformations = rootView.findViewById(R.id.txtMboxInformations);
        txtMboxInformations.setTextSize(Utility.txtSize_15dp);
        txtMboxInformations.setTypeface(Utility.font_roboto_bold);

        rlHomeOffers = rootView.findViewById(R.id.rlHomeOffers);
        txtHomeOffers = rootView.findViewById(R.id.txtHomeOffers);
        edtHomeOffers = rootView.findViewById(R.id.edtHomeOffers);
        setProperty(rlHomeOffers,txtHomeOffers,edtHomeOffers);

        rlSearchOffers = rootView.findViewById(R.id.rlSearchOffers);
        txtSearchOffers = rootView.findViewById(R.id.txtSearchOffers);
        edtSearchOffers = rootView.findViewById(R.id.edtSearchOffers);
        setProperty(rlSearchOffers,txtSearchOffers,edtSearchOffers);

        rlSeatingDeals = rootView.findViewById(R.id.rlSeatingDeals);
        txtSeatingDeals = rootView.findViewById(R.id.txtSeatingDeals);
        edtSeatingDeals = rootView.findViewById(R.id.edtSeatingDeals);
        setProperty(rlSeatingDeals,txtSeatingDeals,edtSeatingDeals);

        rlPaymentsOffers = rootView.findViewById(R.id.rlPaymentsOffers);
        txtPaymentsOffers = rootView.findViewById(R.id.txtPaymentsOffers);
        edtPaymentsOffers = rootView.findViewById(R.id.edtPaymentsOffers);
        setProperty(rlPaymentsOffers,txtPaymentsOffers,edtPaymentsOffers);

        imgSave = rootView.findViewById(R.id.imgSave);
        rlSave = rootView.findViewById(R.id.rlSave);
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,Utility.deviceHeight*72/100,0,0);
        rlSave.setLayoutParams(params);

        tvSave = rootView.findViewById(R.id.tvSave);
        tvSave.setTextSize(Utility.txtSize_12dp);
        tvSave.setTypeface(Utility.font_roboto_bold);

        setSharedPreferenceValue();
        changeAlertBackground(false);
    }

    public void changeAlertBackground(boolean b){
        if(b){
            imgSave.setImageResource(R.drawable.bg_btn_find_bus_one);
            tvSave.setTextColor(getActivity().getResources().getColor(R.color.color_ffffff));
            rlSave.setClickable(true);
        }else{
            imgSave.setImageResource(R.drawable.bg_alert_find_bus_one);
            tvSave.setTextColor(getActivity().getResources().getColor(R.color.color_BCBCBC));
            rlSave.setClickable(false);
        }
    }

    public void setProperty(RelativeLayout rl, TextView tv, EditText edt){
        rl.setPadding(Utility.deviceWidth*60/1000,0,Utility.deviceWidth*60/1000,0);

        tv.setTextSize(Utility.txtSize_12dp);
        tv.setTypeface(Utility.font_roboto_regular);

        edt.setTextSize(Utility.txtSize_12dp);
        edt.setTypeface(Utility.font_roboto_regular);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeAlertBackground(true);
            }
        });
    }

    public void setSharedPreferenceValue(){
        loaderVisibility(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loaderVisibility(false);

                if(!Utility.getInSharedPreference(getActivity(),Constant.shared_launch_id,"").equalsIgnoreCase("")){
                    edtLaunchID.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_launch_id,""));
                    edtLaunchID.setSelection(edtLaunchID.getText().toString().length());
                }

                if(!Utility.getInSharedPreference(getActivity(),Constant.shared_home_offer_recommendation,"").equalsIgnoreCase("")){
                    edtHomeOffers.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_home_offer_recommendation,""));
                    edtHomeOffers.setSelection(edtHomeOffers.getText().toString().length());
                }

                if(!Utility.getInSharedPreference(getActivity(),Constant.shared_search_offers_filters,"").equalsIgnoreCase("")){
                    edtSearchOffers.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_search_offers_filters,""));
                    edtSearchOffers.setSelection(edtSearchOffers.getText().toString().length());
                }

                if(!Utility.getInSharedPreference(getActivity(),Constant.shared_seating_deals,"").equalsIgnoreCase("")){
                    edtSeatingDeals.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_seating_deals,""));
                    edtSeatingDeals.setSelection(edtSeatingDeals.getText().toString().length());
                }

                if(!Utility.getInSharedPreference(getActivity(),Constant.shared_payments_offers,"").equalsIgnoreCase("")){
                    edtPaymentsOffers.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_payments_offers,""));
                    edtPaymentsOffers.setSelection(edtPaymentsOffers.getText().toString().length());
                }
            }
        },Constant.delay_api);
    }

    public void saveData(){
        AppDialogs.dialogLoaderShow(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDialogs.dialogLoaderHide();

                Utility.saveInSharedPreference(getActivity(),Constant.shared_launch_id,edtLaunchID.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_home_offer_recommendation,edtHomeOffers.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_search_offers_filters,edtSearchOffers.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_seating_deals,edtSeatingDeals.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_payments_offers,edtPaymentsOffers.getText().toString());

                Utility.showToast(getActivity(),Constant.msg_settings_saved_successfully);
                changeAlertBackground(false);
            }
        },Constant.delay_api);
    }

    public void loaderVisibility(boolean b){
        rlMain.setVisibility(b ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }
}
