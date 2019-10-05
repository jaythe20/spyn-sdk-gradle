package com.spareio.spyn_sdk;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Spyn {
    private static spynSDK spyn;
    public static spynSDK initSpyn(Drawable icon, String dealId, String lang, Context mContext) {
        spyn = new spynSDK.Builder()
                .setIcon(mContext.getApplicationInfo().loadIcon(mContext.getPackageManager()))
                .setDealId(dealId)
                .setLang("en")
                .setContext(mContext)
                .create();
        return spyn;
    }
}
