package mhealth.c4c;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import mhealth.c4c.AlarmReceiver.AlarmReceiver;
import mhealth.c4c.Registrationtable.Regdetails;
import mhealth.c4c.Tables.ProfileCompletion;
import mhealth.c4c.checkupstatustable.Status;
import mhealth.c4c.checkupstatustable.UpdateStatusTable;
import mhealth.c4c.completionPercentage.Completion;
import mhealth.c4c.dateCalculator.DateCalculator;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.getImmunisationsaveddata.getAllImmunisationData;
import mhealth.c4c.systemstatetables.Influenza;
import mhealth.c4c.systemstatetables.Measles;
import mhealth.c4c.systemstatetables.Meningoco;
import mhealth.c4c.systemstatetables.Tdap;
import mhealth.c4c.systemstatetables.Varicella;

/**
 * Created by root on 1/22/18.
 */

public class ImmunisationProfile extends AppCompatActivity {
    RadioGroup grpinfluenza,grpvaricella,grptdap,grpmeasles,grpvaricellaqn1,grpinfluenzaqn,grpmeningoco,grptrimester,grptdapbooster;
    RadioButton radiobntinfluenza,radiobtnvaricella,radiobtntdap,radiobtnmeasles,radiobtnvaricellaqn1,radiobtninfluenzaqn,radiobtnmeningoco,radiobtntrimester,radiobtntdapbooster;
    LinearLayout llinfluenza,llvaricella,lltdap,llmeasles,llpreg, llvaricellaqn1L, llvaricellaqn2L,llinfluenzaqnL,llmeningoco,parentLayout,lltrimester,lltdapbooster,lltdapboosterdate;
    EditText measlesdose1E,measlesdose2E,influenzadoseE,varicelladose1E,varicelladose2E,tdapdoseE,meningocodoseE,meningocodose2E,tdapdoseboosterdateE;
    TextView measles2label;
    DatePickerDialog datePickerDialog;
    getAllImmunisationData getAD;
    Completion comp;
    UpdateStatusTable ut;

    DateCalculator dcalc;
    Dialogs sweetdialog;


    boolean childItemsEnabled;

    private PendingIntent pendingIntent;

    String influenzapregnantS,influenzadoseS,varicellahistoryS,varicellafirstdoseS,varicellaseconddoseS,tdapimmunisedS,tdapdoseS,measlesimmunisedS,measlesfirstdoseS,measlesseconddoseS,meningocodoseS;

//edittext string for every level
    String influenzadoseETS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immunisation_profile);
        initialise();
        setToolBar();

        setGenderInfluenza();

        getCheckedRadioInfluenza();
        getCheckedRadioVaricella();
        getCheckedRadioTdap();
        getCheckedRadioMeasles();
        getCheckedRadioVaricellaQn1();
        getCheckedRadioMeningoco();
        getCheckedRadioTrimester();

        InfluenzaDateListener();
        measlesDose1DateListener();
        measlesDose2DateListener();
        varicellaDose1DateListener();
        varicellaDose2DateListener();
        tdapDoseDateListener();
        meningocoDoseDateListener();
        meningocoSecondDoseDateListener();
//        meningocoCheckListener();
//        startAlert();
        displayLinearLayoutPregnant();
        getCheckedRadioInfluenzaQn();

        //functions to retrieve system status
        getInfluenzaData();
        getVaricellaData();
        getTdapData();
        getMeaslesData();
        getMeningocoData();
//        disableAllElements();


        checkForSavedProfile();

//        comp.getInfluenzaPercentage();
//        comp.getVaricellaPercentage();


//        getStatusTableDataInfluenza();
//        getStatusTableDataMeasles();
//        getStatusTableDataMeningoco();


    }

    public void getStatusTableDataInfluenza(){

        try{
            List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","influenza");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    Toast.makeText(this, "status influenza "+myl.get(x).getCategory(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch(Exception e){


        }
    }


    public void getStatusTableDataMeasles(){

        try{
            List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","measles");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    Toast.makeText(this, "status measles "+myl.get(x).getCategory(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch(Exception e){


        }
    }

    public void getStatusTableDataMeningoco(){

        try{
            List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","meningoco");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    Toast.makeText(this, "status meningoco "+myl.get(x).getCategory(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch(Exception e){


        }
    }


    public void setGenderInfluenza(){





        try{

            String genderS="";

            if(isGenderFemale()){

                genderS="Female";

            }
            else{

                genderS="Male";

            }

            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            if(myl.size()>0){

                Influenza inf = Influenza.findById(Influenza.class, 1);
                inf.setGender(genderS);
                inf.save();

            }
            else{

                Influenza chi=new Influenza();
                chi.setGender(genderS);
                chi.save();

            }
        }
        catch(Exception e){

            Toast.makeText(this, "error setting gender "+e, Toast.LENGTH_SHORT).show();
        }
    }
    public void checkForSavedProfile(){

        try{

            List<Influenza> mylinf=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            List<Measles> mylmeas=Measles.findWithQuery(Measles.class,"select * from Measles");
            List<Meningoco> mylmen=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
            List<Tdap> myltda=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
            List<Varicella> mylvar=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
            System.out.println("*****************user profile size*****************");
            System.out.println("influenza "+mylinf.size());
            System.out.println("measles "+mylmeas.size());
            System.out.println("meningoco "+mylmen.size());
            System.out.println("tdap "+myltda.size());
            System.out.println("varicella "+mylvar.size());

            if(mylmeas.size()>0||mylmen.size()>0||myltda.size()>0||mylvar.size()>0){

               enableDisableView(parentLayout,false);
               childItemsEnabled=false;


            }
            else{

                enableDisableView(parentLayout,true);
                childItemsEnabled=true;

            }
        }
        catch(Exception e){

        }
    }


//function to disable all views inside the parent view(layout)

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }


    public void getInfluenzaData(){
        try{


            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){
                    if(myl.get(x).getInfluenzavaccineid()!=null){

                        int mselectedvaccineId=Integer.parseInt(myl.get(x).getInfluenzavaccineid());
                        RadioButton rbVaccine = (RadioButton) findViewById(mselectedvaccineId);
                        rbVaccine.setChecked(true);

                    }

                    if(myl.get(x).getPregnantid()!=null){

                        int mselectedpregnantId=Integer.parseInt(myl.get(x).getPregnantid());

                        RadioButton rbPregnant = (RadioButton) findViewById(mselectedpregnantId);
                        rbPregnant.setChecked(true);

                    }

                    if(myl.get(x).getTrimesterid()!=null){

                        int mselectedtrimesterId=Integer.parseInt(myl.get(x).getTrimesterid());

                        RadioButton rbTri = (RadioButton) findViewById(mselectedtrimesterId);
                        rbTri.setChecked(true);

                    }


                    String mdoseDate=myl.get(x).dosedate;


                    influenzadoseE.setText(mdoseDate);



                }
            }
        }
        catch(Exception e){

            Toast.makeText(this, "error getting influenza data "+e, Toast.LENGTH_SHORT).show();


        }
    }


    public void getTdapData(){
        try{


            List<Tdap> myl=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String mdose1Date=myl.get(x).dosedate;
                    String mdoseboosterDate=myl.get(x).doseboosterdate;


                    if(myl.get(x).getImmunisedid()!=null){

                        int mselectedimmunisedId=Integer.parseInt(myl.get(x).getImmunisedid());

                        RadioButton rbimmunised = (RadioButton) findViewById(mselectedimmunisedId);
                        rbimmunised.setChecked(true);

                    }
                    else{
                        grptdap.clearCheck();

                    }

                    if(myl.get(x).getImmunisedboosterid()!=null){

                        int mselectedboosterid=Integer.parseInt(myl.get(x).getImmunisedboosterid());

                        RadioButton rbimmunisedbooster = (RadioButton) findViewById(mselectedboosterid);
                        rbimmunisedbooster.setChecked(true);

                    }
                    else{
                        grptdapbooster.clearCheck();

                    }

                    tdapdoseE.setText(mdose1Date);
                    tdapdoseboosterdateE.setText(mdoseboosterDate);





                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "error getting tdap "+e, Toast.LENGTH_SHORT).show();


        }
    }



    public void getMeaslesData(){
        try{


            List<Measles> myl= Measles.findWithQuery(Measles.class,"select * from Measles");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String firstdoseDate=myl.get(x).firstdosedate;
                    String seconddoseDate=myl.get(x).seconddosedate;


                    if(myl.get(x).getImmunisedid()!=null){

                        int mselectedimmunisedId=Integer.parseInt(myl.get(x).getImmunisedid());

                        RadioButton rbimmunised = (RadioButton) findViewById(mselectedimmunisedId);
                        rbimmunised.setChecked(true);

                    }
                    else{
                        grpmeasles.clearCheck();

                    }

                    measlesdose1E.setText(firstdoseDate);
                    measlesdose2E.setText(seconddoseDate);





                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "error getting measles "+e, Toast.LENGTH_SHORT).show();


        }
    }



    public void getMeningocoData(){
        try{


            List<Meningoco> myl= Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String firstdoseDate=myl.get(x).firstdosedate;
                    String seconddoseDate=myl.get(x).seconddosedate;


                    if(myl.get(x).getImmunisedid()!=null){

                        int mselectedimmunisedId=Integer.parseInt(myl.get(x).getImmunisedid());

                        RadioButton rbimmunised = (RadioButton) findViewById(mselectedimmunisedId);
                        rbimmunised.setChecked(true);

                    }
                    else{
                        grpmeningoco.clearCheck();

                    }

                    meningocodoseE.setText(firstdoseDate);
                    meningocodose2E.setText(seconddoseDate);





                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "error getting meningoco"+e, Toast.LENGTH_SHORT).show();


        }
    }



    public void getVaricellaData(){
        try{


            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String mdose1Date=myl.get(x).firstdosedate;
                    String mdose2Date=myl.get(x).seconddosedate;


                    if((myl.get(x).getHistoryid()!=null)){
                        if(myl.get(x).getHistoryid().length()>2){

                            int mselectedhistoryId=Integer.parseInt(myl.get(x).getHistoryid());

                            RadioButton rbhistory = (RadioButton) findViewById(mselectedhistoryId);
                            rbhistory.setChecked(true);

                        }



                    }

                    else{
                        grpvaricella.clearCheck();

                    }
                    if((myl.get(x).getVaccineid()!=null)){

                        if(myl.get(x).getVaccineid().length()>2){

                            int mselectedvaccineId=Integer.parseInt(myl.get(x).getVaccineid());

                            RadioButton rbVaccine = (RadioButton) findViewById(mselectedvaccineId);
                            rbVaccine.setChecked(true);

                        }


                    }
                    else{
                        grpvaricellaqn1.clearCheck();


                    }


                    varicelladose1E.setText(mdose1Date);
                    varicelladose2E.setText(mdose2Date);





                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "error getting varicella test "+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void getCheckedRadioMeningoco(){

        try{


            grpmeningoco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnmeningoco = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnmeningoco && checkedId > -1) {

                        String selectedOption=radiobtnmeningoco.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
//                            Toast.makeText(ImmunisationProfile.this, "yes", Toast.LENGTH_SHORT).show();


                            llmeningoco.setVisibility(View.VISIBLE);

                            List<Meningoco> myl=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
                            if(myl.size()>0){

                                Meningoco inf = Meningoco.findById(Meningoco.class, 1);

                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Meningoco inf=new Meningoco();
                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            meningocoDate1InputListener();
                            meningocoDate2InputListener();

                        }
                        else{

                            meningocodoseE.setText("");
                            meningocodose2E.setText("");
//                            Toast.makeText(ImmunisationProfile.this, "No", Toast.LENGTH_SHORT).show();

                            llmeningoco.setVisibility(View.GONE);

                            List<Meningoco> myl=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
                            if(myl.size()>0){

                                Meningoco inf = Meningoco.findById(Meningoco.class, 1);

                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Meningoco inf=new Meningoco();
                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                        }
                    }
                }
            });
        }
        catch(Exception e){

            Toast.makeText(this, "error getting radio cheked meningoco "+e, Toast.LENGTH_SHORT).show();

        }
    }


    public void getCheckedRadioInfluenzaQn(){

        try{


            grpinfluenzaqn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtninfluenzaqn = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtninfluenzaqn && checkedId > -1) {

                        String selectedOption=radiobtninfluenzaqn.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){

                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);
                                inf.setInfluenzavaccinevalue("Yes");
                                inf.setInfluenzavaccineid(Integer.toString(checkedId));
                                inf.save();

                            }

                            else{

                                Influenza chi=new Influenza();
                                chi.setInfluenzavaccineid(Integer.toString(checkedId));
                                chi.setInfluenzavaccinevalue("Yes");
                                chi.save();

                            }

                            llinfluenza.setVisibility(View.VISIBLE);

                            influenzaDateInputListener();

                        }
                        else{

                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);
                                inf.setInfluenzavaccinevalue("No");
                                inf.setInfluenzavaccineid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza chi=new Influenza();
                                chi.setInfluenzavaccineid(Integer.toString(checkedId));
                                chi.setInfluenzavaccinevalue("No");
                                chi.save();

                            }

                            llinfluenza.setVisibility(View.GONE);
                            influenzadoseE.setText("");
                            influenzadoseETS="";
                        }
                    }
                }
            });
        }
        catch(Exception e){

            Toast.makeText(this, "error getting radio influenza qn "+e, Toast.LENGTH_SHORT).show();

        }
    }


    public void displayLinearLayoutPregnant(){

        try{

            if(isGenderFemale()){

                llpreg.setVisibility(View.VISIBLE);


            }
            else{

                llpreg.setVisibility(View.GONE);

                llinfluenzaqnL.setVisibility(View.VISIBLE);

            }


        }
        catch(Exception e){

            Toast.makeText(this, "error displaying linear layout pregnant", Toast.LENGTH_SHORT).show();

        }
    }

    public void displayTrimesterLinearlayout(boolean show){

        try{

            if(show){

                lltrimester.setVisibility(View.VISIBLE);

            }
            else{

                lltrimester.setVisibility(View.GONE);


            }
        }
        catch(Exception e){


        }
    }


    public void myNotification(){

        try{

            String completedValue="0";

            List<ProfileCompletion> uplist=ProfileCompletion.findWithQuery(ProfileCompletion.class,"select * from Profile_completion");
            if(uplist.size()>0){
                for(int x=0;x<uplist.size();x++){

                  completedValue= uplist.get(x).getMyvalue();
                }
            }

            Intent intent = new Intent(this, ImmunisationProfile.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);


            Notification n  = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                n = new Notification.Builder(this)
                        .setContentTitle("C4C PROFILE COMPLETION")
                        .setContentText("your profile is "+completedValue+" % complete tap to complete")
                        .setSmallIcon(R.drawable.c4c_logo)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
//                        .addAction(R.drawable.c4c_logo, "Complete Profile", pIntent)
                        .build();
            }


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
        catch(Exception e){


        }
    }


    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaruserprof);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Immunisation Profile");

        }
        catch(Exception e){


        }
    }

    public void meningocoDate1InputListener(){
        try{

            meningocodoseE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        Measles inf = Measles.findById(Measles.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setFirstdosedate(s.toString());
                        inf.save();

                    }
                    else{

                        meningocodoseE.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                    }




                }
            });
        }
        catch(Exception e){


        }
    }

    public void meningocoDate2InputListener(){
        try{

            meningocodose2E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        Measles inf = Measles.findById(Measles.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setFirstdosedate(s.toString());
                        inf.save();

                    }
                    else{

                        meningocodose2E.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                    }




                }
            });
        }
        catch(Exception e){


        }
    }

    public void measlesDate2InputListener(){
        try{

            measlesdose2E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        Measles inf = Measles.findById(Measles.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setFirstdosedate(s.toString());
                        inf.save();

                    }
                    else{

                        measlesdose2E.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                    }




                }
            });
        }
        catch(Exception e){


        }
    }


    public void measlesDate1InputListener(){
        try{

            measlesdose1E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        Measles inf = Measles.findById(Measles.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setFirstdosedate(s.toString());
                        inf.save();

                    }
                    else{

                        measlesdose1E.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                    }




                }
            });
        }
        catch(Exception e){


        }
    }

    public void influenzaDateInputListener(){
        try{

            influenzadoseE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        Influenza inf = Influenza.findById(Influenza.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setDosedate(s.toString());
                        inf.save();

                    }
                    else{

                        influenzadoseE.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                    }




                }
            });
        }
        catch(Exception e){


        }
    }



    public void tdapDoseBoosterInputListener(){
        try{

            tdapdoseboosterdateE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){

                        Tdap inf = Tdap.findById(Tdap.class, 1);

                        inf.setDoseboosterdate(s.toString());
                        inf.save();

                    }
                    else{
                        tdapdoseboosterdateE.setText("");

                        Toast.makeText(ImmunisationProfile.this, "specify a date less than or equal to today", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
        catch(Exception e){


        }
    }



    public void tdapDoseInputListener(){
        try{

            tdapdoseE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){

                        Tdap inf = Tdap.findById(Tdap.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setDosedate(s.toString());
                        inf.save();

                    }
                    else{
                        tdapdoseE.setText("");

                        Toast.makeText(ImmunisationProfile.this, "specify a date less than or equal to today", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
        catch(Exception e){


        }
    }



    public void varicellaDose2InputListener(){
        try{

            varicelladose2E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){

                        Varicella inf = Varicella.findById(Varicella.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setSeconddosedate(s.toString());
                        inf.save();

                    }
                    else{
                        varicelladose2E.setText("");

                        Toast.makeText(ImmunisationProfile.this, "specify a date less than or equal to today", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
        catch(Exception e){


        }
    }

    public void varicellaDose1InputListener(){
        try{

            varicelladose1E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    if(!dcalc.checkDateDifferenceWithCurrentDate(s.toString())){

                        Varicella inf = Varicella.findById(Varicella.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                        inf.setFirstdosedate(s.toString());
                        inf.save();


                    }
                    else{

                        varicelladose1E.setText("");
                        Toast.makeText(ImmunisationProfile.this, "specify a date less than or equal to today", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
        catch(Exception e){


        }
    }

    public void saveAllStatusVaccine(){

        try{

            ut.updateInfluenza();
            ut.updateMeasles();
            ut.updateMeningoco();
            ut.updateTdap();
            ut.updateVaricella();
        }
        catch(Exception e){


        }
    }

    public void updateProfile(View v){

        try{


            List<Influenza> mylinfluenza=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            List<Varicella> mylivaricella=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
            List<Tdap> myltdap=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
            List<Measles> mylmeasles=Measles.findWithQuery(Measles.class,"select * from Measles");
            List<Meningoco> mylmeningoco=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");


            //set influenza data
            if(mylinfluenza.size()>0){



                Influenza inf = Influenza.findById(Influenza.class, 1);
                if(influenzadoseE!=null){

                    inf.setDosedate(influenzadoseE.getText().toString());
                }
                else{

                    inf.setDosedate("");
                }

                inf.save();
                saveAllStatusVaccine();

            }

            else{

                Influenza chi=new Influenza();

                if(influenzadoseE!=null){

                    chi.setDosedate(influenzadoseE.getText().toString());
                }
                else{

                    chi.setDosedate("");
                }

                chi.save();
                saveAllStatusVaccine();

            }
            //end set influenza data

            //set varicella data
            if(mylivaricella.size()>0){



                Varicella infv = Varicella.findById(Varicella.class, 1);
                if(varicelladose1E!=null){

                     infv.setFirstdosedate(varicelladose1E.getText().toString());

                }
                else{

                    infv.setFirstdosedate("");
                }

                if(varicelladose2E!=null){

                    infv.setSeconddosedate(varicelladose2E.getText().toString());

                }
                else{

                    infv.setSeconddosedate("");
                }

                infv.save();

                saveAllStatusVaccine();

            }

            else{

                Varicella chi=new Varicella();

                if(varicelladose1E!=null){


                    chi.setFirstdosedate(varicelladose1E.getText().toString());
                }
                else{

                    chi.setFirstdosedate("");
                }

                if(varicelladose2E!=null){


                    chi.setSeconddosedate(varicelladose2E.getText().toString());
                }
                else{

                    chi.setSeconddosedate("");
                }

                chi.save();
                saveAllStatusVaccine();

            }
            //end set varicella data


            //set tdap data
            if(myltdap.size()>0){



                Tdap inf = Tdap.findById(Tdap.class, 1);
                if(tdapdoseE!=null){

                    inf.setDosedate(tdapdoseE.getText().toString());
                }
                else{

                    inf.setDosedate("");
                }

                inf.save();
                saveAllStatusVaccine();

            }

            else{

                Tdap chi=new Tdap();

                if(tdapdoseE!=null){

                    chi.setDosedate(tdapdoseE.getText().toString());
                }
                else{

                    chi.setDosedate("");
                }

                chi.save();
                saveAllStatusVaccine();

            }
            //end set influenza data



            //set measles data
            if(mylmeasles.size()>0){



                Measles infv =Measles.findById(Measles.class, 1);
                if(measlesdose1E!=null){

                    infv.setFirstdosedate(measlesdose1E.getText().toString());

                }
                else{

                    infv.setFirstdosedate("");
                }

                if(measlesdose2E!=null){

                    infv.setSeconddosedate(measlesdose2E.getText().toString());

                }
                else{

                    infv.setSeconddosedate("");
                }

                infv.save();

                saveAllStatusVaccine();

            }

            else{

                Measles chi=new Measles();

                if(measlesdose1E!=null){


                    chi.setFirstdosedate(measlesdose1E.getText().toString());
                }
                else{

                    chi.setFirstdosedate("");
                }

                if(measlesdose2E!=null){


                    chi.setSeconddosedate(measlesdose2E.getText().toString());
                }
                else{

                    chi.setSeconddosedate("");
                }

                chi.save();

                saveAllStatusVaccine();

            }
            //end set measles data


            //set meningoco data
            if(mylmeningoco.size()>0){



                Meningoco infv =Meningoco.findById(Meningoco.class, 1);
                if(meningocodoseE!=null){

                    infv.setFirstdosedate(meningocodoseE.getText().toString());

                }
                else{

                    infv.setFirstdosedate("");
                }

                if(meningocodose2E!=null){

                    infv.setSeconddosedate(meningocodose2E.getText().toString());

                }
                else{

                    infv.setSeconddosedate("");
                }

                infv.save();

                saveAllStatusVaccine();

            }

            else{

                Meningoco chi=new Meningoco();

                if(meningocodoseE!=null){


                    chi.setFirstdosedate(meningocodoseE.getText().toString());
                }
                else{

                    chi.setFirstdosedate("");
                }

                if(meningocodose2E!=null){


                    chi.setSeconddosedate(meningocodose2E.getText().toString());
                }
                else{

                    chi.setSeconddosedate("");
                }

                chi.save();
                saveAllStatusVaccine();

            }
            //end set measles data


            getAD=new getAllImmunisationData(ImmunisationProfile.this);
            getAD.displayAllData();

            sweetdialog.showSuccessDialogImmunisation("SUCCESS UPDATING IMMUNISATION PROFILE","success");



        }
        catch(Exception e){

            Toast.makeText(this, "error updating profile, try again"+e, Toast.LENGTH_SHORT).show();

            sweetdialog.showErrorDialogImmunisation("error updating profile, try again","Error updating");
            System.out.println("**********error*****"+e);
            Log.e("MYERROR",e.getMessage());
        }
    }

    public void initialise(){

        try{

            sweetdialog=new Dialogs(ImmunisationProfile.this);
            dcalc=new DateCalculator();
            comp=new Completion(ImmunisationProfile.this);
            ut=new UpdateStatusTable(ImmunisationProfile.this);
            childItemsEnabled=false;
            Intent alarmIntent = new Intent(ImmunisationProfile.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(ImmunisationProfile.this, 0, alarmIntent, 0);

            grpinfluenza =(RadioGroup) findViewById(R.id.radiogrpinfluenza);
            grptrimester =(RadioGroup) findViewById(R.id.radiogrptrimester);
            grptdapbooster =(RadioGroup) findViewById(R.id.radiogrptdapbooster);
            grpinfluenzaqn =(RadioGroup) findViewById(R.id.radiogrpinfluenzaqn);
            grpmeningoco =(RadioGroup) findViewById(R.id.radiogrpmeningocoqn);

            llinfluenza=(LinearLayout) findViewById(R.id.influenzadoselayout);
            llinfluenzaqnL=(LinearLayout) findViewById(R.id.llinfluenzaqn);
            llmeningoco=(LinearLayout) findViewById(R.id.meningocodoselayout);
            llpreg=(LinearLayout) findViewById(R.id.llpregnant);
            lltrimester=(LinearLayout) findViewById(R.id.lltrimester);
            lltdapbooster=(LinearLayout) findViewById(R.id.tdapboosterlayout);
            lltdapboosterdate=(LinearLayout) findViewById(R.id.tdapdoseboosterdatelayout);
            grpvaricella =(RadioGroup) findViewById(R.id.radiogrpvaricella);
            grpvaricellaqn1 =(RadioGroup) findViewById(R.id.radiogrpvaricellaqn1);
            llvaricella=(LinearLayout) findViewById(R.id.varicelladoselayout);
            llvaricellaqn1L =(LinearLayout) findViewById(R.id.llvaricellaqn1);
            llvaricellaqn2L =(LinearLayout) findViewById(R.id.llvaricellaqn2);
            parentLayout =(LinearLayout) findViewById(R.id.immunisationparentlayout);

            grptdap =(RadioGroup) findViewById(R.id.radiogrptdap);
            lltdap=(LinearLayout) findViewById(R.id.tdapdoselayout);

            grpmeasles =(RadioGroup) findViewById(R.id.radiogrpmeasles);

            llmeasles=(LinearLayout) findViewById(R.id.measlesdoselayout);

            influenzapregnantS="";
            influenzadoseS="";
            varicellahistoryS="";
            varicellafirstdoseS="";
            varicellaseconddoseS="";
            tdapimmunisedS="";
            tdapdoseS="";
            measlesimmunisedS="";
            measlesfirstdoseS="";
            measlesseconddoseS="";
            meningocodoseS="";

            influenzadoseETS="";





            measlesdose1E=(EditText) findViewById(R.id.measlesdose1);
            measlesdose2E=(EditText) findViewById(R.id.measlesdose2);
            influenzadoseE=(EditText) findViewById(R.id.influenzadose);
            varicelladose1E=(EditText) findViewById(R.id.variccelladose1);
            varicelladose2E=(EditText) findViewById(R.id.variccelladose2);
            tdapdoseE=(EditText) findViewById(R.id.tdapdose);
            meningocodoseE=(EditText) findViewById(R.id.meningocofirstdose);
            meningocodose2E=(EditText) findViewById(R.id.meningocoseconddose);
            tdapdoseboosterdateE=(EditText) findViewById(R.id.tdapdoseboosterdate);


            measles2label=(TextView) findViewById(R.id.measlessecondoselabel);
//            meningocoChk=(CheckBox) findViewById(R.id.chkmeningoco);


        }
        catch(Exception e){


        }
    }




    public void getCheckedRadioTrimester(){

        try{


            grptrimester.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtntrimester = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtntrimester && checkedId > -1) {

                        String selectedOption=radiobtntrimester.getText().toString();
                        if(selectedOption.equalsIgnoreCase("First")){


                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);

                                inf.setTrimestervalue("First");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza inf=new Influenza();
                                inf.setTrimestervalue("First");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }

                        }

                       else if(selectedOption.equalsIgnoreCase("Second")){


                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);

                                inf.setTrimestervalue("Second");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza inf=new Influenza();
                                inf.setTrimestervalue("Second");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }

                        }
                        else{

                           List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);

                                inf.setTrimestervalue("Third");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza inf=new Influenza();
                                inf.setTrimestervalue("Third");
                                inf.setTrimesterid(Integer.toString(checkedId));
                                inf.save();

                            }

                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }


    public void getCheckedRadioInfluenza(){

        try{


            grpinfluenza.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobntinfluenza = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobntinfluenza && checkedId > -1) {

                        String selectedOption=radiobntinfluenza.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
//                            llinfluenza.setVisibility(View.GONE);
//                            influenzadoseE.setText("");
                            influenzapregnantS="yes";
                            displayTrimesterLinearlayout(true);

//                            Checkedinfluenza.deleteAll(Checkedinfluenza.class);
//                            Checkedinfluenza chi=new Checkedinfluenza(Integer.toString(checkedId));
//                            chi.save();

                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);

                                inf.setPregnantvalue("Yes");
                                inf.setPregnantid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza inf=new Influenza();
                                inf.setPregnantvalue("Yes");
                                inf.setPregnantid(Integer.toString(checkedId));
                                inf.save();

                            }

                        }
                        else{
                            displayTrimesterLinearlayout(false);
//                            llinfluenza.setVisibility(View.VISIBLE);
                            influenzapregnantS="no";


                            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
                            if(myl.size()>0){

                                Influenza inf = Influenza.findById(Influenza.class, 1);

                                inf.setPregnantvalue("No");
                                inf.setPregnantid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Influenza inf=new Influenza();
                                inf.setPregnantvalue("No");
                                inf.setPregnantid(Integer.toString(checkedId));
                                inf.save();

                            }

//                            Checkedinfluenza.deleteAll(Checkedinfluenza.class);
//                            Checkedinfluenza chi=new Checkedinfluenza(Integer.toString(checkedId));
//                            chi.save();


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }


    public void getCheckedRadioVaricellaQn1(){

        try{


            grpvaricellaqn1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnvaricellaqn1 = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnvaricellaqn1 && checkedId > -1) {

                        String selectedOption=radiobtnvaricellaqn1.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
                            grpvaricella.clearCheck();

                            List<Varicella> mylvari=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
                            if(mylvari.size()>0){

                                Varicella inf = Varicella.findById(Varicella.class, 1);

                                inf.setHistoryid("");
                                inf.setHistoryvalue("");
                                inf.save();

                            }


                            llvaricellaqn2L.setVisibility(View.GONE);
                            llvaricella.setVisibility(View.VISIBLE);

//                            varicelladose1E.setText("");
//                            varicelladose2E.setText("");
//                            varicellahistoryS="yes";

                            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
                            if(myl.size()>0){

                                Varicella inf = Varicella.findById(Varicella.class, 1);

                                inf.setVaccinevalue("Yes");
                                inf.setVaccineid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Varicella inf=new Varicella();
                                inf.setVaccinevalue("Yes");
                                inf.setVaccineid(Integer.toString(checkedId));
                                inf.save();

                            }

                            varicellaDose1InputListener();
                            varicellaDose2InputListener();

                        }
                        else{

                            varicelladose1E.setText("");
                            varicelladose2E.setText("");
                            llvaricellaqn2L.setVisibility(View.VISIBLE);
                            llvaricella.setVisibility(View.GONE);


                            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
                            if(myl.size()>0){

                                Varicella inf = Varicella.findById(Varicella.class, 1);

                                inf.setVaccinevalue("No");
                                inf.setVaccineid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Varicella inf=new Varicella();
                                inf.setVaccinevalue("No");
                                inf.setVaccineid(Integer.toString(checkedId));
                                inf.save();

                            }


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }



    public void getCheckedRadioVaricella(){

        try{


            grpvaricella.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnvaricella = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnvaricella && checkedId > -1) {

                        String selectedOption=radiobtnvaricella.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
//                            llvaricella.setVisibility(View.GONE);
//                            varicelladose1E.setText("");
//                            varicelladose2E.setText("");
//                            varicellahistoryS="yes";

                            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
                            if(myl.size()>0){

                                Varicella inf = Varicella.findById(Varicella.class, 1);

                                inf.setHistoryvalue("Yes");
                                inf.setHistoryid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{


                                Varicella inf=new Varicella();
                                inf.setHistoryvalue("Yes");
                                inf.setHistoryid(Integer.toString(checkedId));
                                inf.save();

                            }

                        }
                        else{



                            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
                            if(myl.size()>0){

                                Varicella inf = Varicella.findById(Varicella.class, 1);

                                inf.setHistoryvalue("No");
                                inf.setHistoryid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Varicella inf=new Varicella();
                                inf.setHistoryvalue("NO");
                                inf.setHistoryid(Integer.toString(checkedId));
                                inf.save();

                            }
//                            llvaricella.setVisibility(View.VISIBLE);
//                            varicellahistoryS="no";


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }

    public void getCheckedRadioTdap(){

        try{


            grptdap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtntdap = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtntdap && checkedId > -1) {

                        String selectedOption=radiobtntdap.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
                            lltdap.setVisibility(View.VISIBLE);
                            tdapimmunisedS="yes";

                            List<Tdap> myl=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
                            if(myl.size()>0){

                                Tdap inf = Tdap.findById(Tdap.class, 1);

                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Tdap inf=new Tdap();
                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }

                            tdapDoseInputListener();


                        }
                        else{
                            lltdap.setVisibility(View.GONE);
                            lltdapbooster.setVisibility(View.VISIBLE);
                            getCheckedRadioTdapBooster();

                            tdapdoseE.setText("");
                            tdapimmunisedS="no";

                            List<Tdap> myl=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
                            if(myl.size()>0){

                                Tdap inf = Tdap.findById(Tdap.class, 1);

                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Tdap inf=new Tdap();
                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }



    public void getCheckedRadioTdapBooster(){

        try{


            grptdapbooster.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                     radiobtntdapbooster= (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtntdapbooster && checkedId > -1) {

                        String selectedOption=radiobtntdapbooster.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
                            lltdapboosterdate.setVisibility(View.VISIBLE);

                            tdapBoosterDateListener();
                            tdapDoseBoosterInputListener();
//                            Toast.makeText(ImmunisationProfile.this, "yes", Toast.LENGTH_SHORT).show();

                            List<Tdap> myl=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
                            if(myl.size()>0){

                                Tdap inf = Tdap.findById(Tdap.class, 1);

                                inf.setImmunisedboostervalue("Yes");
                                inf.setImmunisedboosterid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Tdap inf=new Tdap();
                                inf.setImmunisedboostervalue("Yes");
                                inf.setImmunisedboosterid(Integer.toString(checkedId));
                                inf.save();

                            }

                            tdapDoseInputListener();


                        }
                        else{

                            lltdapboosterdate.setVisibility(View.GONE);

                            tdapdoseboosterdateE.setText("");

//
                            List<Tdap> myl=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
                            if(myl.size()>0){

                                Tdap inf = Tdap.findById(Tdap.class, 1);

                                inf.setImmunisedboostervalue("No");
                                inf.setImmunisedboosterid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Tdap inf=new Tdap();
                                inf.setImmunisedboostervalue("No");
                                inf.setImmunisedboosterid(Integer.toString(checkedId));
                                inf.save();

                            }


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }


    public void getCheckedRadioMeasles(){

        try{


            grpmeasles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnmeasles = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnmeasles && checkedId > -1) {

                        String selectedOption=radiobtnmeasles.getText().toString();
                        if(selectedOption.equalsIgnoreCase("yes")){
                            llmeasles.setVisibility(View.VISIBLE);
                            measlesdose1E.setVisibility(View.VISIBLE);
                            measlesdose2E.setVisibility(View.VISIBLE);
                            measles2label.setVisibility(View.VISIBLE);
                            measlesimmunisedS="yes";


                            List<Measles> myl=Measles.findWithQuery(Measles.class,"select * from Measles");
                            if(myl.size()>0){

                                Measles inf = Measles.findById(Measles.class, 1);

                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Measles inf=new Measles();
                                inf.setImmunisedvalue("Yes");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }

                            measlesDate1InputListener();
                            measlesDate2InputListener();

                        }
                        else{

                            llmeasles.setVisibility(View.GONE);
                            measlesdose1E.setVisibility(View.VISIBLE);
                            measlesdose2E.setVisibility(View.VISIBLE);
                            measles2label.setVisibility(View.VISIBLE);
                            measlesdose2E.setText("");
                            measlesdose1E.setText("");
                            measlesimmunisedS="no";


                            List<Measles> myl=Measles.findWithQuery(Measles.class,"select * from Measles");
                            if(myl.size()>0){

                                Measles inf = Measles.findById(Measles.class, 1);

                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }
                            else{

                                Measles inf=new Measles();
                                inf.setImmunisedvalue("No");
                                inf.setImmunisedid(Integer.toString(checkedId));
                                inf.save();

                            }


                        }
                    }
                }
            });
        }
        catch(Exception e){


        }
    }



    public void InfluenzaDateListener(){
        try{

            DateListener(influenzadoseE);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying influenza datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void measlesDose1DateListener(){
        try{

            DateListener(measlesdose1E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying measles dose 1 datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void measlesDose2DateListener(){
        try{

            DateListener(measlesdose2E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying measles dose 2 datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void varicellaDose1DateListener(){
        try{

            DateListener(varicelladose1E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying varicella dose 1 datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void varicellaDose2DateListener(){
        try{

            DateListener(varicelladose2E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying varicella dose 2 datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void tdapBoosterDateListener(){
        try{

            DateListener(tdapdoseboosterdateE);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying tdap dose booster datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void tdapDoseDateListener(){
        try{

            DateListener(tdapdoseE);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying tdap dose datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void meningocoDoseDateListener(){
        try{

            DateListener(meningocodoseE);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying meningoco dose datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void meningocoSecondDoseDateListener(){
        try{

            DateListener(meningocodose2E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying meningoco second dose datepicker", Toast.LENGTH_SHORT).show();
        }

    }


    public void DateListener(final EditText et){

        try{

            et.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(ImmunisationProfile.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    String dom=String.format("%02d", dayOfMonth);
                                    String moy=String.format("%02d", (monthOfYear + 1));

                                    // set day of month , month and year value in the edit text
                                    et.setText(dom + "/"
                                            + moy + "/" + year);


                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                }
            });
        }
        catch(Exception e){


        }
    }


    //alarm management function start

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 1;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//        Toast.makeText(this, "Alarm Set start", Toast.LENGTH_SHORT).show();
    }


//    public void startAlert() {
//
////        int i = 60*60*24;
//        int i=60;
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 234324243, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (i * 1000), (i * 1000), pendingIntent);
//  /*alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//    + (i * 1000), 8000, pendingIntent);*/
//
//
////        Toast.makeText(this, "Starting alarm in " + i + " seconds",
////                Toast.LENGTH_LONG).show();
//    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }



    //alarm manegement function end



    public boolean isGenderFemale(){
        boolean isFemale=false;
        try{

            List<RegistrationTable> myl=RegistrationTable.findWithQuery(RegistrationTable.class,"select * from Registration_table limit 1");
            for(int x=0;x<myl.size();x++){
                if(myl.get(x).gender.equalsIgnoreCase("2")){
                    isFemale=true;
                }
            }
            return isFemale;
        }
        catch (Exception e){
            isFemale=false;
            return isFemale;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.immunisationmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.edit) {
            if(childItemsEnabled){
                enableDisableView(parentLayout,false);
                childItemsEnabled=false;

            }
            else{
                enableDisableView(parentLayout,true);
                childItemsEnabled=true;

            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
