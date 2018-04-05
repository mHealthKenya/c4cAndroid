package mhealth.c4c;

/**
 * Created by kennedy on 9/13/17.
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import mhealth.c4c.dialogs.Dialogs;


public class Report extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapterWhere;
    private ArrayAdapter<String> arrayAdapterWhat;

    private EditText hours,otherWhereE,otherWhatE;
    Dialogs sweetdialog;

    private Button btn_submit;
    MaterialBetterSpinner SpinnerWhat,SpinnerWhere;
    String selectedWhere,selectedWhat,otherWhere,otherWhat;

    String[] SPINNERLISTWHERE = {"Medical Ward", "Surgical Ward", "Theater", "Maternity", "Dental Clinic", "OP/MCH", "laundry", "Laboratory","Other"};
    String[] SPINNERLISTWHAT = {"Needle Stick", "Cuts", "Splash on mucosa", "Non-intact Skin", "Bite", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        setToolbar();
        initialise();
        setSpinnerAdapters();
        setSpinnerWhereListener();
        setSpinnerWhatListener();
        submit();






    }


    public void submit(){

        try{

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    selectedWhere,selectedWhat,otherWhere,otherWhat
                    String myhour= hours.getText().toString();
                    String myotherwhere="";
                    String myotherwhat="";

                    String where="";
                    String what="";
                    String hrs="";

//                    Toast.makeText(Report.this, "submitting", Toast.LENGTH_SHORT).show();
                    if(selectedWhere.contentEquals("")){
                        sweetdialog.showErrorDialogReportExposure("where the exposure occured is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "where the exposure occured is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedWhat.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The nature of exposure is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedWhere.equalsIgnoreCase("Other")&& otherWhereE.getText().toString().isEmpty()){


                        sweetdialog.showErrorDialogReportExposure("other of where the exposure occured is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Other for where exposure occured is required", Toast.LENGTH_SHORT).show();


                    }
                    else if(selectedWhat.equalsIgnoreCase("Other") && otherWhatE.getText().toString().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("other for nature of exposure occured is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }
                    else if(myhour.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("Hours after exposure is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Hours after exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String otherWhereS="";
                        String otherWhatS="";
                        if(selectedWhere.equalsIgnoreCase("Other")){
                            otherWhereS=otherWhereE.getText().toString();
                            where=otherWhereS;
                        }
                        else{
                            where=selectedWhere;
                        }
                        if(selectedWhat.equalsIgnoreCase("Other")){
                            otherWhatS=otherWhatE.getText().toString();
                            what=otherWhatS;
                        }
                        else{
                            what=selectedWhat;
                        }
                        System.out.println("***************************************************");
                        System.out.println("where is:"+selectedWhere+"\n"+"what is: "+selectedWhat+"\n"+"where other: "+otherWhereS+"\n"+"what other: "+otherWhatS);
                        System.out.println("***************************************************");

//                        Toast.makeText(Report.this, "submitting exposure", Toast.LENGTH_SHORT).show();

                        String Message = "Rep*"+where+"*"+what+"*"+myhour;

                        SmsManager sm = SmsManager.getDefault();
                        sm.sendTextMessage("40145", null, Message, null, null);
                        clearFields();

//                        SignupsuccessDialog("");

                        sweetdialog.showSuccessDialogReportExposure("SUCCESS SUBMITTING EXPOSURE REPORT","Success");


                    }


                }
            });

        }
        catch(Exception e){


        }
    }

    public void clearFields(){

        try{

            SpinnerWhat.setText("");
            SpinnerWhere.setText("");
            hours.setText("");

        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            sweetdialog=new Dialogs(Report.this);

            hours = (EditText) findViewById(R.id.hours);
            btn_submit = (Button) findViewById(R.id.btn_submit);

            arrayAdapterWhere = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, SPINNERLISTWHERE);

            SpinnerWhere = (MaterialBetterSpinner)
                    findViewById(R.id.exposure);

            arrayAdapterWhat = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, SPINNERLISTWHAT);

            SpinnerWhat = (MaterialBetterSpinner)
                    findViewById(R.id.cause);
            otherWhat="";
            otherWhere="";
            selectedWhere="";
            selectedWhat="";


        }
        catch(Exception e){


        }
    }

    public void setSpinnerWhereListener(){

        try{


            SpinnerWhere.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedWhere=SpinnerWhere.getText().toString();
//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();
                    displayWhereOther();

                }
            });

        }
        catch(Exception e){


        }
    }



    public void displayWhereOther(){

        try{

            if(selectedWhere.equalsIgnoreCase("Other")){

                otherWhereE=(EditText) findViewById(R.id.whereother);
                otherWhereE.setVisibility(View.VISIBLE);
                otherWhere=otherWhereE.getText().toString();
            }
            else{
                otherWhereE.setVisibility(View.GONE);
                otherWhere="";

            }
        }
        catch(Exception e){


        }
    }

    public void displayWhatOther(){

        try{

            if(selectedWhat.equalsIgnoreCase("Other")){

                otherWhatE=(EditText) findViewById(R.id.whatother);
                otherWhatE.setVisibility(View.VISIBLE);
                otherWhat=otherWhatE.getText().toString();

            }
            else{

                otherWhatE.setVisibility(View.GONE);
                otherWhat="";


            }
        }
        catch(Exception e){


        }
    }

    public void setSpinnerWhatListener(){

        try{


            SpinnerWhat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedWhat=SpinnerWhat.getText().toString();
//                    Toast.makeText(Report.this, "selected "+selectedWhat, Toast.LENGTH_SHORT).show();

                    displayWhatOther();
                }
            });

        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerWhere.setAdapter(arrayAdapterWhere);

            SpinnerWhat.setAdapter(arrayAdapterWhat);


        }
        catch(Exception e){


        }
    }

    public void setToolbar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

                    dialog.dismiss();


                }
            });





            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }



}
