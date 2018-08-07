package mhealth.c4c.userlogindata;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import mhealth.c4c.Login;
import mhealth.c4c.R;
import mhealth.c4c.RegistrationTable;
import mhealth.c4c.SpinnerAdapter;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;

/**
 * Created by KENWEEZY on 2016-10-31.
 */


public class UserLoginData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nameE,lnameE,munameE,mpassE,mcpassE,mhint;

    Dialogs sweetdialog;


    public final Pattern textPattern = Pattern.compile("^([a-zA-Z+]+[0-9+]+)$");

    String[] secqn={"choose a security question","what is your favourite pet ?","what is your favourite country ?"};

    Spinner myspinner4;

    String myselected4="";
    String selectedQn="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_data);
        requestPerms();

        initialise();
        setToolBar();

        populateSpinner4();

        myspinner4.setOnItemSelectedListener(this);


    }




    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.signuptoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Signup");

        }
        catch(Exception e){


        }
    }





    public void initialise(){

        try{

            sweetdialog=new Dialogs(UserLoginData.this);

            nameE=(EditText) findViewById(R.id.suname);

            mhint=(EditText) findViewById(R.id.suhint);
            lnameE=(EditText) findViewById(R.id.sulname);

            munameE=(EditText) findViewById(R.id.sumuname);
            mpassE=(EditText) findViewById(R.id.sumpass);
            mcpassE=(EditText) findViewById(R.id.sumcpass);

            myspinner4=(Spinner) findViewById(R.id.suspinner4);

        }
        catch(Exception e){


        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin=(Spinner) parent;



        if (spin.getId()==R.id.suspinner4){

            selectedQn=secqn[position];


            myselected4=Integer.toString(position);
            if(position>0){

                mhint.setEnabled(true);
            }
            else{
                mhint.setEnabled(false);
                mhint.setText("");
            }
            actOnSelected();

        }


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




    public void actOnSelected(){

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }



    public void populateSpinner4(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),secqn);

            myspinner4.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }




    //ill get back here

    public void Signup(View v){

        try{

            String myname=nameE.getText().toString();
            String mylname=lnameE.getText().toString();
            String myuname=munameE.getText().toString();
            String mympass=mpassE.getText().toString();
            String mymcpass=mcpassE.getText().toString();
            String myhint=mhint.getText().toString();



            if(myname.trim().isEmpty()){
                nameE.setError("First Name is Required");
                Toast.makeText(this, "First Name is Required", Toast.LENGTH_SHORT).show();

            }
            else if(mylname.trim().isEmpty()){
                lnameE.setError("Last Name is Required");
                Toast.makeText(this, "Last Name is Required", Toast.LENGTH_SHORT).show();

            }

            else if(myuname.trim().isEmpty()){
                lnameE.setError("User Name is Required");
                Toast.makeText(this, "User Name is Required", Toast.LENGTH_SHORT).show();

            }

            else if(mympass.trim().isEmpty()){
                mpassE.setError("Password is Required");
                Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();

            }
            else if(!(isTextValid(mympass)&&mympass.length()>5)){

                mpassE.setError("Password should have numbers and letters and atleast a minimum of 6 characters");
                Toast.makeText(this, "Password should have numbers and letters and atleast a minimum of 6 characters", Toast.LENGTH_SHORT).show();
            }

            else if(mymcpass.trim().isEmpty()){
                mcpassE.setError("Confirm Password is Required");
                Toast.makeText(this, "Confirm Password is Required", Toast.LENGTH_SHORT).show();

            }
            else if(!mymcpass.contentEquals(mympass)){
                mcpassE.setError("Passwords Do not match");
                Toast.makeText(this, "Passwords Do not match", Toast.LENGTH_SHORT).show();

            }


            else if(myselected4.contentEquals("0")){

                sweetdialog.showErrorDialogRegistration("Specify security question","Registration Error");


//                Toast.makeText(this, "Specify security question", Toast.LENGTH_LONG).show();


            }
            else if(myhint.trim().isEmpty()){
                mhint.setError("Answer is required");
                Toast.makeText(this, "Answer is required", Toast.LENGTH_SHORT).show();

            }

            else{

                SignupUser(myname,mylname,myuname,mympass,myhint);


            }

        }
        catch(Exception e){

            sweetdialog.showErrorDialogRegistration("Error occured "+e,"Registration Error");

//            SignupdisplayDialog("Error occured "+e);


        }
    }


    public void SignupsuccessDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("SIGNUP SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        Intent myint=new Intent(getApplicationContext(),Login.class);

                        startActivity(myint);


                }
            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });


            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }



    public void SignupUser(final String myname, final String mylname, final String myuname, final String mympass, final String mhnt){


        final ProgressDialog pdialog= mydialog("loading...","Signing Up");


            pdialog.cancel();


                RegistrationTable.deleteAll(RegistrationTable.class);
                RegistrationTable rt=new RegistrationTable(myname,mylname,"","","","","","",myuname,mympass,selectedQn,mhnt);
                rt.save();




                String mymess="";

                mymess=myname+"*"+mylname+"*"+myuname+"*"+mympass+"*"+myselected4+"*"+mhnt;
                String encrypted = Base64Encoder.encryptString(mymess);


                SmsManager smsM=SmsManager.getDefault();
                smsM.sendTextMessage("40145",null,"SU*"+encrypted,null,null);
                SignupsuccessDialog("Success in Registration");


    }

    public ProgressDialog mydialog(String message,String title){
        ProgressDialog progress = new ProgressDialog(this);

        try{


            progress.setMessage(message);
            progress.setTitle(title);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        }
        catch(Exception e){
//            Toast.makeText(this, "error displaying progress", Toast.LENGTH_SHORT).show();

        }

        return progress;
    }

    public void requestPerms(){

        try{

            int permissionCheck = ContextCompat.checkSelfPermission(UserLoginData.this, Manifest.permission.SEND_SMS);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        UserLoginData.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1235);
            } else {

            }
        }
        catch(Exception e){
            Toast.makeText(this, "error in granting permissions "+e, Toast.LENGTH_SHORT).show();


        }
    }


//check if the provided password matches the regular expression
    public boolean isTextValid(String textToCheck) {
        return textPattern.matcher(textToCheck).matches();
    }
}
