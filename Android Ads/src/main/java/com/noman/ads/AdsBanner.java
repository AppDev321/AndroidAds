package com.noman.ads;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import androidx.annotation.NonNull;


public class AdsBanner {
    Context context;

    RelativeLayout rlBannerAd;

String adType="";

    //Method of calling
   // new AdsBanner(this, (RelativeLayout) findViewById(R.id.rl_ad));



    public AdsBanner(String adType,Context context2, RelativeLayout bannerLayout) {
        this.context = context2;
        this.rlBannerAd = bannerLayout;
        this.adType=adType;
        loadBannerAd();

    }


    public void loadBannerAd() {
        if (AdConfig.getAppData == null || !NetworkConnectivityReceiver.isConnected()) {
            noAds();
        } else if (adType.equalsIgnoreCase("1")) {
            bannerAdAdmob();
        } else if (adType.equalsIgnoreCase("2")) {
            bannerAdFacebook();
        } else {
            noAds();
        }

    }

    public void bannerAdAdmob() {
        AdView adView = new AdView(this.context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AdConfig.getAppData.getAdmobbanner());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                // you can load custom ads here

            }
        });
        AdRequest build = new AdRequest.Builder().build();


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(14);
        this.rlBannerAd.addView(adView, layoutParams);
        adView.loadAd(build);
    }

    public void bannerAdFacebook() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(this.context, AdConfig.getAppData.getFbbanner(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(14);
        com.facebook.ads.AdListener r2 = new com.facebook.ads.AdListener() {
            /* class com.videoplayer.hdmaxplayer.video.player.saxhdvideoplayer.appmanage.ads.AdsBanner.AnonymousClass2 */

            @Override // com.facebook.ads.AdListener
            public void onAdClicked(Ad ad) {
            }

            @Override // com.facebook.ads.AdListener
            public void onAdLoaded(Ad ad) {
            }

            @Override // com.facebook.ads.AdListener
            public void onLoggingImpression(Ad ad) {
            }

            @Override // com.facebook.ads.AdListener
            public void onError(Ad ad, AdError adError) {
              // can be load custom ads here

            }
        };
        this.rlBannerAd.addView(adView, layoutParams);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(r2).build());
    }


    public void noAds() {
        RelativeLayout relativeLayout = this.rlBannerAd;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.GONE);
        }
    }
}
