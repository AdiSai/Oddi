package com.adithyasairam.oddi.twilio;

import com.adithyasairam.oddi.OddiApp;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.StatusListener;

/**
 * Status listener that shows a toast with operation results.
 */
class ToastStatusListener extends StatusListener
{
    private final String okText;
    private final String errorText;

    ToastStatusListener(String ok, String error) {
        okText = ok;
        errorText = error;
    }

    @Override
    public void onSuccess()
    {
        OddiApp.get().showToast(okText);
    }

    @Override
    public void onError(ErrorInfo errorInfo)
    {
        OddiApp.get().showError(errorText, errorInfo);
    }
}