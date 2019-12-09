package mhealth.c4c;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.List;

import mhealth.c4c.dialogs.Dialogs;

/**
 * Created by root on 11/22/17.
 */

public class ForgotPassword extends AppCompatActivity {
    TextInputEditText secQnT,mysecansT,newpassT,cnewpassT;

    Toolbar toolbar;
    Dialogs sweetdialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initialise();
        setToolbar();
        populateSecurityQn();
    }

    public void initialise(){

        try{
            sweetdialog=new Dialogs(ForgotPassword.this);
            secQnT=(TextInputEditText) findViewById(R.id.secqn);
            mysecansT=(TextInputEditText) findViewById(R.id.mysecans);
            newpassT=(TextInputEditText) findViewById(R.id.newpass);
            cnewpassT=(TextInputEditText) findViewById(R.id.cnewpass);

        }
        catch(Exception e){


        }
    }

    public void setToolbar(){

        try{

            toolbar = (Toolbar) findViewById(R.id.toolbarfp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Password Recovery");
        }
        catch(Exception e){


        }
    }

    public void populateSecurityQn(){

        try{

            List<Registrationdatatable> myl= Registrationdatatable.findWithQuery(Registrationdatatable.class,"select * from Registrationdatatable limit 1",null);
            for(int y=0;y<myl.size();y++){

                String secQ=myl.get(y).getSecurityqn();
                secQnT.setText(secQ);
            }

        }
        catch(Exception e){
            sweetdialog.showErrorDialogForgotPassword("error getting security question, try again","Password Recovery Error");

//            Toast.makeText(this, "error getting security question", Toast.LENGTH_SHORT).show();

        }
    }

    public void RecoverPassword(View v){

        try{
            String secans=mysecansT.getText().toString();
            String newp=newpassT.getText().toString();
            String cnewp=cnewpassT.getText().toString();

            if(secans.trim().isEmpty()){

                sweetdialog.showErrorDialogForgotPassword("please provide a security answer, try again","Password Recovery Error");

//                Toast.makeText(this, "Please Provide a security answer", Toast.LENGTH_SHORT).show();

            }
            else if(newp.trim().isEmpty()){

                sweetdialog.showErrorDialogForgotPassword("please provide new password, try again","Password Recovery Error");

//                Toast.makeText(this, "please provide new password", Toast.LENGTH_SHORT).show();

            }
            else if(cnewp.trim().isEmpty()){

                sweetdialog.showErrorDialogForgotPassword("please provide confirm password, try again","Password Recovery Error");


//                Toast.makeText(this, "please provide Confirm password", Toast.LENGTH_SHORT).show();


            }
            else if(!newp.contentEquals(cnewp)){

                sweetdialog.showErrorDialogForgotPassword("passwords do not match, try again","Password Recovery Error");

//                Toast.makeText(this, "passwords do not match, try again", Toast.LENGTH_SHORT).show();

            }
            else{


                List<Registrationdatatable> myl= Registrationdatatable.findWithQuery(Registrationdatatable.class,"select * from Registrationdatatable limit 1",null);
                for(int y=0;y<myl.size();y++){

                    String existingAns=myl.get(y).getSecurityans();
                    if(secans.contentEquals(existingAns)){

                        Registrationdatatable rt = Registrationdatatable.findById(Registrationdatatable.class, 1);
                        rt.password = newp;
                        rt.save();

                        sweetdialog.showSuccessDialogForgotPassword("PASSWORD UPDATED SUCCESSFULLY","success");
//                        ForgotPassConf("Choose Your Action ?","PASSWORD UPDATED SUCCESSFULLY");




                    }

                    else{

                        sweetdialog.showErrorDialogForgotPassword("the security answer is wrong, try again","Password Recovery Error");

//                        Toast.makeText(this, "The security answer is wrong, try again", Toast.LENGTH_LONG).show();
                    }
                }


            }



        }
        catch(Exception e){


        }
    }



    public void ForgotPassConf(final String message,final String title){

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message+"\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CONTINUE TO LOGIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                dialog.cancel();
            }
        });

        b.setPositiveButton("EXIT APP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                finish();

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

    }
}
