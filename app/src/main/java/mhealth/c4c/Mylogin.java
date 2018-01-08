package mhealth.c4c;

/**
 * Created by KENWEEZY on 2016-10-31.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import mhealth.c4c.Tables.UserTable;

import static com.orm.SugarRecord.deleteAll;

/**
 * Created by KENWEEZY on 2016-10-23.
 */

public class Mylogin extends AppCompatActivity {

    private static final int PERMS_REQUEST_CODE=123;

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    Progress pr=new Progress();

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "unameKey";
    EditText un,pw;
    //CheckBox mychkbx;
    public static final String UNAME = "UNAME";
    SharedPreferences sharedpreferences;
    public static final String SETTING_INFOS = "SETTING_Infos";

    List<RegistrationTable> user_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylogin);

//        deleting();
        LoadRegistration();

        un=(EditText) findViewById(R.id.uname);
        pw=(EditText) findViewById(R.id.pass);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);

        String name = settings.getString(UNAME, "");
        un.setText(name);



    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
        settings.edit()
                .putString(UNAME, un.getText().toString()).commit();
    }


    public void LoginUser(View v){

        try{




            loginCheck();

        }
        catch(Exception e){

            LogindisplayDialog(e.getMessage());

        }
    }



    public void loginCheck(){
        pr.progressing(this,"Validating c4c Details","Login Validation");

        try {
            String myun = un.getText().toString();
            String mypass = pw.getText().toString();

            if (myun.isEmpty()) {
                pr.DissmissProgress();
                LogindisplayDialog("Empty Username");

            } else if (mypass.isEmpty()) {
                pr.DissmissProgress();
                LogindisplayDialog("Empty password");

            }

            else {

                user_list = RegistrationTable.find(RegistrationTable.class,"username=? and password=?",myun,mypass);
                if (!user_list.isEmpty()) {
                    pr.DissmissProgress();
                    if(hasPermissions()){
                        Intent myint = new Intent(getApplicationContext(), home.class);
                        startActivity(myint);

                    }
                    else{

                        requestPerms();
                    }


                } else {
                    pr.DissmissProgress();
                    LogindisplayDialog("Kindly create an account to access c4c");

                }

            }
        }
        catch(Exception e){
            pr.DissmissProgress();
            LogindisplayDialog(e.getMessage());

        }
    }

    public void LoadRegistration(){

        try{
            List<RegistrationTable> myl=RegistrationTable.findWithQuery(RegistrationTable.class,"select * from Registration_table");
            if(myl.size()==0){

                Intent i=new Intent(getApplicationContext(),CreateUser.class);
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
    private boolean hasPermissions(){


        int res = 0;

        String[] permissions = new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.INTERNET
        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }
//end check permissions

    private void requestPerms(){

        String[] permissions=new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.INTERNET
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);

        }

    }

    public void deleting(){

        List<UserTable> books = UserTable.listAll(UserTable.class);

        deleteAll(UserTable.class);
    }

}

