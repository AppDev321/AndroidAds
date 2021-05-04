package com.noman.ads;

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
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple activity class that displays native ad formats.
 */
public class AdsNative {


    private NativeAd nativeAd;
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
            } else if (adType.equalsIgnoreCase("2")) {
                nativeFacebook();
            } else {
                noAds();
            }
        }
    }

    public void noAds() {
        FrameLayout frameLayout = this.layout;
        if (frameLayout != null) {
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void nativeAdmobe() {


        AdLoader.Builder builder = new AdLoader.Builder(activity, AdConfig.getAppData.getAdmobnative());

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {

                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (AdsNative.this.nativeAd != null) {
                            AdsNative.this.nativeAd.destroy();
                        }
                        AdsNative.this.nativeAd = nativeAd;
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_admob, null);
                        populateNativeAdView(nativeAd, adView);
                        layout.removeAllViews();
                        layout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(false).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {


                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());

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
        final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity2, "" + AdConfig.getAppData.getFbnative());
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

    private void inflateAd(com.facebook.ads.NativeAd nativeAd, NativeAdLayout nativeAdLayout, Activity activity2) {
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

    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {

            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {


                    super.onVideoEnd();
                }
            });
        }
    }


}