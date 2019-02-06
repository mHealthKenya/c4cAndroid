package mhealth.c4c.sendMessages;


import android.telephony.SmsManager;

import java.util.ArrayList;

public class SendMessage {

    public static void sendMessage(String message,String destination){

        try{

            SmsManager sm = SmsManager.getDefault();

            ArrayList<String> parts = sm.divideMessage(message);

            sm.sendMultipartTextMessage(destination, null, parts, null, null);

        }
        catch(Exception e){



        }
    }
}

