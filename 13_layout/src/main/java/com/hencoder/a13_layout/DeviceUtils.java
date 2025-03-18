package com.hencoder.a13_layout;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtils {
    public String getUUIDString(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;

    }
}
