package mhealth.c4c.AccessServer;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhealth.c4c.CreateUser;
import mhealth.c4c.LandingPage;
import mhealth.c4c.LoadMessages.LoadMessages;
import mhealth.c4c.Login;
import mhealth.c4c.ProgressOld;
import mhealth.c4c.R;
import mhealth.c4c.Registrationdatatable;
import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;
import mhealth.c4c.progress.Progress;

public class AccessServer {



    Context ctx;
    List<Registrationdatatable> user_list = new ArrayList<>();

    Progress pr;
    ProgressOld prold = new ProgressOld();
    Dialog mydialog;
    private JSONArray id_result;
    LoadMessages loadmessages;
    Dialogs sweetdialog;


    public AccessServer(Context ctx) {
        try {

            initialise(ctx);
        } catch (Exception e) {

        }

    }

    //    *******************function to initialise variables in the constructor
    public void initialise(Context ctx) {

        try {
            this.ctx = ctx;
            pr = new Progress(ctx);
            mydialog = new Dialog(ctx);
            loadmessages=new LoadMessages(ctx);
            sweetdialog = new Dialogs(ctx);
        } catch (Exception e) {


        }
    }


    public void signupUser(final String firstname, final String lastname, final String username , final String phone_no, final String password,
                            final String security, final String answer,final String selectedQn) {

        pr.showProgress("Signing up user.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        if(response.contentEquals("Welldone")){
                            saveSignupDetailsLocally(firstname,lastname,username,password,security,answer,"");

                            SignupsuccessDialog("Success signing up");


                        }
                        else{

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

//                        mydialog.showErrorDialogAccessServer("Getting Token Error", "check internet connection");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.KEY_SIGNUP_FNAME, firstname);
                params.put(Config.KEY_SIGNUP_LNAME, lastname);
                params.put(Config.KEY_SIGNUP_UNAME, username);
                params.put(Config.KEY_SIGNUP_PWD, password);
                params.put(Config.KEY_SIGNUP_SECQN, security);
                params.put(Config.KEY_SIGNUP_ANS, answer);
                params.put(Config.KEY_SIGNUP_PHONE, phone_no);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public void saveSignupDetailsLocally(String myname,String mylname,String myuname,String mympass,String myselected4,String myhint,String selectedQn){

        try{

//            SignupUser(myname, mylname, myuname, mympass, myhint);
            //save data locally
            Registrationdatatable.deleteAll(Registrationdatatable.class);
            Registrationdatatable rt = new Registrationdatatable(myname, mylname, "", "", "", "", "", "", myuname, mympass, selectedQn, myhint);
            rt.save();


            String mymess = "";

            mymess = myname + "*" + mylname + "*" + myuname + "*" + mympass + "*" + myselected4 + "*" + myhint;
            String encrypted = Base64Encoder.encryptString(mymess);


            SmsManager smsM = SmsManager.getDefault();
            smsM.sendTextMessage(Config.shortcode, null, "SU*" + encrypted, null, null);
//            SignupsuccessDialog("Success in Registration");
        }
        catch(Exception e){


        }
    }

    public void SignupsuccessDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
            adb.setTitle("SIGNUP SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent myint = new Intent(ctx, Login.class);

                    ctx.startActivity(myint);


                }
            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ((Activity)(ctx)).finish();

                }
            });


            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }






    public void loginUser(final String username , final String phone_no, final String password) {

        pr.showProgress("Logging in user.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        if(response.contentEquals("Welldone")){
//                            saveSignupDetailsLocally(firstname,lastname,username,password,security,answer,"");

                            SignupsuccessDialog("Success signing up");


                        }
                        else{

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

//                        mydialog.showErrorDialogAccessServer("Getting Token Error", "check internet connection");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put(Config.KEY_SIGNUP_UNAME, username);
                params.put(Config.KEY_SIGNUP_PWD, password);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

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

                prold.DissmissProgress();

                Broadcastsmsrights.deleteAll(Broadcastsmsrights.class);

                loadmessages.loadInboxMessages();


                if(dob.trim().isEmpty()){

                    Intent myint = new Intent(ctx, CreateUser.class);
//                            myint.putExtra("kmpduChecked","true");
                    ctx.startActivity(myint);

                }
                else{

                    Intent myint = new Intent(ctx, LandingPage.class);
//                            myint.putExtra("kmpduChecked","true");
                    ctx.startActivity(myint);
                }





            } else {
                prold.DissmissProgress();

                sweetdialog.showErrorDialogLogin("Incorrect username, password combination","Login Error");

//                    LogindisplayDialog("Kindly create an account to access c4c");

            }

        }
        catch(Exception e){


        }
    }






    public void createProfile(final String partner , final String specs, final String gender,final String cdr,final String idno,final String dob,final String mflno,final String phone,final boolean kmpduchecked) {

        pr.showProgress("creating profile.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CREATPROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();
                        Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();
                        CreatprofilesuccessDialog("success creating profile",kmpduchecked);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "error "+error, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put(Config.KEY_CREATEPROFILE_PARTNER, partner);
                params.put(Config.KEY_CREATEPROFILE_CDR, cdr);
                params.put(Config.KEY_CREATEPROFILE_DOB, dob);
                params.put(Config.KEY_CREATEPROFILE_GENDER, gender);
                params.put(Config.KEY_CREATEPROFILE_IDNO, idno);
                params.put(Config.KEY_CREATEPROFILE_MFLNO, mflno);
                params.put(Config.KEY_CREATEPROFILE_PHONENO, phone);
                params.put(Config.KEY_CREATEPROFILE_SPECS, specs);



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }



    public void CreatprofilesuccessDialog(String message, final boolean kmpduChecked) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
            adb.setTitle("PROFILE CREATION SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    if (kmpduChecked) {
                        kmpdu km = new kmpdu("true");
                        km.save();

                        Intent myint = new Intent(ctx, LandingPage.class);

                        ctx.startActivity(myint);

                    } else {

                        kmpdu mykm = new kmpdu("false");
                        mykm.save();

                        Intent myint = new Intent(ctx, LandingPage.class);

                        ctx.startActivity(myint);


                    }


                }
            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((Activity)ctx).finish();

                }
            });


            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }

}

