package com.example.allnetworkadslibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import com.example.allnetworkadslibrary.admob.AdmobAds;
import com.example.allnetworkadslibrary.adslib.Constants;
import com.example.allnetworkadslibrary.adslib.SharedPrefUtils;
import com.example.allnetworkadslibrary.applovin.AppLovinAds;

public class Ads {

    public static void loadNative(Context context, Activity activity,  FrameLayout frameLayout) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob)
            AdmobAds.Companion.refreshAd(context, activity, frameLayout);
        else
            AppLovinAds.Companion.loadNativeAd(context, frameLayout);
    }

    public static void loadInter(Context context, Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob)
            AdmobAds.Companion.loadAdmobInters(context);
        else
            AppLovinAds.Companion.loadInterstitialAd(context, activity);
    }

    public static void showInter(Context context, Activity activity, Intent intent) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob)
            AdmobAds.Companion.showInterAd(activity, intent);
        else
            AppLovinAds.Companion.showAd(context, intent);
    }
}
