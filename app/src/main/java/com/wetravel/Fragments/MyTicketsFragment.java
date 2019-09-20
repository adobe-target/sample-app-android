package com.wetravel.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wetravel.Adapter.HistoryRecommendationsAdapter;
import com.wetravel.Adapter.MyTicketsAdapter;
import com.wetravel.Adapter.OffersAdapter;
import com.wetravel.Adapter.RecommandationAdapter;
import com.wetravel.BackEnd.GetJSON;
import com.wetravel.Controller.HomeActivity;
import com.wetravel.Models.MyOffers;
import com.wetravel.Models.MyTickets;
import com.wetravel.Models.Recommandation;
import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;

public class MyTicketsFragment extends Fragment {
    View rootView;
    RelativeLayout rlUpcoming,rlCompleted;
    TextView tvUpcoming,tvCompleted,tvRecommendations;
    View viewUpcoming,viewCompleted;

    RecyclerView rvMyTickets;
    MyTicketsAdapter myTicketsAdapter;
    ArrayList<MyTickets> myTickets = new ArrayList<>();
    ArrayList<MyTickets> masterList;

    RecyclerView rvRecommendations;
    HistoryRecommendationsAdapter historyRecommendationsAdapter;
    ArrayList<Recommandation> recommandations = new ArrayList<>();

    RelativeLayout rlMain;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_tickets,container,false);

        ((HomeActivity)getActivity()).setHeader("My Tickets");
        initLayout();
        loaderVisibility(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMyTickets();
            }
        },Constant.delay_api);

        return rootView;
    }

    //View initialization
    public void initLayout(){
        rlMain = rootView.findViewById(R.id.rlMain);
        progressBar = rootView.findViewById(R.id.progressBar);

        tvRecommendations = rootView.findViewById(R.id.tvRecommendations);
        tvRecommendations.setTextSize(Utility.txtSize_15dp);
        tvRecommendations.setTypeface(Utility.font_roboto_bold);

        tvUpcoming = rootView.findViewById(R.id.tvUpcoming);
        tvUpcoming.setTextSize(Utility.txtSize_12dp);
        tvUpcoming.setTypeface(Utility.font_roboto_regular);
        viewUpcoming = rootView.findViewById(R.id.viewUpcoming);
        rlUpcoming = rootView.findViewById(R.id.rlUpcoming);
        rlUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpcoming();
            }
        });

        tvCompleted = rootView.findViewById(R.id.tvCompleted);
        tvCompleted.setTextSize(Utility.txtSize_12dp);
        tvCompleted.setTypeface(Utility.font_roboto_regular);
        viewCompleted = rootView.findViewById(R.id.viewCompleted);
        rlCompleted = rootView.findViewById(R.id.rlCompleted);
        rlCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCompleted();
            }
        });

        rvMyTickets = rootView.findViewById(R.id.rvMyTickets);
        rvMyTickets.setPadding(0,0,0,Utility.deviceWidth*2/100);
        rvMyTickets.getLayoutParams().height = Utility.deviceHeight*52/100;
        myTicketsAdapter = new MyTicketsAdapter(getActivity(),myTickets);
        RecyclerView.LayoutManager layoutManagerTravels = new LinearLayoutManager(getActivity());
        rvMyTickets.setLayoutManager(layoutManagerTravels);
        rvMyTickets.setItemAnimator(new DefaultItemAnimator());
        rvMyTickets.setAdapter(myTicketsAdapter);

        rvRecommendations = rootView.findViewById(R.id.rvRecommendations);
        historyRecommendationsAdapter = new HistoryRecommendationsAdapter(getActivity(),recommandations);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        ((GridLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecommendations.setLayoutManager(layoutManager);
        rvRecommendations.setItemAnimator(new DefaultItemAnimator());
        rvRecommendations.setAdapter(historyRecommendationsAdapter);
    }

    public void setUpcoming(){
        viewUpcoming.setVisibility(View.VISIBLE);
        viewCompleted.setVisibility(View.GONE);

        tvUpcoming.setTextColor(getResources().getColor(R.color.color_003232));
        tvCompleted.setTextColor(getResources().getColor(R.color.color_919191));

        ArrayList<MyTickets> tempList = new ArrayList<>();
        for(int i=0;i<masterList.size();i++){
            if(Utility.isFutureDate(masterList.get(i).getDeparture_date()+" "+masterList.get(i).getDeparture_time()+":00")){
                tempList.add(masterList.get(i));
            }
        }
        refreshAdapter(tempList);
    }

    public void setCompleted(){
        viewUpcoming.setVisibility(View.GONE);
        viewCompleted.setVisibility(View.VISIBLE);

        tvUpcoming.setTextColor(getResources().getColor(R.color.color_919191));
        tvCompleted.setTextColor(getResources().getColor(R.color.color_003232));

        ArrayList<MyTickets> tempList = new ArrayList<>();
        for(int i=0;i<masterList.size();i++){
            if(!Utility.isFutureDate(masterList.get(i).getDeparture_date()+" "+masterList.get(i).getDeparture_time()+":00")){
                tempList.add(masterList.get(i));
            }
        }
        refreshAdapter(tempList);
    }

    public void getMyTickets(){
        GetJSON getJSON = new GetJSON(getActivity(),Constant.json_my_tickets) {
            @Override
            public void response(String response) {
                loaderVisibility(false);
                try {
                    Gson gson = new Gson();
                    MyTickets tickets = gson.fromJson(response,MyTickets.class);
                    masterList = tickets.ticket_history;

                    recommandations.addAll(tickets.recommendations);
                    historyRecommendationsAdapter.notifyDataSetChanged();
                    setUpcoming();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void refreshAdapter(ArrayList<MyTickets> list){
        myTickets.clear();
        myTickets.addAll(list);
        myTicketsAdapter.notifyDataSetChanged();
    }

    public void loaderVisibility(boolean b){
        rlMain.setVisibility(b ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }
}
