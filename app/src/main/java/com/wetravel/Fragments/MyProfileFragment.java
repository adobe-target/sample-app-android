package com.wetravel.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.LoyaltyAdapter;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Controller.HomeActivity;
import com.wetravel.Models.Loyalty;
import com.wetravel.Models.Recommandation;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

public class MyProfileFragment extends Fragment {
    View rootView;
    Spinner spnLoyaltyLevel;
    Button btnSave;
    EditText edtName,edtAge;
    TextView txtName,txtAge,txtLoyaltyLevel;
    int loyaltyLevel = 0;
    RelativeLayout rlName,rlAge,rlLoyaltyLevel;

    RelativeLayout rlMain;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_profile,container,false);

        ((HomeActivity)getActivity()).setHeader("My Profile");
        initLayout();
        loaderVisibility(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLoyalty();
            }
        },Constant.delay_api);

        return rootView;
    }

    //View initialization
    public void initLayout(){
        rlMain = rootView.findViewById(R.id.rlMain);
        progressBar = rootView.findViewById(R.id.progressBar);

        rlName = rootView.findViewById(R.id.rlName);
        rlName.setPadding(Utility.deviceWidth*60/1000,0,Utility.deviceWidth*60/1000,0);

        rlAge = rootView.findViewById(R.id.rlAge);
        rlAge.setPadding(Utility.deviceWidth*60/1000,0,Utility.deviceWidth*60/1000,0);

        rlLoyaltyLevel = rootView.findViewById(R.id.rlLoyaltyLevel);
        rlLoyaltyLevel.setPadding(Utility.deviceWidth*60/1000,0,Utility.deviceWidth*60/1000,0);

        txtName = rootView.findViewById(R.id.txtName);
        txtName.setTextSize(Utility.txtSize_12dp);
        txtName.setTypeface(Utility.font_roboto_regular);

        txtAge = rootView.findViewById(R.id.txtAge);
        txtAge.setTextSize(Utility.txtSize_12dp);
        txtAge.setTypeface(Utility.font_roboto_regular);

        txtLoyaltyLevel = rootView.findViewById(R.id.txtLoyaltyLevel);
        txtLoyaltyLevel.setTextSize(Utility.txtSize_12dp);
        txtLoyaltyLevel.setTypeface(Utility.font_roboto_regular);

        edtName = rootView.findViewById(R.id.edtName);
        edtName.setTextSize(Utility.txtSize_12dp);
        edtName.setTypeface(Utility.font_roboto_regular);
        edtName.addTextChangedListener(new TextWatcher() {
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

        edtAge = rootView.findViewById(R.id.edtAge);
        edtAge.setTextSize(Utility.txtSize_12dp);
        edtAge.setTypeface(Utility.font_roboto_regular);
        edtAge.addTextChangedListener(new TextWatcher() {
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

        spnLoyaltyLevel = rootView.findViewById(R.id.spnLoyaltyLevel);
        spnLoyaltyLevel.setDropDownVerticalOffset(Utility.deviceWidth*12/100);
        spnLoyaltyLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loyaltyLevel = position;
                if(position!=0){
                    changeAlertBackground(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave = rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        btnSave.setTextSize(Utility.txtSize_12dp);
        btnSave.setTypeface(Utility.font_roboto_bold);

        setSharedPreferenceValue();
    }

    public void getLoyalty(){
        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_loyalty) {
            @Override
            public void response(String response) {
                loaderVisibility(false);
                try {
                    Gson gson = new Gson();
                    Loyalty loyalty = gson.fromJson(response,Loyalty.class);

                    spnLoyaltyLevel.setAdapter(new LoyaltyAdapter(getActivity(),loyalty.loyalties));
                    if(!Utility.getInSharedPreference(getActivity(),Constant.shared_loyalty_level,"").equalsIgnoreCase("")) {
                        spnLoyaltyLevel.setSelection(Integer.parseInt(Utility.getInSharedPreference(getActivity(), Constant.shared_loyalty_level, "")));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void changeAlertBackground(boolean b){
        if(b){
            btnSave.setBackground(getActivity().getResources().getDrawable(R.drawable.blue_round_button));
            btnSave.setTextColor(getActivity().getResources().getColor(R.color.color_ffffff));
            btnSave.setClickable(true);
        }else{
            btnSave.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_round_button));
            btnSave.setTextColor(getActivity().getResources().getColor(R.color.color_BCBCBC));
            btnSave.setClickable(false);
        }
    }

    public void setSharedPreferenceValue(){
        if(!Utility.getInSharedPreference(getActivity(),Constant.shared_name,"").equalsIgnoreCase("")){
            edtName.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_name,""));
            edtName.setSelection(edtName.getText().toString().length());
        }

        if(!Utility.getInSharedPreference(getActivity(),Constant.shared_age,"").equalsIgnoreCase("")){
            edtAge.setText(""+Utility.getInSharedPreference(getActivity(),Constant.shared_age,""));
            edtAge.setSelection(edtAge.getText().toString().length());
        }
        changeAlertBackground(false);
    }

    public void saveData(){
        AppDialogs.dialogLoaderShow(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDialogs.dialogLoaderHide();

                Utility.saveInSharedPreference(getActivity(),Constant.shared_name,edtName.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_age,edtAge.getText().toString());
                Utility.saveInSharedPreference(getActivity(),Constant.shared_loyalty_level,""+loyaltyLevel);

                Utility.showToast(getActivity(),Constant.msg_profile_saved_successfully);
                changeAlertBackground(false);
            }
        },Constant.delay_api);
    }

    public void loaderVisibility(boolean b){
        rlMain.setVisibility(b ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }
}
