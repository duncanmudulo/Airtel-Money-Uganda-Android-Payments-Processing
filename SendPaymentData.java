package com.electron.apps;

/**
 * Created by Electron on 4/4/2016.
 */
import android.app.IntentService;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;
import com.electron.apps.StoreActivity;

public class SendPaymentData extends IntentService {

    WebView store;

    public SendPaymentData() {
        super(SendPaymentData.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            Toast.makeText(getBaseContext(), otp, Toast.LENGTH_LONG);

         //   StoreActivity.confirmId();





        }
    }
}
