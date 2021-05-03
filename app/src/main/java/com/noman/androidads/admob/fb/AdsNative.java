package com.noman.androidads.admob.fb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;
import java.util.List;


public class AdsNative {
    Activity activity;
    FrameLayout layout;
    String adType = "";


    //Calling method

    //  new AdsNative(this, (FrameLayout) findViewById(R.id.fl_adplaceholder));


    public AdsNative(String adType, Activity activity2, FrameLayout layoutNativeAd) {
        this.activity = activity2;
        this.layout = layoutNativeAd;
        this.adType = adType;
        NativeAds();
    }

    public void NativeAds() {
        if (AdConfig.getAppData == null || !NetworkConnectivityReceiver.isConnected()) {
            noAds();
        } else if (this.layout == null) {
        } else {
            if (adType.equalsIgnoreCase("1")) {
                nativeAdmobe();
            } else {
                nativeFacebook();
            }
        }
    }

    public void nativeAdmobe() {
        AdLoader.Builder builder = new AdLoader.Builder(this.activity, AdConfig.getAppData.getAdmobnative());
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_admob, (ViewGroup) null);
                populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
                layout.removeAllViews();
                layout.addView(unifiedNativeAdView);
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        builder.withAdListener(new AdListener() {

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(int i) {

            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public void nativeFacebook() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.setLayoutParams(layoutParams);
        this.layout.addView(scrollView);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        final NativeAdLayout nativeAdLayout = new NativeAdLayout(this.activity);
        nativeAdLayout.setLayoutParams(layoutParams2);
        scrollView.addView(nativeAdLayout);
        Activity activity2 = this.activity;
        final NativeAd nativeAd = new NativeAd(activity2, "" + AdConfig.getAppData.getFbnative());
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("getFbnative", "======" + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd != null && nativeAd == ad) {

                    inflateAd(nativeAd, nativeAdLayout, activity);
                }
            }
        }).build());
    }


    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
        unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getPrice() == null) {
            unifiedNativeAdView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getPriceView()).setText(unifiedNativeAd.getPrice());
        }
        if (unifiedNativeAd.getStore() == null) {
            unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
        }
        if (unifiedNativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getAdvertiser() == null) {
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        VideoController videoController = unifiedNativeAd.getVideoController();
        if (videoController.hasVideoContent()) {
            videoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                /* class com.videoplayer.hdmaxplayer.video.player.saxhdvideoplayer.appmanage.ads.AdsNative.AnonymousClass5 */

                @Override // com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }


    private void inflateAd(NativeAd nativeAd, NativeAdLayout nativeAdLayout, Activity activity2) {
        nativeAd.unregisterView();
        int i = View.VISIBLE;
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(activity2).inflate(R.layout.native_ad_fb, (ViewGroup) nativeAdLayout, false);
        nativeAdLayout.addView(linearLayout);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity2, nativeAd, nativeAdLayout);
        linearLayout2.removeAllViews();
        linearLayout2.addView(adOptionsView, View.VISIBLE);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) linearLayout.findViewById(R.id.native_ad_icon);
        TextView textView = (TextView) linearLayout.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView mediaView2 = (com.facebook.ads.MediaView) linearLayout.findViewById(R.id.native_ad_media);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.native_ad_sponsored_label);
        Button button = (Button) linearLayout.findViewById(R.id.native_ad_call_to_action);
        textView.setText(nativeAd.getAdvertiserName());
        ((TextView) linearLayout.findViewById(R.id.native_ad_body)).setText(nativeAd.getAdBodyText());
        ((TextView) linearLayout.findViewById(R.id.native_ad_social_context)).setText(nativeAd.getAdSocialContext());
        if (!nativeAd.hasCallToAction()) {
            i = View.INVISIBLE;
        }
        button.setVisibility(i);
        button.setText(nativeAd.getAdCallToAction());
        textView2.setText(nativeAd.getSponsoredTranslation());
        List<View> arrayList = new ArrayList<>();
        arrayList.add(textView);
        arrayList.add(button);
        nativeAd.registerViewForInteraction(linearLayout, mediaView2, mediaView, arrayList);
    }


    @SuppressLint("WrongConstant")
    public void noAds() {
        FrameLayout frameLayout = this.layout;
        if (frameLayout != null) {
            frameLayout.setVisibility(View.GONE);
        }
    }
}
