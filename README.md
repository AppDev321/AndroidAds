## For Native/Banner/Interstitial
> Note: `For including Native ads you Download the code and add it in your project as a module ` 

Download Library project add in your build.gradle
```sh
implementation project(path: ':Android Ads')

 ```

## For Banner/Interstitial

<b>1: Add in build.gradle

```sh

implementation 'com.github.AppDev321:AndroidAds:1.0.2'
```
2: Add in root build.gradle in allprojects tag
```sh

maven { url 'https://jitpack.io' }
```



Step 1 #

Add in manifest for ad mobs under application tag

```sh

			<meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713" />  //Replace your admob application ID
			
 tools:replace="android:theme" // this in application tag

```

Step 2 #
 
Initialize ads Ids , if you dont use any of ad network replace with empty 
              
              AppData appData = new AppData(getResources().getString(R.string.banner_ad_unit_id),
                getResources().getString(R.string.interstitial_ad_unit_id),
                getResources().getString(R.string.native_ad_unit_id),
                getResources().getString(R.string.fb_banner_ad_unit_id),
                getResources().getString(R.string.fb_interstitial_ad_unit_id),
                getResources().getString(R.string.fb_native_ad_unit_id));
                AdConfig.getAppData = appData;
		
Step 3 #
		
    String	adTypes:
		0 = no add
		1 = admob ads
		2 = facebook ads
	
For banner
  ```sh
		new AdsBanner(adTypes,this, (RelativeLayout) findViewById(R.id.rl_ad)); //Replace your banner add layout
  ```

For interstitial
  ```sh
	AdsInterstitial adsFullScreen2 = new AdsInterstitial(adTypes,this); //intialize oncreate 
	
  //Now call to show interstial on everywhere in class

  adsFullScreen2.showActivityAd(new Intent(MainActivity.this, SecondClass.class)); //Replace your activities if you dont want to move to next class then send null in params
  ```

For Native ads calling
  ```sh
		new AdsNative(adTypes,this, (FrameLayout) findViewById(R.id.fl_adplaceholder));
  ```