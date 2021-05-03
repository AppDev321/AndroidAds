package com.noman.ads;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

public class AdsInterstitial {
    Activity activity;
    FrameLayout frameLayout;
    Intent intent = null;
    InterstitialAd facebookInterstitial;

    com.google.android.gms.ads.InterstitialAd adMobInterstitial;
    String adType = "";

/*

        //Calling method
        AdsInterstitial adsFullScreen2 = new AdsInterstitial(this);
        adsFullScreen2.setIntentActivity(false);
        //To display full add screen
        adsFullScreen2.loadFullAd();
        //Display ad after closing add new activity started
        adsFullScreen2.showFullAd(new Intent(MainActivity.this, SecondClass.class));


 */


    public AdsInterstitial(String adType, Activity activity2) {
        this.activity = activity2;
        this.adType = adType;
        loadAd();
    }


    public void loadAd() {
        if (AdConfig.getAppData == null || !NetworkConnectivityReceiver.isConnected()) {
            openNextActivity();
        } else if (adType.equalsIgnoreCase("1")) {
            adMobeAd();
        } else {
            facebookAd();
        }


    }

    public void showActivityAd(Intent intent2) {
        this.intent = intent2;
        if (AdConfig.getAppData == null || !NetworkConnectivityReceiver.isConnected()) {
            openNextActivity();
        } else if (adType.equalsIgnoreCase("1")) {

            if (adMobInterstitial == null || !adMobInterstitial.isLoaded()) {

                if (facebookInterstitial == null || !facebookInterstitial.isAdLoaded()) {
                    customAd();
                } else {
                    this.facebookInterstitial.show();
                }
            } else {
                this.adMobInterstitial.show();
            }
        } else {

            if (facebookInterstitial == null || !facebookInterstitial.isAdLoaded()) {
                if (adMobInterstitial == null || !adMobInterstitial.isLoaded()) {
                    customAd();
                } else {
                    this.adMobInterstitial.show();
                }
            } else {
                this.facebookInterstitial.show();
            }
        }
    }

    public void adMobeAd() {
        com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(this.activity);
        this.adMobInterstitial = interstitialAd;
        interstitialAd.setAdUnitId("" + AdConfig.getAppData.getAdmobinterstitial());
        this.adMobInterstitial.loadAd(new AdRequest.Builder().build());
        this.adMobInterstitial.setAdListener(new AdListener() {

            @Override // com.google.android.gms.ads.AdListener
            public void onAdClicked() {
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(int i) {
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLeftApplication() {
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {

            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdOpened() {
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdClosed() {
                AdsInterstitial.this.adMobInterstitial.loadAd(new AdRequest.Builder().build());
                AdsInterstitial.this.openNextActivity();
            }
        });
    }

    public void facebookAd() {
        Activity activity2 = this.activity;
        this.facebookInterstitial = new InterstitialAd(activity2, "" + AdConfig.getAppData.getFbinterstitial());
        InterstitialAdListener r0 = new InterstitialAdListener() {


            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                AdsInterstitial.this.openNextActivity();
                AdsInterstitial.this.facebookInterstitial.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("getFbinterstitial==", "======" + adError.getErrorMessage() + "" + adError.getErrorCode());
            }
        };
        InterstitialAd interstitialAd = this.facebookInterstitial;
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(r0).build());
    }


    public void customAd() {
        openNextActivity();

    }

    public void openNextActivity() {

        if (intent != null) {
            this.activity.startActivity(this.intent);
            this.activity.finish();
        }

    }


}
