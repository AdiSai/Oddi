package com.adithyasairam.oddi;

import android.app.Application;

import com.twilio.Twilio;

/**
 * Created by AdiSai on 9/23/17.
 */

public class OddiApp extends Application {
    public static final String ACCOUNT_SID = "AC48397abcbbd3b2013078b65cd3887fa7";
    public static final String AUTH_TOKEN = "your_auth_token";
    @Override
    public void onCreate() {
        super.onCreate();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
