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
import mhealth.c4c.Report;
import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.Tables.Signupform.Signup;
import mhealth.c4c.Tables.Signupform.Signupforothers;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;
import mhealth.c4c.progress.Progress;
import mhealth.c4c.sendMessages.SendMessage;
import mhealth.c4c.systemstatetables.Hepatitis;
import mhealth.c4c.systemstatetables.Hepatitisforothers;

public class AccessServer {



    Context ctx;
    List<Registrationdatatable> user_list = new ArrayList<>();

    Progress pr;
    ProgressOld prold = new ProgressOld();
    Dialog mydialog;
    private JSONArray id_result;
    LoadMessages loadmessages;
    Dialogs sweetdialog;
    SendMessage sm;


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
            sm=new SendMessage(ctx);
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
                        if(response.contains("Oops")){

                            sweetdialog.showErrorDialogLogin(" "+response,"Error signing up");



                        }
                        else{


                            SignupsuccessDialog("Success signing up "+response);
                            saveSignupDetailsLocally(firstname,lastname,username,password,security,answer,"");




                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        sweetdialog.showErrorDialogLogin("Error occured "+error.getMessage(), "Error");

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



    public void SignupErrorDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
            adb.setTitle("SIGNUP Error");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

//            adb.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    Intent myint = new Intent(ctx, Login.class);
//
//                    ctx.startActivity(myint);
//
//
//                }
//            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



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



    public void broadcastSms(final String message , final String date, final String cadre,final String title,final String phone_no) {

        pr.showProgress("reporting exposure....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BROADCAST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();
//                        Toast.makeText(ctx, "sent "+response, Toast.LENGTH_SHORT).show();
                        System.out.println("*************response exposure****************");
                        System.out.println(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ctx, "error "+error, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        System.out.println("*************response exposure****************");
                        System.out.println(error);
                        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();

                        //send sms to the server

                        String bmes="BM*"+Base64Encoder.encryptString(message+"*"+date+"*"+cadre+"*"+title);
//                SendMessage.sendMessage(bmes,Config.shortcode);
                        sm.sendMessageApi(bmes,Config.shortcode);


//                        Intent i = new Intent(ctx, LandingPage.class);
//                        // Closing all the Activities
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                        ctx.startActivity(i);
//                        ((Report)ctx).finish();


                        //send sms to the server



                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("text", message);
                params.put("sendDate", date);
                params.put("cdr", cadre);
                params.put("bname", title);
                params.put("phone", phone_no);



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public void reportExposure(final String eloc , final String etype, final String purp,final String whenithapnd,final String HivStatus,final String hbvstatus,final String expno,final String pepinit,final String dateexpd,final String device,final String deviceSafety,final String deep,final String datepep,final String expresult,final String phone_no) {

        pr.showProgress("reporting exposure....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REPORTEXPOSURE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();
//                        Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ctx, "sent "+response, Toast.LENGTH_SHORT).show();
                        System.out.println("*************response exposure****************");
                        System.out.println(response);

                        Intent i = new Intent(ctx, LandingPage.class);
                        // Closing all the Activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        ctx.startActivity(i);
                        ((Report)ctx).finish();
//                        CreatprofilesuccessDialog("success creating profile",device);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ctx, "error "+error, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        System.out.println("*************response exposure****************");
                        System.out.println(error);
                        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();

                        //send sms to the server

                        String Message="Rep*"+ Base64Encoder.encryptString(eloc+"*"+etype+"*"+purp+"*"+whenithapnd+"*"+HivStatus+"*"+hbvstatus+"*"+expno+"*"+pepinit+"*"+dateexpd+"*"+device+"*"+deviceSafety+"*"+deep+"*"+datepep+"*"+expresult);
//                        String Message = "Rep*"+where+"*"+nature+"*"+myhour;

                        sm.sendMessageApi(Message,Config.shortcode);



                        //send sms to the server



                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put(Config.KEY_REPORTEXPOSURE_DATEEXPD, dateexpd);
                params.put(Config.KEY_REPORTEXPOSURE_DATEPEP, datepep);
                params.put(Config.KEY_REPORTEXPOSURE_DEEP, deep);
                params.put(Config.KEY_REPORTEXPOSURE_DEVICE, device);
                params.put(Config.KEY_REPORTEXPOSURE_DEVICESAFETY, deviceSafety);
                params.put(Config.KEY_REPORTEXPOSURE_ELOC, eloc);
                params.put(Config.KEY_REPORTEXPOSURE_ETYPE, etype);
                params.put(Config.KEY_REPORTEXPOSURE_EXPNO, expno);
                params.put(Config.KEY_REPORTEXPOSURE_EXPRESULT, expresult);
                params.put(Config.KEY_REPORTEXPOSURE_HIVSTATUS, HivStatus);
                params.put(Config.KEY_REPORTEXPOSURE_HBVSTATUS, hbvstatus);
                params.put(Config.KEY_REPORTEXPOSURE_PEPINIT, pepinit);
                params.put(Config.KEY_REPORTEXPOSURE_PURP, purp);
                params.put(Config.KEY_REPORTEXPOSURE_WHENITHAPND, whenithapnd);
                params.put(Config.KEY_REPORTEXPOSURE_PHONENO, phone_no);



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }



    public void createProfile(final String partner , final String specs, final String gender, final String cdr, final String idno, final String dob, final String mflno, final String phone, final String othercadre,final String dose1,final String dose2,final String dose3,final String seroStatus,final String homeCty,final String nokname,final String nokcontact,final String hbv1,final String hbv2,final String hbv3) {

        pr.showProgress("creating profile.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CREATPROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(ctx, ""+phone, Toast.LENGTH_SHORT).show();

                        pr.dissmissProgress();
//                        Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();
                        CreatprofilesuccessDialog("success creating profile, "+response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ctx, "Check your internet and try again "+error, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                List<Signup> mym=Signup.findWithQuery(Signup.class,"select * from Signup limit 1");
                for(int x=0;x<mym.size();x++){
//                    String hbv1="-1";
//                    String hbv2="-1";
//                    String dose1="-1";
//                    String dose2="-1";
//
//                    List<Hepatitis> hep=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis limit 1");
//                    for(int h=0;h<hep.size();h++){
//                        if(!(hep.get(h).getImmunisedvaluedose1()==null)){
//
//                            hbv1=getHbvId(hep.get(h).getImmunisedvaluedose1());
//
//
//                        }
//                        if(!(hep.get(h).getImmunisedvaluedose2()==null)){
//
//                            hbv2=getHbvId(hep.get(h).getImmunisedvaluedose2());
//                        }
//                        if(!(hep.get(h).getFirstdosedate()==null)){
//
//                            dose1=hep.get(h).getFirstdosedate();
//                        }
//                        if(!(hep.get(h).getSeconddosedate()==null)){
//
//                            dose2=hep.get(h).getSeconddosedate();
//
//                        }
//
//                    }
                    params.put("fname", mym.get(x).getFname());
                    params.put("lname", mym.get(x).getLname());
                    params.put("phone_no", phone);
                    params.put("partner", partner);
                    params.put("gender", gender);
                    params.put("cdr", cdr);
                    params.put("other_cadre", othercadre);
                    params.put("idno", idno);
                    params.put("dob", dob);
                    params.put("mflno", mflno);
                    params.put("hbv1", hbv1);
                    params.put("dose1", dose1);
                    params.put("hbv2", hbv2);
                    params.put("dose2", dose2);
                    params.put("hbv3", hbv2);
                    params.put("dose3", dose3);
                    params.put("homecounty", homeCty);
                    params.put("nextofkinname", nokname);
                    params.put("nextofkincontact", nokcontact);
                    params.put("serostatus", seroStatus);

                }




                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }






    public void createProfileForOthers(final String partner , final String specs, final String gender, final String cdr, final String idno, final String dob, final String mflno, final String phone, final String othercadre) {

        pr.showProgress("creating profile.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CREATPROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(ctx, ""+phone, Toast.LENGTH_SHORT).show();

                        pr.dissmissProgress();
//                        Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();

                        sweetdialog.showRegisterUserOptions("Report Exposure","Choose an option, "+response);

//                        CreatprofilesuccessDialog("success creating profile, "+response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ctx, "Check your internet and try again "+error, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                List<Signupforothers> mym=Signupforothers.findWithQuery(Signupforothers.class,"select * from Signupforothers limit 1");
                for(int x=0;x<mym.size();x++){
                    String hbv1="-1";
                    String hbv2="-1";
                    String dose1="-1";
                    String dose2="-1";

                    List<Hepatitisforothers> hep=Hepatitisforothers.findWithQuery(Hepatitisforothers.class,"select * from Hepatitisforothers limit 1");
                    for(int h=0;h<hep.size();h++){
                        if(!(hep.get(h).getImmunisedvaluedose1()==null)){

                            hbv1=getHbvId(hep.get(h).getImmunisedvaluedose1());


                        }
                        if(!(hep.get(h).getImmunisedvaluedose2()==null)){

                            hbv2=getHbvId(hep.get(h).getImmunisedvaluedose2());
                        }
                        if(!(hep.get(h).getFirstdosedate()==null)){

                            dose1=hep.get(h).getFirstdosedate();
                        }
                        if(!(hep.get(h).getSeconddosedate()==null)){

                            dose2=hep.get(h).getSeconddosedate();

                        }

                    }
                    params.put("fname", mym.get(x).getFname());
                    params.put("lname", mym.get(x).getLname());
                    params.put("phone_no", phone);
                    params.put("partner", partner);
                    params.put("gender", gender);
                    params.put("cdr", cdr);
                    params.put("other_cadre", othercadre);
                    params.put("idno", idno);
                    params.put("dob", dob);
                    params.put("mflno", mflno);
                    params.put("hbv1", hbv1);
                    params.put("dose1", dose1);
                    params.put("hbv2", hbv2);
                    params.put("dose2", dose2);

                }




                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    private String getHbvId(String hbv){
        String newId="-1";

        if(hbv.equalsIgnoreCase("Yes")){
            newId="1";

        }
        else if(hbv.equalsIgnoreCase("Partially")){
            newId="2";

        }
        else if(hbv.equalsIgnoreCase("No")){

            newId="3";

        }

        return newId;

    }

    public void CreatprofilesuccessDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
            adb.setTitle("PROFILE CREATION SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                        kmpdu mykm = new kmpdu("false");
                        mykm.save();

                    Intent myint = new Intent(ctx, Login.class);

                    ctx.startActivity(myint);





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

