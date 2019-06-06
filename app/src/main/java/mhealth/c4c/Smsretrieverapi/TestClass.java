package mhealth.c4c.Smsretrieverapi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import mhealth.c4c.R;
import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.encryption.Base64Encoder;

import static mhealth.c4c.StringSplitter.SplitString.splittedString;


public class TestClass extends AppCompatActivity implements SmsReceiver.MessageReceiveListener{

    @org.jetbrains.annotations.Nullable
    private GoogleApiClient mCredentialsApiClient;

    private final int RC_HINT = 2;
    Base64Encoder encoder;

    @NotNull
    private final SmsReceiver smsBroadcast = new SmsReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_class);
        encoder=new Base64Encoder();
        sendMessage();

//        generateAppSignature();
//       app id for debug => +11AKmxGULS
//       app id for release => 2C+posZ0ovi

        listenForIncomingMessage();


//        generateAppSignature();

        initiateBackgroundService();
        Stetho.initializeWithDefaults(this);
    }

    protected void onResume() {
        super.onResume();

        listenForIncomingMessage();
    }

    protected void sendMessage(){

        try{

//            Uri smsUri = Uri.parse("tel:0728802160");
////            Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
//            intent.putExtra("sms_body", "test message from c4c");
//            intent.setType("vnd.android-dir/mms-sms");
//            startActivity(intent);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
//            intent.setType();
            intent.putExtra("sms_body", "test message");
            intent.setData(Uri.parse("sms:0728802160"));

//            SmsManager sm = SmsManager.getDefault();
//
//            ArrayList<String> parts = sm.divideMessage("message");
//
//            sm.sendMultipartTextMessage("0728802160", null, parts, null, null);

//            intent.putExtra(Intent.EXTRA_STREAM, attachment);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        catch(Exception e){

        }
    }



    //helps to identify the app unique identifier, should only run once

    private void generateAppSignature(){

        System.out.println("******************hash code***********");
        System.out.println((new AppSignatureHelper(this.getApplicationContext())).getAppSignatures());
        System.out.println("appid");

    }

    //function triggered when there is an incoming message from receiver
    private void listenForIncomingMessage() {

        this.mCredentialsApiClient = (new GoogleApiClient.Builder((Context) this)).addApi(Auth.CREDENTIALS_API).build();
        this.startSMSListener();
        this.smsBroadcast.initOTPListener((SmsReceiver.MessageReceiveListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        this.getApplicationContext().registerReceiver((BroadcastReceiver) this.smsBroadcast, intentFilter);


    }

    //    function triggered when the application is in background or closed
    private void initiateBackgroundService() {

        //background code after every 5 seconds


        Intent alarm = new Intent(TestClass.this, SmsReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(TestClass.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(TestClass.this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, pendingIntent);
        }

        //background code

    }



    //    function triggered when the actual message is received from our receiver
    public void onMessageReceived(@NotNull String otp) {
        Intrinsics.checkParameterIsNotNull(otp, "otp");
        LocalBroadcastManager.getInstance((Context) this).unregisterReceiver((BroadcastReceiver) this.smsBroadcast);
        TextView otpTextView = (TextView) findViewById(R.id.tv1);
        Intrinsics.checkExpressionValueIsNotNull(otpTextView, "otpTxtView");
        otpTextView.setText((CharSequence) ("Your message is: " + splittedString(otp)));
        saveReceivedMessage(splittedString(otp));

        Toast.makeText(this, "" + splittedString(otp), Toast.LENGTH_LONG).show();
    }

    private void saveReceivedMessage(String str){

        encoder=new Base64Encoder();


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

    public void onMessageTimeOut() {


    }

    private final void startSMSListener() {
        SmsRetriever.getClient((Activity) this).startSmsRetriever().addOnSuccessListener((OnSuccessListener) (new OnSuccessListener() {

            public void onSuccess(Object var1) {
                this.onSuccess((Void) var1);
            }

            public final void onSuccess(Void it) {
                TextView otpTxtView = (TextView) findViewById(R.id.tv1);
                Intrinsics.checkExpressionValueIsNotNull(otpTxtView, "otpTxtView");
                otpTxtView.setText((CharSequence) "Waiting for message");

                Toast.makeText(getApplicationContext(), "SMS Retriever starts", Toast.LENGTH_SHORT).show();
            }
        })).addOnFailureListener((OnFailureListener) (new OnFailureListener() {
            public final void onFailure(@NotNull Exception it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                TextView otpTextView = (TextView) findViewById(R.id.tv1);
                Intrinsics.checkExpressionValueIsNotNull(otpTextView, "otpTxtView");
                otpTextView.setText((CharSequence) "Cannot Start SMS Retriever");

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }));
    }


    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.RC_HINT && resultCode == -1) {

            if (data == null) {
                Intrinsics.throwNpe();
            }

            Parcelable credentials = data.getParcelableExtra("com.google.android.gms.credentials.Credential");
            Intrinsics.checkExpressionValueIsNotNull(credentials, "data!!.getParcelableExtra(Credential.EXTRA_KEY)");
            Credential credential = (Credential) credentials;
            String credString = "credential : " + credential;
            System.out.print(credString);
        }

    }
}
