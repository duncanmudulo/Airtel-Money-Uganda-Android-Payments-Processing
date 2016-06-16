package electron

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;



/**
 * Created by Electron on 2/5/2016.
 */
public class PaymentSmsReceiver extends BroadcastReceiver {
    static String id, payment_num;
    static float payment_amount, account_balance;

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    // Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    regexChecker("Trans ID\\s\\w+", message);

                    regexChecker("sent UGX\\s\\w+", message);

                    //regexChecker("Balance:UGX\\s\\w+.\\w+", sms2);

                    regexChecker("Balance UGX\\s\\w+.\\w+", message);

                    regexChecker("on\\s\\w+", message);




                    Toast.makeText(context, "Transaction ID: "+id+" Amount Paid: "+payment_amount+" Balance: "+account_balance+" Paid To: "+payment_num, Toast.LENGTH_LONG).show();

                   // Dialog dialog = new Dialog(this);

                    insertPaymentToDatabase(id);

                    StoreActivity.confirmId();


                    Intent sendPaymentData = new Intent(context, SendPaymentData.class);
                    sendPaymentData.putExtra("otp", message);
                    context.startService(sendPaymentData);
                }
            }
        } catch (Exception e) {
            //Log.e(TAG, "Exception: " + e.getMessage());
        }



    }

    /**
     * Getting the payment details from sms message body
     *
     * @param
     * @return
     */

    public static void regexChecker(String theRegex, String sms2) {

        String balance = null, amount, paymentnumber;


        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(sms2);

        while (matcher.find()) {
            if (matcher.group().length() != 0) {

                String x = matcher.group().trim();

                if (x.contains("Trans ID")) {
                    id = x.replaceAll("[\\D]", "");
                    id.trim();
                    System.out.println(id);

                }

                if(x.contains("sent UGX")){
                    String amt = x.replaceAll("[\\D]", "");
                    amount = amt.trim();
                    System.out.println(amount);

                    payment_amount = Float.parseFloat(amount);



                }


                if(x.contains("Balance UGX")&&x.length()>1){
                    String amt = x.replaceAll("[\\D]", "");
                    balance = amt.trim();
                    System.out.println(balance);

                    account_balance = Float.parseFloat(balance);

                }

                if(x.contains("on")){
                    String pay_number = x.replaceAll("[\\D]","");
                    paymentnumber = pay_number.trim();

                    payment_num = paymentnumber;

                }


            }

        }
    }

    public void insertPaymentToDatabase(String trans_id){

        trans_id = id;
      //  final String paymentnumber = pay_num;

        final String finalTrans_id = trans_id;
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Map<String, String> param = new HashMap<>();
                param.put("id", finalTrans_id);

                try {

                    String link ="server_side/insert_id.php";
                    String data  = URLEncoder.encode("id", "UTF-8")
                            + "=" + URLEncoder.encode(finalTrans_id, "UTF-8");
                    data += "&" + URLEncoder.encode("payment_amount", "UTF-8")
                            + "=" + URLEncoder.encode(String.valueOf(payment_amount), "UTF-8");
                    data += "&" + URLEncoder.encode("account_balance", "UTF-8")
                            + "=" + URLEncoder.encode(String.valueOf(account_balance), "UTF-8");
                    data += "&" + URLEncoder.encode("app_id", "UTF-8")
                            + "=" + URLEncoder.encode(StoreActivity.application_id, "UTF-8");
                    data += "&" + URLEncoder.encode("payment_number", "UTF-8")
                            + "=" + URLEncoder.encode(payment_num, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
// Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();


                }  catch (IOException e) {

                }

                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(trans_id);

    }

}
