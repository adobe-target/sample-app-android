package com.wetravel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.CityAdapter;
import com.wetravel.Adapter.OffersAdapter;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Controller.HomeActivity;
import com.wetravel.Controller.SearchBusActivity;
import com.wetravel.Controller.ThankYouActivity;
import com.wetravel.Models.City;
import com.wetravel.Models.HomeResponse;
import com.wetravel.Models.Offer;
import com.wetravel.Models.SearchResponse;
import com.wetravel.Models.Travel;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.CalenderView;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.DateResponse;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements DateResponse {
    View rootView;
    TextView tvWhere;
    TextView tvFrom,tvTo,txtDate,tvDate,tvOffers;
    AutoCompleteTextView edtDeparture,edtDestination;
    RelativeLayout rlDepartureError,rlDestinationError;
    TextView tvDepartureErrorMsg,tvDestinationErrorMsg;
    RelativeLayout rlFrom,rlTo,rlDate;
    ScrollView svMain;
    ImageView imgDate;
    Button btnFindBus;
    CardView cvFromTo;

    RecyclerView rvOffers;
    OffersAdapter offersAdapter;
    ArrayList<Offer> offerList = new ArrayList<>();

    ProgressBar progressBar;
    PopupWindow datePopUp;

    String selectDate = "", dayOfWeek=""; //dayOfWeek = 0 for sunday and dayOfWeek = 6 for saturday

    ArrayList<City> departureList = new ArrayList<>();
    ArrayList<City> destinationList = new ArrayList<>();
    String departureCityCode="",destinationCityCode="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home,container,false);

        ((HomeActivity)getActivity()).setHeader("We.Travel");
        initLayout();
        loaderVisibility(true);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getOffers();
                getDeparture();
                getDestination();
            }
        },Constant.delay_api);*/
        getOffers();
        getDeparture();
        getDestination();
        findBusAlert(true);

        return rootView;
    }

    //View initialization
    public void initLayout(){
        //Scroll View Property
        svMain = rootView.findViewById(R.id.svMain);
        svMain.smoothScrollTo(0,0);

        //Heading Where Property
        tvWhere = rootView.findViewById(R.id.tvWhere);
        tvWhere.setPadding(Utility.deviceWidth*6/100,Utility.deviceWidth*4/100,0,0);
        tvWhere.setTextSize(Utility.txtSize_16dp);
        tvWhere.setTypeface(Utility.font_roboto_regular);

        //From layer Property
        tvFrom = rootView.findViewById(R.id.tvFrom);
        tvFrom.setTextSize(Utility.txtSize_12dp);
        tvFrom.setTypeface(Utility.font_roboto_medium);
        tvFrom.setPadding(Utility.deviceWidth*9/100,0,0,0);

        edtDeparture = rootView.findViewById(R.id.edtDeparture);
        edtDeparture.setPadding(Utility.deviceWidth*9/100,0,0,0);
        edtDeparture.getLayoutParams().width = Utility.deviceWidth*74/100;
        edtDeparture.setTextSize(Utility.txtSize_15dp);
        edtDeparture.setTypeface(Utility.font_roboto_regular);
        edtDeparture.setThreshold(1);
        edtDeparture.setDropDownVerticalOffset(Utility.deviceWidth*2/100);
        edtDeparture.setDropDownWidth(Utility.deviceWidth*65/100);
//        edtDeparture.setDropDownBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_autocomplete));
        edtDeparture.setDropDownBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.border_round_gray_autocomplete));
        edtDeparture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utility.hideKeyboard(getActivity());
                City city = (City) parent.getItemAtPosition(position);
                departureCityCode = city.getCity_code();
                edtDeparture.setText(city.getCity_name());
                edtDeparture.setSelection(edtDeparture.getText().toString().length());
            }
        });
        edtDeparture.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                findBusAlert(true);
            }
        });

        edtDeparture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtDeparture.showDropDown();
                return false;
            }
        });


        rlFrom = rootView.findViewById(R.id.rlFrom);
        rlFrom.setPadding(Utility.deviceWidth*5/100,Utility.deviceWidth*4/100,0,0);

        //To layer Property
        tvTo = rootView.findViewById(R.id.tvTo);
        tvTo.setPadding(Utility.deviceWidth*9/100,0,0,0);
        tvTo.setTextSize(Utility.txtSize_12dp);
        tvTo.setTypeface(Utility.font_roboto_medium);


        edtDestination = rootView.findViewById(R.id.edtDestination);
        edtDestination.setPadding(Utility.deviceWidth*9/100,0,0,0);
        edtDestination.setTextSize(Utility.txtSize_15dp);
        edtDestination.getLayoutParams().width = Utility.deviceWidth*74/100;
        edtDeparture.setTypeface(Utility.font_roboto_regular);
        edtDestination.setThreshold(1);
        edtDestination.setDropDownVerticalOffset(Utility.deviceWidth*2/100);
        edtDestination.setDropDownWidth(Utility.deviceWidth*65/100);
        edtDestination.setDropDownBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_autocomplete));
        edtDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utility.hideKeyboard(getActivity());
                City city = (City) parent.getItemAtPosition(position);
                destinationCityCode = city.getCity_code();
                edtDestination.setText(city.getCity_name());
                edtDestination.setSelection(edtDestination.getText().toString().length());
            }
        });
        edtDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    findBusAlert(true);
            }
        });
        edtDestination.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtDestination.showDropDown();
                return false;
            }
        });


        tvDepartureErrorMsg = rootView.findViewById(R.id.tvDepartureErrorMsg);
        tvDepartureErrorMsg.setTextSize(Utility.txtSize_12dp);
        tvDepartureErrorMsg.setTypeface(Utility.font_roboto_regular_italic);

        tvDestinationErrorMsg = rootView.findViewById(R.id.tvDestinationErrorMsg);
        tvDestinationErrorMsg.setTextSize(Utility.txtSize_12dp);
        tvDestinationErrorMsg.setTypeface(Utility.font_roboto_regular_italic);

        rlTo = rootView.findViewById(R.id.rlTo);
        rlTo.setPadding(Utility.deviceWidth*5/100,Utility.deviceWidth*4/100,0,Utility.deviceWidth*4/100);

        //Date layer Property
        txtDate = rootView.findViewById(R.id.txtDate);
        txtDate.setTextSize(Utility.txtSize_12dp);
        txtDate.setPadding(Utility.deviceWidth*5/100,Utility.deviceWidth*4/100,0,0);
        txtDate.setTypeface(Utility.font_roboto_medium);

        Calendar mCalender = Calendar.getInstance();
        int currentDay = mCalender.get(Calendar.DAY_OF_MONTH);
        int currentMonth = mCalender.get(Calendar.MONTH)+1;
        int currentYear = mCalender.get(Calendar.YEAR);
        //int dayOfWeek = mCalender.get(Calendar.DAY_OF_WEEK) - 1;

        tvDate = rootView.findViewById(R.id.tvDate);
        tvDate.setPadding(Utility.deviceWidth*5/100,0,0,0);
        tvDate.setTextSize(Utility.txtSize_15dp);
        tvDate.setTypeface(Utility.font_roboto_regular);
        tvDate.setText(Utility.changeDateFormat(Constant.date_ddmyyyy,Constant.date_ddmmmyyyy,""+currentDay+"-"+currentMonth+"-"+currentYear));

        rlDate = rootView.findViewById(R.id.rlDate);
        imgDate = rootView.findViewById(R.id.imgDate);
        imgDate.setPadding(Utility.deviceWidth*10/100,Utility.deviceWidth*8/100,0,0);
        rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalenderPopUp(v);
            }
        });


        btnFindBus = rootView.findViewById(R.id.btnFindBus);
//        btnFindBus.setTextSize(Utility.txtSize_12dp);
        btnFindBus.setTypeface(Utility.font_roboto_bold);
        RelativeLayout.LayoutParams params_btnFindBus = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params_btnFindBus.setMargins(Utility.deviceWidth*6/100,Utility.deviceWidth*1/100,Utility.deviceWidth*6/100,Utility.deviceWidth*5/100);
        params_btnFindBus.addRule(RelativeLayout.BELOW,R.id.rlDate);
        btnFindBus.setLayoutParams(params_btnFindBus);
        btnFindBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.dialogLoaderShow(getActivity());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFindBus();
                    }
                },Constant.delay_api);
            }
        });

        //Offers Heading layer Property
        tvOffers = rootView.findViewById(R.id.tvOffers);
        tvOffers.setTextSize(Utility.txtSize_20dp);
        tvOffers.setTypeface(Utility.font_roboto_bold);
        tvOffers.setPadding(Utility.deviceWidth*6/100,0,0,0);

        //Offers Banner layer Property
        rvOffers = rootView.findViewById(R.id.rvOffers);
        offersAdapter = new OffersAdapter(getActivity(),offerList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        ((GridLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rvOffers.setLayoutManager(layoutManager);
        rvOffers.setItemAnimator(new DefaultItemAnimator());

        //Progress Bar
        progressBar = rootView.findViewById(R.id.progressBar);

        rlDestinationError = rootView.findViewById(R.id.rlDestinationError);
        rlDepartureError = rootView.findViewById(R.id.rlDepartureError);

        cvFromTo = rootView.findViewById(R.id.cvFromTo);
        RelativeLayout.LayoutParams params_cvFromTo = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params_cvFromTo.setMargins(Utility.deviceWidth*4/100,Utility.deviceWidth*1/100,Utility.deviceWidth*4/100,0);
        params_cvFromTo.addRule(RelativeLayout.BELOW,R.id.tvWhere);
        cvFromTo.setLayoutParams(params_cvFromTo);
    }

    //Get Offers api
    public void getOffers(){
        rvOffers.setAdapter(offersAdapter);

        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_offers) {
            @Override
            public void response(String response) {
                try {
                    Gson gson = new Gson();
                    HomeResponse homeResponse = gson.fromJson(response,HomeResponse.class);
                    offerList.addAll(homeResponse.offers);
                    offersAdapter.notifyDataSetChanged();
                    loaderVisibility(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //Get Departure Cities api
    public void getDeparture(){
        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_departure) {
            @Override
            public void response(String response) {
                try {
                    Gson gson = new Gson();
                    City city = gson.fromJson(response,City.class);
                    departureList.addAll(city.cities);
                    edtDeparture.setAdapter(new CityAdapter(getActivity(),departureList));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //Get Destination Cities api
    public void getDestination(){
        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_destination) {
            @Override
            public void response(String response) {
                try {
                    Gson gson = new Gson();
                    City city = gson.fromJson(response,City.class);
                    destinationList.addAll(city.cities);
                    edtDestination.setAdapter(new CityAdapter(getActivity(),destinationList));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //Get Offers api
    public void getFindBus(){
        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_search_success) {
            @Override
            public void response(String response) {
                AppDialogs.dialogLoaderHide();
                try {
                    Gson gson = new Gson();
                    SearchResponse searchResponse = gson.fromJson(response,SearchResponse.class);

                    if(searchResponse.getResponse_code().equalsIgnoreCase("1")){
                        ArrayList<Travel> tempList = new ArrayList<>();
                        boolean statusDeparture = false;
                        boolean statusDestination = false;
                        for(int i = 0;i<searchResponse.travel_details.size();i++){
                            if(searchResponse.travel_details.get(i).getDeparture().equalsIgnoreCase(edtDeparture.getText().toString())){
                                statusDeparture = true;
                            }

                            if(searchResponse.travel_details.get(i).getDestination().equalsIgnoreCase(edtDestination.getText().toString())){
                                statusDestination = true;
                            }

                            if(searchResponse.travel_details.get(i).getDeparture().equalsIgnoreCase(edtDeparture.getText().toString()) &&
                                    searchResponse.travel_details.get(i).getDestination().equalsIgnoreCase(edtDestination.getText().toString()) &&
                                    searchResponse.travel_details.get(i).getTravel_week_day().contains(dayOfWeek)){
                                tempList.add(searchResponse.travel_details.get(i));
                            }
                        }

                        if(!statusDeparture){
                            rlDepartureError.setVisibility(View.VISIBLE);
                        }

                        if(statusDeparture && !statusDestination){
                            rlDestinationError.setVisibility(View.VISIBLE);
                        }

                        if(tempList.size()>0) {
                            findBusAlert(true);
                            Intent intent = new Intent(getActivity(),SearchBusActivity.class);
                            intent.putExtra("offerList",searchResponse.offers);
                            intent.putExtra("searchList",tempList);
                            intent.putExtra("departure",""+edtDeparture.getText().toString());
                            intent.putExtra("destination",""+edtDestination.getText().toString());
                            intent.putExtra("heading",""+departureCityCode+" to "+destinationCityCode);
                            intent.putExtra("mDate",tvDate.getText().toString());
                            startActivity(intent);
                        }else{
                            findBusAlert(false);
                        }
                    }
                    loaderVisibility(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void findBusAlert(boolean b){
        if(b){
            rlDepartureError.setVisibility(View.GONE);
            rlDestinationError.setVisibility(View.GONE);
            btnFindBus.setBackground(getActivity().getResources().getDrawable(R.drawable.blue_round_button));
            btnFindBus.setTextColor(getActivity().getResources().getColor(R.color.color_ffffff));
            btnFindBus.setClickable(true);
        }else{
            btnFindBus.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_round_button));
            btnFindBus.setTextColor(getActivity().getResources().getColor(R.color.color_BCBCBC));
            btnFindBus.setClickable(false);
        }
    }

    //Show and hide loader
    public void loaderVisibility(boolean b){
        svMain.setVisibility(b ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void openCalenderPopUp(View v){
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int[] location = new int[2];
        rlDate.getLocationOnScreen(location);
        View mView = inflater.inflate(R.layout.calender_view_popup,null);

        datePopUp = new PopupWindow(mView, Utility.deviceWidth*85/100,Utility.deviceWidth*83/100,false);
        datePopUp.setTouchable(true);
        datePopUp.setFocusable(true);
        datePopUp.setOutsideTouchable(true);
        datePopUp.showAtLocation(v,Gravity.NO_GRAVITY, location[0]+Utility.deviceWidth*7/100,
                location[1]+Utility.deviceWidth*18/100);

        CalenderView calenderView = mView.findViewById(R.id.cvPopup);
        calenderView.setListener(HomeFragment.this,selectDate);
    }

    @Override
    public void onDateSelected(String selectedDate,String dayOfWeek) {
        datePopUp.dismiss();
        this.selectDate = selectedDate;
        this.dayOfWeek = dayOfWeek;

        tvDate.setText(Utility.changeDateFormat(Constant.date_ddmyyyy,Constant.date_ddmmmyyyy,selectDate));
        findBusAlert(true);
    }
}
