package mhealth.c4c;

/**
 * Created by kennedy on 9/13/17.
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import mhealth.c4c.AccessServer.AccessServer;
import mhealth.c4c.Checkinternet.CheckInternet;
import mhealth.c4c.LoadMessages.LoadMessages;
import mhealth.c4c.RequestPermissions.RequestPerms;
import mhealth.c4c.SSLTrustCertificate.SSLTrust;
import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.Tables.Partners;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.sendMessages.SendMessage;
import mhealth.c4c.userlogindata.UserLoginData;


public class Login extends AppCompatActivity {

    private EditText input_email;
    private EditText input_password;
    private Button btnSignin;
    private TextView link_forgot_password;
    Dialogs sweetdialog;
    AccessServer accessServer;
    CheckInternet chkInternet;
    RequestPerms requestPerms;

    private static final int PERMS_REQUEST_CODE = 123;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    ProgressOld pr = new ProgressOld();
    List<Registrationdatatable> user_list = new ArrayList<>();
    boolean kmpduChecked = false;
    LoadMessages loadmessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Stetho.initializeWithDefaults(this);

        LoadRegistration();

        initialise();

        SSLTrust.nuke();

        btnSigninListener();
        forgotPasswordListener();
        populateUsername();
        getPassedValues();
        getPartners();

    }


    public void btnSigninListener(){

        try{


            btnSignin.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    loginCheck();

                }
            });


        }
        catch(Exception e){


        }
    }

    public void forgotPasswordListener(){

        try{

            link_forgot_password.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Start the Register activity
                    Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                    startActivityForResult(intent, REQUEST_SIGNUP);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });

        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{
            requestPerms=new RequestPerms(Login.this,this);
            chkInternet=new CheckInternet(Login.this);
            accessServer=new AccessServer(Login.this);
            loadmessages=new LoadMessages(Login.this);
            sweetdialog = new Dialogs(Login.this);

            input_email = (EditText) findViewById(R.id.input_email);
            input_password = (EditText) findViewById(R.id.input_password);
            btnSignin = (Button) findViewById(R.id.btnSignin);
            //link_signup = (TextView) findViewById(R.id.link_signup);
            link_forgot_password = (TextView) findViewById(R.id.forgot_password);


        }
        catch (Exception e){


        }
    }

    @SuppressLint("MissingPermission")
    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        requestPerms();
        return phoneMgr.getSubscriberId();
    }

    public void getPassedValues(){

        try{
            List<kmpdu> myl=kmpdu.findWithQuery(kmpdu.class,"select * from kmpdu limit 1",null);
            for(int x=0;x<myl.size();x++){

                String mykmpdu=myl.get(x).getKmpduchecked();
                if(mykmpdu.contentEquals("true")){

                    kmpduChecked=true;
//                    Toast.makeText(this, "kmpdu checked", Toast.LENGTH_SHORT).show();
                }
                else{

                    kmpduChecked=false;
//                    Toast.makeText(this, "kmpdu not checked", Toast.LENGTH_SHORT).show();
                }
            }




        }
        catch(Exception e){

        }
    }


    public boolean isKmtcAvailable(){

        try{
            List<Partners> myl=Partners.findWithQuery(Partners.class,"select * from Partners where partnername=?","KMTC");
            if(myl.size()>0){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){

            return false;
        }
    }

    public void getPartners(){

        try{
            List<Partners> myl=Partners.findWithQuery(Partners.class,"select * from Partners",null);
            System.out.println("********************partners**********************");
            for(int x=0;x<myl.size();x++){

                String mykmpdu=myl.get(x).getPartnername();
                System.out.println(mykmpdu+" "+isKmtcAvailable());

            }




        }
        catch(Exception e){

        }
    }


    public void populateUsername(){

        try{

            List<Registrationdatatable> myl= Registrationdatatable.findWithQuery(Registrationdatatable.class,"select * from Registrationdatatable limit 1",null);
            for(int y=0;y<myl.size();y++){

                String un=myl.get(y).getUsername();
                input_email.setText(un);

            }
        }
        catch(Exception e){


        }
    }




    public void LoadRegistration(){

        try{
            List<Registrationdatatable> myl= Registrationdatatable.findWithQuery(Registrationdatatable.class,"select * from Registrationdatatable");
            if(myl.size()==0){



                Intent i=new Intent(getApplicationContext(),UserLoginData.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                finish();

            }
            else{


            }


        }
        catch(Exception e){


        }
    }





    public void loginCheck(){
        pr.progressing(this,"Validating c4c Details","Login Validation");

        try {
            String myun = input_email.getText().toString();
            String mypass = input_password.getText().toString();

            if (myun.isEmpty()) {
                pr.DissmissProgress();
                sweetdialog.showErrorDialogLogin("Empty Username,try again","Login Error");
//                LogindisplayDialog("Empty Username");

            } else if (mypass.isEmpty()) {
                pr.DissmissProgress();

                sweetdialog.showErrorDialogLogin("Empty password,try again","Login Error");

//                LogindisplayDialog("Empty password");

            }

            else {

                if(chkInternet.isInternetAvailable()){
                    locallyLoginUser(myun,mypass);

                }
                else{

                    locallyLoginUser(myun,mypass);
                }

            }
        }
        catch(Exception e){
            pr.DissmissProgress();

            sweetdialog.showErrorDialogLogin(""+e.getMessage(),"Login Error");

//            LogindisplayDialog(e.getMessage());

        }
    }


    public void locallyLoginUser(String myun,String mypass){

        try{


            user_list = Registrationdatatable.find(Registrationdatatable.class,"username=? and password=?",myun,mypass);
            if (!user_list.isEmpty()) {
                String dob="";

                List<Registrationdatatable> chkDOB= Registrationdatatable.findWithQuery(Registrationdatatable.class,"select * from Registrationdatatable");
                for(int myx=0;myx<chkDOB.size();myx++){

                    dob=chkDOB.get(myx).getAge();
                }

                pr.DissmissProgress();

                Broadcastsmsrights.deleteAll(Broadcastsmsrights.class);

                loadmessages.loadInboxMessages();


                if(dob.trim().isEmpty()){

                    Intent myint = new Intent(getApplicationContext(), CreateUser.class);
//                            myint.putExtra("kmpduChecked","true");
                    startActivity(myint);

                }
                else{



                    Intent myint = new Intent(getApplicationContext(), LandingPage.class);
//                            myint.putExtra("kmpduChecked","true");
                    startActivity(myint);
                }





            } else {
                pr.DissmissProgress();

                sweetdialog.showErrorDialogLogin("Incorrect username, password combination","Login Error");

//                    LogindisplayDialog("Kindly create an account to access c4c");

            }

        }
        catch(Exception e){


        }
    }


    public void LogindisplayDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("LOGIN ERROR");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("Error, Please try again", new DialogInterface.OnClickListener() {
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




    //check permissions granted at runtime in api 23 and above
    @SuppressLint("WrongConstant")
    private boolean hasPermissions(){

        boolean returnVal=false;

        try{



            int res = 0;

            String[] permissions = new String[]{
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.GET_ACCOUNTS,
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE

            };

            for (String perms : permissions) {
                res = checkCallingOrSelfPermission(perms);

                if (!(res == PackageManager.PERMISSION_GRANTED)) {
                    returnVal=false;
                }

            }
            returnVal=true;


        }
        catch(Exception e){


        }


    return returnVal;

    }
//end check permissions

    private void requestPerms(){

        String[] permissions=new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_PHONE_STATE

        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);

        }

    }



    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        else if (!validate()) {
            onLoginSuccess();
            return;
        }

        btnSignin.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), LandingPage.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

        btnSignin.setEnabled(true);
    }

    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        sweetdialog.showErrorDialogLogin("incorrect username, password combination","Login Error");


        btnSignin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }

    public void call(View v)
    {
//        Toast.makeText(this, "call us", Toast.LENGTH_LONG).show();

    }

    public void tweet(View v)
    {
//        Toast.makeText(this, "twitter", Toast.LENGTH_LONG).show();
  }

    public void whatsapp(View v)
    {

//        Toast.makeText(this, "whatsapp", Toast.LENGTH_LONG).show();
    }
}



