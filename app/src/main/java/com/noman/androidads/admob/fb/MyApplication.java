package com.noman.androidads.admob.fb;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MyApplication extends Application {


    private static MyApplication mInstance;




    public static synchronized MyApplication getInstance() {
        MyApplication myApplication;
        synchronized (MyApplication.class) {
            synchronized (MyApplication.class) {
                myApplication = mInstance;
            }
            return myApplication;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance =this;
        //For admob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //For Facebook
        AudienceNetworkAds.initialize(this);

    }
}
