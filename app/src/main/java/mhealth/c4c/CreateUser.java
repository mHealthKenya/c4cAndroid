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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import mhealth.c4c.AccessServer.AccessServer;
import mhealth.c4c.Checkinternet.CheckInternet;
import mhealth.c4c.GetRemoteData.GetRemoteData;
import mhealth.c4c.Registrationtable.Regpartners;
import mhealth.c4c.RequestPermissions.RequestPerms;
import mhealth.c4c.SSLTrustCertificate.SSLTrust;
import mhealth.c4c.Tables.Edittable;
import mhealth.c4c.Tables.Facilitydata;
import mhealth.c4c.Tables.Partners;
import mhealth.c4c.Tables.Profiletable;
import mhealth.c4c.Tables.Signupform.Signup;
import mhealth.c4c.Tables.UserTable;
import mhealth.c4c.Tables.Userphonenumber;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.checkupstatustable.UpdateStatusTable;
import mhealth.c4c.config.Config;
import mhealth.c4c.dateCalculator.DateCalculator;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;
import mhealth.c4c.models.CountyModel;
import mhealth.c4c.models.FacilityModel;
import mhealth.c4c.models.SubCountyModel;
import mhealth.c4c.progress.Progress;
import mhealth.c4c.systemstatetables.Hepatitis;
import mhealth.c4c.userlogindata.UserLoginData;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * Created by KENWEEZY on 2016-10-31.
 */


public class CreateUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText idnoE, ageE, motherE, dunumber,facilitySpinnerEdt;
    SpinnerDialog spinnerDialog;
    CheckBox mchkb;
    String otherValue;
    TextView specialisel, cadrel;

    String selectedspecialisation;
    Progress pr;

//   ****** hepatitis b variables
    RadioGroup grphepatitis,grphepatitissecondose;
    RadioButton radiobtnhepaqn1,radiobtnhepaseconddose;
    LinearLayout llhepadoselayout1,llhepadoseseconddose;
    EditText hepatitisdose1E,hepatitisdose2E;

    UpdateStatusTable ut;

    // ******hepatitis b variables


    RadioButton radiobtnseconddose;
    Dialogs sweetdialog;
    GetRemoteData grd;

    MaterialBetterSpinner ctyM, sbctyM;

    String selectedCty, selectedSbcty, selectedFacility;

    private ArrayAdapter<String> arrayAdapterCounty, arrayAdapterSubCounty, arrayAdapterFacility;


    public final Pattern textPattern = Pattern.compile("^([a-zA-Z+]+[0-9+]+)$");


    EditText partnerorgE, specialisationE;
    Dialog partnerdialog, specialisationdialog;


    final ArrayList itemsSelected = new ArrayList();
    final ArrayList itemsSelectedSpecialisation = new ArrayList();

    private ArrayList<CountyModel> countyList;
    private ArrayList<SubCountyModel> subcountyList;
    private ArrayList<FacilityModel> facilityList;
    private JSONArray id_county_result,id_subcounty_result,id_facility_result;
    String passedkmpdu;




    public static final String KEY_MFLCODE = "facility_code";

    String[] genders = {"Please Select Gender", "Male", "Female"};
    String[] cadres = {"Please Select Cadre", "Student", "Doctor", "Nurse", "Clinical Officer", "Laboratory Technologist", "Cleaner", "Waste Handlers", "VCT Counselor", "Other"};
    String[] hepa = {"Have you been vaccinated against Hepatitis B?", "Yes", "Partially", "No"};


    List<UserTable> user_list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    int selectedYear;
    Spinner myspinner;
    Spinner myspinner2;



    String selected_item = "";
    String myselectedgender = "";
    String selected_item2 = "";
    String myselected2 = "";


    StringBuilder partners;

    boolean kmpduChecked;
    DateCalculator dcalc;
    AccessServer accessServer;
    CheckInternet chkInternet;
    RequestPerms requestPerms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);


        initialise();

        SSLTrust.nuke();

        requestPerms();

        countyList.clear();
        getCounties();


        setSpinnerCountyListener();
        setSpinnerSubCountyListener();



        CheckToperiodListener();

        populateSpinner();
        populateSpinner2();


        myspinner.setOnItemSelectedListener(this);
        myspinner2.setOnItemSelectedListener(this);



        setPartnerClickListener();
        setSpecialisationClickListener();

        getCheckedRadioHepatitis();
        hepatitisDose1DateListener();
        hepatitisDose2DateListener();



        Stetho.initializeWithDefaults(this);


    }



    public void Previous(View v){

        try{

//            Toast.makeText(this, "going to previous", Toast.LENGTH_SHORT).show();
            Intent myintt=new Intent(getApplicationContext(), UserLoginData.class);
            startActivity(myintt);
        }
        catch(Exception e){


        }
    }


    public void addListenerToFacilitySpinnerEdt(){

        try{




            System.out.println("***************facility lists***************");
            System.out.println(facilityList);


           final ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < facilityList.size(); x++) {
                String faciityname = facilityList.get(x).getName();
                y.add(faciityname);

            }

            facilitySpinnerEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    spinnerDialog=new SpinnerDialog(CreateUser.this,items,"Select or Search City","Close Button Text");// With No Animation
                    spinnerDialog=new SpinnerDialog(CreateUser.this,y,"Select Facility",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation


                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
//                            Toast.makeText(CreateUser.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                            selectedFacility = item;
                            facilitySpinnerEdt.setText(item);
                        }
                    });

                    spinnerDialog.showSpinerDialog();

//
//                    ArrayList<String> items=new ArrayList<>();
//                    items.add("Mumbai");
//                    items.add("Delhi");
//                    items.add("Bengaluru");
//                    items.add("Hyderabad");
//                    items.add("Ahmedabad");
//                    items.add("Chennai");
//                    items.add("Kolkata");
//                    items.add("Surat");
//                    items.add("Pune");
//                    items.add("Jaipur");
//                    items.add("Lucknow");
//                    items.add("Kanpur");
//
//                    final ArrayList<String> y = new ArrayList<>();
//
//                    for (int x = 0; x < facilityList.size(); x++) {
//                        String faciityname = facilityList.get(x).getName();
//                        y.add(faciityname);
//
//                    }
//
//
//                    //            spinnerDialog=new SpinnerDialog(CreateUser.this,items,"Select or Search City","Close Button Text");// With No Animation
//                    spinnerDialog=new SpinnerDialog(CreateUser.this,y,"Select or Search City",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With 	Animation
//
//
//                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
//                        @Override
//                        public void onClick(String item, int position) {
//                            Toast.makeText(CreateUser.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
////                    selectedItems.setText(item + " Position: " + position);
//                        }
//                    });
//
//                    spinnerDialog.showSpinerDialog();

                }
            });
        }
        catch(Exception e){


        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        setFacilitySpinnerData();
//        getCounties();
    }

    public void setFacilitySpinnerData() {

        try {

//            getRemoteData();

//            getCounties();
//
//            setCountyAdapter();
//            setSpinnerCountyListener();

            setSpinnerAdapters();
        } catch (Exception e) {


        }
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
//        just to make sure there is always data shown on our county list
        setCountyAdapter();
        setSub1CountyAdapter();
//        setFac1Adapter();
//        setSpinnerCountyListener();
        setSpinnerAdapters();
    }




    public void setSpinnerAdapters() {

        try {
            ctyM.setAdapter(arrayAdapterCounty);
            sbctyM.setAdapter(arrayAdapterSubCounty);


        } catch (Exception e) {


        }
    }

    public void setSpinnerCountyListener() {

        try {


            ctyM.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedCty = ctyM.getText().toString();

//                    Toast.makeText(CreateUser.this, ""+selectedCty, Toast.LENGTH_SHORT).show();

                    sbctyM.setVisibility(View.VISIBLE);
                    sbctyM.setText("");

//                    List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where countyname=? group by subcountyname", selectedCty);

                    sbctyM.setVisibility(View.VISIBLE);
                    subcountyList.clear();
                    getSubCounties(selectedCty);

//                    setSubCountyAdapter(subcountyList);

                    //                    subcountyList.clear();
//                    getSubCounties(selectedCty);
//
//                    setSubCountyAdapter(subcountyList);
//                    for(int x=0;x<myl.size();x++){
//                        String countyId=myl.get(x).getCountyid();
//                        setSubCountyAdapter(countyId);
//                    }



                }
            });

        } catch (Exception e) {


        }
    }


    public void setSpinnerSubCountyListener() {

        try {

            sbctyM.setText("");
            sbctyM.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedSbcty = sbctyM.getText().toString();
//                    Toast.makeText(CreateUser.this, ""+selectedSbcty, Toast.LENGTH_SHORT).show();




//                    List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where subcountyname=? group by facilityname", selectedSbcty);
//                    setFacilityAdapter(myl);
//                    for(int x=0;x<myl.size();x++){
//                        String countyId=myl.get(x).getCountyid();
//                        setSubCountyAdapter(countyId);
//                    }




                    facilitySpinnerEdt.setVisibility(View.VISIBLE);
                    facilitySpinnerEdt.setText("");


                    facilityList.clear();
                    getFacilities(selectedSbcty);

//                    setFacilityAdapter();

//                    for(int x=0;x<myl.size();x++){
//                        String countyId=myl.get(x).getCountyid();
//                        setSubCountyAdapter(countyId);
//                    }







                }
            });

        } catch (Exception e) {


        }
    }



    public void setsbCountyAdapter() {

        try {


            ArrayList<String> x = new ArrayList<>();
            x.clear();

            for(int x1=0;x1<countyList.size();x1++){
                x.add(countyList.get(x1).getName());
            }

//            List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata group by countyname");
//            System.out.println("************getting countyies**************");
//            if (myl.size() > 0) {
//
//                for (int y = 0; y < myl.size(); y++) {
//                    x.add(myl.get(y).getCountyname());
//                    System.out.println(myl.get(y).getCountyname());
//
//                }
//
//
//            }


            arrayAdapterCounty = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, x);
        } catch (Exception e) {


        }
    }

//
//    public void setSubCountyAdapter(ArrayList<SubCountyModel> sbctyList) {
//
//        try {
//
//            ArrayList<String> y = new ArrayList<>();
//
//            for (int x = 0; x < sbctyList.size(); x++) {
//                String sbctyname = sbctyList.get(x).getName();
//                y.add(sbctyname);
//
//            }
//
//
//            arrayAdapterSubCounty = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_checked, y);
//
//
//            sbctyM.setAdapter(arrayAdapterSubCounty);
//            setSpinnerSubCountyListener();
//        } catch (Exception e) {
//
//
//        }
//    }


    public void setSearchSpinnerAdapter(ArrayList<String> y) {

        try {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, y);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//        R.array.symptoms, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {

        }
    }




    public void setFacilityAdapter() {

        try {

            ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < facilityList.size(); x++) {
                String faciityname = facilityList.get(x).getName();
                y.add(faciityname);

            }


            arrayAdapterFacility = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, y);
            arrayAdapterFacility.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {


        }
    }


    //set subcounty spiner logic here

    //set county spinner logic here



    public void setFac1Adapter() {

        try {


            ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < facilityList.size(); x++) {
                String faciityname = facilityList.get(x).getName();
                y.add(faciityname);

            }


            arrayAdapterFacility = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, y);
            arrayAdapterFacility.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {


        }
    }

    public void setSub1CountyAdapter() {

        try {


            ArrayList<String> x = new ArrayList<>();
            x.clear();

            for(int x1=0;x1<subcountyList.size();x1++){
                x.add(subcountyList.get(x1).getName());
            }

//            List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata group by countyname");
//            System.out.println("************getting countyies**************");
//            if (myl.size() > 0) {
//
//                for (int y = 0; y < myl.size(); y++) {
//                    x.add(myl.get(y).getCountyname());
//                    System.out.println(myl.get(y).getCountyname());
//
//                }
//
//
//            }


            arrayAdapterSubCounty = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, x);
        } catch (Exception e) {


        }
    }


    public void setCountyAdapter() {

        try {


            ArrayList<String> x = new ArrayList<>();
            x.clear();

            for(int x1=0;x1<countyList.size();x1++){
                x.add(countyList.get(x1).getName());
            }

//            List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata group by countyname");
//            System.out.println("************getting countyies**************");
//            if (myl.size() > 0) {
//
//                for (int y = 0; y < myl.size(); y++) {
//                    x.add(myl.get(y).getCountyname());
//                    System.out.println(myl.get(y).getCountyname());
//
//                }
//
//
//            }


            arrayAdapterCounty = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, x);
        } catch (Exception e) {


        }
    }




    public void setPartnerClickListener() {

        try {

            partnerorgE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    partnerorgE.setText("");
                    itemsSelected.clear();
                    displayMultiselectForPartner();


                }
            });

        } catch (Exception e) {


        }
    }

    public void setSpecialisationClickListener() {

        try {

            specialisationE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    specialisationE.setText("");
                    itemsSelectedSpecialisation.clear();
                    displayMultiselectForSpecialisation();


                }
            });

        } catch (Exception e) {


        }
    }


    public void CheckToperiodListener() {

        try {

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
                                    String dom = String.format("%02d", dayOfMonth);
                                    String moy = String.format("%02d", (monthOfYear + 1));

                                    ageE.setText(dom + "/"
                                            + moy + "/" + year);
                                    selectedYear = year;

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }






    public long calculateDateDifference(String date1, String date2) {

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


        } catch (Exception e) {
            Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();
            System.out.println("*********errorr***********" + e);

            return -1;


        }
    }


    public void displayMultiselectForPartner() {

        try {


            final boolean selected[] = new boolean[]{false,false, false, false, false, false, false, false,false,false,false,false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Affiliation:");
            builder.setMultiChoiceItems(Config.itemsorg, selected,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {

                            if (isSelected) {


                                //logic

                                for (int i = 0; i < selected.length; i++) {
                                    if (i == selectedItemId) {

                                        selected[i] = true;
                                        itemsSelected.add(selectedItemId);


                                    }

                                }

                                if (selected[11]) {//check for Not Applicable

                                    for (int x = 0; x < selected.length; x++) {

                                        itemsSelected.remove(Integer.valueOf(x));
                                        selected[x] = false;
                                        ((android.app.AlertDialog) dialog).getListView().setItemChecked(x, false);
                                        if (x == 11) {
                                            selected[11] = true;
                                            itemsSelected.add(11);

                                            ((android.app.AlertDialog) dialog).getListView().setItemChecked(11, true);
//                                            continue;
                                        }
                                    }


                                }
//                                else if (selected[10]) {
//
//
//                                    kmpduChecked = true;
//                                    dunumber.setVisibility(View.VISIBLE);
//                                    specialisationE.setVisibility(View.VISIBLE);
//
//                                    specialisel.setVisibility(View.VISIBLE);
//                                    idnoE.setVisibility(View.GONE);
//
//
//                                    ctyM.setVisibility(View.GONE);
//                                    sbctyM.setVisibility(View.GONE);
//
//
//                                    myspinner2.setVisibility(View.GONE);
//                                    cadrel.setVisibility(View.GONE);
//
//
//                                }

//                                else if (!selected[10]) {//if kmpdu is not checked
//                                    kmpduChecked = false;
//                                    dunumber.setVisibility(View.GONE);
//
//                                    specialisationE.setVisibility(View.GONE);
//                                    specialisel.setVisibility(View.GONE);
//
//                                    idnoE.setVisibility(View.VISIBLE);
//
//
//                                    ctyM.setVisibility(View.VISIBLE);
//                                    sbctyM.setVisibility(View.VISIBLE);
//
//                                    myspinner2.setVisibility(View.VISIBLE);
//                                    cadrel.setVisibility(View.VISIBLE);
////                        motherE.setVisibility(View.GONE);
//
//
//                                }


                            } else if (itemsSelected.contains(selectedItemId)) {
                                itemsSelected.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId] = false;

//                                if (selectedItemId == 10) { //check for kmpdu if unchecked
//
//
//                                    kmpduChecked = false;
//                                    dunumber.setVisibility(View.GONE);
//
//                                    specialisel.setVisibility(View.GONE);
//                                    specialisationE.setVisibility(View.GONE);
//                                    idnoE.setVisibility(View.VISIBLE);
//
//
//                                    ctyM.setVisibility(View.VISIBLE);
//                                    sbctyM.setVisibility(View.VISIBLE);
//
//
//                                    myspinner2.setVisibility(View.VISIBLE);
//                                    cadrel.setVisibility(View.VISIBLE);
////                        motherE.setVisibility(View.GONE);
//
//
//                                }
                            }

                        }
                    })
                    .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked

                            Iterator<String> it = itemsSelected.iterator();

                            while (it.hasNext()) {

                                partnerorgE.append(Config.itemsorg[Integer.parseInt(String.valueOf(it.next()))]);


                                if (it.hasNext()) {
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


                            ctyM.setVisibility(View.VISIBLE);
                            sbctyM.setVisibility(View.VISIBLE);


                            myspinner2.setVisibility(View.VISIBLE);
                            cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);


                        }
                    });
            partnerdialog = builder.create();
            partnerdialog.show();


        } catch (Exception e) {


        }
    }


    public void displayMultiselectForSpecialisation() {

        try {


            final boolean selected[] = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false, false, false, false, false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Specialisation: ");
            builder.setMultiChoiceItems(Config.itemsspecialisation, selected,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {

                            if (isSelected) {


                                //logic

                                for (int i = 0; i < selected.length; i++) {
                                    if (i == selectedItemId) {

                                        selected[i] = true;

                                    }

                                }


                                //logic

                                itemsSelectedSpecialisation.add(selectedItemId);
                            } else if (itemsSelectedSpecialisation.contains(selectedItemId)) {
                                itemsSelectedSpecialisation.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId] = false;

                            }
                        }
                    })
                    .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked
                            if (itemsSelectedSpecialisation.size() > 1) {
                                Toast.makeText(CreateUser.this, "Select only one specialisation", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if (itemsSelectedSpecialisation.size() == 1) {

                                specialisationE.append(Config.itemsspecialisation[Integer.parseInt(itemsSelectedSpecialisation.get(0).toString())]);


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


        } catch (Exception e) {


        }
    }


    public void initialise() {

        try {


            //hepatitis b
            ut=new UpdateStatusTable(CreateUser.this);
            grphepatitis =(RadioGroup) findViewById(R.id.radiogrphepatitis);
            grphepatitissecondose =(RadioGroup) findViewById(R.id.radiogrphepatitisseconddose);

            llhepadoselayout1=(LinearLayout) findViewById(R.id.hapatitisdoselayout);
            llhepadoseseconddose=(LinearLayout) findViewById(R.id.llhepaseconddose);

            hepatitisdose1E=(EditText) findViewById(R.id.hepatitisdose1);
            hepatitisdose2E=(EditText) findViewById(R.id.hepatitisdose2);

            //hepatits b
            pr=new Progress(CreateUser.this);
            countyList=new ArrayList<>();
            facilityList=new ArrayList<>();
            subcountyList=new ArrayList<>();

            requestPerms=new RequestPerms(CreateUser.this,this);
            chkInternet=new CheckInternet(CreateUser.this);
            accessServer=new AccessServer(CreateUser.this);

            selectedCty = "";
            selectedSbcty = "";
            selectedFacility = "";
            facilitySpinnerEdt =(EditText) findViewById(R.id.facilityspinner);


            ctyM = (MaterialBetterSpinner) findViewById(R.id.county_txt);

            sbctyM = (MaterialBetterSpinner) findViewById(R.id.subcounty_txt);


            grd = new GetRemoteData(CreateUser.this);
            sweetdialog = new Dialogs(CreateUser.this);
            dcalc = new DateCalculator();
            partnerorgE = (EditText) findViewById(R.id.partorg);
            specialisationE = (EditText) findViewById(R.id.specialisationselect);

            specialisel = (TextView) findViewById(R.id.specialisationlabel);
            cadrel = (TextView) findViewById(R.id.cadrelabel);
            kmpduChecked = false;
            dunumber = (EditText) findViewById(R.id.du);
            otherValue = "";
            motherE = (EditText) findViewById(R.id.myother);

            idnoE = (EditText) findViewById(R.id.idno);
            ageE = (EditText) findViewById(R.id.age);


            myspinner = (Spinner) findViewById(R.id.spinnergender);
            myspinner2 = (Spinner) findViewById(R.id.spinner2);




            partners = new StringBuilder();


        } catch (Exception e) {


        }
    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;

        if (spin.getId() == R.id.spinnergender) {


//            selected_item=parent.getItemAtPosition(position).toString();
            myselectedgender = Integer.toString(position);
            Toast.makeText(this, "selected "+myselectedgender, Toast.LENGTH_SHORT).show();
            actOnSelected();

        }
        else if (spin.getId() == R.id.spinner2) {
            try {

//                selected_item2=parent.getItemAtPosition(position).toString();

                if (position == 9) {


                    motherE.setVisibility(View.VISIBLE);

                } else {
                    motherE.setText("");
                    motherE.setVisibility(View.GONE);

                }

                myselected2 = Integer.toString(position);

                actOnSelected();


            } catch (Exception e) {


            }


        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void actOnSelected() {

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselectedgender, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }


    public void populateSpinner() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), genders);


            myspinner.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateSpinner2() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), cadres);

            myspinner2.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }



    public void populatePartnerTable() {

        try {

            Partners.deleteAll(Partners.class);

            if (itemsSelected.size() > 0) {

                for (int x = 0; x < itemsSelected.size(); x++) {

                    Partners pt = new Partners();
                    pt.setPartnername(Config.itemsorg[Integer.parseInt(itemsSelected.get(x).toString())]);
                    pt.save();
                }

            }

        } catch (Exception e) {
            Toast.makeText(this, "error populating partner table " + e, Toast.LENGTH_SHORT).show();


        }
    }

    //ill get back here

    public void CreatingUser(View v) {

        try {

           //***save hpbdata**
            updateHPBData();

            populatePartnerTable();

            partners = new StringBuilder("");


            if (itemsSelected.size() == 1) {
                String val = Integer.toString((Integer.parseInt(itemsSelected.get(0).toString())) + 1);
                partners.append(val);
            } else if (itemsSelected.size() > 1) {


                for (int x = 0; x < itemsSelected.size(); x++) {
                    String val = Integer.toString((Integer.parseInt(itemsSelected.get(x).toString())) + 1);

                    partners.append(val + "+");

                }
            }
//            partners.append("5");


//            Toast.makeText(this, ""+partners, Toast.LENGTH_SHORT).show();


            String duns = "";


            String myidno = idnoE.getText().toString();
            String myage = ageE.getText().toString();
            String mymfl = "";
            String mdose1 = "-1";
            String mdose2 = "-1";



            int curyear = Calendar.getInstance().get(Calendar.YEAR);
//            idnoE.setVisibility(View.VISIBLE);
//            nameE.setVisibility(View.VISIBLE);
//            motherE.setVisibility(View.VISIBLE);
//            lnameE.setVisibility(View.VISIBLE);

           if (!kmpduChecked && myidno.trim().isEmpty()) {
                idnoE.setError("ID NUMBER is Required");

            }
            else if(dunumber.isShown()&& dunumber.getText().toString().trim().isEmpty()){
                dunumber.setError("Doctors union number is required");

            }
            else if (!kmpduChecked && myidno.length() < 4) {

                idnoE.setError("ID NUMBER should contain more than 3 values");

            } else if (myage.isEmpty()) {
                ageE.setError("Age is Required");

            } else if (!kmpduChecked && selectedFacility.trim().isEmpty()) {
                Toast.makeText(this, "MFL Number is Required", Toast.LENGTH_SHORT).show();

            } else if (partners.toString().isEmpty()) {
                sweetdialog.showErrorDialogRegistration("Select atleast one partner", "Registration Error");

//                Toast.makeText(this, "select atleast one partner", Toast.LENGTH_SHORT).show();
            } else if ((curyear - selectedYear) < 18) {

                sweetdialog.showErrorDialogRegistration("age should be greater than 18, try again", "Registration Error");


//                Toast.makeText(this, "age should be greater than 18, try again", Toast.LENGTH_LONG).show();
            } else if (myselectedgender.contentEquals("0")) {

                sweetdialog.showErrorDialogRegistration("Please Select Gender", "Registration Error");

//                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_LONG).show();


            } else if (!kmpduChecked && myselected2.contentEquals("0")) {

                sweetdialog.showErrorDialogRegistration("Please select Cadre", "Registration Error");

//                Toast.makeText(this, "Please select Cadre", Toast.LENGTH_LONG).show();


            }
            else if (specialisationE.isShown()&& specialisationE.getText().toString().trim().isEmpty()) {

                sweetdialog.showErrorDialogRegistration("Please select specialisation", "Registration Error");

//                Toast.makeText(this, "Please select Cadre", Toast.LENGTH_LONG).show();


            }

            else {


                //new code here

                if (kmpduChecked) {


                    selectedspecialisation=specialisationE.getText().toString();


                    duns = dunumber.getText().toString();
                    mymfl = "13528";
                    myidno = "-1";

                    checkFacilityCode(myidno, myage, mymfl, partners, duns, selectedspecialisation);



                } else {

                    if(specialisationE.isShown()){
                        selectedspecialisation=specialisationE.getText().toString();
                    }
                    else{
                        selectedspecialisation="-1";
                    }


                    mymfl = selectedFacility;
                    duns = "-1";

                    checkFacilityCode(myidno, myage, mymfl, partners, duns, selectedspecialisation);



//                    List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where facilityname=? limit 1", mymfl);
//                    for (int d = 0; d < myl.size(); d++) {
//
//                        String newmflcode = myl.get(d).getMflcode();
//                        checkFacilityCode(myidno, myage, newmflcode, partners, duns, selectedspecialisation);
//
//                    }

                }



                //new code here



            }

        } catch (Exception e) {

            sweetdialog.showErrorDialogRegistration("Error occured " + e, "Registration Error");

//            SignupdisplayDialog("Error occured "+e);


        }
    }


    public void SignupsuccessDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("PROFILE CREATION SUCCESS");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    if (kmpduChecked) {
                        kmpdu km = new kmpdu("true");
                        km.save();

                        Intent myint = new Intent(getApplicationContext(), LandingPage.class);

                        startActivity(myint);

                    } else {

                        kmpdu mykm = new kmpdu("false");
                        mykm.save();

                        Intent myint = new Intent(getApplicationContext(), LandingPage.class);

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


            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }


    public void clearFields() {

        try {

//            mpassf.setText("");
//            mcpassf.setText("");
//            munamef.setText("");


        } catch (Exception e) {


        }
    }


    public void checkFacilityCode(final String myidno, final String myage, final String mymfl, final StringBuilder partner, final String duns, final String sspecial) {


        final ProgressDialog pdialog = mydialog("loading...", "Creating Profile");

        pdialog.cancel();


            Registrationdatatable rt = Registrationdatatable.findById(Registrationdatatable.class, 1);
            rt.gender = myselectedgender;
            rt.cadre = myselected2;
            rt.idnumber = myidno;
            rt.age = myage;
            rt.mflcode = mymfl;
            rt.myhepa = "-1";
            rt.save();
//
//                                    Registrationdatatable rt=new Registrationdatatable("","",myselectedgender,myselected2,myidno,myage,mymfl,myselected3,"","","","");
//                                    rt.save();
            String myoth = "";
            String phoneS="";
            String newPhoneS="";
            String mfname="";
            String mlname="";





            myoth = motherE.getText().toString();

        List<Signup> myn=Signup.findWithQuery(Signup.class,"select * from Signup limit 1");
        for(int p=0;p<myn.size();p++){

            phoneS=myn.get(p).getPhone();

            Userphonenumber.deleteAll(Userphonenumber.class);
            Userphonenumber up=new Userphonenumber(phoneS);
            up.save();
            newPhoneS="+254"+phoneS.substring(1);



        }

                    Profiletable pt=new Profiletable(mymfl);
                    pt.save();

                    //save user details for further editing
                    Edittable.deleteAll(Edittable.class);
                    Edittable et=new Edittable(mymfl,phoneS,myidno);
                    et.save();



                    accessServer.createProfile(partner.toString(),sspecial,myselectedgender,myselected2,myidno,myage,mymfl,newPhoneS);





//
//        if(chkInternet.isInternetAvailable()){
//
//
//
//                //********************create profile submit starts here***************************************
//
//
//
//                Profiletable.deleteAll(Profiletable.class);
//
//
//
//                if (kmpduChecked) {
//
//                    Profiletable pt=new Profiletable("n/a");
//                    pt.save();
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//
//
//                    accessServer.createProfile(partner.toString(),sspecial,myselectedgender,myselected2,myidno,myage,mymfl,newPhoneS,kmpduChecked);
//
//
//                } else if (!motherE.isShown()) {
//
//                    Profiletable pt=new Profiletable(mymfl);
//                    pt.save();
//
//
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//                    accessServer.createProfile(partner.toString(),sspecial,myselectedgender,myselected2,myidno,myage,mymfl,newPhoneS,kmpduChecked);
//
//
//                } else if (motherE.isShown()) {
//                    myoth=motherE.getText().toString();
//                    Profiletable pt=new Profiletable(mymfl);
//                    pt.save();
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//                    accessServer.createProfile(partner.toString(),sspecial,myselectedgender,myoth,myidno,myage,mymfl,newPhoneS,kmpduChecked);
//
//                }
//
//
////                populatePartners();
//
////                SignupsuccessDialog("Success in Creating Profile");
//
////********************create profile submit ends here***************************************
//
//
//            }

//            else{
//
//
////********************create profile submit starts here***************************************
//
//                String mymess = "";
//
//                Profiletable.deleteAll(Profiletable.class);
//
//
//
//                if (kmpduChecked) {
//
//                    Profiletable pt=new Profiletable("n/a");
//                    pt.save();
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//
//                    mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + "-1" + "*" + "-1" + "*" + duns + "*" + sspecial +"*"+partner);
//
//
//                } else if (!motherE.isShown()) {
//
//                    Profiletable pt=new Profiletable(mymfl);
//                    pt.save();
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//                    mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + myselected2 + "*" + mymfl + "*" + duns + "*" + sspecial +"*"+partner);
//
//
//                } else if (motherE.isShown()) {
//
//                    myoth=motherE.getText().toString();
//                    Profiletable pt=new Profiletable(mymfl);
//                    pt.save();
//
//                    //save user details for further editing
//                    Edittable.deleteAll(Edittable.class);
//                    Edittable et=new Edittable(mymfl,phoneS,myidno);
//                    et.save();
//
//                    mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + myoth + "*" + mymfl + "*" + duns + "*" + sspecial +"*"+partner);
//
//                }
//
//
////                populatePartners();
//
//                SmsManager sm = SmsManager.getDefault();
//                ArrayList<String> parts = sm.divideMessage(mymess);
//
//                sm.sendMultipartTextMessage(Config.shortcode, null, parts, null, null);
//
//
//
////            SmsManager smsM = SmsManager.getDefault();
////            smsM.sendTextMessage(Config.shortcode, null, mymess, null, null);
//                SignupsuccessDialog("Success in Creating Profile");
//
////********************create profile submit ends here***************************************
//
//
//            }




//        pr.progressing(getApplicationContext(),"getting facility","loading....");


    }

    public ProgressDialog mydialog(String message, String title) {
        ProgressDialog progress = new ProgressDialog(this);

        try {


            progress.setMessage(message);
            progress.setTitle(title);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        } catch (Exception e) {
//            Toast.makeText(this, "error displaying progress", Toast.LENGTH_SHORT).show();

        }

        return progress;
    }

    public void getRemoteData() {

        try {
//            Toast.makeText(this, "getting data", Toast.LENGTH_SHORT).show();

            List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata limit 1");
            if (myl.size() > 0) {


            } else {
                grd.getFacilityData();

            }

        } catch (Exception e) {


        }
    }

    public void requestPerms() {

        try {

            requestPerms.requestPerms();

        } catch (Exception e) {
            Toast.makeText(this, "error in granting permissions " + e, Toast.LENGTH_SHORT).show();


        }
    }


    public void populatePartners() {

        try {


            EditText poE = (EditText) findViewById(R.id.partorg);
            String poS = poE.getText().toString();
            if (!poS.trim().isEmpty()) {

                String[] posArr = poS.split(",");
                for (int x = 0; x < posArr.length; x++) {

                    Regpartners rp = new Regpartners(posArr[x]);
                    rp.save();
                }
            }

            List<Regpartners> rpl = Regpartners.findWithQuery(Regpartners.class, "select * from Regpartners");
            System.out.println("******************partners************************");

            for (int x = 0; x < rpl.size(); x++) {


                System.out.println("partner name: " + rpl.get(x).name);

            }

            System.out.println("******************partners************************");


        } catch (Exception e) {

            Toast.makeText(this, "error populating partners " + e, Toast.LENGTH_SHORT).show();


        }
    }


    //check if the provided password matches the regular expression
    public boolean isTextValid(String textToCheck) {
        return textPattern.matcher(textToCheck).matches();
    }






    //***********************************function to get data from server*************************/


    public void getCounties(){


        try{

            pr.showProgress("getting counties....");


            StringRequest stringRequest = new StringRequest(GET,Config.GETCOUNTY_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            pr.dissmissProgress();

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_county_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMyCounties(id_county_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            Toast.makeText(getApplicationContext(), "Check your internet connection and try again", Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getApplicationContext(), "error occured "+error, Toast.LENGTH_SHORT).show();

                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

//                    params.put(Config.KEY_BORROWINGSPOSTEMAIL, sessionEmail);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(CreateUser.this);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

//            Toast.makeText(CreateUser.this, "error getting BORROWINGS "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyCounties(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String cid=json.getString(Config.KEY_COUNTYID);
                String cname=json.getString(Config.KEY_COUNTYNAME);

                System.out.println("********"+cname+"*******");

                CountyModel bm= new CountyModel(cid,cname);

                countyList.add(bm);





            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting counties "+ e, Toast.LENGTH_SHORT).show();
            }
        }


    }





    public void getSubCounties(final String ctyid){


        try{

            pr.showProgress("getting subcounties....");

            StringRequest stringRequest = new StringRequest(POST,Config.GETSUBCOUNTY_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            pr.dissmissProgress();
                            System.out.println("**************responsess************");
                            System.out.println(response);
//                            Toast.makeText(CreateUser.this, ""+response, Toast.LENGTH_SHORT).show();

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_subcounty_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMySubCounties(id_subcounty_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            Log.e("Response", error.toString());
                            System.out.println("***********subcounty response********");
                            System.out.println(error);

                            Toast.makeText(getApplicationContext(), "Check your internet connection and try again "+error, Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getApplicationContext(), "error occured "+error, Toast.LENGTH_SHORT).show();

                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("cntyID", ctyid);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Content-Type", "text/html; charset=utf-8");
//                    return params;
//                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(CreateUser.this);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

//            Toast.makeText(CreateUser.this, "error getting subcounties "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMySubCounties(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String scid=json.getString(Config.KEY_SUBCOUNTYID);
                String scname=json.getString(Config.KEY_SUBCOUNTYNAME);
                System.out.println("name:"+scname);
                System.out.println("id: "+scid);

                SubCountyModel bm= new SubCountyModel(scid,scname);

                subcountyList.add(bm);






            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting subcounties "+ e, Toast.LENGTH_SHORT).show();
            }
        }


    }











    public void getFacilities(final String sbctyid){


        try{

            pr.showProgress("getting facilities...");

            StringRequest stringRequest = new StringRequest(POST,Config.GETFACILITY_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************facilities*********************");
                            System.out.println(response);

                            pr.dissmissProgress();

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_facility_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMyFacilities(id_facility_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            Toast.makeText(getApplicationContext(), "Check your internet connection and try again", Toast.LENGTH_SHORT).show();

                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("mfl", sbctyid);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(CreateUser.this);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

//            Toast.makeText(CreateUser.this, "error getting Facilities "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyFacilities(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String fcode=json.getString(Config.KEY_FACILITYCODE);
                String fname=json.getString(Config.KEY_FACILITYNAME);

                FacilityModel bm= new FacilityModel(fcode,fname);

                facilityList.add(bm);





            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }

        addListenerToFacilitySpinnerEdt();


    }

    //***********************************function to get data from server*************************/



    //************************start hepatits b listeners function*******************************/




    public void getCheckedRadioHepatitis(){

        try{


            grphepatitis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnhepaqn1 = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnhepaqn1 && checkedId > -1) {

                        String selectedOption=radiobtnhepaqn1.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
//                            llinfluenza.setVisibility(View.GONE);
//                            influenzadoseE.setText("");
//                            influenzapregnantS="yes";
                            displayHepatitisDoseLinearlayout(true);
                            getCheckedRadioHepatitisDose2();

//                            Checkedinfluenza.deleteAll(Checkedinfluenza.class);
//                            Checkedinfluenza chi=new Checkedinfluenza(Integer.toString(checkedId));
//                            chi.save();

                            List<Hepatitis> myl=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
                            if(myl.size()>0){

                                Hepatitis inf = Hepatitis.findById(Hepatitis.class, 1);

                                inf.setImmunisedvaluedose1("Yes");
                                inf.setImmunisediddose1("2131296553");
                                inf.save();

                            }
                            else{

                                Hepatitis inf=new Hepatitis();
                                inf.setImmunisedvaluedose1("Yes");
                                inf.setImmunisediddose1("2131296553");
                                inf.save();

                            }

                        }
                        else if(selectedOption.equalsIgnoreCase("partially")){
//                            llinfluenza.setVisibility(View.GONE);
//                            influenzadoseE.setText("");
//                            influenzapregnantS="yes";
                            displayHepatitisDoseLinearlayout(true);
                            getCheckedRadioHepatitisDose2();

//                            Checkedinfluenza.deleteAll(Checkedinfluenza.class);
//                            Checkedinfluenza chi=new Checkedinfluenza(Integer.toString(checkedId));
//                            chi.save();

                            List<Hepatitis> myl=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
                            if(myl.size()>0){

                                Hepatitis inf = Hepatitis.findById(Hepatitis.class, 1);

                                inf.setImmunisedvaluedose1("Partially");
                                inf.setImmunisediddose1("2131296552");
                                inf.save();

                            }
                            else{

                                Hepatitis inf=new Hepatitis();
                                inf.setImmunisedvaluedose1("Partially");
                                inf.setImmunisediddose1("2131296552");
                                inf.save();

                            }

                        }
                        else{
                            displayHepatitisDoseLinearlayout(false);
//                            displayTrimesterLinearlayout(false);
////                            llinfluenza.setVisibility(View.VISIBLE);
//                            influenzapregnantS="no";


                            List<Hepatitis> myl=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
                            if(myl.size()>0){

                                Hepatitis inf = Hepatitis.findById(Hepatitis.class, 1);

                                inf.setImmunisedvaluedose1("No");
                                inf.setImmunisediddose1("2131296550");
                                inf.save();

                            }
                            else{

                                Hepatitis inf=new Hepatitis();
                                inf.setImmunisedvaluedose1("No");
                                inf.setImmunisediddose1("2131296550");
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



    public void getCheckedRadioHepatitisDose2(){

        try{


            grphepatitissecondose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radiobtnhepaseconddose = (RadioButton) group.findViewById(checkedId);
                    if (null != radiobtnhepaseconddose && checkedId > -1) {

                        String selectedOption=radiobtnhepaseconddose.getText().toString();
                        if(selectedOption.equalsIgnoreCase("Yes")){
//                            llinfluenza.setVisibility(View.GONE);
//                            influenzadoseE.setText("");
//                            influenzapregnantS="yes";
                            displayHepatitisDoseLinearlayout2(true);

//                            Checkedinfluenza.deleteAll(Checkedinfluenza.class);
//                            Checkedinfluenza chi=new Checkedinfluenza(Integer.toString(checkedId));
//                            chi.save();

                            List<Hepatitis> myl=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
                            if(myl.size()>0){

                                Hepatitis inf = Hepatitis.findById(Hepatitis.class, 1);

                                inf.setImmunisedvaluedose2("Yes");
                                inf.setImmunisediddose2("2131296554");
                                inf.save();

                            }
                            else{

                                Hepatitis inf=new Hepatitis();
                                inf.setImmunisedvaluedose2("Yes");
                                inf.setImmunisediddose2("2131296554");
                                inf.save();

                            }

                        }

                        else{
                            displayHepatitisDoseLinearlayout2(false);
//                            displayTrimesterLinearlayout(false);
////                            llinfluenza.setVisibility(View.VISIBLE);
//                            influenzapregnantS="no";


                            List<Hepatitis> myl=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
                            if(myl.size()>0){

                                Hepatitis inf = Hepatitis.findById(Hepatitis.class, 1);

                                inf.setImmunisedvaluedose2("No");
                                inf.setImmunisediddose2("2131296551");
                                inf.save();

                            }
                            else{

                                Hepatitis inf=new Hepatitis();
                                inf.setImmunisedvaluedose2("No");
                                inf.setImmunisediddose2("2131296551");
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



    public void hepatitisDose1DateListener(){
        try{

            DateListener(hepatitisdose1E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying hepatitis dose datepicker", Toast.LENGTH_SHORT).show();
        }

    }

    public void hepatitisDose2DateListener(){
        try{

            DateListener(hepatitisdose2E);

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying hepatitis dose datepicker", Toast.LENGTH_SHORT).show();
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
                    datePickerDialog = new DatePickerDialog(CreateUser.this,
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






    public void displayHepatitisDoseLinearlayout(boolean show){

        try{

            if(show){

                llhepadoselayout1.setVisibility(View.VISIBLE);

            }
            else{

                llhepadoselayout1.setVisibility(View.GONE);


            }
        }
        catch(Exception e){


        }
    }



    public void displayHepatitisDoseLinearlayout2(boolean show){

        try{

            if(show){

                llhepadoseseconddose.setVisibility(View.VISIBLE);

            }
            else{

                llhepadoseseconddose.setVisibility(View.GONE);


            }
        }
        catch(Exception e){


        }
    }



    private void saveAllStatusVaccine(){

        try{


            ut.updateHepatits();

        }
        catch(Exception e){


        }
    }

    private void updateHPBData(){

        try{

            List<Hepatitis> mylhepatits=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");


            //set hepatitis data
            if(mylhepatits.size()>0){



                Hepatitis infv = Hepatitis.findById(Hepatitis.class, 1);
                if(hepatitisdose1E!=null){

                    infv.setFirstdosedate(hepatitisdose1E.getText().toString());

                }
                else{

                    infv.setFirstdosedate("");
                }

                if(hepatitisdose2E!=null){

                    infv.setSeconddosedate(hepatitisdose2E.getText().toString());

                }
                else{

                    infv.setSeconddosedate("");
                }

                infv.save();

                saveAllStatusVaccine();

            }

            else{

                Hepatitis chi=new Hepatitis();

                if(hepatitisdose1E!=null){


                    chi.setFirstdosedate(hepatitisdose1E.getText().toString());
                }
                else{

                    chi.setFirstdosedate("");
                }

                if(hepatitisdose2E!=null){


                    chi.setSeconddosedate(hepatitisdose2E.getText().toString());
                }
                else{

                    chi.setSeconddosedate("");
                }

                chi.save();
                saveAllStatusVaccine();

            }
            //end set hepatitis data

        }
        catch(Exception e){


        }
    }

    //************************end hapatitis b listeners function*********************************/
}
