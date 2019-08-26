package com.wetravel.Models;

import java.util.ArrayList;

public class SeatingResponse extends BaseModel{
    String sleeper_price;
    String siting_price;

    public ArrayList<Seat> seats_lower;
    public ArrayList<Seat> seats_upper;
    public ArrayList<Deal> deal_offer;

    public String getSleeper_price() {
        return sleeper_price;
    }

    public void setSleeper_price(String sleeper_price) {
        this.sleeper_price = sleeper_price;
    }

    public String getSiting_price() {
        return siting_price;
    }

    public void setSiting_price(String siting_price) {
        this.siting_price = siting_price;
    }
}
