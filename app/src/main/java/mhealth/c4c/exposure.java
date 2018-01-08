package mhealth.c4c;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 12/11/2015.
 */
public class exposure extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mySpinner1;
    Spinner mySpinner2;
    String selected_item="";
    String selected_item2="";
    String myselected="";
    String myselected2="";
    EditText txtEhourE;
    Button btnSSubmit;

    String[] where={"Where did the exposure occur?","Medical Ward","Surgical Ward","Theatre","Maternity","Dental Clinic","OP/MCH","Laundry","Laboratory","Other"};
    String[] what={"What was the type of exposure?","Needle Stick","Cut","Splash on mucosa","Non-intact skin","Bite","Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exposre);

        initialise();
        populateSpinner1();
        populateSpinner2();

        btnSSubmit.setEnabled(true);

        mySpinner1.setOnItemSelectedListener(this);
        mySpinner2.setOnItemSelectedListener(this);
    }


    public void initialise(){

        try{

            mySpinner1=(Spinner)findViewById(R.id.spinner1);
            mySpinner2=(Spinner)findViewById(R.id.spinner2);
            txtEhourE=(EditText) findViewById(R.id.txtEHour);
            btnSSubmit = (Button) findViewById(R.id.btnSSubmit);
        }
        catch(Exception e){


        }
    }

    public void submiting(View v){

        try{

            String hr=txtEhourE.getText().toString();

            if(hr.trim().isEmpty()){

                txtEhourE.setError("Exposure Hour required");
            }
            else if(myselected.contentEquals("0")){

                Toast.makeText(this, "Select Where the Exposure occured", Toast.LENGTH_SHORT).show();
            }

            else if(myselected2.contentEquals("0")){

                Toast.makeText(this, "Select What caused the exposure", Toast.LENGTH_SHORT).show();
            }

            else{


                String Message = "Rep*"+myselected+"*"+myselected2+"*"+hr;

                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage("40149", null, Message, null, null);
                clearFields();
                SignupsuccessDialog("");


            }



        }
        catch(Exception e){


        }
    }

    public void clearFields(){

        try{

            txtEhourE.setText("");
        }
        catch(Exception e){


        }
    }


    public void SignupsuccessDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("EXPOSURE REPORTED SUCCESSFULLY");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setNegativeButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i=new Intent(getApplicationContext(),home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }
            });





            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // On selecting a spinner item
        Spinner spin=(Spinner) parent;

        if(spin.getId()==R.id.spinner1){

//            selected_item=parent.getItemAtPosition(position).toString();
            myselected=Integer.toString(position);

            actOnSelected();

        }
        else if (spin.getId()==R.id.spinner2){

//            selected_item2=parent.getItemAtPosition(position).toString();
            myselected2=Integer.toString(position);

            actOnSelected();

        }
//
        }



    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void actOnSelected(){

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }





    public void populateSpinner1(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),where);

            mySpinner1.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void populateSpinner2(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),what);

            mySpinner2.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void LogindisplayDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message.toLowerCase());
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
}

