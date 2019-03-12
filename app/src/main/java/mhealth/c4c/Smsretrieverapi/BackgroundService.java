package mhealth.c4c.Smsretrieverapi;


import android.app.*;
import android.content.*;
import android.os.*;

import java.util.List;

import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.encryption.Base64Encoder;

import static mhealth.c4c.StringSplitter.SplitString.splittedString;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    String myotp="";
    Base64Encoder encoder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            System.out.println("*******running in background****");
            System.out.println(myotp);
            saveReceivedMessage(myotp);

            stopSelf();
        }
    };

    private void saveReceivedMessage(String mess){

        encoder=new Base64Encoder();
        String str=splittedString(mess);

        if(str.contains("CHKRIGHTS")){

            String[] decryptedPiece=str.split("\\*");
            String messToDec=decryptedPiece[1];


            String decryptedmess =encoder.decryptedString(messToDec.getBytes());

            String hasRights=decryptedmess;

            Broadcastsmsrights.deleteAll(Broadcastsmsrights.class);
            List<Broadcastsmsrights> myl=Broadcastsmsrights.findWithQuery(Broadcastsmsrights.class,"select * from Broadcastsmsrights limit 1");
            if(myl.size()>0){
                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                tr.save();

            }
            else{

                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                tr.save();

            }


        }

        else{

            String hasRights="no";


            List<Broadcastsmsrights> myl=Broadcastsmsrights.findWithQuery(Broadcastsmsrights.class,"select * from Broadcastsmsrights");
            if(myl.size()>0){

                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                tr.save();

            }
            else{

                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                tr.save();

            }



        }


    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
            if(intent.getStringExtra("otp")!=null) {


                myotp = intent.getStringExtra("otp");

            }
            else{

                myotp="no otp";
            }
        }
        return START_STICKY;
    }

}

