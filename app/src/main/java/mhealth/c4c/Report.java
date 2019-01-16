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

import java.util.ArrayList;

import mhealth.c4c.AccessServer.AccessServer;
import mhealth.c4c.Checkinternet.CheckInternet;
import mhealth.c4c.DateTimePicker.DateTimePicker;
import mhealth.c4c.SSLTrustCertificate.SSLTrust;
import mhealth.c4c.config.Config;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;


public class Report extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapterWhere,arrayAdapterWhat,arrayAdapterDevice,arrayAdapterSafety,arrayAdapterAuto,arrayAdapterDeep,arrayAdapterPurpose,arrayAdapterHiv,arrayAdapterHbv,arrayAdapterWhen,arrayAdapterPepInit,arrayAdapterExposureResult;

    private EditText datetimeofexposureE,otherWhereE,otherWhatE,otherDeviceE,otherSafetyE,otherAutoE,otherPurposeE,otherWhenE,dateTimeOfPepInitE,otherExposureResult,numberofexposuresE;
    Dialogs sweetdialog;
    DateTimePicker dtp;

    private Button btn_submit;
    MaterialBetterSpinner SpinnerWhat,SpinnerWhere,SpinnerDevice,SpinnerSafety,SpinnerExposureDeep,SpinnerPurpose,SpinnerWhen,SpinnerHiv,SpinnerHbv,SpinnerPepInit,SpinnerExposureResult;
    String selectedWhere,selectedWhat,otherWhere,otherWhat,selecteddevice,otherdevice,selectedSafety,othersafety,selectedautodisable,otherautodisable,selectedExposuredeep,selectedPurpose,otherpurpose,selectedWhen,otherwhen,selectedHivstatus,selectedHbvstatus,selectedPepinitS,selectedExposureResult,otherExposureResultS,datetimeofexposureS,datetimeofpepinitS,numberOfExposuresS;

    LinearLayout llHidden,llhiddensafetydesign;
    CheckInternet chkInternet;
    AccessServer accessServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        setToolbar();
        initialise();

        SSLTrust.nuke();

        setHoursListener();

        setDateTimeOfPepInitListener();
        setSpinnerAdapters();
        setSpinnerWhereListener();
        setSpinnerPepInitListener();
        setSpinnerWhatListener();

        setSpinnerPurposeListener();
        setSpinnerWhenListener();
        setSpinnerHivstatusListener();
        setSpinnerHbvstatusListener();

        submit();






    }



    public void setHoursListener(){

        try{

            datetimeofexposureE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dtp.setDateTimePicker(datetimeofexposureE);

                }
            });
        }
        catch(Exception e){


        }
    }

    public void setDateTimeOfPepInitListener(){

        try{

            dateTimeOfPepInitE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dtp.setDateTimePicker(dateTimeOfPepInitE);

                }
            });
        }
        catch(Exception e){


        }
    }


    public void submit(){

        try{

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    datetimeofexposureS= datetimeofexposureE.getText().toString();
                    numberOfExposuresS=numberofexposuresE.getText().toString();



                    String where="";
                    String what="";
                    String purpose="";
                    String when="";
                    String HivStatus="";
                    String HbvStatus="";
                    String numberofexposures=numberOfExposuresS;
                    String pepinit="";
                    String dateofexposure=datetimeofexposureS;

                    String otherWhereS="";
                    String otherWhatS="";
                    String otherDeviceS="";
                    String otherPurposeS="";
                    String otherWhenS="";


                    String device="-1";
                    String deviceSafety="-1";
                    String deviceAuto="-1";
                    String deep="-1";
                    String dateofpepinit="-1";
                    String exposureresult="-1";





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

                     else if((selectedWhat.equalsIgnoreCase("Splash on Mucosa")||selectedWhat.equalsIgnoreCase("Non-intact Skin")) && selectedExposureResult.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Exposure result is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }

                    else if((selectedWhat.equalsIgnoreCase("Splash on Mucosa")||selectedWhat.equalsIgnoreCase("Non-intact Skin")) && selectedExposureResult.contentEquals("Other")&& otherExposureResult.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Exposure result other is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }

                    else if((selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedSafety.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify the safety","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }
                    else if((selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedSafety.contentEquals("Other") && otherSafetyE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify other for safety","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }

                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedExposuredeep.isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Specify if it was deep","Exposure Report Error");

//                        Toast.makeText(Report.this, "other for nature of exposure is required", Toast.LENGTH_SHORT).show();


                    }


                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick"))&&selectedPurpose.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("The purpose is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick")) && selectedPurpose.equalsIgnoreCase("Other")&& otherPurposeE.getText().toString().isEmpty()){


                        sweetdialog.showErrorDialogReportExposure("other of the purpose is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Other for where exposure occured is required", Toast.LENGTH_SHORT).show();


                    }


                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick"))&&selectedWhen.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("how the injury occured is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else if((selectedWhat.equalsIgnoreCase("Cuts")||selectedWhat.equalsIgnoreCase("Needle Stick"))&&selectedWhen.equalsIgnoreCase("Other")&& otherWhenE.getText().toString().isEmpty()){


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

                    else if(numberofexposuresE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Number of exposures is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }

                    else if(selectedPepinitS.trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("Was pep initiated is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }

                    else if(dateTimeOfPepInitE.getText().toString().trim().isEmpty()){

                        sweetdialog.showErrorDialogReportExposure("date of pep initiation is required","Exposure Report Error");
//                        Toast.makeText(Report.this, "the nature of exposure is required", Toast.LENGTH_SHORT).show();
                    }


                    else if(datetimeofexposureS.contentEquals("")){

                        sweetdialog.showErrorDialogReportExposure("Date of exposure is required","Exposure Report Error");

//                        Toast.makeText(Report.this, "Hours after exposure is required", Toast.LENGTH_SHORT).show();
                    }
                    else{



                        HbvStatus=selectedHbvstatus;
                        HivStatus=selectedHivstatus;
                        deviceSafety=selectedSafety;
                        deviceAuto=selectedautodisable;
                        deep=selectedExposuredeep;
                        pepinit=selectedPepinitS;


                        if(pepinit.trim().equalsIgnoreCase("Yes")){

                            dateofpepinit=dateTimeOfPepInitE.getText().toString();
                        }


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

                        if(selectedPepinitS.equalsIgnoreCase("Yes")){
                            dateofpepinit=dateTimeOfPepInitE.getText().toString();
                        }
                        else{
                            dateofpepinit="-1";
                        }

                        if((!selectedWhat.equalsIgnoreCase("Cuts")||!selectedWhat.equalsIgnoreCase("Needle Stick")||!selectedWhat.equalsIgnoreCase("Other"))&&selectedExposureResult.equalsIgnoreCase("Other")){

                           exposureresult=otherExposureResult.getText().toString();

                        }
                        else{

                            exposureresult=SpinnerExposureResult.getText().toString();
                        }

                        if(selectedPurpose.equalsIgnoreCase("Other")){
                            otherPurposeS=otherPurposeE.getText().toString();
                            purpose=otherPurposeS;
                        }
                        else{
                            purpose=selectedPurpose;
                        }

                        if(selectedSafety.equalsIgnoreCase("Other")){
                            othersafety=otherSafetyE.getText().toString();
                            selectedSafety=othersafety;
                        }
                        else{

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
                            what=otherWhatS;
                        }
                        else{

                            what=selectedWhat;
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

                        if(chkInternet.isInternetAvailable()){

                            accessServer.reportExposure(where,what,purpose,when,HivStatus,numberofexposures,pepinit,dateofexposure,device,deviceSafety,deep,dateofpepinit,exposureresult,"0713559850");
                            clearFields();

                        }
                        else{

                            String Message="Rep*"+ Base64Encoder.encryptString(where+"*"+what+"*"+purpose+"*"+when+"*"+HivStatus+"*"+HbvStatus+"*"+numberofexposures+"*"+pepinit+"*"+dateofexposure+"*"+device+"*"+deviceSafety+"*"+deep+"*"+dateofpepinit+"*"+exposureresult);
//                        String Message = "Rep*"+where+"*"+nature+"*"+myhour;

                            SmsManager sm = SmsManager.getDefault();
                            ArrayList<String> parts = sm.divideMessage(Message);

                            sm.sendMultipartTextMessage(Config.shortcode, null, parts, null, null);

                            clearFields();


                        }




//                        SignupsuccessDialog("");

                        sweetdialog.showSuccessDialogReportExposure("Successfully submitted","");


                    }


                }
            });

        }
        catch(Exception e){


        }
    }

    public void clearFields(){

        try{

            datetimeofexposureE.setText("");
            SpinnerWhat.setText("");
            SpinnerWhere.setText("");
            SpinnerWhen.setText("");
            SpinnerHbv.setText("");
            SpinnerHiv.setText("");
            SpinnerPurpose.setText("");
            SpinnerExposureDeep.setText("");
            SpinnerPepInit.setText("");
            numberofexposuresE.setText("");
            dateTimeOfPepInitE.setText("");
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
            chkInternet=new CheckInternet(Report.this);
            accessServer=new AccessServer(Report.this);
            dateTimeOfPepInitE=(EditText) findViewById(R.id.datetimeofpepinitiation);
            numberofexposuresE=(EditText) findViewById(R.id.numberOfExposures);
            dtp=new DateTimePicker(Report.this);
            sweetdialog=new Dialogs(Report.this);
            llHidden=(LinearLayout) findViewById(R.id.llhidden);
            llhiddensafetydesign=(LinearLayout) findViewById(R.id.llsafetydesigned);

            datetimeofexposureE = (EditText) findViewById(R.id.hours);
            btn_submit = (Button) findViewById(R.id.btn_submit);

            arrayAdapterWhere = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTWHERE);



            arrayAdapterDeep = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTDEEPALGORITHM);

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

            arrayAdapterPepInit = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTPEPINIT);

            arrayAdapterExposureResult = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTEXPOSURERESULT);


            arrayAdapterWhat = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTWHAT);


            SpinnerWhere = (MaterialBetterSpinner)
                    findViewById(R.id.exposure);

            SpinnerPepInit = (MaterialBetterSpinner)
                    findViewById(R.id.waspepinitiated);

            SpinnerWhat = (MaterialBetterSpinner)
                    findViewById(R.id.cause);

            SpinnerDevice = (MaterialBetterSpinner)
                    findViewById(R.id.device);

            SpinnerSafety = (MaterialBetterSpinner)
                    findViewById(R.id.devicesafety);



            SpinnerExposureDeep = (MaterialBetterSpinner)findViewById(R.id.exposuredeep);

            SpinnerPurpose = (MaterialBetterSpinner)
                    findViewById(R.id.purpose);

            SpinnerWhen = (MaterialBetterSpinner)
                    findViewById(R.id.when);

            SpinnerHiv = (MaterialBetterSpinner)
                    findViewById(R.id.hivstatus);

            SpinnerHbv = (MaterialBetterSpinner)
                    findViewById(R.id.hbvstatus);

            SpinnerExposureResult = (MaterialBetterSpinner)
                    findViewById(R.id.exposureresult);

            otherWhat="";
            otherWhere="";
            otherdevice="";
            othersafety="";
            otherautodisable="";
            datetimeofexposureS="";
            datetimeofpepinitS="";
            numberOfExposuresS="";

            otherpurpose="";
            otherwhen="";
            otherExposureResultS="";

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
            selectedPepinitS="";
            selectedExposureResult="";


        }
        catch(Exception e){


        }
    }

    public void setSpinnerExposureResult(){

        try{


            SpinnerExposureResult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedExposureResult=SpinnerExposureResult.getText().toString();
                    displayExposureResultOther();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void setSpinnerPepInitListener(){

        try{


            SpinnerPepInit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedPepinitS=SpinnerPepInit.getText().toString();
                    if(selectedPepinitS.equalsIgnoreCase("yes")){

//                        dateTimeOfPepInitE.setVisibility(View.VISIBLE);



                    }
                    else{
//                        dateTimeOfPepInitE.setVisibility(View.GONE);
//                        dateTimeOfPepInitE.setHint("");
//                        dateTimeOfPepInitE.setText("");


                    }


                }
            });

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


    public void displayExposureResultOther(){

        try{
            otherExposureResult=(EditText) findViewById(R.id.exposureresultother);

            if(selectedExposureResult.equalsIgnoreCase("Other")){

                Toast.makeText(this, "other selected ", Toast.LENGTH_SHORT).show();
                otherExposureResult.setVisibility(View.VISIBLE);
                otherExposureResultS=otherExposureResult.getText().toString();
            }
            else{
                otherExposureResult.setText("");
                otherExposureResult.setVisibility(View.GONE);
                otherExposureResultS="";


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
                    try{

                        selectedExposuredeep=SpinnerExposureDeep.getText().toString();

                    }
                    catch(Exception e){
                        Toast.makeText(Report.this, "exception here "+e, Toast.LENGTH_SHORT).show();

                    }




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
                        boolean isNeedleStick;
                        if(selectedWhat.contentEquals("Cuts")){
                            isNeedleStick=false;
                        }
                        else if (selectedWhat.contentEquals("Needle Stick")){

                            isNeedleStick=true;
                        }
                        else{
                            isNeedleStick=false;
                        }
                        llHidden.setVisibility(View.VISIBLE);
                        setHiddenSpinnerAdapters(isNeedleStick);
                        setSpinnerDeviceListener();
                        setSpinnerSafetyListener();
                        setSpinnerDeepListener();
                        SpinnerExposureResult.setVisibility(View.GONE);
//                        otherExposureResult.setText("");
                        SpinnerExposureResult.setText("");

                    }

                    else if(selectedWhat.equalsIgnoreCase("Bite")){
                        boolean isNeedleStick;

                        isNeedleStick=false;

                        llHidden.setVisibility(View.GONE);
                        setHiddenSpinnerAdapters(isNeedleStick);
                        setSpinnerDeviceListener();
                        setSpinnerSafetyListener();
                        setSpinnerDeepListener();
                        SpinnerExposureResult.setVisibility(View.GONE);
//                        otherExposureResult.setText("");
                        SpinnerExposureResult.setText("");

                    }
                    else{
                        SpinnerExposureResult.setVisibility(View.VISIBLE);
                        setSpinnerExposureResultAdapter();
                        setSpinnerExposureResult();


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
            SpinnerPepInit.setAdapter(arrayAdapterPepInit);
            SpinnerWhat.setAdapter(arrayAdapterWhat);
            SpinnerPurpose.setAdapter(arrayAdapterPurpose);
            SpinnerWhen.setAdapter(arrayAdapterWhen);
            SpinnerHiv.setAdapter(arrayAdapterHiv);
            SpinnerHbv.setAdapter(arrayAdapterHbv);





        }
        catch(Exception e){


        }
    }



    public void setSpinnerExposureResultAdapter(){

        try{

            SpinnerExposureResult.setAdapter(arrayAdapterExposureResult);

        }
        catch(Exception e){


        }
    }


    public void setHiddenSpinnerAdapters(Boolean isNeedleStick){

        try{
            if(isNeedleStick){

                llhiddensafetydesign.setVisibility(View.VISIBLE);
                SpinnerSafety.setAdapter(arrayAdapterSafety);
            }
            else{
                llhiddensafetydesign.setVisibility(View.GONE);
                SpinnerSafety.setText("");
                selectedSafety="";
            }

            SpinnerDevice.setAdapter(arrayAdapterDevice);



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
            getSupportActionBar().setTitle("Report Exposure");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


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
