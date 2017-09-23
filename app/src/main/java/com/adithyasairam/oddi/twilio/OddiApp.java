package com.adithyasairam.oddi.twilio;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.twilio.chat.ErrorInfo;

/**
 * Created by AdiSai on 9/23/17.
 */

public class OddiApp extends Application {
    public static final String ACCOUNT_SID = "AC48397abcbbd3b2013078b65cd3887fa7";
    public static final String AUTH_TOKEN = "your_auth_token";
    private static OddiApp instance;
    private com.adithyasairam.oddi.twilio.BasicChatClient basicClient;

    @Override
    public void onCreate()
    {
        super.onCreate();
        OddiApp.instance = this;
        basicClient = new com.adithyasairam.oddi.twilio.BasicChatClient(getApplicationContext());
    }

    public static OddiApp get()
    {
        return instance;
    }

    public com.adithyasairam.oddi.twilio.BasicChatClient getBasicClient()
    {
        return this.basicClient;
    }

    public void showToast(final String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(final String text, final int duration)
    {
        Log.d("TwilioApplication", text);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
    }

    public void showError(final ErrorInfo error)
    {
        showError("Something went wrong", error);
    }

    public void showError(final String message, final ErrorInfo error)
    {
        showToast(formatted(message, error), Toast.LENGTH_LONG);
        logErrorInfo(message, error);
    }

    public void logErrorInfo(final String message, final ErrorInfo error)
    {
        Log.e("TwilioApplication", formatted(message, error));
    }

    private String formatted(String message, ErrorInfo error)
    {
        return String.format("%s. %s", message, error.toString());
    }
}
