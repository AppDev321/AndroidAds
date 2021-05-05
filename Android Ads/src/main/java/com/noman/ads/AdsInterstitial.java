package com.noman.ads;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import androidx.annotation.NonNull;

public class AdsInterstitial {
    Activity activity;
    FrameLayout frameLayout;
    Intent intent = null;
    InterstitialAd facebookInterstitial;

    com.google.android.gms.ads.interstitial.InterstitialAd adMobInterstitial;
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
            openNextActivity(true);
        } else if (adType.equalsIgnoreCase("1")) {
            adMobeAd();
        } else if (adType.equalsIgnoreCase("2")) {
            facebookAd();
        }


    }


    public void adMobeAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        com.google.android.gms.ads.interstitial.InterstitialAd.load(
                activity,
                AdConfig.getAppData.getAdmobinterstitial(),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        AdsInterstitial.this.adMobInterstitial = interstitialAd;

                        adMobInterstitial.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                        AdsInterstitial.this.adMobInterstitial = null;
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {

                                        AdsInterstitial.this.openNextActivity(true);

                                    }


                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        AdsInterstitial.this.adMobInterstitial = null;
                                    }
                                });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                        AdsInterstitial.this.adMobInterstitial = null;


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
                AdsInterstitial.this.openNextActivity(false);

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("getFbinterstitial==", "======" + adError.getErrorMessage() + "" + adError.getErrorCode());
            }
        };
        InterstitialAd interstitialAd = this.facebookInterstitial;
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(r0).build());
    }


    //Ads Calling logic


    public void showActivityAd(Intent intent2) {
        this.intent = intent2;
        if (AdConfig.getAppData == null || !NetworkConnectivityReceiver.isConnected()) {
            openNextActivity(true);
        } else if (adType.equalsIgnoreCase("1")) {
            if (adMobInterstitial != null) {
                adMobInterstitial.show(activity);
            } else {
                adMobeAd();
            }

        } else {

            if (facebookInterstitial == null || !facebookInterstitial.isAdLoaded()) {
                facebookAd();
            } else {
                this.facebookInterstitial.show();
            }
        }
    }


    //when no Ads data available
    public void openNextActivity() {
        if (intent != null) {
            this.activity.startActivity(this.intent);
            this.activity.finish();
        }
    }


    //when ads data available
    public void openNextActivity(boolean isAdmobRequest) {

        if (intent != null) {
            this.activity.startActivity(this.intent);
            this.activity.finish();
        } else {
            if (isAdmobRequest) {
                adMobeAd();
            } else {
                facebookInterstitial.loadAd();
            }

        }
    }


}
