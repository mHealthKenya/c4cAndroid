package mhealth.c4c;

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
import android.view.View;
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
import mhealth.c4c.Tables.UserProfileTable;
import mhealth.c4c.systemstatetables.Influenza;
import mhealth.c4c.systemstatetables.Varicella;

/**
 * Created by root on 1/22/18.
 */

public class ImmunisationProfile extends AppCompatActivity {
    RadioGroup grpinfluenza,grpvaricella,grptdap,grpmeasles,grpvaricellaqn1,grpinfluenzaqn,grpmeningoco;
    RadioButton radiobntinfluenza,radiobtnvaricella,radiobtntdap,radiobtnmeasles,radiobtnvaricellaqn1,radiobtninfluenzaqn,radiobtnmeningoco;
    LinearLayout llinfluenza,llvaricella,lltdap,llmeasles,llpreg, llvaricellaqn1L, llvaricellaqn2L,llinfluenzaqnL,llmeningoco;
    EditText measlesdose1E,measlesdose2E,influenzadoseE,varicelladose1E,varicelladose2E,tdapdoseE,meningocodoseE,meningocodose2E;
    TextView measles2label;
    DatePickerDialog datePickerDialog;


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

        getCheckedRadioInfluenza();
        getCheckedRadioVaricella();
        getCheckedRadioTdap();
        getCheckedRadioMeasles();
        getCheckedRadioVaricellaQn1();
        getCheckedRadioMeningoco();

        InfluenzaDateListener();
        measlesDose1DateListener();
        measlesDose2DateListener();
        varicellaDose1DateListener();
        varicellaDose2DateListener();
        tdapDoseDateListener();
        meningocoDoseDateListener();
        meningocoSecondDoseDateListener();
//        meningocoCheckListener();
        startAlert();
        displayLinearLayoutPregnant();
        getCheckedRadioInfluenzaQn();

        //functions to retrieve system status
        getInfluenzaData();
        getVaricellaData();


    }

    public void getInfluenzaData(){
        try{


            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){
                    int mselectedvaccineId=Integer.parseInt(myl.get(x).getInfluenzavaccineid());
                    int mselectedpregnantId=Integer.parseInt(myl.get(x).getPregnantid());
                    String mdoseDate=myl.get(x).dosedate;

                    RadioButton rbVaccine = (RadioButton) findViewById(mselectedvaccineId);
                    rbVaccine.setChecked(true);

                    RadioButton rbPregnant = (RadioButton) findViewById(mselectedpregnantId);
                    rbPregnant.setChecked(true);

                    Toast.makeText(this, "dose date is "+mdoseDate, Toast.LENGTH_SHORT).show();
                    influenzadoseE.setText(mdoseDate);



                }
            }
        }
        catch(Exception e){


        }
    }

    public void getVaricellaData(){
        try{


            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String mdose1Date=myl.get(x).firstdosedate;
                    String mdose2Date=myl.get(x).seconddosedate;
                    Toast.makeText(this, ""+mdose1Date+" "+mdose2Date, Toast.LENGTH_SHORT).show();



                    if(myl.get(x).getHistoryid()!=null){

                        int mselectedhistoryId=Integer.parseInt(myl.get(x).getHistoryid());

                        RadioButton rbhistory = (RadioButton) findViewById(mselectedhistoryId);
                        rbhistory.setChecked(true);

                    }
                    else{
                        grpvaricella.clearCheck();

                    }
                    if(myl.get(x).getVaccineid()!=null){
                        int mselectedvaccineId=Integer.parseInt(myl.get(x).getVaccineid());

                        RadioButton rbVaccine = (RadioButton) findViewById(mselectedvaccineId);
                        rbVaccine.setChecked(true);

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
            Toast.makeText(this, "error getting "+e, Toast.LENGTH_SHORT).show();


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

                        }
                        else{

//                            Toast.makeText(ImmunisationProfile.this, "No", Toast.LENGTH_SHORT).show();

                            llmeningoco.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        catch(Exception e){


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


    public void populateFields(){

        try{

            List<UserProfileTable> uplist=UserProfileTable.findWithQuery(UserProfileTable.class,"select * from User_profile_table");
            if(uplist.size()>0){

                for(int x=0;x<uplist.size();x++) {

                    System.out.println("pregnant: " + uplist.get(x).getInfluenzapregnant());
                    System.out.println("influenza dose: " + uplist.get(x).getInfluenzadose());
                    System.out.println("varicella history: " + uplist.get(x).getVaricellahistory());
                    System.out.println("varicella first dose: " + uplist.get(x).getVaricellafirstdose());
                    System.out.println("varicella second dose: " + uplist.get(x).getVaricellaseconddose());
                    System.out.println("tdapimmunised: " + uplist.get(x).getTdapimmunised());
                    System.out.println("tdapdose: " + uplist.get(x).getTdapdose());
                    System.out.println("measles immunised: " + uplist.get(x).getMeaslesimmunised());
                    System.out.println("measles first dose: " + uplist.get(x).getMeaslesfirstdose());
                    System.out.println("measles second dose: " + uplist.get(x).getMeaslesseconddose());
                    System.out.println("meningoco: " + uplist.get(x).getMeningocodose());

                }


            }

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

                    Influenza inf = Influenza.findById(Influenza.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                    inf.setDosedate(s.toString());
                    inf.save();

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

                    Varicella inf = Varicella.findById(Varicella.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                    inf.setSeconddosedate(s.toString());
                    inf.save();

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

                    Varicella inf = Varicella.findById(Varicella.class, 1);
//                    inf.setDosedate(influenzadoseE.getText().toString());
                    inf.setFirstdosedate(s.toString());
                    inf.save();

                }
            });
        }
        catch(Exception e){


        }
    }

    public void updateProfile(View v){

        try{


            List<Influenza> mylinfluenza=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            List<Varicella> mylivaricella=Varicella.findWithQuery(Varicella.class,"select * from Varicella");

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

            }
            //end set varicella data

            System.out.println("*************************************influenza data*****************");
            for(int y=0;y<mylinfluenza.size();y++){

                System.out.println("gender :"+mylinfluenza.get(y).getGender());
                System.out.println("pregnantvalue :"+mylinfluenza.get(y).getPregnantvalue());
                System.out.println("influenzavaccine value :"+mylinfluenza.get(y).getInfluenzavaccinevalue());
                System.out.println("dose date :"+mylinfluenza.get(y).getDosedate());
            }

            System.out.println("*************************************influenza data*****************");

            System.out.println("*************************************varicella data*****************");
            for(int y=0;y<mylivaricella.size();y++){


                System.out.println("vaccinated id :"+mylivaricella.get(y).getVaccineid());
                System.out.println("history id :"+mylivaricella.get(y).getHistoryid());
                System.out.println("first dose date :"+mylivaricella.get(y).getFirstdosedate());
                System.out.println("second dose date :"+mylivaricella.get(y).getSeconddosedate());
            }

            System.out.println("*************************************varicella data*****************");



//            Toast.makeText(this, "updating profile", Toast.LENGTH_SHORT).show();

//
//
//            if(influenzapregnantS.equalsIgnoreCase("Yes")){
//
//                influenzadoseS="empty";
//            }
//            else if(influenzapregnantS.equalsIgnoreCase("No")){
//                influenzadoseS=influenzadoseE.getText().toString();
//            }
//            if(varicellahistoryS.equalsIgnoreCase("yes")){
//                varicellafirstdoseS="empty";
//                varicellaseconddoseS="empty";
//
//            }
//            else{
//
//                varicellafirstdoseS=varicelladose1E.getText().toString();
//                varicellaseconddoseS=varicelladose2E.getText().toString();
//            }
//            if(tdapimmunisedS.equalsIgnoreCase("yes")){
//
//                tdapdoseS=tdapdoseE.getText().toString();
//            }
//            else if(tdapimmunisedS.equalsIgnoreCase("no")){
//                tdapdoseS="empty";
//
//            }
//            if(measlesimmunisedS.equalsIgnoreCase("one dose")){
//
//                measlesfirstdoseS=measlesdose1E.getText().toString();
//                measlesseconddoseS="empty";
//
//            }
//            else if(measlesimmunisedS.equalsIgnoreCase("both doses")){
//
//                measlesfirstdoseS=measlesdose1E.getText().toString();
//                measlesseconddoseS=measlesdose2E.getText().toString();
//            }
//            else if(measlesimmunisedS.equalsIgnoreCase("no")){
//
//                measlesfirstdoseS="empty";
//                measlesseconddoseS="empty";
//
//            }
//
////            if(meningocoChk.isChecked()){
////
////                meningocodoseS="empty";
////            }
////            else{
////
////                meningocodoseS=meningocodoseE.getText().toString();
////            }
//
//            UserProfileTable.deleteAll(UserProfileTable.class);
//
//            UserProfileTable up=new UserProfileTable(influenzapregnantS,influenzadoseS,varicellahistoryS,varicellafirstdoseS,varicellaseconddoseS,tdapimmunisedS,tdapdoseS,measlesimmunisedS,measlesfirstdoseS,measlesseconddoseS,meningocodoseS);
//            up.save();
//            Toast.makeText(this, "Success Updating user Profile", Toast.LENGTH_SHORT).show();
//
//            int emptyCounter=0;
//            List<UserProfileTable> uplist=UserProfileTable.findWithQuery(UserProfileTable.class,"select * from User_profile_table");
//            for(int x=0;x<uplist.size();x++){
//                if(uplist.get(x).getInfluenzapregnant().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getInfluenzadose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getVaricellahistory().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getVaricellafirstdose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getVaricellaseconddose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getTdapimmunised().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getTdapdose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getMeaslesimmunised().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getMeaslesfirstdose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getMeaslesseconddose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//                if(uplist.get(x).getMeningocodose().contentEquals("")){
//                    emptyCounter+=1;
//                }
//
//                System.out.println("*******************");
//                System.out.println("pregnant: "+uplist.get(x).getInfluenzapregnant());
//                System.out.println("influenza dose: "+uplist.get(x).getInfluenzadose());
//                System.out.println("varicella history: "+uplist.get(x).getVaricellahistory());
//                System.out.println("varicella first dose: "+uplist.get(x).getVaricellafirstdose());
//                System.out.println("varicella second dose: "+uplist.get(x).getVaricellaseconddose());
//                System.out.println("tdapimmunised: "+uplist.get(x).getTdapimmunised());
//                System.out.println("tdapdose: "+uplist.get(x).getTdapdose());
//                System.out.println("measles immunised: "+uplist.get(x).getMeaslesimmunised());
//                System.out.println("measles first dose: "+uplist.get(x).getMeaslesfirstdose());
//                System.out.println("measles second dose: "+uplist.get(x).getMeaslesseconddose());
//                System.out.println("meningoco: "+uplist.get(x).getMeningocodose());
//                System.out.println("*******************");
//
////                Toast.makeText(this, "total empty "+emptyCounter, Toast.LENGTH_SHORT).show();
//
//                float res=0;
//                if((emptyCounter)==11){
//
//                    res=0;
//
//                }
//                else{
//
//                    res=((((float)(11-emptyCounter))/11)*100);
//
//                }
//
//                ProfileCompletion.deleteAll(ProfileCompletion.class);
//
//                ProfileCompletion pc=new ProfileCompletion(String.format("%1$.0f",res));
//                pc.save();
//
////                Toast.makeText(this, ""+String.format("%1$.0f",res)+"% complete", Toast.LENGTH_SHORT).show();
//            }
        }
        catch(Exception e){

            Toast.makeText(this, "error updating profile, try again"+e, Toast.LENGTH_SHORT).show();

            System.out.println("**********error*****"+e);
            Log.e("MYERROR",e.getMessage());
        }
    }

    public void initialise(){

        try{

            Intent alarmIntent = new Intent(ImmunisationProfile.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(ImmunisationProfile.this, 0, alarmIntent, 0);

            grpinfluenza =(RadioGroup) findViewById(R.id.radiogrpinfluenza);
            grpinfluenzaqn =(RadioGroup) findViewById(R.id.radiogrpinfluenzaqn);
            grpmeningoco =(RadioGroup) findViewById(R.id.radiogrpmeningocoqn);

            llinfluenza=(LinearLayout) findViewById(R.id.influenzadoselayout);
            llinfluenzaqnL=(LinearLayout) findViewById(R.id.llinfluenzaqn);
            llmeningoco=(LinearLayout) findViewById(R.id.meningocodoselayout);
            llpreg=(LinearLayout) findViewById(R.id.llpregnant);
            grpvaricella =(RadioGroup) findViewById(R.id.radiogrpvaricella);
            grpvaricellaqn1 =(RadioGroup) findViewById(R.id.radiogrpvaricellaqn1);
            llvaricella=(LinearLayout) findViewById(R.id.varicelladoselayout);
            llvaricellaqn1L =(LinearLayout) findViewById(R.id.llvaricellaqn1);
            llvaricellaqn2L =(LinearLayout) findViewById(R.id.llvaricellaqn2);

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


            measles2label=(TextView) findViewById(R.id.measlessecondoselabel);
//            meningocoChk=(CheckBox) findViewById(R.id.chkmeningoco);


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


                        }
                        else{
                            lltdap.setVisibility(View.GONE);
                            tdapdoseE.setText("");
                            tdapimmunisedS="no";


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
                        if(selectedOption.equalsIgnoreCase("one dose")){
                            llmeasles.setVisibility(View.VISIBLE);
                            measlesdose1E.setVisibility(View.VISIBLE);
                            measlesdose2E.setVisibility(View.GONE);
                            measles2label.setVisibility(View.GONE);
                            measlesdose2E.setText("");
                            measlesimmunisedS="one dose";

                        }
                        else if(selectedOption.equalsIgnoreCase("both doses")){
                            llmeasles.setVisibility(View.VISIBLE);
                            measlesdose1E.setVisibility(View.VISIBLE);
                            measlesdose2E.setVisibility(View.VISIBLE);
                            measles2label.setVisibility(View.VISIBLE);
                            measlesimmunisedS="both doses";


                        }
                        else{

                            llmeasles.setVisibility(View.GONE);
                            measlesdose1E.setVisibility(View.VISIBLE);
                            measlesdose2E.setVisibility(View.VISIBLE);
                            measles2label.setVisibility(View.VISIBLE);
                            measlesdose2E.setText("");
                            measlesdose1E.setText("");
                            measlesimmunisedS="none";


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


//    public void meningocoCheckListener(){
//
//        try{
//
//            meningocoChk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (((CheckBox) v).isChecked()) {
//                        meningocodoseE.setEnabled(false);
//                        meningocodoseE.setText("");
//                    }
//                    else{
//                        meningocodoseE.setEnabled(true);
//                    }
//
//                }
//            });
//        }
//        catch(Exception e){
//
//        }
//    }


    //alarm management function start

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 1;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//        Toast.makeText(this, "Alarm Set start", Toast.LENGTH_SHORT).show();
    }


    public void startAlert() {

//        int i = 60*60*24;
        int i=60;
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), (i * 1000), pendingIntent);
  /*alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    + (i * 1000), 8000, pendingIntent);*/


//        Toast.makeText(this, "Starting alarm in " + i + " seconds",
//                Toast.LENGTH_LONG).show();
    }

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

            List<Regdetails> myl=Regdetails.findWithQuery(Regdetails.class,"select * from Regdetails");
            for(int x=0;x<myl.size();x++){
                if(myl.get(x).gender.equalsIgnoreCase("Female")){
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
}