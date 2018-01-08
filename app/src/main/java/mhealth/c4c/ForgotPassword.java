package mhealth.c4c;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by root on 11/22/17.
 */

public class ForgotPassword extends AppCompatActivity {
    TextInputEditText secQnT,mysecansT,newpassT,cnewpassT;

    Toolbar toolbar;


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

            List<RegistrationTable> myl=RegistrationTable.findWithQuery(RegistrationTable.class,"select * from Registration_table limit 1",null);
            for(int y=0;y<myl.size();y++){

                String secQ=myl.get(y).getSecurityqn();
                secQnT.setText(secQ);
            }

        }
        catch(Exception e){

            Toast.makeText(this, "error getting security question", Toast.LENGTH_SHORT).show();

        }
    }

    public void RecoverPassword(View v){

        try{
            String secans=mysecansT.getText().toString();
            String newp=newpassT.getText().toString();
            String cnewp=cnewpassT.getText().toString();

            if(secans.trim().isEmpty()){
                Toast.makeText(this, "Please Provide a security answer", Toast.LENGTH_SHORT).show();

            }
            else if(newp.trim().isEmpty()){
                Toast.makeText(this, "please provide new password", Toast.LENGTH_SHORT).show();

            }
            else if(cnewp.trim().isEmpty()){

                Toast.makeText(this, "please provide Confirm password", Toast.LENGTH_SHORT).show();


            }
            else if(!newp.contentEquals(cnewp)){
                Toast.makeText(this, "passwords do not match, try again", Toast.LENGTH_SHORT).show();

            }
            else{


                List<RegistrationTable> myl=RegistrationTable.findWithQuery(RegistrationTable.class,"select * from Registration_table limit 1",null);
                for(int y=0;y<myl.size();y++){

                    String existingAns=myl.get(y).getSecurityans();
                    if(secans.contentEquals(existingAns)){

                        RegistrationTable rt = RegistrationTable.findById(RegistrationTable.class, 1);
                        rt.password = newp;
                        rt.save();

                        ForgotPassConf("Choose Your Action ?","PASSWORD UPDATED SUCCESSFULLY");




                    }

                    else{

                        Toast.makeText(this, "The security answer is wrong, try again", Toast.LENGTH_LONG).show();
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
