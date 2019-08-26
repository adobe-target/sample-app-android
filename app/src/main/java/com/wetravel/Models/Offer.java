package com.wetravel.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Offer implements Parcelable {
    String id;
    String offer_code;
    String offer_name;
    String offer_desc;
    String offer_price;
    String offer_banner;
    String flag_banner;
    String action_banner;
    String full_offer_banner;
    String tag_filter;

    protected Offer(Parcel in) {
        id = in.readString();
        offer_code = in.readString();
        offer_name = in.readString();
        offer_desc = in.readString();
        offer_price = in.readString();
        offer_banner = in.readString();
        flag_banner = in.readString();
        action_banner = in.readString();
        full_offer_banner = in.readString();
        tag_filter = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(offer_code);
        dest.writeString(offer_name);
        dest.writeString(offer_desc);
        dest.writeString(offer_price);
        dest.writeString(offer_banner);
        dest.writeString(flag_banner);
        dest.writeString(action_banner);
        dest.writeString(full_offer_banner);
        dest.writeString(tag_filter);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffer_code() {
        return offer_code;
    }

    public void setOffer_code(String offer_code) {
        this.offer_code = offer_code;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getOffer_desc() {
        return offer_desc;
    }

    public void setOffer_desc(String offer_desc) {
        this.offer_desc = offer_desc;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getOffer_banner() {
        return offer_banner;
    }

    public void setOffer_banner(String offer_banner) {
        this.offer_banner = offer_banner;
    }

    public String getFlag_banner() {
        return flag_banner;
    }

    public void setFlag_banner(String flag_banner) {
        this.flag_banner = flag_banner;
    }

    public String getAction_banner() {
        return action_banner;
    }

    public void setAction_banner(String action_banner) {
        this.action_banner = action_banner;
    }

    public String getFull_offer_banner() {
        return full_offer_banner;
    }

    public void setFull_offer_banner(String full_offer_banner) {
        this.full_offer_banner = full_offer_banner;
    }

    public String getTag_filter() {
        return tag_filter;
    }

    public void setTag_filter(String tag_filter) {
        this.tag_filter = tag_filter;
    }
}
