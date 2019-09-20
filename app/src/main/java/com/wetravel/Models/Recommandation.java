package com.wetravel.Models;

import java.util.ArrayList;

public class Recommandation extends BaseModel{
    public ArrayList<Recommandation> recommandations;

    String id;
    String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
