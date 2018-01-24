package mhealth.c4c;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import mhealth.c4c.Tables.ProfileCompletion;
import mhealth.c4c.Tables.UserProfileTable;

/**
 * Created by root on 1/22/18.
 */

public class UserProfile extends AppCompatActivity {
    RadioGroup grpinfluenza,grpvaricella,grptdap,grpmeasles;
    RadioButton radiobntinfluenza,radiobtnvaricella,radiobtntdap,radiobtnmeasles;
    LinearLayout llinfluenza,llvaricella,lltdap,llmeasles;
    EditText measlesdose1E,measlesdose2E,influenzadoseE,varicelladose1E,varicelladose2E,tdapdoseE,meningocodoseE;
    TextView measles2label;
    DatePickerDialog datePickerDialog;
    CheckBox meningocoChk;

    private PendingIntent pendingIntent;

    String influenzapregnantS,influenzadoseS,varicellahistoryS,varicellafirstdoseS,varicellaseconddoseS,tdapimmunisedS,tdapdoseS,measlesimmunisedS,measlesfirstdoseS,measlesseconddoseS,meningocodoseS;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        initialise();
        setToolBar();

        getCheckedRadioInfluenza();
        getCheckedRadioVaricella();
        getCheckedRadioTdap();
        getCheckedRadioMeasles();

        InfluenzaDateListener();
        measlesDose1DateListener();
        measlesDose2DateListener();
        varicellaDose1DateListener();
        varicellaDose2DateListener();
        tdapDoseDateListener();
        meningocoDoseDateListener();
        meningocoCheckListener();
        startAlert();

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

            Intent intent = new Intent(this, UserProfile.class);
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
            getSupportActionBar().setTitle("User Profile");

        }
        catch(Exception e){


        }
    }


    public void updateProfile(View v){

        try{

//            Toast.makeText(this, "updating profile", Toast.LENGTH_SHORT).show();



            if(influenzapregnantS.equalsIgnoreCase("Yes")){

                influenzadoseS="empty";
            }
            else if(influenzapregnantS.equalsIgnoreCase("No")){
                influenzadoseS=influenzadoseE.getText().toString();
            }
            if(varicellahistoryS.equalsIgnoreCase("yes")){
                varicellafirstdoseS="empty";
                varicellaseconddoseS="empty";

            }
            else{

                varicellafirstdoseS=varicelladose1E.getText().toString();
                varicellaseconddoseS=varicelladose2E.getText().toString();
            }
            if(tdapimmunisedS.equalsIgnoreCase("yes")){

                tdapdoseS=tdapdoseE.getText().toString();
            }
            else if(tdapimmunisedS.equalsIgnoreCase("no")){
                tdapdoseS="empty";

            }
            if(measlesimmunisedS.equalsIgnoreCase("one dose")){

                measlesfirstdoseS=measlesdose1E.getText().toString();
                measlesseconddoseS="empty";

            }
            else if(measlesimmunisedS.equalsIgnoreCase("both doses")){

                measlesfirstdoseS=measlesdose1E.getText().toString();
                measlesseconddoseS=measlesdose2E.getText().toString();
            }
            else if(measlesimmunisedS.equalsIgnoreCase("no")){

                measlesfirstdoseS="empty";
                measlesseconddoseS="empty";

            }

            if(meningocoChk.isChecked()){

                meningocodoseS="empty";
            }
            else{

                meningocodoseS=meningocodoseE.getText().toString();
            }

            UserProfileTable.deleteAll(UserProfileTable.class);

            UserProfileTable up=new UserProfileTable(influenzapregnantS,influenzadoseS,varicellahistoryS,varicellafirstdoseS,varicellaseconddoseS,tdapimmunisedS,tdapdoseS,measlesimmunisedS,measlesfirstdoseS,measlesseconddoseS,meningocodoseS);
            up.save();
            Toast.makeText(this, "Success Updating user Profile", Toast.LENGTH_SHORT).show();

            int emptyCounter=0;
            List<UserProfileTable> uplist=UserProfileTable.findWithQuery(UserProfileTable.class,"select * from User_profile_table");
            for(int x=0;x<uplist.size();x++){
                if(uplist.get(x).getInfluenzapregnant().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getInfluenzadose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getVaricellahistory().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getVaricellafirstdose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getVaricellaseconddose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getTdapimmunised().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getTdapdose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getMeaslesimmunised().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getMeaslesfirstdose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getMeaslesseconddose().contentEquals("")){
                    emptyCounter+=1;
                }
                if(uplist.get(x).getMeningocodose().contentEquals("")){
                    emptyCounter+=1;
                }

                System.out.println("*******************");
                System.out.println("pregnant: "+uplist.get(x).getInfluenzapregnant());
                System.out.println("influenza dose: "+uplist.get(x).getInfluenzadose());
                System.out.println("varicella history: "+uplist.get(x).getVaricellahistory());
                System.out.println("varicella first dose: "+uplist.get(x).getVaricellafirstdose());
                System.out.println("varicella second dose: "+uplist.get(x).getVaricellaseconddose());
                System.out.println("tdapimmunised: "+uplist.get(x).getTdapimmunised());
                System.out.println("tdapdose: "+uplist.get(x).getTdapdose());
                System.out.println("measles immunised: "+uplist.get(x).getMeaslesimmunised());
                System.out.println("measles first dose: "+uplist.get(x).getMeaslesfirstdose());
                System.out.println("measles second dose: "+uplist.get(x).getMeaslesseconddose());
                System.out.println("meningoco: "+uplist.get(x).getMeningocodose());
                System.out.println("*******************");

//                Toast.makeText(this, "total empty "+emptyCounter, Toast.LENGTH_SHORT).show();

                float res=0;
                if((emptyCounter)==11){

                    res=0;

                }
                else{

                    res=((((float)(11-emptyCounter))/11)*100);

                }

                ProfileCompletion.deleteAll(ProfileCompletion.class);

                ProfileCompletion pc=new ProfileCompletion(String.format("%1$.0f",res));
                pc.save();

//                Toast.makeText(this, ""+String.format("%1$.0f",res)+"% complete", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){

            Toast.makeText(this, "error updating profile, try again"+e, Toast.LENGTH_SHORT).show();

            System.out.println("**********error*****"+e);
            Log.e("MYERROR",e.getMessage());
        }
    }

    public void initialise(){

        try{

            Intent alarmIntent = new Intent(UserProfile.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(UserProfile.this, 0, alarmIntent, 0);

            grpinfluenza =(RadioGroup) findViewById(R.id.radiogrpinfluenza);
            llinfluenza=(LinearLayout) findViewById(R.id.influenzadoselayout);

            grpvaricella =(RadioGroup) findViewById(R.id.radiogrpvaricella);
            llvaricella=(LinearLayout) findViewById(R.id.varicelladoselayout);

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





            measlesdose1E=(EditText) findViewById(R.id.measlesdose1);
            measlesdose2E=(EditText) findViewById(R.id.measlesdose2);
            influenzadoseE=(EditText) findViewById(R.id.influenzadose);
            varicelladose1E=(EditText) findViewById(R.id.variccelladose1);
            varicelladose2E=(EditText) findViewById(R.id.variccelladose2);
            tdapdoseE=(EditText) findViewById(R.id.tdapdose);
            meningocodoseE=(EditText) findViewById(R.id.meningocofulldose);


            measles2label=(TextView) findViewById(R.id.measlessecondoselabel);
            meningocoChk=(CheckBox) findViewById(R.id.chkmeningoco);


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
                            llinfluenza.setVisibility(View.GONE);
                            influenzadoseE.setText("");
                            influenzapregnantS="yes";

                        }
                        else{
                            llinfluenza.setVisibility(View.VISIBLE);
                            influenzapregnantS="no";


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
                            llvaricella.setVisibility(View.GONE);
                            varicelladose1E.setText("");
                            varicelladose2E.setText("");
                            varicellahistoryS="yes";

                        }
                        else{
                            llvaricella.setVisibility(View.VISIBLE);
                            varicellahistoryS="no";


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
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(UserProfile.this,
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


    public void meningocoCheckListener(){

        try{

            meningocoChk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((CheckBox) v).isChecked()) {
                        meningocodoseE.setEnabled(false);
                        meningocodoseE.setText("");
                    }
                    else{
                        meningocodoseE.setEnabled(true);
                    }

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
}
