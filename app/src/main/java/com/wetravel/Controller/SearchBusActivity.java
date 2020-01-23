package com.wetravel.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Adapter.SearchOffersAdapter;
import com.wetravel.Adapter.SearchTravelsAdapter;
import com.wetravel.Models.Offer;
import com.wetravel.Models.Travel;
import com.wetravel.Models.TravelSorter;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchBusActivity extends AppCompatActivity {
    String heading,mDate,departure,destination,strFilter = "";
    ImageView imgBack,imgFilter,imgOfferFullBanner,imgOfferCancel;
    TextView tvHeading;
    TextView tvDate,tvBusFound,txtRecommended;
    Button rlBook;
    RelativeLayout rlBusFound;
    LinearLayout rlFilter;

    RecyclerView rvSearchOffers;
    SearchOffersAdapter searchOffersAdapter;
    ArrayList<Offer> searchOffersList = new ArrayList<>();

    RecyclerView rvSearchTravels;
    SearchTravelsAdapter searchTravelsAdapter;
    ArrayList<Travel> searchTravelsList = new ArrayList<>();

    ArrayList<Travel> masterList;
    ArrayList<Travel> filterList = null;

    String travelId="",travelName,departureTime,busInfo;

    public int banner_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
    }

    public void setUpSearch() {
        setContentView(R.layout.activity_search_bus);
        initLayouts();
        getSearchData();
    }

    public void getSearchData(){
        searchOffersList.clear();
        searchTravelsList.clear();

        heading = getIntent().getExtras().getString("heading");
        mDate = getIntent().getExtras().getString("mDate");
        departure = getIntent().getExtras().getString("departure");
        destination = getIntent().getExtras().getString("destination");

        tvHeading.setText(heading);
        tvDate.setText(mDate);

        ArrayList<Offer> offerList = getIntent().getParcelableArrayListExtra("offerList");


        // FIND BANNER DETERMINED BY ADOBE TARGET OFFER
        searchOffersList.add(offerList.get(banner_id));
        searchOffersList.add(offerList.get(banner_id+1));
//      searchOffersList.addAll(offerList);
        searchOffersAdapter.notifyDataSetChanged();

        masterList = getIntent().getParcelableArrayListExtra("searchList");

        ArrayList<Travel> tempList = (ArrayList<Travel>) masterList.clone();

        tvBusFound.setText(tempList.size()+" Buses found");
        searchTravelsList.addAll(tempList);
        searchTravelsAdapter.notifyDataSetChanged();
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

        imgFilter = findViewById(R.id.imgFilter);
        imgFilter.getLayoutParams().height = Utility.deviceWidth*6/100;
        imgFilter.getLayoutParams().width = Utility.deviceWidth*6/100;
        imgFilter.setPadding(Utility.deviceWidth*1/100, Utility.deviceWidth*1/100,0,0);

        rlFilter = findViewById(R.id.rlFilter);
        rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilter();
            }
        });

        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);
        tvHeading.setTypeface(Utility.font_roboto_bold);

        tvDate = findViewById(R.id.tvDate);
        tvDate.setTextSize(Utility.txtSize_15dp);
        tvDate.setTypeface(Utility.font_roboto_regular);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.dialogLoaderShow(SearchBusActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppDialogs.dialogLoaderHide();
                        if(travelId.equalsIgnoreCase("")) {
                            Utility.showToast(getApplicationContext(), Constant.msg_select_travel);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), SeatingActivityOld.class);
                            intent.putExtra("travelName", travelName);
                            intent.putExtra("travelDate", mDate);
                            intent.putExtra("departureTime", departureTime);
                            intent.putExtra("busInfo", busInfo);
                            startActivity(intent);
                        }
                    }
                }, Constant.delay_api);
            }
        });

        tvBusFound = findViewById(R.id.tvBusFound);
        tvBusFound.setTextSize(Utility.txtSize_16dp);
        tvBusFound.setTypeface(Utility.font_roboto_regular);

        txtRecommended = findViewById(R.id.txtRecommended);
        txtRecommended.setTextSize(Utility.txtSize_12dp);
        txtRecommended.setTypeface(Utility.font_roboto_regular);
        txtRecommended.setText(strFilter);

        //Offers horizontal view
        rvSearchOffers = findViewById(R.id.rvSearchOffers);
        searchOffersAdapter = new SearchOffersAdapter(this,searchOffersList);
        RecyclerView.LayoutManager layoutManagerOffers = new GridLayoutManager(this,1);
        ((GridLayoutManager) layoutManagerOffers).setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSearchOffers.setLayoutManager(layoutManagerOffers);
        rvSearchOffers.setItemAnimator(new DefaultItemAnimator());
        rvSearchOffers.setAdapter(searchOffersAdapter);

        //Offers travels view
        rvSearchTravels = findViewById(R.id.rvSearchTravels);
        searchTravelsAdapter = new SearchTravelsAdapter(this,searchTravelsList);
        RecyclerView.LayoutManager layoutManagerTravels = new LinearLayoutManager(this);
        rvSearchTravels.setLayoutManager(layoutManagerTravels);
        rvSearchTravels.setItemAnimator(new DefaultItemAnimator());
        rvSearchTravels.setAdapter(searchTravelsAdapter);

        rlBook = findViewById(R.id.btnBook);
        rlBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.dialogLoaderShow(SearchBusActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppDialogs.dialogLoaderHide();
                        if(travelId.equalsIgnoreCase("")) {
                            Utility.showToast(getApplicationContext(), Constant.msg_select_travel);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), SeatingActivity.class);
                            intent.putExtra("travelName", travelName);
                            intent.putExtra("travelDate", mDate);
                            intent.putExtra("departureTime", departureTime);
                            intent.putExtra("busInfo", busInfo);
                            startActivity(intent);
                        }
                    }
                }, Constant.delay_api);
            }
        });

        rlBusFound = findViewById(R.id.rlBusFound);
        rlBusFound.setPadding(Utility.deviceWidth*5/100, Utility.deviceWidth*4/100, Utility.deviceWidth*4/100, Utility.deviceWidth*5/100);


        imgOfferCancel = findViewById(R.id.imgOfferCancel);
        imgOfferFullBanner = findViewById(R.id.imgOfferFullBanner);
        imgOfferCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.dialogLoaderShow(SearchBusActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppDialogs.dialogLoaderHide();

                        strFilter = "";
                        txtRecommended.setText(strFilter);
                        filterList = null;

                        imgOfferFullBanner.setVisibility(View.GONE);
                        imgOfferCancel.setVisibility(View.GONE);
                        rvSearchOffers.setVisibility(View.VISIBLE);

                        ArrayList<Travel> tempList = (ArrayList<Travel>) masterList.clone();

                        tvBusFound.setText(tempList.size()+" Buses found");
                        searchTravelsList.clear();
                        searchTravelsList.addAll(tempList);
                        searchTravelsAdapter.notifyDataSetChanged();
                    }
                }, Constant.delay_api);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void dialogFilter(){
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_search_filter,null);
        mBottomSheetDialog.setContentView(view);

        mBottomSheetDialog.show();

        ImageView imgRecommended = view.findViewById(R.id.imgRecommended);
        ImageView imgFare = view.findViewById(R.id.imgFare);
        ImageView imgJourneyTime = view.findViewById(R.id.imgJourneyTime);
        ImageView imgDepartureTime = view.findViewById(R.id.imgDepartureTime);
        ImageView imgRatings = view.findViewById(R.id.imgRatings);

        TextView tvTxtSortBy = view.findViewById(R.id.tvTxtSortBy);
        tvTxtSortBy.setTextSize(Utility.txtSize_14dp);
        tvTxtSortBy.setTypeface(Utility.font_roboto_regular);

        final TextView tvRecommended = view.findViewById(R.id.tvRecommended);
        tvRecommended.setTextSize(Utility.txtSize_14dp);
        tvRecommended.setTypeface(Utility.font_roboto_regular);

        final TextView tvFare = view.findViewById(R.id.tvFare);
        tvFare.setTextSize(Utility.txtSize_14dp);
        tvFare.setTypeface(Utility.font_roboto_regular);

        final TextView tvJourneyTime = view.findViewById(R.id.tvJourneyTime);
        tvJourneyTime.setTextSize(Utility.txtSize_14dp);
        tvJourneyTime.setTypeface(Utility.font_roboto_regular);

        final TextView tvDepartureTime = view.findViewById(R.id.tvDepartureTime);
        tvDepartureTime.setTextSize(Utility.txtSize_14dp);
        tvDepartureTime.setTypeface(Utility.font_roboto_regular);

        final TextView tvRatings = view.findViewById(R.id.tvRatings);
        tvRatings.setTextSize(Utility.txtSize_14dp);
        tvRatings.setTypeface(Utility.font_roboto_regular);


        RelativeLayout rlRecommended = view.findViewById(R.id.rlRecommended);
        rlRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter(Constant.filter_recommended,tvRecommended);
                mBottomSheetDialog.dismiss();
            }
        });

        RelativeLayout rlFare = view.findViewById(R.id.rlFare);
        rlFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter(Constant.filter_fare,tvFare);
                mBottomSheetDialog.dismiss();
            }
        });

        RelativeLayout rlJourneyTime = view.findViewById(R.id.rlJourneyTime);
        rlJourneyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter(Constant.filter_journey_time,tvJourneyTime);
                mBottomSheetDialog.dismiss();
            }
        });

        RelativeLayout rlDepartureTime = view.findViewById(R.id.rlDepartureTime);
        rlDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter(Constant.filter_departure_time,tvDepartureTime);
                mBottomSheetDialog.dismiss();
            }
        });

        RelativeLayout rlRatings = view.findViewById(R.id.rlRatings);
        rlRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter(Constant.filter_ratings,tvRatings);
                mBottomSheetDialog.dismiss();
            }
        });

        if(strFilter.equalsIgnoreCase(Constant.filter_recommended)) {
            tvRecommended.setTextColor(getResources().getColor(R.color.color_1473E6));
            imgRecommended.setVisibility(View.VISIBLE);
        }else if(strFilter.equalsIgnoreCase(Constant.filter_fare)) {
            tvFare.setTextColor(getResources().getColor(R.color.color_1473E6));
            imgFare.setVisibility(View.VISIBLE);
        }else if(strFilter.equalsIgnoreCase(Constant.filter_journey_time)) {
            tvJourneyTime.setTextColor(getResources().getColor(R.color.color_1473E6));
            imgJourneyTime.setVisibility(View.VISIBLE);
        }else if(strFilter.equalsIgnoreCase(Constant.filter_departure_time)) {
            tvDepartureTime.setTextColor(getResources().getColor(R.color.color_1473E6));
            imgDepartureTime.setVisibility(View.VISIBLE);
        }else if(strFilter.equalsIgnoreCase(Constant.filter_ratings)) {
            tvRatings.setTextColor(getResources().getColor(R.color.color_1473E6));
            imgRatings.setVisibility(View.VISIBLE);
        }
    }

    public void setFilter(final String str, final TextView tv){
        AppDialogs.dialogLoaderShow(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDialogs.dialogLoaderHide();

                tv.setTextColor(getResources().getColor(R.color.color_1473E6));
                strFilter = str;
                txtRecommended.setText(strFilter);
                ArrayList<Travel> tempList;
                if(filterList==null) {
                    tempList = (ArrayList<Travel>) masterList.clone();
                }else{
                    tempList = (ArrayList<Travel>) filterList.clone();
                }
                TravelSorter travelSorter = new TravelSorter(tempList);
                searchTravelsList.clear();

                if(str.equalsIgnoreCase(Constant.filter_recommended)) {
                    searchTravelsList.addAll(travelSorter.getSortedListByRecommended());
                }else if(str.equalsIgnoreCase(Constant.filter_fare)) {
                    searchTravelsList.addAll(travelSorter.getSortedListByFare());
                }else if(str.equalsIgnoreCase(Constant.filter_journey_time)) {
                    searchTravelsList.addAll(travelSorter.getSortedListByJourneyTime());
                }else if(str.equalsIgnoreCase(Constant.filter_departure_time)) {
                    searchTravelsList.addAll(travelSorter.getSortedListByDepartureTime());
                }else if(str.equalsIgnoreCase(Constant.filter_ratings)) {
                    searchTravelsList.addAll(travelSorter.getSortedListByRatings());
                }

                tvBusFound.setText(searchTravelsList.size() + " Buses found");
                searchTravelsAdapter.notifyDataSetChanged();
            }
        }, Constant.delay_api);
    }

    public void setFilterForOffers(final String tag, final String bannerUrl){
        AppDialogs.dialogLoaderShow(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDialogs.dialogLoaderHide();

                strFilter = "";
                txtRecommended.setText(strFilter);

                ArrayList<Travel> tempList = (ArrayList<Travel>) masterList.clone();
                TravelSorter travelSorter = new TravelSorter(tempList);
                searchTravelsList.clear();

                if(tag.equalsIgnoreCase(Constant.offer_tag_refund)){
                    filterList = travelSorter.getSortedListByRefund();
                    searchTravelsList.addAll(travelSorter.getSortedListByRefund());
                }else if(tag.equalsIgnoreCase(Constant.offer_tag_top_rated_bus)){
                    filterList = travelSorter.getSortedListByTopRatedBus();
                    searchTravelsList.addAll(travelSorter.getSortedListByTopRatedBus());
                }else if(tag.equalsIgnoreCase(Constant.offer_tag_new_operator)){
                    filterList = travelSorter.getSortedListByNewOperator();
                    searchTravelsList.addAll(travelSorter.getSortedListByNewOperator());
                }

                if(!bannerUrl.equalsIgnoreCase("")) {
                    imgOfferFullBanner.setVisibility(View.VISIBLE);
                    imgOfferCancel.setVisibility(View.VISIBLE);
                    rvSearchOffers.setVisibility(View.GONE);
                    Bitmap bitmap = Utility.drawableToBitmap(SearchBusActivity.this, (Utility.getDrawableFromAssets(SearchBusActivity.this, "IMAGES/" + Utility.deviceDensityImage + "/" + bannerUrl)));
                    imgOfferFullBanner.setImageBitmap(bitmap);
                }

                tvBusFound.setText(searchTravelsList.size() + " Buses found");
                searchTravelsAdapter.gotoupdate(searchTravelsList);
            }
        }, Constant.delay_api);
    }

    public void setTravelSelectable(String travelId,String travelName,String departureTime,String busInfo){
        this.travelId = travelId;
        this.travelName = travelName;
        this.departureTime = departureTime;
        this.busInfo = busInfo;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpSearch();
        getSearchData();
    }

}
