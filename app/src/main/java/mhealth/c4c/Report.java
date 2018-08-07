package mhealth.c4c;

/**
 * Created by kennedy on 9/13/17.
 */


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;


public class Report extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapterWhere,arrayAdapterWhat,arrayAdapterDevice,arrayAdapterSafety,arrayAdapterAuto,arrayAdapterDeep,arrayAdapterPurpose,arrayAdapterHiv,arrayAdapterHbv,arrayAdapterWhen;

    private EditText hoursE,otherWhereE,otherWhatE,otherDeviceE,otherSafetyE,otherAutoE,otherPurposeE,otherWhenE;
    Dialogs sweetdialog;

    private Button btn_submit;
    MaterialBetterSpinner SpinnerWhat,SpinnerWhere,SpinnerDevice,SpinnerSafety,SpinnerAutodisable,SpinnerExposureDeep,SpinnerPurpose,SpinnerWhen,SpinnerHiv,SpinnerHbv;
    String selectedWhere,selectedWhat,otherWhere,otherWhat,selecteddevice,otherdevice,selectedSafety,othersafety,selectedautodisable,otherautodisable,selectedExposuredeep,selectedPurpose,otherpurpose,selectedWhen,otherwhen,selectedHivstatus,selectedHbvstatus;

    LinearLayout llHidden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        setToolbar();
        initialise();
        setSpinnerAdapters();
        setSpinnerWhereListener();
        setSpinnerWhatListener();

        setSpinnerPurposeListener();
        setSpinnerWhenListener();
        setSpinnerHivstatusListener();
        setSpinnerHbvstatusListener();

        submit();






    }


    public void submit(){

        try{

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String myhour= hoursE.getText().toString();

                    String where="";
                    String nature="";

                    String device="-1";
                    String deviceSafety="-1";
                    String deviceAuto="-1";
                    String deep="-1";
                    String purpose="";
                    String when="";
                    String HivStatus="";
                    String HbvStatus="";



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
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selecteddevice.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify the device","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selecteddevice.contentEquals("Other") && otherDeviceE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify other for device","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }


                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedSafety.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify the safety","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedSafety.contentEquals("Other") && otherSafetyE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify other for safety","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }



                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedautodisable.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify the device auto disable","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedautodisable.contentEquals("Other") && otherAutoE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify other for auto disable","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }





                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedExposuredeep.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify if it was deep","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }



                    else if(selectedPurpose.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The purpose is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedPurpose.equalsIgnoreCase("Other")&& otherPurposeE.getText().toString().isEmpty()){


                        sweetdialog.showErrorDialogReportExposure("other of the purpose is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Other for where exposure occured is required", Toast.LENGTH_SHORT).show();


                    }


                    else if(selectedWhen.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The when is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedWhen.equalsIgnoreCase("Other")&& otherWhenE.getText().toString().isEmpty()){


                        sweetdialog.showErrorDialogReportExposure("other of when is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Other for where exposure occured is required", Toast.LENGTH_SHORT).show();


                    }


                    else if(selectedHivstatus.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The HIV status is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }


                    else if(selectedHbvstatus.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The HBV status is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }


                    else if(myhour.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("Hours after exposure is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Hours after exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String otherWhereS="";
                        String otherWhatS="";
                        String otherDeviceS="";

                        String otherPurposeS="";
                        String otherWhenS="";
                        HbvStatus=selectedHbvstatus;
                        HivStatus=selectedHivstatus;
                        deviceSafety=selectedSafety;
                        deviceAuto=selectedautodisable;
                        deep=selectedExposuredeep;

                        if(selectedWhere.equalsIgnoreCase("Other")){
                            otherWhereS=otherWhereE.getText().toString();
                            where=otherWhereS;
                        }
                        else{
                            where=selectedWhere;
                        }

                        if(selecteddevice.equalsIgnoreCase("Other")){
                            otherDeviceS=otherDeviceE.getText().toString();
                            device=otherDeviceS;
                        }
                        else{
                            device=selecteddevice;
                        }

                        if(selectedPurpose.equalsIgnoreCase("Other")){
                            otherPurposeS=otherPurposeE.getText().toString();
                            purpose=otherPurposeS;
                        }
                        else{
                            purpose=selectedPurpose;
                        }

                        if(selectedWhen.equalsIgnoreCase("Other")){
                            otherWhenS=otherWhenE.getText().toString();
                            when=otherWhenS;
                        }
                        else{
                            when=selectedWhen;
                        }

                        if(selectedWhat.equalsIgnoreCase("Other")){
                            otherWhatS=otherWhatE.getText().toString();
                            nature=otherWhatS;
                        }
                        else{
                            nature=selectedWhat;
                        }
                        System.out.println("***************************************************");
                        System.out.println("where is:"+selectedWhere+"\n"+"what is: "+selectedWhat+"\n"+"where other: "+otherWhereS+"\n"+"what other: "+otherWhatS);
                        System.out.println("***************************************************");
                        if(selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")){


                        }
                        else{

                            device="-1";
                            deviceSafety="-1";
                            deviceAuto="-1";
                            deep="-1";

                        }
                        String Message="Rep*"+ Base64Encoder.encryptString(where+"*"+nature+"*"+device+"*"+deviceSafety+"*"+deviceAuto+"*"+deep+"*"+purpose+"*"+when+"*"+HivStatus+"*"+HbvStatus+"*"+myhour);
//                        String Message = "Rep*"+where+"*"+nature+"*"+myhour;

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
            hoursE.setText("");
            SpinnerWhat.setText("");
            SpinnerWhere.setText("");
            SpinnerWhen.setText("");
            SpinnerHbv.setText("");
            SpinnerHiv.setText("");
            SpinnerPurpose.setText("");
            SpinnerExposureDeep.setText("");
            SpinnerAutodisable.setText("");
            SpinnerSafety.setText("");
            SpinnerDevice.setText("");
            otherWhenE.setText("");
            otherPurposeE.setText("");
            otherAutoE.setText("");
            otherSafetyE.setText("");
            otherDeviceE.setText("");



        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            sweetdialog=new Dialogs(Report.this);
            llHidden=(LinearLayout) findViewById(R.id.llhidden);

            hoursE = (EditText) findViewById(R.id.hours);
            btn_submit = (Button) findViewById(R.id.btn_submit);

            arrayAdapterWhere = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTWHERE);

            arrayAdapterAuto= new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTAUTODISABLE);

            arrayAdapterDeep = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTDEEP);

            arrayAdapterDevice = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTDEVICE);

            arrayAdapterHbv = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTHBVSTATUS);

            arrayAdapterHiv = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTHIVSTATUS);

            arrayAdapterPurpose = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTPURPOSE);

            arrayAdapterSafety = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSAFETY);

            arrayAdapterWhen = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTWHEN);

            arrayAdapterWhat = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTWHAT);


            SpinnerWhere = (MaterialBetterSpinner)
                    findViewById(R.id.exposure);

            SpinnerWhat = (MaterialBetterSpinner)
                    findViewById(R.id.cause);

            SpinnerDevice = (MaterialBetterSpinner)
                    findViewById(R.id.device);

            SpinnerSafety = (MaterialBetterSpinner)
                    findViewById(R.id.devicesafety);

            SpinnerAutodisable = (MaterialBetterSpinner)
                    findViewById(R.id.deviceautodisable);

            SpinnerExposureDeep = (MaterialBetterSpinner)
                    findViewById(R.id.exposuredeep);

            SpinnerPurpose = (MaterialBetterSpinner)
                    findViewById(R.id.purpose);

            SpinnerWhen = (MaterialBetterSpinner)
                    findViewById(R.id.when);

            SpinnerHiv = (MaterialBetterSpinner)
                    findViewById(R.id.hivstatus);

            SpinnerHbv = (MaterialBetterSpinner)
                    findViewById(R.id.hbvstatus);

            otherWhat="";
            otherWhere="";
            otherdevice="";
            othersafety="";
            otherautodisable="";

            otherpurpose="";
            otherwhen="";

            selectedWhere="";
            selectedWhat="";
            selecteddevice="";
            selectedSafety="";
            selectedautodisable="";
            selectedExposuredeep="";
            selectedPurpose="";
            selectedWhen="";
            selectedHivstatus="";
            selectedHbvstatus="";


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



    public void setSpinnerDeviceListener(){

        try{


            SpinnerDevice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selecteddevice=SpinnerDevice.getText().toString();
                    displayDeviceOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void displayDeviceOther(){

        try{

            if(selecteddevice.equalsIgnoreCase("Other")){

                otherDeviceE=(EditText) findViewById(R.id.deviceother);
                otherDeviceE.setVisibility(View.VISIBLE);
                otherdevice=otherDeviceE.getText().toString();
            }
            else{
                otherDeviceE.setVisibility(View.GONE);
                otherdevice="";

            }
        }
        catch(Exception e){


        }
    }



    public void setSpinnerSafetyListener(){

        try{


            SpinnerSafety.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedSafety=SpinnerSafety.getText().toString();
                    displaySafetyOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void displaySafetyOther(){

        try{

            if(selectedSafety.equalsIgnoreCase("Other")){

                otherSafetyE=(EditText) findViewById(R.id.devicesafetyother);
                otherSafetyE.setVisibility(View.VISIBLE);
                othersafety=otherSafetyE.getText().toString();
            }
            else{
                otherSafetyE.setVisibility(View.GONE);
                othersafety="";

            }
        }
        catch(Exception e){


        }
    }



    public void setSpinnerAutoListener(){

        try{


            SpinnerAutodisable.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedautodisable=SpinnerAutodisable.getText().toString();
                    displayAutoOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void displayAutoOther(){

        try{

            if(selectedautodisable.equalsIgnoreCase("Other")){

                otherAutoE=(EditText) findViewById(R.id.deviceautodisableother);
                otherAutoE.setVisibility(View.VISIBLE);
                otherautodisable=otherAutoE.getText().toString();
            }
            else{
                otherAutoE.setVisibility(View.GONE);
                otherautodisable="";

            }
        }
        catch(Exception e){


        }
    }



    public void setSpinnerDeepListener(){

        try{


            SpinnerExposureDeep.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedExposuredeep=SpinnerExposureDeep.getText().toString();


                }
            });

        }
        catch(Exception e){


        }
    }





    public void setSpinnerPurposeListener(){

        try{


            SpinnerPurpose.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedPurpose=SpinnerPurpose.getText().toString();
                    displayPurposeOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void displayPurposeOther(){

        try{

            if(selectedPurpose.equalsIgnoreCase("Other")){

                otherPurposeE=(EditText) findViewById(R.id.purposeother);
                otherPurposeE.setVisibility(View.VISIBLE);
                otherpurpose=otherPurposeE.getText().toString();
            }
            else{
                otherPurposeE.setVisibility(View.GONE);
                otherpurpose="";

            }
        }
        catch(Exception e){


        }
    }



    public void setSpinnerWhenListener(){

        try{


            SpinnerWhen.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedWhen=SpinnerWhen.getText().toString();
                    displayWhenOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void displayWhenOther(){

        try{

            if(selectedWhen.equalsIgnoreCase("Other")){

                otherWhenE=(EditText) findViewById(R.id.whenother);
                otherWhenE.setVisibility(View.VISIBLE);
                otherwhen=otherWhenE.getText().toString();
            }
            else{
                otherWhenE.setVisibility(View.GONE);
                otherwhen="";

            }
        }
        catch(Exception e){


        }
    }


    public void setSpinnerHivstatusListener(){

        try{


            SpinnerHiv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedHivstatus=SpinnerHiv.getText().toString();


                }
            });

        }
        catch(Exception e){


        }
    }


    public void setSpinnerHbvstatusListener(){

        try{


            SpinnerHbv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedHbvstatus=SpinnerHbv.getText().toString();


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

                    if(selectedWhat.contentEquals("Cuts")|| selectedWhat.contentEquals("Needle Stick")){

                        llHidden.setVisibility(View.VISIBLE);
                        setHiddenSpinnerAdapters();
                        setSpinnerDeviceListener();
                        setSpinnerSafetyListener();
                        setSpinnerAutoListener();
                        setSpinnerDeepListener();
                    }
                    else{

                        llHidden.setVisibility(View.GONE);

                    }
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
            SpinnerPurpose.setAdapter(arrayAdapterPurpose);
            SpinnerWhen.setAdapter(arrayAdapterWhen);
            SpinnerHiv.setAdapter(arrayAdapterHiv);
            SpinnerHbv.setAdapter(arrayAdapterHbv);





        }
        catch(Exception e){


        }
    }


    public void setHiddenSpinnerAdapters(){

        try{
            SpinnerDevice.setAdapter(arrayAdapterDevice);

            SpinnerSafety.setAdapter(arrayAdapterSafety);
            SpinnerAutodisable.setAdapter(arrayAdapterAuto);
            SpinnerExposureDeep.setAdapter(arrayAdapterDeep);
//            SpinnerDevice,SpinnerSafety,SpinnerAutodisable,SpinnerExposureDeep,SpinnerPurpose,SpinnerWhen,SpinnerHiv,SpinnerHbv;




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
