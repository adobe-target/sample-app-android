package com.wetravel.Models;

import java.util.ArrayList;

public class MyOffers extends BaseModel{
    public ArrayList<MyOffers> my_offers;

    String id;
    String my_offer_bg;
    String my_offer_banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMy_offer_bg() {
        return my_offer_bg;
    }

    public void setMy_offer_bg(String my_offer_bg) {
        this.my_offer_bg = my_offer_bg;
    }

    public String getMy_offer_banner() {
        return my_offer_banner;
    }

    public void setMy_offer_banner(String my_offer_banner) {
        this.my_offer_banner = my_offer_banner;
    }
}
