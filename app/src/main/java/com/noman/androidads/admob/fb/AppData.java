package com.noman.androidads.admob.fb;

public class AppData {


    String admobbanner;

    String admobinterstitial;
    String admobnative;
    String fbbanner;
    String fbinterstitial;
    String fbnative;


    // isActive =1 admob
    //isActive=2 facebooke
    public AppData( String admobbanner, String admobinterstitial, String admobnative, String fbbanner, String fbinterstitial, String fbnative) {
        this.admobbanner = admobbanner;

        this.admobinterstitial = admobinterstitial;
        this.admobnative = admobnative;
        this.fbbanner = fbbanner;
        this.fbinterstitial = fbinterstitial;
        this.fbnative = fbnative;
    }


    public String getAdmobbanner() {
        return admobbanner;
    }

    public void setAdmobbanner(String admobbanner) {
        this.admobbanner = admobbanner;
    }

    public String getAdmobinterstitial() {
        return admobinterstitial;
    }

    public void setAdmobinterstitial(String admobinterstitial) {
        this.admobinterstitial = admobinterstitial;
    }

    public String getAdmobnative() {
        return admobnative;
    }

    public void setAdmobnative(String admobnative) {
        this.admobnative = admobnative;
    }

    public String getFbbanner() {
        return fbbanner;
    }

    public void setFbbanner(String fbbanner) {
        this.fbbanner = fbbanner;
    }

    public String getFbinterstitial() {
        return fbinterstitial;
    }

    public void setFbinterstitial(String fbinterstitial) {
        this.fbinterstitial = fbinterstitial;
    }

    public String getFbnative() {
        return fbnative;
    }

    public void setFbnative(String fbnative) {
        this.fbnative = fbnative;
    }
}
