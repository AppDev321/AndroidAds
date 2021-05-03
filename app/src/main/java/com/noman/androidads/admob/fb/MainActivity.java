package com.noman.androidads.admob.fb;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.noman.ads.AdConfig;
import com.noman.ads.AdsBanner;
import com.noman.ads.AdsInterstitial;
import com.noman.ads.AdsNative;
import com.noman.ads.AppData;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    AdsInterstitial adsFullScreen2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppData appData = new AppData("ca-app-pub-3940256099942544/6300978111",
                "ca-app-pub-3940256099942544/1033173712",
                "ca-app-pub-3940256099942544/2247696110",
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
                "YOUR_PLACEMENT_ID",
                "YOUR_PLACEMENT_ID");

        AdConfig.getAppData = appData;


        adsFullScreen2 = new AdsInterstitial("2", MainActivity.this);

        findViewById(R.id.btn_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AdsBanner("2", MainActivity.this, (RelativeLayout) findViewById(R.id.rel_banner));
            }
        });

        findViewById(R.id.btn_inter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsFullScreen2.showActivityAd(null);
            }
        });


        findViewById(R.id.btn_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AdsNative("2", MainActivity.this, (FrameLayout) findViewById(R.id.frame_native));
            }
        });

    }


}