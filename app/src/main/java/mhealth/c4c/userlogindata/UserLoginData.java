package mhealth.c4c.userlogindata;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mhealth.c4c.AccessServer.AccessServer;
import mhealth.c4c.Checkinternet.CheckInternet;
import mhealth.c4c.CreateUser;
import mhealth.c4c.Login;
import mhealth.c4c.R;
import mhealth.c4c.Registrationdatatable;
import mhealth.c4c.RequestPermissions.RequestPerms;
import mhealth.c4c.SpinnerAdapter;
import mhealth.c4c.Tables.Edittable;
import mhealth.c4c.Tables.Profiletable;
import mhealth.c4c.Tables.Signupform.Signup;
import mhealth.c4c.Tables.Signupform.Userserverdetails;
import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;
import mhealth.c4c.models.CountyModel;
import mhealth.c4c.progress.Progress;

import static com.android.volley.Request.Method.POST;

/**
 * Created by KENWEEZY on 2016-10-31.
 */


public class UserLoginData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nameE, lnameE, munameE, mpassE, mcpassE, mhint,phoneE;
    LinearLayout uloginLL;
    Button verifyBtn,btnnxt;

    Dialogs sweetdialog;
    AccessServer acessServer;
    boolean isPhoneValid,isPasswordValid;


    public final Pattern textPattern = Pattern.compile("^([a-zA-Z+]+[0-9+]+)$");

    String[] secqn = {"choose a security question", "what is your favourite pet ?", "what is your favourite country ?"};

    Spinner myspinner4;

    String myselected4 = "";
    String selectedQn = "";
    RequestPerms requestPerms;
    CheckInternet chkInternet;

    boolean isUserAvailable;
    Button createacctbtn;

    Progress pr;

    private JSONArray id_userinfo_result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_data);


        initialise();
        requestPerms();
        setToolBar();

        populateSpinner4();

        myspinner4.setOnItemSelectedListener(this);
        setPhoneErrorListener();
        setPasswordErrorListener();
        verifyButtonClickListener();
        enableOtherFields();


    }

//    public void Next(View v){
//        try{
//            Intent myintt=new Intent(getApplicationContext(), CreateUser.class);
//            startActivity(myintt);
//
//        }
//        catch(Exception e){
//
//
//        }
//    }

    private void setPhoneErrorListener(){
        try{

            phoneE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (phoneE.getText().toString().trim().length() != 10) {
                            phoneE.setError("provide a valid phone number e.g 0712345678");
                            isPhoneValid=false;
                        } else {
                            // your code here
                            phoneE.setError(null);
                            isPhoneValid=true;
                        }
                    } else {
                        if (phoneE.getText().toString().trim().length() != 10) {
                            phoneE.setError("provide a valid phone number e.g 0712345678");
                            isPhoneValid=false;
                        } else {
                            // your code here
                            phoneE.setError(null);
                            isPhoneValid=true;
                        }
                    }

                }
            });
        }
        catch(Exception e){


        }
    }




    private void setPasswordErrorListener(){
        try{

            mpassE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (!(isTextValid(mpassE.getText().toString()))) {
                            mpassE.setError("Password should have atleast a minimum of 6 characters");
                            isPasswordValid=false;
                        } else {
                            // your code here
                            mpassE.setError(null);
                            isPasswordValid=true;
                        }
                    } else {
                        if (!(isTextValid(mpassE.getText().toString()))) {
                            mpassE.setError("Password should have atleast a minimum of 6 characters");
                            isPasswordValid=false;
                        } else {
                            // your code here
                            mpassE.setError(null);
                            isPasswordValid=true;
                        }
                    }

                }
            });
        }
        catch(Exception e){


        }
    }



    public void setToolBar() {

        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.signuptoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Signup");

        } catch (Exception e) {


        }
    }

    private void verifyButtonClickListener(){

        try{

                verifyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(phoneE.getText().toString().trim().isEmpty()){

                            Toast.makeText(UserLoginData.this, "Phone number is required", Toast.LENGTH_SHORT).show();
                        }
                        else if(phoneE.getText().toString().trim().length()!=10){

                            Toast.makeText(UserLoginData.this, "provide a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            VerifyNumberFromServer(phoneE.getText().toString());

                        }




                    }
                });



        }
        catch(Exception e){

        }
    }

    private void enableOtherFields(){

        try{

            List<Signup> sl=Signup.findWithQuery(Signup.class,"select * from Signup limit 1");
            if(sl.size()>0){

                uloginLL.setVisibility(View.VISIBLE);
                createacctbtn.setVisibility(View.GONE);
                for(int y=0;y<sl.size();y++){

                    String ph=sl.get(y).getPhone();
                    String fn=sl.get(y).getFname();
                    String ln=sl.get(y).getLname();
                    String un=sl.get(y).getUname();
                    String pas=sl.get(y).getPassword();
                    String msecqn=sl.get(y).getSecqn();
                    String ans=sl.get(y).getAnswer();


                    nameE.setText(fn);
                    phoneE.setText(ph);
                    mhint.setText(ans);
                    lnameE.setText(ln);

                    munameE.setText(un);
                    mpassE.setText(pas);
                    mcpassE.setText(pas);

                    myspinner4.setSelection(1);

                }

                verifyBtn.setVisibility(View.GONE);

            }
            else{

                uloginLL.setVisibility(View.GONE);
                verifyBtn.setVisibility(View.VISIBLE);
                createacctbtn.setVisibility(View.GONE);
            }


        }
        catch(Exception e){


        }
    }


    public void initialise() {

        try {
            btnnxt=(Button) findViewById(R.id.btnnext);
            createacctbtn =(Button) findViewById(R.id.createaccountbtn);
            pr=new Progress(UserLoginData.this);
            verifyBtn=(Button) findViewById(R.id.verifyButton);
            isUserAvailable=false;
            uloginLL=(LinearLayout) findViewById(R.id.loginfieldsll);
            isPhoneValid=false;
            isPasswordValid=false;
            chkInternet=new CheckInternet(UserLoginData.this);
            requestPerms=new RequestPerms(UserLoginData.this,this);
            sweetdialog = new Dialogs(UserLoginData.this);
            acessServer=new AccessServer(UserLoginData.this);

            nameE = (EditText) findViewById(R.id.suname);
            phoneE = (EditText) findViewById(R.id.phone);
            mhint = (EditText) findViewById(R.id.suhint);
            lnameE = (EditText) findViewById(R.id.sulname);

            munameE = (EditText) findViewById(R.id.sumuname);
            mpassE = (EditText) findViewById(R.id.sumpass);
            mcpassE = (EditText) findViewById(R.id.sumcpass);

            myspinner4 = (Spinner) findViewById(R.id.suspinner4);

        } catch (Exception e) {


        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;


        if (spin.getId() == R.id.suspinner4) {

            selectedQn = secqn[position];


            myselected4 = Integer.toString(position);
            if (position > 0) {

                mhint.setEnabled(true);
            } else {
                mhint.setEnabled(false);
                mhint.setText("");
            }
            actOnSelected();

        }


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void actOnSelected() {

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }


    public void populateSpinner4() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), secqn);

            myspinner4.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }



    public void CreateAccount(View v) {

        try {

            String myname = nameE.getText().toString();
            String mylname = lnameE.getText().toString();
            String myphone = phoneE.getText().toString();
            String myuname = munameE.getText().toString();
            String mympass = mpassE.getText().toString();
            String mymcpass = mcpassE.getText().toString();
            String myhint = mhint.getText().toString();


            if (myname.trim().isEmpty()) {
                nameE.setError("First Name is Required");
                Toast.makeText(this, "First Name is Required", Toast.LENGTH_SHORT).show();

            } else if (mylname.trim().isEmpty()) {
                lnameE.setError("Last Name is Required");
                Toast.makeText(this, "Last Name is Required", Toast.LENGTH_SHORT).show();

            } else if (myphone.trim().isEmpty()) {
                phoneE.setError("Phone number is Required");
                Toast.makeText(this, "Phone number is Required", Toast.LENGTH_SHORT).show();

            }

            else if (!isPhoneValid) {

                Toast.makeText(this, "Provide a valid phone number e.g 0712345678", Toast.LENGTH_SHORT).show();

            }

            else if (myuname.trim().isEmpty()) {
                lnameE.setError("User Name is Required");
                Toast.makeText(this, "User Name is Required", Toast.LENGTH_SHORT).show();

            } else if (mympass.trim().isEmpty()) {
                mpassE.setError("Password is Required");
                Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();

            } else if (!isPasswordValid) {

                mpassE.setError("Password should have atleast a minimum of 6 characters");
                Toast.makeText(this, "Password should have atleast a minimum of 6 characters", Toast.LENGTH_SHORT).show();
            } else if (mymcpass.trim().isEmpty()) {
                mcpassE.setError("Confirm Password is Required");
                Toast.makeText(this, "Confirm Password is Required", Toast.LENGTH_SHORT).show();

            } else if (!mymcpass.contentEquals(mympass)) {
                mcpassE.setError("Passwords Do not match");
                Toast.makeText(this, "Passwords Do not match", Toast.LENGTH_SHORT).show();

            } else if (myselected4.contentEquals("0")) {

                sweetdialog.showErrorDialogRegistration("Specify security question", "Registration Error");


//                Toast.makeText(this, "Specify security question", Toast.LENGTH_LONG).show();


            } else if (myhint.trim().isEmpty()) {
                mhint.setError("Answer is required");
                Toast.makeText(this, "Answer is required", Toast.LENGTH_SHORT).show();

            }

            else{

                Signup.deleteAll(Signup.class);

                Signup sp=new Signup(myphone,myname,mylname,myuname,mympass,myselected4,myhint);
                sp.save();
                String gender="",cadre="",idnum="",age="",code="";

                List<Userserverdetails> ml=Userserverdetails.findWithQuery(Userserverdetails.class,"select * from Userserverdetails limit 1");
                for(int x=0;x<ml.size();x++){
                    gender=ml.get(x).getGender();
                    cadre=ml.get(x).getCadre();
                    idnum=ml.get(x).getNationalid();
                    age=ml.get(x).getDob();
                    code=ml.get(x).getMfl();

                }

                Registrationdatatable.deleteAll(Registrationdatatable.class);
                Registrationdatatable rt = new Registrationdatatable(myname, mylname, gender, cadre, idnum, age, code, "-1", myuname, mympass, selectedQn, myhint);
                rt.save();

                Intent myintt=new Intent(getApplicationContext(), Login.class);
                startActivity(myintt);

                String newPhoneS="+254"+myphone.substring(1);

                Profiletable pt=new Profiletable(code);
                pt.save();

                //save user details for further editing
                Edittable.deleteAll(Edittable.class);
                Edittable et=new Edittable(code,myphone,idnum);
                et.save();

                Toast.makeText(this, "creating account", Toast.LENGTH_SHORT).show();


            }

//            else if(chkInternet.isInternetAvailable()){
//
//                Userphonenumber.deleteAll(Userphonenumber.class);
//                Userphonenumber pn=new Userphonenumber(myphone);
//                pn.save();
//
//                   String newPhone="+254"+myphone.substring(1);
//
//
//
//
//
//                    acessServer.signupUser(myname,mylname,myuname,newPhone,mympass,myselected4,myhint,selectedQn);
//
//                }
//                else{
//
//                Userphonenumber.deleteAll(Userphonenumber.class);
//                Userphonenumber pn=new Userphonenumber(myphone);
//                pn.save();
//
//                    SignupUser(myname, mylname, myuname, mympass, myhint);
//
//                }






        } catch (Exception e) {

            sweetdialog.showErrorDialogRegistration("Error occured " + e, "Registration Error");

//            SignupdisplayDialog("Error occured "+e);


        }
    }


    //ill get back here

    public void Next(View v) {

        try {

            String myname = nameE.getText().toString();
            String mylname = lnameE.getText().toString();
            String myphone = phoneE.getText().toString();
            String myuname = munameE.getText().toString();
            String mympass = mpassE.getText().toString();
            String mymcpass = mcpassE.getText().toString();
            String myhint = mhint.getText().toString();


            if (myname.trim().isEmpty()) {
                nameE.setError("First Name is Required");
                Toast.makeText(this, "First Name is Required", Toast.LENGTH_SHORT).show();

            } else if (mylname.trim().isEmpty()) {
                lnameE.setError("Last Name is Required");
                Toast.makeText(this, "Last Name is Required", Toast.LENGTH_SHORT).show();

            } else if (myphone.trim().isEmpty()) {
                phoneE.setError("Phone number is Required");
                Toast.makeText(this, "Phone number is Required", Toast.LENGTH_SHORT).show();

            }

            else if (!isPhoneValid) {

                Toast.makeText(this, "Provide a valid phone number e.g 0712345678", Toast.LENGTH_SHORT).show();

            }

            else if (myuname.trim().isEmpty()) {
                lnameE.setError("User Name is Required");
                Toast.makeText(this, "User Name is Required", Toast.LENGTH_SHORT).show();

            } else if (mympass.trim().isEmpty()) {
                mpassE.setError("Password is Required");
                Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();

            } else if (!isPasswordValid) {

                mpassE.setError("Password should have atleast a minimum of 6 characters");
                Toast.makeText(this, "Password should have atleast a minimum of 6 characters", Toast.LENGTH_SHORT).show();
            } else if (mymcpass.trim().isEmpty()) {
                mcpassE.setError("Confirm Password is Required");
                Toast.makeText(this, "Confirm Password is Required", Toast.LENGTH_SHORT).show();

            } else if (!mymcpass.contentEquals(mympass)) {
                mcpassE.setError("Passwords Do not match");
                Toast.makeText(this, "Passwords Do not match", Toast.LENGTH_SHORT).show();

            } else if (myselected4.contentEquals("0")) {

                sweetdialog.showErrorDialogRegistration("Specify security question", "Registration Error");


//                Toast.makeText(this, "Specify security question", Toast.LENGTH_LONG).show();


            } else if (myhint.trim().isEmpty()) {
                mhint.setError("Answer is required");
                Toast.makeText(this, "Answer is required", Toast.LENGTH_SHORT).show();

            }

            else{

                Signup.deleteAll(Signup.class);

                Signup sp=new Signup(myphone,myname,mylname,myuname,mympass,myselected4,myhint);
                sp.save();

                Registrationdatatable.deleteAll(Registrationdatatable.class);
                Registrationdatatable rt = new Registrationdatatable(myname, mylname, "", "", "", "", "", "", myuname, mympass, selectedQn, myhint);
                rt.save();

                Intent myintt=new Intent(getApplicationContext(), CreateUser.class);
                startActivity(myintt);


            }

//            else if(chkInternet.isInternetAvailable()){
//
//                Userphonenumber.deleteAll(Userphonenumber.class);
//                Userphonenumber pn=new Userphonenumber(myphone);
//                pn.save();
//
//                   String newPhone="+254"+myphone.substring(1);
//
//
//
//
//
//                    acessServer.signupUser(myname,mylname,myuname,newPhone,mympass,myselected4,myhint,selectedQn);
//
//                }
//                else{
//
//                Userphonenumber.deleteAll(Userphonenumber.class);
//                Userphonenumber pn=new Userphonenumber(myphone);
//                pn.save();
//
//                    SignupUser(myname, mylname, myuname, mympass, myhint);
//
//                }






        } catch (Exception e) {

            sweetdialog.showErrorDialogRegistration("Error occured " + e, "Registration Error");

//            SignupdisplayDialog("Error occured "+e);


        }
    }


    public void SignupsuccessDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("SIGNUP SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent myint = new Intent(getApplicationContext(), Login.class);

                    startActivity(myint);


                }
            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });


            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }


    public void SignupUser(final String myname, final String mylname, final String myuname, final String mympass, final String mhnt) {


        final ProgressDialog pdialog = mydialog("loading...", "Signing Up");


        pdialog.cancel();


        Registrationdatatable.deleteAll(Registrationdatatable.class);
        Registrationdatatable rt = new Registrationdatatable(myname, mylname, "", "", "", "", "", "", myuname, mympass, selectedQn, mhnt);
        rt.save();


        String mymess = "";

        mymess = myname + "*" + mylname + "*" + myuname + "*" + mympass + "*" + myselected4 + "*" + mhnt;
        String encrypted = Base64Encoder.encryptString(mymess);


        SmsManager smsM = SmsManager.getDefault();
        smsM.sendTextMessage(Config.shortcode, null, "SU*" + encrypted, null, null);
        SignupsuccessDialog("Success in Registration");


    }

    public ProgressDialog mydialog(String message, String title) {
        ProgressDialog progress = new ProgressDialog(this);

        try {


            progress.setMessage(message);
            progress.setTitle(title);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        } catch (Exception e) {
//            Toast.makeText(this, "error displaying progress", Toast.LENGTH_SHORT).show();

        }

        return progress;
    }

    public void requestPerms() {

        try {

           requestPerms.requestPerms();
        } catch (Exception e) {
            Toast.makeText(this, "error in granting permissions " + e, Toast.LENGTH_SHORT).show();


        }
    }


//check if the provided password matches the regular expression
//    public boolean isTextValid(String textToCheck) {
//        return textPattern.matcher(textToCheck).matches();
//    }



/*    ****** password regular expression explanation*******

            (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{6,} at least 6 characters

*/

    public boolean isTextValid(String mytext) {
        boolean isCorrect = false;
        String pattern = "(?=\\S+$).{6,}";
        if (mytext.matches(pattern)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }

        return isCorrect;
    }





    public void VerifyNumberFromServer(final String phone){


        try{

            pr.showProgress("Verifying Phone number....");

            StringRequest stringRequest = new StringRequest(POST,Config.VERIFYNUMBER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();


                            pr.dissmissProgress();



                            if(response.trim().contentEquals("NotFound")){


                                isUserAvailable=false;
                                uloginLL.setVisibility(View.VISIBLE);
                                btnnxt.setVisibility(View.VISIBLE);
                                createacctbtn.setVisibility(View.GONE);
                                verifyBtn.setVisibility(View.GONE);

                            }
                            else{



                                isUserAvailable=true;
                                uloginLL.setVisibility(View.VISIBLE);
                                btnnxt.setVisibility(View.GONE);
                                createacctbtn.setVisibility(View.VISIBLE);
                                verifyBtn.setVisibility(View.GONE);


                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_userinfo_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                    getMyUserInfo(id_userinfo_result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                                }




                            }

//                            else{
//
//                                isUserAvailable=false;
//                                uloginLL.setVisibility(View.GONE);
//
//                            }


//                            Toast.makeText(UserLoginData.this, ""+response, Toast.LENGTH_SHORT).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();
                            isUserAvailable=false;

                            uloginLL.setVisibility(View.GONE);

                            Log.e("Response", error.toString());

                            Toast.makeText(getApplicationContext(), "Check your internet connection and try again", Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getApplicationContext(), "error occured "+error, Toast.LENGTH_SHORT).show();

                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", phone);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Content-Type", "text/html; charset=utf-8");
//                    return params;
//                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(UserLoginData.this);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

//            Toast.makeText(CreateUser.this, "error getting subcounties "+e, Toast.LENGTH_SHORT).show();
        }
    }



    private void getMyUserInfo(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);

                String[] genders = {"Please Select Gender", "Male", "Female"};
                String[] cadres = {"Please Select Cadre", "Student", "Doctor", "Nurse", "Clinical Officer", "Laboratory Technologist", "Cleaner", "Waste Handlers", "VCT Counselor", "Other"};



                String nationalid=json.getString("national_id");
                String cadreid=json.getString("cadre_id");
                String partnerid=json.getString("partner_id");
                String genderid=json.getString("gender_id");
                String dob=json.getString("DOB");
                String mfl=json.getString("facility_id");

//                Toast.makeText(this, ""+nationalid, Toast.LENGTH_SHORT).show();
                String partnerName="n/a";
                String cadreName="n/a";
                String genderName="n/a";
//                if(partnerid!=null){

                String[] partnerArr=partnerid.split("\\+");
                partnerName=Config.itemsorg[Integer.parseInt(partnerArr[0])-1];

//                }
//                if(cadreid!=null){

                cadreName=cadres[Integer.parseInt(cadreid)-1];

//                }

//                if(genderid!=null){

                genderName=genders[Integer.parseInt(genderid)-1];


//                }





                Userserverdetails.deleteAll(Userserverdetails.class);

                Userserverdetails ud=new Userserverdetails(nationalid,cadreName,partnerName,genderName,dob,mfl);
                ud.save();


            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting counties "+ e, Toast.LENGTH_SHORT).show();
            }
            catch(ArrayIndexOutOfBoundsException e){
//                Toast.makeText(this, "cadre information in db", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
