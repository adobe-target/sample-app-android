package com.wetravel.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Travel implements Parcelable {
    String id;
    String travel_name;
    String departure_time;
    String travel_week_day;
    String travel_price;
    String bus_info;
    String bus_sets;
    String journey_time;
    String bus_stop;
    String average_rating;
    String refund;
    String offer_text;
    String recommended;
    String count_ratings;
    String departure;
    String destination;
    String top_rated_bus;
    String new_operator;
    int isSelected;

    protected Travel(Parcel in) {
        id = in.readString();
        travel_name = in.readString();
        departure_time = in.readString();
        travel_week_day = in.readString();
        travel_price = in.readString();
        bus_info = in.readString();
        bus_sets = in.readString();
        journey_time = in.readString();
        bus_stop = in.readString();
        average_rating = in.readString();
        refund = in.readString();
        offer_text = in.readString();
        recommended = in.readString();
        count_ratings = in.readString();
        departure = in.readString();
        destination = in.readString();
        top_rated_bus = in.readString();
        new_operator = in.readString();
        isSelected = in.readInt();
    }

    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(travel_name);
            dest.writeString(departure_time);
            dest.writeString(travel_week_day);
            dest.writeString(travel_price);
            dest.writeString(bus_info);
            dest.writeString(bus_sets);
            dest.writeString(journey_time);
            dest.writeString(bus_stop);
            dest.writeString(average_rating);
            dest.writeString(refund);
            dest.writeString(offer_text);
            dest.writeString(recommended);
            dest.writeString(count_ratings);
            dest.writeString(departure);
            dest.writeString(destination);
            dest.writeString(top_rated_bus);
            dest.writeString(new_operator);
            dest.writeInt(isSelected);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTravel_name() {
        return travel_name;
    }

    public void setTravel_name(String travel_name) {
        this.travel_name = travel_name;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getTravel_week_day() {
        return travel_week_day;
    }

    public void setTravel_week_day(String travel_week_day) {
        this.travel_week_day = travel_week_day;
    }

    public String getTravel_price() {
        return travel_price;
    }

    public void setTravel_price(String travel_price) {
        this.travel_price = travel_price;
    }

    public String getBus_info() {
        return bus_info;
    }

    public void setBus_info(String bus_info) {
        this.bus_info = bus_info;
    }

    public String getBus_sets() {
        return bus_sets;
    }

    public void setBus_sets(String bus_sets) {
        this.bus_sets = bus_sets;
    }

    public String getJourney_time() {
        return journey_time;
    }

    public void setJourney_time(String journey_time) {
        this.journey_time = journey_time;
    }

    public String getBus_stop() {
        return bus_stop;
    }

    public void setBus_stop(String bus_stop) {
        this.bus_stop = bus_stop;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getOffer_text() {
        return offer_text;
    }

    public void setOffer_text(String offer_text) {
        this.offer_text = offer_text;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getCount_ratings() {
        return count_ratings;
    }

    public void setCount_ratings(String count_ratings) {
        this.count_ratings = count_ratings;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTop_rated_bus() {
        return top_rated_bus;
    }

    public void setTop_rated_bus(String top_rated_bus) {
        this.top_rated_bus = top_rated_bus;
    }

    public String getNew_operator() {
        return new_operator;
    }

    public void setNew_operator(String new_operator) {
        this.new_operator = new_operator;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public static Comparator<Travel> travelDepartureTimeComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel t1, Travel t2) {
            String[] split1 = t1.getDeparture_time().split(":");
            String[] split2 = t2.getDeparture_time().split(":");

            int miliSecond1 = Integer.parseInt(split1[0])*60*60*1000+Integer.parseInt(split1[1])*60*1000;
            int miliSecond2 = Integer.parseInt(split2[0])*60*60*1000+Integer.parseInt(split2[1])*60*1000;

           return (miliSecond1 < miliSecond2 ? -1 : miliSecond1 == miliSecond2 ? 0 : 1);
        }
    };

    public static Comparator<Travel> travelJourneyTimeComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel t1, Travel t2) {
            String[] split1 = t1.getJourney_time().split(":");
            String[] split2 = t2.getJourney_time().split(":");

            int miliSecond1 = Integer.parseInt(split1[0])*60*60*1000+Integer.parseInt(split1[1])*60*1000;
            int miliSecond2 = Integer.parseInt(split2[0])*60*60*1000+Integer.parseInt(split2[1])*60*1000;

            return (miliSecond1 < miliSecond2 ? -1 : miliSecond1 == miliSecond2 ? 0 : 1);
        }
    };

    public static Comparator<Travel> nameComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel t1, Travel t2) {
            return (int) (t1.getTravel_name().compareTo(t2.getTravel_name()));
        }
    };

    public static Comparator<Travel> ratingComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel t1, Travel t2) {
            return (Integer.parseInt(t1.getAverage_rating()) > Integer.parseInt(t2.getAverage_rating()) ? -1 : Integer.parseInt(t1.getAverage_rating()) == Integer.parseInt(t2.getAverage_rating()) ? 0 : 1);
        }
    };

    public static Comparator<Travel> fareComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel t1, Travel t2) {
            return (Double.parseDouble(t1.getTravel_price()) < Double.parseDouble(t2.getTravel_price()) ? -1 : Double.parseDouble(t1.getTravel_price()) == Double.parseDouble(t2.getTravel_price()) ? 0 : 1);
        }
    };
}
