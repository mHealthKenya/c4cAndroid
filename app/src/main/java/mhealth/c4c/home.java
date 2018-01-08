
package mhealth.c4c;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP on 6/23/2016.
 */
public class home extends AppCompatActivity {
    Button btnRegister,btnReport,btnnascopwebsite;
    final String mfl = "http://197.248.10.20/mfl";
    final String nascopwebsite = "http://www.nascop.or.ke/";

    String[] action={"Look out for an update on playstore.","Look out for an update on playstore."};


    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mhealth.c4c.R.layout.home);


//        btnRegister=(Button)findViewById(mhealth.c4c.R.id.btnRegister);
        btnReport=(Button)findViewById(mhealth.c4c.R.id.btnReport);

//        btnRegister.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent=new Intent(getApplicationContext(),Registration.class);
//                startActivity(intent);
//            }
//        });

        btnReport.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),exposure.class);
                startActivity(intent);
            }
        });

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(false)
                .build();




    }



    public void nascopwebsite(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(nascopwebsite));
    }
    public void mfl(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(mfl));
    }



    public void artguidelines(View v){
        try {

//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/nascopkenya1" + id));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/user/nascopkenya1"));
            startActivity(intent);

        }catch(ActivityNotFoundException e) {

            // youtube is not installed.Will be opened in other available apps

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/nascopkenya1"));
            startActivity(i);

        }
    }
    public void faq(View v){
        try {
            Intent myint=new Intent(getApplicationContext(),CardViewActivity.class);
            startActivity(myint);

        }
        catch(ActivityNotFoundException e) {
            comingupsoon(action[1].toLowerCase());
        }
    }
    public void comingupsoon(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Coming Soon");
            adb.setIcon(R.mipmap.faqasset);
            adb.setMessage(message.toLowerCase());
            adb.setCancelable(false);

            adb.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });





            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }







}
