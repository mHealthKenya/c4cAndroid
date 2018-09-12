package mhealth.c4c.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mhealth.c4c.FragmentRead;
import mhealth.c4c.FragmentUnRead;
import mhealth.c4c.Tables.Messages;
import mhealth.c4c.config.Config;

/**
 * Created by root on 11/6/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {


    public static final String SMS_BUNDLE = "pdus";


    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String getAdd="";
            Long mydate=null;
            String mytimestamp=null;



            for (int i = 0; i < sms.length; ++i) {
                try {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    getAdd = smsMessage.getOriginatingAddress();
                    mydate = smsMessage.getTimestampMillis();


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    mytimestamp = formatter.format(calendar.getTime());

                    smsMessageStr += smsBody;
                }
                catch(Exception e){

                    Toast.makeText(context, "error occured"+e, Toast.LENGTH_SHORT).show();
                    System.out.println("smsbroadcastreceiver error "+e);
                    Log.v("SMSRECEIVER BROADCAST..",e.getMessage());
                }
//

            }
            try {
                if(getAdd.contentEquals(Config.shortcode)){


                    //new code here

                    Messages ms = new Messages(getAdd,smsMessageStr,mytimestamp,"unread","null");
                    ms.save();



                    FragmentRead instread = FragmentRead.instance();
                    FragmentUnRead instread1 = FragmentUnRead.instance();

                    instread.updateList();
                    instread1.updateList();




                }


            }
            catch (Exception e){
                Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG).show();
                System.out.println("smsbroadcastreceiver error "+e);
                Log.v("SMSRECEIVER BROADCAST..",e.getMessage());

            }
        }






    }
}
