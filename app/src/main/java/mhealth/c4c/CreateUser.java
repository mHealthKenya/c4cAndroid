package mhealth.c4c;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mhealth.c4c.Tables.UserTable;
import mhealth.c4c.Tables.kmpdu;

/**
 * Created by KENWEEZY on 2016-10-31.
 */


public class CreateUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nameE,lnameE,idnoE,ageE,mflE,munameE,mpassE,mcpassE,mhint,motherE,dunumber,dose1E,dose2E;
    CheckBox mchkb;
    String otherValue;
    TextView specialisel,cadrel;
    LinearLayout doselayout;
    String selectedspecialisation;

    RadioGroup radio2dosegrp;
    RadioButton radiobtnseconddose;


    EditText partnerorgE,specialisationE;
    Dialog partnerdialog,specialisationdialog;

    String[] itemsorg={"MOH","EGPAF","CHS","UON",
            "KMPDU","Not Applicable"};
    String[] itemsspecialisation={"Anaesthesia","Cardiothoracic surgery","Dermatology","Ear Nose And Throat",
    "Internal Medicine","Microbiology","Neurosurgery","Obstetrics and Gynaecology","Occupational Medicine",
    "Ophthalmology","Orthopaedic Surgery","Paediatrics and Child Health","Palliative Medicine",
    "Pathology","Psychiatry","Plastic and Reconstructive Surgery","Public Health","Radiology",
    "Surgery","Immunology","Infectious Diseases","Clinical Medical Genetics","Emergency Medicine",
    "Opthalmology"};
    final ArrayList itemsSelected = new ArrayList();
    final ArrayList itemsSelectedSpecialisation = new ArrayList();

    String passedkmpdu;

    boolean correctMfl;

    public static final String REGISTER_URL = "http://everest.co.ke/mlabphp/checkfacility.php";


    public static final String KEY_MFLCODE = "facility_code";

    String[] genders={"Please Select Gender","Male","Female"};
    String[] cadres={"Please Select Cadre","Student","Doctor","Nurse","Clinical Officer","Laboratory Technologist","Cleaner","Waste Handlers","VCT Counselor","Other"};
    String[] hepa={"Have you been vaccinated against Hepatitis B?","Yes","Partially","No"};
    String[] secqn={"choose a security question","what is your last name ?","what is your favourite pet ?","what is your favourite country ?"};



    List<UserTable> user_list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    int selectedYear;
    Spinner myspinner;
    Spinner myspinner2;
    Spinner myspinner3;
    Spinner myspinner4;

    String selected_item="";
    String myselected="";
    String selected_item2="";
    String myselected2="";
    String myselected3="";
    String myselected4="";
    StringBuilder partners;
    String selectedQn="";
    boolean kmpduChecked;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);
        requestPerms();

        initialise();

        CheckToperiodListener();

        populateSpinner();
        populateSpinner2();
        populateSpinner3();
        populateSpinner4();


        myspinner.setOnItemSelectedListener(this);
        myspinner2.setOnItemSelectedListener(this);
        myspinner3.setOnItemSelectedListener(this);
        myspinner4.setOnItemSelectedListener(this);

        setPartnerClickListener();
        setSpecialisationClickListener();


    }


    public void setPartnerClickListener(){

        try{

            partnerorgE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    partnerorgE.setText("");
                    itemsSelected.clear();
                    displayMultiselectForPartner();


                }
            });

        }
        catch(Exception e){



        }
    }

    public void setSpecialisationClickListener(){

        try{

            specialisationE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    specialisationE.setText("");
                    itemsSelectedSpecialisation.clear();
                    displayMultiselectForSpecialisation();


                }
            });

        }
        catch(Exception e){



        }
    }


    public void CheckToperiodListener(){

        try{

            ageE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(CreateUser.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    ageE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);
                                    selectedYear=year;

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
        catch(Exception e){


        }
    }




    public void Dose1DateListener(){

        try{

            dose1E.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(CreateUser.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    String dom=String.format("%02d", dayOfMonth);
                                    String moy=String.format("%02d", (monthOfYear + 1));

                                    // set day of month , month and year value in the edit text
                                    dose1E.setText(dom + "/"
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



    public void Dose2DateListener(){

        try{

            dose2E.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(CreateUser.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text


                                    String dom=String.format("%02d", dayOfMonth);
                                    String moy=String.format("%02d", (monthOfYear + 1));
                                    dose2E.setText(dom+ "/"
                                            + moy + "/" + year);

                                    String startDate=dose1E.getText().toString();
                                    String endDate=dose2E.getText().toString();

                                    long mydiff=calculateDateDifference(startDate,endDate);
                                    if(mydiff<1){
                                        dose2E.setText("");
                                        Toast.makeText(CreateUser.this, "select date greater than today", Toast.LENGTH_SHORT).show();

                                    }



                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
        catch(Exception e){


        }
    }



    public long calculateDateDifference(String date1,String date2){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            return diffDays;




        }
        catch(Exception e){
            Toast.makeText(this, "error "+e, Toast.LENGTH_SHORT).show();
            System.out.println("*********errorr***********"+e);

            return -1;


        }
    }


    public void displayMultiselectForPartner(){

        try{


            final boolean selected[] = new boolean[]{false, false, false, false,false,false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Partner Organisation : ");
            builder.setMultiChoiceItems(itemsorg, selected,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {

                            if (isSelected) {



                                //logic

                                for (int i = 0; i < selected.length; i++) {
                                if (i == selectedItemId) {

                                    selected[i]=true;
                                    itemsSelected.add(selectedItemId);


                                }

                            }

                            if(selected[5]){//check for Not Applicable

                                    for(int x=0;x<selected.length;x++){

                                        itemsSelected.remove(Integer.valueOf(x));
                                        selected[x]=false;
                                        ((android.app.AlertDialog)dialog).getListView().setItemChecked(x, false);
                                        if(x==5){
                                            selected[5]=true;
                                            itemsSelected.add(5);

                                            ((android.app.AlertDialog)dialog).getListView().setItemChecked(5, true);
//                                            continue;
                                        }
                                    }


                            }
                            else if(selected[4]){


                                kmpduChecked=true;
                                dunumber.setVisibility(View.VISIBLE);
                                specialisationE.setVisibility(View.VISIBLE);

                                specialisel.setVisibility(View.VISIBLE);
                                idnoE.setVisibility(View.GONE);
                                nameE.setVisibility(View.GONE);
                                mflE.setVisibility(View.GONE);
                                myspinner2.setVisibility(View.GONE);
                                cadrel.setVisibility(View.GONE);

                                lnameE.setVisibility(View.GONE);


                            }

                            else if(!selected[4]){//if kmpdu is not checked
                                kmpduChecked=false;
                                dunumber.setVisibility(View.GONE);

                                specialisationE.setVisibility(View.GONE);
                                specialisel.setVisibility(View.GONE);

                                idnoE.setVisibility(View.VISIBLE);
                                nameE.setVisibility(View.VISIBLE);
                                mflE.setVisibility(View.VISIBLE);
                                myspinner2.setVisibility(View.VISIBLE);
                                cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);
                                lnameE.setVisibility(View.VISIBLE);

                            }


                            }

                            else if (itemsSelected.contains(selectedItemId)) {
                                itemsSelected.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId]=false;

                                if(selectedItemId==4){ //check for kmpdu if unchecked


                                    kmpduChecked=false;
                                    dunumber.setVisibility(View.GONE);

                                    specialisel.setVisibility(View.GONE);
                                    specialisationE.setVisibility(View.GONE);
                                    idnoE.setVisibility(View.VISIBLE);
                                    nameE.setVisibility(View.VISIBLE);
                                    mflE.setVisibility(View.VISIBLE);
                                    myspinner2.setVisibility(View.VISIBLE);
                                    cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);
                                    lnameE.setVisibility(View.VISIBLE);


                                }
                            }

                        }
                    })
                    .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked

                            Iterator<String> it=itemsSelected.iterator();

                            while(it.hasNext()){

                                partnerorgE.append(itemsorg[Integer.parseInt(String.valueOf(it.next()))]);


                                if(it.hasNext()){
                                    partnerorgE.append(",");
                                }




                            }



                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            dunumber.setVisibility(View.GONE);

                            specialisel.setVisibility(View.GONE);
                            idnoE.setVisibility(View.VISIBLE);
                            nameE.setVisibility(View.VISIBLE);
                            mflE.setVisibility(View.VISIBLE);
                            myspinner2.setVisibility(View.VISIBLE);
                            cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);
                            lnameE.setVisibility(View.VISIBLE);


                        }
                    });
            partnerdialog = builder.create();
            partnerdialog.show();


        }
        catch(Exception e){


        }
    }





    public void displayMultiselectForSpecialisation(){

        try{


            final boolean selected[] = new boolean[]{false, false, false, false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,false,false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Specialisation: ");
            builder.setMultiChoiceItems(itemsspecialisation, selected,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {

                            if (isSelected) {



                                //logic

                                for (int i = 0; i < selected.length; i++) {
                                    if (i == selectedItemId) {

                                        selected[i]=true;

                                    }

                                }



                                //logic

                                itemsSelectedSpecialisation.add(selectedItemId);
                            }

                            else if (itemsSelectedSpecialisation.contains(selectedItemId)) {
                                itemsSelectedSpecialisation.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId]=false;

                            }
                        }
                    })
                    .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked
                            if(itemsSelectedSpecialisation.size()>1){
                                Toast.makeText(CreateUser.this, "Select only one specialisation", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(itemsSelectedSpecialisation.size()==1){

                                specialisationE.append(itemsspecialisation[Integer.parseInt(itemsSelectedSpecialisation.get(0).toString())]);



                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            specialisationdialog = builder.create();
            specialisationdialog.show();


        }
        catch(Exception e){


        }
    }


    public void initialise(){

        try{

            partnerorgE=(EditText) findViewById(R.id.partorg);
            specialisationE=(EditText) findViewById(R.id.specialisationselect);
            nameE=(EditText) findViewById(R.id.name);
            specialisel=(TextView) findViewById(R.id.specialisationlabel);
            cadrel=(TextView) findViewById(R.id.cadrelabel);
            kmpduChecked=false;
            dunumber=(EditText) findViewById(R.id.du);
            otherValue="";
            motherE=(EditText) findViewById(R.id.myother);
            mhint=(EditText) findViewById(R.id.hint);
            lnameE=(EditText) findViewById(R.id.lname);
            idnoE=(EditText) findViewById(R.id.idno);
            ageE=(EditText) findViewById(R.id.age);
            mflE=(EditText) findViewById(R.id.mfl);
            munameE=(EditText) findViewById(R.id.muname);
            mpassE=(EditText) findViewById(R.id.mpass);
            mcpassE=(EditText) findViewById(R.id.mcpass);
            myspinner=(Spinner) findViewById(R.id.spinner);
            myspinner2=(Spinner) findViewById(R.id.spinner2);
            myspinner3=(Spinner) findViewById(R.id.spinner3);
            myspinner4=(Spinner) findViewById(R.id.spinner4);
            radio2dosegrp=(RadioGroup) findViewById(R.id.radiogrpseconddose);

            doselayout=(LinearLayout) findViewById(R.id.doses);


            correctMfl=false;

            partners=new StringBuilder();

            dose1E=(EditText) findViewById(R.id.dose1);
            dose2E=(EditText) findViewById(R.id.dose2);

        }
        catch(Exception e){


        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin=(Spinner) parent;

        if(spin.getId()==R.id.spinner){


//            selected_item=parent.getItemAtPosition(position).toString();
            myselected=Integer.toString(position);
            actOnSelected();

        }

        else if (spin.getId()==R.id.spinner2){
            try{

//                selected_item2=parent.getItemAtPosition(position).toString();

                if(position==9){


                    motherE.setVisibility(View.VISIBLE);

                }
                else{
                    motherE.setText("");
                    motherE.setVisibility(View.GONE);

                }

                myselected2=Integer.toString(position);

                actOnSelected();


            }
            catch(Exception e){


            }



        }
        else if (spin.getId()==R.id.spinner3){

//            selected_item2=parent.getItemAtPosition(position).toString();

            myselected3=Integer.toString(position);

            if(position==2){

                doselayout.setVisibility(View.VISIBLE);
                Dose1DateListener();
                secondDoseRadioGroups();

            }

            else{

                doselayout.setVisibility(View.GONE);
            }

            actOnSelected();

        }

        else if (spin.getId()==R.id.spinner4){

            selectedQn=secqn[position];


            myselected4=Integer.toString(position);
            if(position>0){

                mhint.setEnabled(true);
            }
            else{
                mhint.setEnabled(false);
                mhint.setText("");
            }
            actOnSelected();

        }


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




    public void actOnSelected(){

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }

    public void secondDoseRadioGroups(){
        try{

            radio2dosegrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch(checkedId){
                        case R.id.radiono:
                            dose2E.setVisibility(View.GONE);
                            dose2E.setText("");
                            break;
                        case R.id.radioyes:
                            dose2E.setVisibility(View.VISIBLE);
                            Dose2DateListener();
                            break;
                        }

                }
            });

        }
        catch(Exception e){


        }
    }

    public void populateSpinner(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),genders);



            myspinner.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void populateSpinner2(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),cadres);

            myspinner2.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void populateSpinner3(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),hepa);

            myspinner3.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }



    public void populateSpinner4(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),secqn);

            myspinner4.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }


    //ill get back here

    public void CreatingUser(View v){

        try{

            partners=new StringBuilder("");

            Iterator<String> it=itemsSelected.iterator();

//            while(it.hasNext()){
//
//                partners.append(it.next());
//
////                partners.append("5");
//
//                if(it.hasNext()){
//                    partners.append("*");
//                }
//
//
//
//
//            }
            if(itemsSelected.size()==1){
                String val=Integer.toString((Integer.parseInt(itemsSelected.get(0).toString()))+1);
                partners.append(val);
            }
            else if(itemsSelected.size()>1) {


                for (int x = 0; x < itemsSelected.size(); x++) {
                    String val=Integer.toString((Integer.parseInt(itemsSelected.get(x).toString()))+1);

                    partners.append(val+"*");

                }
            }
//            partners.append("5");


            Toast.makeText(this, ""+partners, Toast.LENGTH_SHORT).show();


            String myname=nameE.getText().toString();
            String duns="";


            String mylname=lnameE.getText().toString();
            String myidno=idnoE.getText().toString();
            String myage=ageE.getText().toString();
            String mymfl="";
            String mdose1="-1";
            String mdose2="-1";

            if(kmpduChecked){
               mymfl="13528";
            }
            else{

                mymfl=mflE.getText().toString();
            }

            String myuname=munameE.getText().toString();
            String mympass=mpassE.getText().toString();
            String mymcpass=mcpassE.getText().toString();
            String myhint=mhint.getText().toString();
            int curyear = Calendar.getInstance().get(Calendar.YEAR);
//            idnoE.setVisibility(View.VISIBLE);
//            nameE.setVisibility(View.VISIBLE);
//            motherE.setVisibility(View.VISIBLE);
//            lnameE.setVisibility(View.VISIBLE);
            if(kmpduChecked){
                duns=dunumber.getText().toString();
                if(duns.isEmpty()){

                    dunumber.setError("Doctors union number is required");
                }
                if(selectedspecialisation.contentEquals("0")){
                    Toast.makeText(this, "select specialisation", Toast.LENGTH_SHORT).show();
                }

                myname="-1";
                mylname="-1";
                myidno="-1";

            }
            else{

                selectedspecialisation="-1";

                duns="-1";


            }

            if(!kmpduChecked && myname.trim().isEmpty()){
                nameE.setError("First Name is Required");

            }
            if(!kmpduChecked && mylname.trim().isEmpty()){
                lnameE.setError("Last Name is Required");

            }
            else if(!kmpduChecked && myidno.trim().isEmpty()){
                idnoE.setError("ID NUMBER is Required");

            }
            else if(myhint.trim().isEmpty()){
                mhint.setError("Answer is required");

            }
            else if(!kmpduChecked && myidno.length()<4){

                idnoE.setError("ID NUMBER should contain more than 3 values");

            }
            else if(myage.isEmpty()){
                ageE.setError("Age is Required");

            }

            else if(mymfl.trim().isEmpty()){
                mflE.setError("MFL Number is Required");

            }
            else if(partners.toString().isEmpty()){

                Toast.makeText(this, "select atleast one partner", Toast.LENGTH_SHORT).show();
            }

            else if(mymfl.length()!=5){

                mflE.setError("MFL Number should have a length of Five");

            }
            else if(mympass.trim().isEmpty()){
                mpassE.setError("Password is Required");

            }
            else if(mymcpass.trim().isEmpty()){
                mcpassE.setError("Confirm Password is Required");

            }
            else if(!mymcpass.contentEquals(mympass)){
                mcpassE.setError("Passwords Do not match");

            }
            else if((curyear-selectedYear)<18){

                Toast.makeText(this, "age should be greater than 18, try again", Toast.LENGTH_LONG).show();
            }
            else if(myselected.contentEquals("0")){
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_LONG).show();


            }


            else if(!kmpduChecked && myselected2.contentEquals("0")){
                Toast.makeText(this, "Please select Cadre", Toast.LENGTH_LONG).show();


            }


            else if(myselected3.contentEquals("0")){
                Toast.makeText(this, "Specify your vaccination", Toast.LENGTH_LONG).show();


            }

            else if(myselected4.contentEquals("0")){
                Toast.makeText(this, "Specify security question", Toast.LENGTH_LONG).show();


            }

            else{

                checkFacilityCode(myname,mylname,myidno,myage,mymfl,myuname,mympass,partners,myhint,duns,selectedspecialisation);


            }

        }
        catch(Exception e){
            SignupdisplayDialog("Error occured "+e);


        }
    }




    public void SignupdisplayDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("SIGNUP ERROR");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
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


    public void SignupsuccessDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("SIGNUP SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    if(kmpduChecked){
                        kmpdu km=new kmpdu("true");
                        km.save();

                        Intent myint=new Intent(getApplicationContext(),Login.class);

                        startActivity(myint);

                    }
                    else{

                        kmpdu mykm=new kmpdu("false");
                        mykm.save();

                        Intent myint=new Intent(getApplicationContext(),Login.class);

                        startActivity(myint);


                    }


                }
            });
            adb.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });





            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }


    public void clearFields(){

        try{

//            mpassf.setText("");
//            mcpassf.setText("");
//            munamef.setText("");


        }
        catch(Exception e){



        }
    }



    public void checkFacilityCode(final String myname, final String mylname, final String myidno, final String myage, final String mymfl, final String myuname, final String mympass,final StringBuilder partner,final String mhnt,final String duns,final String sspecial){


        final ProgressDialog pdialog= mydialog("loading...","Signing Up");

//        Toast.makeText(this, "checking facility", Toast.LENGTH_SHORT).show();



        final String mymflcode[]={""};
        if(kmpduChecked){
            mymflcode[0]="13528";

        }
        else{
            mymflcode[0]=mflE.getText().toString();

        }
//        pr.progressing(getApplicationContext(),"getting facility","loading....");


        try{

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contentEquals("empty code")) {

                                pdialog.cancel();
                                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                                correctMfl=false;

                            }

                            else if(response.contentEquals("code does not exist")){

                                pdialog.cancel();
                                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                                correctMfl=false;


                            }
                            else if(response.contentEquals("error occured")){

                                pdialog.cancel();
                                Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                                correctMfl=false;


                            }
                            else {
                                pdialog.cancel();
                                correctMfl=true;

                                String mflName=response;
                                String mdose1="-1";
                                String mdose2="-1";
                                if(!dose1E.getText().toString().trim().isEmpty()){
                                    mdose1=dose1E.getText().toString().trim();

                                }
                                if(!dose2E.getText().toString().trim().isEmpty()){
                                    mdose2=dose2E.getText().toString().trim();

                                }

                                if(correctMfl){
                                    RegistrationTable rt=new RegistrationTable(myname,mylname,myselected,myselected2,myidno,myage,mymfl,myselected3,myuname,mympass,selectedQn,mhnt);
                                    rt.save();
                                    String myoth="";

                                    try{

                                        myoth=motherE.getText().toString();


                                    }
                                    catch(Exception e){


                                    }

                                    String mymess="";

                                    if(kmpduChecked){

                                        mymess="Reg*"+myname+"*"+mylname+"*"+myidno+"*"+myage+"*"+myselected+"*"+"-1"+"*"+"-1"+"*"+myselected3+"*"+mdose1+"*"+mdose2+"*"+myuname+"*"+mympass+"*"+myselected4+"*"+mhnt+"*"+duns+"*"+sspecial+"*"+partner;


                                    }
                                    else if(!kmpduChecked && myoth.isEmpty()){
                                        mymess="Reg*"+myname+"*"+mylname+"*"+myidno+"*"+myage+"*"+myselected+"*"+myselected2+"*"+mymfl+"*"+myselected3+"*"+mdose1+"*"+mdose2+"*"+myuname+"*"+mympass+"*"+myselected4+"*"+mhnt+"*"+duns+"*"+sspecial+"*"+partner;


                                    }
                                    else if(!kmpduChecked && !myoth.isEmpty()){

                                        mymess="Reg*"+myname+"*"+mylname+"*"+myidno+"*"+myage+"*"+myselected+"*"+myoth+"*"+mymfl+"*"+myselected3+"*"+mdose1+"*"+mdose2+"*"+myuname+"*"+mympass+"*"+myselected4+"*"+mhnt+"*"+duns+"*"+sspecial+"*"+partner;



                                    }




                                    SmsManager smsM=SmsManager.getDefault();
                                    smsM.sendTextMessage("40149",null,mymess,null,null);
                                    SignupsuccessDialog("Success in Registration");


                                }
                                else{
//                    SignupdisplayDialog("WRONG MFLCODE, TRY AGAIN");


                                }




                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pdialog.cancel();
                            correctMfl=false;

                            Toast.makeText(getApplicationContext(), "error occured "+error, Toast.LENGTH_SHORT).show();

                        }

                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_MFLCODE, mymflcode[0]);
//                    params.put(KEY_EMAIL, email);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }

        catch(Exception e){


        }


    }

    public ProgressDialog mydialog(String message,String title){
        ProgressDialog progress = new ProgressDialog(this);

        try{


            progress.setMessage(message);
            progress.setTitle(title);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        }
        catch(Exception e){
            Toast.makeText(this, "error displaying progress", Toast.LENGTH_SHORT).show();

        }

        return progress;
    }

    public void requestPerms(){

        try{

            int permissionCheck = ContextCompat.checkSelfPermission(CreateUser.this, Manifest.permission.SEND_SMS);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        CreateUser.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1235);
            } else {

            }
        }
        catch(Exception e){
            Toast.makeText(this, "error in granting permissions "+e, Toast.LENGTH_SHORT).show();


        }
    }
}
