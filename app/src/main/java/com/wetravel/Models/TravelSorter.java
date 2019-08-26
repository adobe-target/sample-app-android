package com.wetravel.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TravelSorter{
   ArrayList<Travel> travelSorter = new ArrayList<Travel>();

   public TravelSorter(ArrayList<Travel> travelSorter){
        this.travelSorter = travelSorter;
   }

   public ArrayList<Travel> getSortedListByName(){
       Collections.sort(travelSorter,Travel.nameComparator);
       return travelSorter;
   }

    public ArrayList<Travel> getSortedListByDepartureTime(){
        Collections.sort(travelSorter,Travel.travelDepartureTimeComparator);
        return travelSorter;
    }

    public ArrayList<Travel> getSortedListByJourneyTime(){
        Collections.sort(travelSorter,Travel.travelJourneyTimeComparator);
        return travelSorter;
    }


    public ArrayList<Travel> getSortedListByRatings(){
        Collections.sort(travelSorter,Travel.ratingComparator);
        return travelSorter;
    }

    public ArrayList<Travel> getSortedListByFare(){
        Collections.sort(travelSorter,Travel.fareComparator);
        return travelSorter;
    }

    public ArrayList<Travel> getSortedListByRecommended(){
        ArrayList<Travel> list = new ArrayList<>();
        for(int i = 0;i<travelSorter.size();i++){
            if(travelSorter.get(i).getRecommended().equalsIgnoreCase("1")){
                list.add(travelSorter.get(i));
            }
        }
        return list;
    }

    public ArrayList<Travel> getSortedListByRefund(){
       ArrayList<Travel> list = new ArrayList<>();
        for(int i = 0;i<travelSorter.size();i++){
            if(travelSorter.get(i).getRefund().equalsIgnoreCase("1")){
                list.add(travelSorter.get(i));
            }
        }
        return list;
    }

    public ArrayList<Travel> getSortedListByTopRatedBus(){
        ArrayList<Travel> list = new ArrayList<>();
        for(int i = 0;i<travelSorter.size();i++){
            if(travelSorter.get(i).getTop_rated_bus().equalsIgnoreCase("1")){
                list.add(travelSorter.get(i));
            }
        }
        return list;
    }

    public ArrayList<Travel> getSortedListByNewOperator(){
        ArrayList<Travel> list = new ArrayList<>();
        for(int i = 0;i<travelSorter.size();i++){
            if(travelSorter.get(i).getNew_operator().equalsIgnoreCase("1")){
                list.add(travelSorter.get(i));
            }
        }
        return list;
    }
}
