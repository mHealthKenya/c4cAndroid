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

import com.facebook.stetho.Stetho;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import mhealth.c4c.GetRemoteData.GetRemoteData;
import mhealth.c4c.Registrationtable.Regpartners;
import mhealth.c4c.Tables.Facilitydata;
import mhealth.c4c.Tables.Partners;
import mhealth.c4c.Tables.UserTable;
import mhealth.c4c.Tables.kmpdu;
import mhealth.c4c.config.Config;
import mhealth.c4c.dateCalculator.DateCalculator;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.encryption.Base64Encoder;

/**
 * Created by KENWEEZY on 2016-10-31.
 */


public class CreateUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText idnoE, ageE, motherE, dunumber, dose1E, dose2E, facilitySpinnerEdt,exposureselectE,howmanyexposureE,pepinitiateselectE;
    SpinnerDialog spinnerDialog;
    CheckBox mchkb;
    String otherValue;
    TextView specialisel, cadrel;
    LinearLayout doselayout;
    String selectedspecialisation;

    RadioGroup radio2dosegrp;
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

    String passedkmpdu;

    boolean correctMfl;


    public static final String KEY_MFLCODE = "facility_code";

    String[] genders = {"Please Select Gender", "Male", "Female"};
    String[] cadres = {"Please Select Cadre", "Student", "Doctor", "Nurse", "Clinical Officer", "Laboratory Technologist", "Cleaner", "Waste Handlers", "VCT Counselor", "Other"};
    String[] hepa = {"Have you been vaccinated against Hepatitis B?", "Yes", "Partially", "No"};


    List<UserTable> user_list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    int selectedYear;
    Spinner myspinner;
    Spinner myspinner2;
    Spinner myspinner3;


    String selected_item = "";
    String myselectedgender = "";
    String selected_item2 = "";
    String myselected2 = "";
    String myselected3 = "";

    StringBuilder partners;

    boolean kmpduChecked;
    DateCalculator dcalc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);
        requestPerms();

        initialise();


        addListenerToExposureSelect();


        CheckToperiodListener();

        populateSpinner();
        populateSpinner2();
        populateSpinner3();

        myspinner.setOnItemSelectedListener(this);
        myspinner2.setOnItemSelectedListener(this);
        myspinner3.setOnItemSelectedListener(this);


        setPartnerClickListener();
        setSpecialisationClickListener();
        dose1InputListener();
        dose2InputListener();
        Stetho.initializeWithDefaults(this);


    }


    public void addListenerToPepinitSelect(){

        try{
            pepinitiateselectE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    spinnerDialog=new SpinnerDialog(CreateUser.this,Config.YESNOARRAY,"Select an option",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation


                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
//                            Toast.makeText(CreateUser.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
//                            selectedFacility = item;
                            pepinitiateselectE.setText(item);
                        }
                    });

                    spinnerDialog.showSpinerDialog();

                }
            });


        }
        catch(Exception e){


        }
    }


    public void addListenerToExposureSelect(){

        try{
            exposureselectE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    spinnerDialog=new SpinnerDialog(CreateUser.this,Config.YESNOARRAY,"Select an option",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation


                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
//                            Toast.makeText(CreateUser.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
//                            selectedFacility = item;

                            if(item.equalsIgnoreCase("Yes")){

                                howmanyexposureE.setVisibility(View.VISIBLE);
                                pepinitiateselectE.setVisibility(View.VISIBLE);
                                addListenerToPepinitSelect();
                            }
                            else{

                                howmanyexposureE.setVisibility(View.GONE);
                                pepinitiateselectE.setVisibility(View.GONE);
                                howmanyexposureE.setHint("");
                                pepinitiateselectE.setHint("");
                            }
                            exposureselectE.setText(item);
                        }
                    });

                    spinnerDialog.showSpinerDialog();

                }
            });


        }
        catch(Exception e){


        }
    }


    public void addListenerToFacilitySpinnerEdt(List<Facilitydata> myl){

        try{


           final ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < myl.size(); x++) {
                String faciityname = myl.get(x).getFacilityname();
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
        setFacilitySpinnerData();
    }

    public void setFacilitySpinnerData() {

        try {

            getRemoteData();

            setCountyAdapter();
            setSpinnerCountyListener();

            setSpinnerAdapters();
        } catch (Exception e) {


        }
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
//        just to make sure there is always data shown on our county list
        setCountyAdapter();
        setSpinnerCountyListener();
        setSpinnerAdapters();
    }




    public void setSpinnerAdapters() {

        try {
            ctyM.setAdapter(arrayAdapterCounty);


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
                    sbctyM.setVisibility(View.VISIBLE);
                    List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where countyname=? group by subcountyname", selectedCty);
                    setSubCountyAdapter(myl);
//                    for(int x=0;x<myl.size();x++){
//                        String countyId=myl.get(x).getCountyid();
//                        setSubCountyAdapter(countyId);
//                    }

                    sbctyM.setAdapter(arrayAdapterSubCounty);
                    setSpinnerSubCountyListener();

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



                    List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where subcountyname=? group by facilityname", selectedSbcty);
                    setFacilityAdapter(myl);
//                    for(int x=0;x<myl.size();x++){
//                        String countyId=myl.get(x).getCountyid();
//                        setSubCountyAdapter(countyId);
//                    }




                    facilitySpinnerEdt.setVisibility(View.VISIBLE);

                    addListenerToFacilitySpinnerEdt(myl);


                }
            });

        } catch (Exception e) {


        }
    }


    public void setSubCountyAdapter(List<Facilitydata> myl) {

        try {

            ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < myl.size(); x++) {
                String sbctyname = myl.get(x).getSubcountyname();
                y.add(sbctyname);

            }


            arrayAdapterSubCounty = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, y);
        } catch (Exception e) {


        }
    }


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




    public void setFacilityAdapter(List<Facilitydata> myl) {

        try {

            ArrayList<String> y = new ArrayList<>();

            for (int x = 0; x < myl.size(); x++) {
                String faciityname = myl.get(x).getFacilityname();
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


    public void setCountyAdapter() {

        try {
            ArrayList<String> x = new ArrayList<>();

            List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata group by countyname");
            System.out.println("************getting countyies**************");
            if (myl.size() > 0) {

                for (int y = 0; y < myl.size(); y++) {
                    x.add(myl.get(y).getCountyname());
                    System.out.println(myl.get(y).getCountyname());

                }


            }


            arrayAdapterCounty = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, x);
        } catch (Exception e) {


        }
    }


    public void dose1InputListener() {
        try {

            dose1E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {


                        if (!dcalc.checkDateDifferenceWithCurrentDate(s.toString())) {


                        } else {

                            dose1E.setText("");
                            sweetdialog.showErrorDialogRegistration("specify a date less or equal to today", "Registration Error");
//                        Toast.makeText(CreateUser.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {

                        Toast.makeText(CreateUser.this, "crash " + e, Toast.LENGTH_SHORT).show();


                    }


                }
            });
        } catch (Exception e) {


        }
    }

    public void dose2InputListener() {
        try {

            dose2E.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {

                        long dateDiff = dcalc.calculateDateDifference(dose1E.getText().toString(), s.toString());
                        if (!dcalc.checkDateDifferenceWithCurrentDate(s.toString())) {


                            if (dateDiff < 28) {

//                            dose2E.setText("");
                                sweetdialog.showErrorDialogRegistration("second dose should be 28 days after first dose, try again", "Vaccination Date Error");


                            } else {


                            }

                        } else {

                            dose2E.setText("");
                            sweetdialog.showErrorDialogRegistration("specify a date less or equal to today", "Registration Error");
//                        Toast.makeText(CreateUser.this, "specify a date less or equal to today", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {

                        Toast.makeText(CreateUser.this, "crash " + e, Toast.LENGTH_SHORT).show();

                    }


                }
            });
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


    public void Dose1DateListener() {

        try {

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

                                    String dom = String.format("%02d", dayOfMonth);
                                    String moy = String.format("%02d", (monthOfYear + 1));

                                    // set day of month , month and year value in the edit text
                                    dose1E.setText(dom + "/"
                                            + moy + "/" + year);


                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }


    public void Dose2DateListener() {

        try {

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


                                    String dom = String.format("%02d", dayOfMonth);
                                    String moy = String.format("%02d", (monthOfYear + 1));
                                    dose2E.setText(dom + "/"
                                            + moy + "/" + year);

                                    String startDate = dose1E.getText().toString();
                                    String endDate = dose2E.getText().toString();

//                                    long mydiff=calculateDateDifference(startDate,endDate);
//                                    if(mydiff<1){
//                                        dose2E.setText("");
//                                        Toast.makeText(CreateUser.this, "select date greater than today", Toast.LENGTH_SHORT).show();
//
//                                    }


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


            final boolean selected[] = new boolean[]{false, false, false, false, false, false, false, false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Partner Organisation : ");
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

                                if (selected[6]) {//check for Not Applicable

                                    for (int x = 0; x < selected.length; x++) {

                                        itemsSelected.remove(Integer.valueOf(x));
                                        selected[x] = false;
                                        ((android.app.AlertDialog) dialog).getListView().setItemChecked(x, false);
                                        if (x == 6) {
                                            selected[6] = true;
                                            itemsSelected.add(6);

                                            ((android.app.AlertDialog) dialog).getListView().setItemChecked(6, true);
//                                            continue;
                                        }
                                    }


                                } else if (selected[5]) {


                                    kmpduChecked = true;
                                    dunumber.setVisibility(View.VISIBLE);
                                    specialisationE.setVisibility(View.VISIBLE);

                                    specialisel.setVisibility(View.VISIBLE);
                                    idnoE.setVisibility(View.GONE);


                                    ctyM.setVisibility(View.GONE);
                                    sbctyM.setVisibility(View.GONE);


                                    myspinner2.setVisibility(View.GONE);
                                    cadrel.setVisibility(View.GONE);


                                } else if (!selected[5]) {//if kmpdu is not checked
                                    kmpduChecked = false;
                                    dunumber.setVisibility(View.GONE);

                                    specialisationE.setVisibility(View.GONE);
                                    specialisel.setVisibility(View.GONE);

                                    idnoE.setVisibility(View.VISIBLE);


                                    ctyM.setVisibility(View.VISIBLE);
                                    sbctyM.setVisibility(View.VISIBLE);

                                    myspinner2.setVisibility(View.VISIBLE);
                                    cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);


                                }


                            } else if (itemsSelected.contains(selectedItemId)) {
                                itemsSelected.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId] = false;

                                if (selectedItemId == 5) { //check for kmpdu if unchecked


                                    kmpduChecked = false;
                                    dunumber.setVisibility(View.GONE);

                                    specialisel.setVisibility(View.GONE);
                                    specialisationE.setVisibility(View.GONE);
                                    idnoE.setVisibility(View.VISIBLE);


                                    ctyM.setVisibility(View.VISIBLE);
                                    sbctyM.setVisibility(View.VISIBLE);


                                    myspinner2.setVisibility(View.VISIBLE);
                                    cadrel.setVisibility(View.VISIBLE);
//                        motherE.setVisibility(View.GONE);


                                }
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

            selectedCty = "";
            selectedSbcty = "";
            selectedFacility = "";
            facilitySpinnerEdt =(EditText) findViewById(R.id.facilityspinner);
            exposureselectE=(EditText) findViewById(R.id.exposureselect);
            howmanyexposureE=(EditText) findViewById(R.id.howmanyexposures);
            pepinitiateselectE=(EditText) findViewById(R.id.pepinitiateselect);
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
            myspinner3 = (Spinner) findViewById(R.id.spinner3);

            radio2dosegrp = (RadioGroup) findViewById(R.id.radiogrpseconddose);

            doselayout = (LinearLayout) findViewById(R.id.doses);


            correctMfl = false;

            partners = new StringBuilder();

            dose1E = (EditText) findViewById(R.id.dose1);
            dose2E = (EditText) findViewById(R.id.dose2);

        } catch (Exception e) {


        }
    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;

        if (spin.getId() == R.id.spinnergender) {


//            selected_item=parent.getItemAtPosition(position).toString();
            myselectedgender = Integer.toString(position);
//            Toast.makeText(this, "selected "+myselectedgender, Toast.LENGTH_SHORT).show();
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


        } else if (spin.getId() == R.id.spinner3) {

//            selected_item2=parent.getItemAtPosition(position).toString();

            myselected3 = Integer.toString(position);

            if (position == 2) {

                doselayout.setVisibility(View.VISIBLE);
                Dose1DateListener();
                secondDoseRadioGroups();

            } else {

                doselayout.setVisibility(View.GONE);
            }

            actOnSelected();

        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void actOnSelected() {

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselectedgender, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }

    public void secondDoseRadioGroups() {
        try {

            radio2dosegrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    try {


                        switch (checkedId) {
                            case R.id.radiono:
                                dose2E.setVisibility(View.GONE);
                                dose2E.setText("");
                                break;
                            case R.id.radioyes:
                                dose2E.setVisibility(View.VISIBLE);
                                Dose2DateListener();
                                break;
                        }

                    } catch (Exception e) {


                    }


                }
            });

        } catch (Exception e) {


        }
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

    public void populateSpinner3() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), hepa);

            myspinner3.setAdapter(customAdapter);


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


            populatePartnerTable();

            partners = new StringBuilder("");

            Iterator<String> it = itemsSelected.iterator();

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
            if (itemsSelected.size() == 1) {
                String val = Integer.toString((Integer.parseInt(itemsSelected.get(0).toString())) + 1);
                partners.append(val);
            } else if (itemsSelected.size() > 1) {


                for (int x = 0; x < itemsSelected.size(); x++) {
                    String val = Integer.toString((Integer.parseInt(itemsSelected.get(x).toString())) + 1);

                    partners.append(val + "*");

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

            if (kmpduChecked) {
                mymfl = "13528";
            } else {

                mymfl = selectedFacility;
            }

            int curyear = Calendar.getInstance().get(Calendar.YEAR);
//            idnoE.setVisibility(View.VISIBLE);
//            nameE.setVisibility(View.VISIBLE);
//            motherE.setVisibility(View.VISIBLE);
//            lnameE.setVisibility(View.VISIBLE);
            if (kmpduChecked) {
                duns = dunumber.getText().toString();
                if (duns.isEmpty()) {

                    dunumber.setError("Doctors union number is required");
                }
//                if(selectedspecialisation.contentEquals("0")){
//                    Toast.makeText(this, "select specialisation", Toast.LENGTH_SHORT).show();
//                }


                myidno = "-1";

            } else {

                selectedspecialisation = "-1";

                duns = "-1";


            }


            if (!kmpduChecked && myidno.trim().isEmpty()) {
                idnoE.setError("ID NUMBER is Required");

            } else if (!kmpduChecked && myidno.length() < 4) {

                idnoE.setError("ID NUMBER should contain more than 3 values");

            } else if (myage.isEmpty()) {
                ageE.setError("Age is Required");

            } else if (mymfl.trim().isEmpty()) {
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


            } else if (myselected3.contentEquals("0")) {

                sweetdialog.showErrorDialogRegistration("Specify your vaccination", "Registration Error");

//                Toast.makeText(this, "Specify your vaccination", Toast.LENGTH_LONG).show();


            } else {
                List<Facilitydata> myl = Facilitydata.findWithQuery(Facilitydata.class, "select * from Facilitydata where facilityname=? limit 1", mymfl);
                for (int d = 0; d < myl.size(); d++) {

                    String newmflcode = myl.get(d).getMflcode();
                    checkFacilityCode(myidno, myage, newmflcode, partners, duns, selectedspecialisation);

                }


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

//        Toast.makeText(this, "checking facility", Toast.LENGTH_SHORT).show();


        final String mymflcode[] = {""};
        if (kmpduChecked) {
            mymflcode[0] = "13528";

        } else {
            mymflcode[0] = mymfl;

        }

        pdialog.cancel();
        correctMfl = true;


        String mdose1 = "-1";
        String mdose2 = "-1";
        if (!dose1E.getText().toString().trim().isEmpty()) {
            mdose1 = dose1E.getText().toString().trim();

        }
        if (!dose2E.getText().toString().trim().isEmpty()) {
            mdose2 = dose2E.getText().toString().trim();

        }

        if (correctMfl) {

            RegistrationTable rt = RegistrationTable.findById(RegistrationTable.class, 1);
            rt.gender = myselectedgender;
            rt.cadre = myselected2;
            rt.idnumber = myidno;
            rt.age = myage;
            rt.mflcode = mymfl;
            rt.myhepa = myselected3;
            rt.save();
//
//                                    RegistrationTable rt=new RegistrationTable("","",myselectedgender,myselected2,myidno,myage,mymfl,myselected3,"","","","");
//                                    rt.save();
            String myoth = "";

            try {

                myoth = motherE.getText().toString();


            } catch (Exception e) {


            }

            String mymess = "";
            String haveyouHadAnyExposures="-1";
            String numberOfExposures="-1";
            String wasPepInitiatedS="-1";

            if(exposureselectE.getText().toString().trim().equalsIgnoreCase("yes")){

                numberOfExposures=howmanyexposureE.getText().toString();
                wasPepInitiatedS=pepinitiateselectE.getText().toString();
                haveyouHadAnyExposures="yes";

            }
            else{
                haveyouHadAnyExposures="yes";
            }


            if (kmpduChecked) {


                mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + "-1" + "*" + "-1" + "*" + myselected3 + "*" + mdose1 + "*" + mdose2 + "*" + duns + "*" + sspecial +"*"+haveyouHadAnyExposures+ "*"+numberOfExposures+"*"+wasPepInitiatedS+"*"+partner);


            } else if (!kmpduChecked && myoth.isEmpty()) {
                mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + myselected2 + "*" + mymfl + "*" + myselected3 + "*" + mdose1 + "*" + mdose2 + "*" + duns + "*" + sspecial +"*"+haveyouHadAnyExposures+ "*"+numberOfExposures+"*"+wasPepInitiatedS+"*"+partner);


            } else if (!kmpduChecked && !myoth.isEmpty()) {

                mymess = "Reg*" + Base64Encoder.encryptString(myidno + "*" + myage + "*" + myselectedgender + "*" + myoth + "*" + mymfl + "*" + myselected3 + "*" + mdose1 + "*" + mdose2 + "*" + duns + "*" + sspecial +"*"+haveyouHadAnyExposures+ "*"+numberOfExposures+"*"+wasPepInitiatedS+"*"+partner);

            }


            populatePartners();

            SmsManager sm = SmsManager.getDefault();
            ArrayList<String> parts = sm.divideMessage(mymess);

            sm.sendMultipartTextMessage(Config.shortcode, null, parts, null, null);



//            SmsManager smsM = SmsManager.getDefault();
//            smsM.sendTextMessage(Config.shortcode, null, mymess, null, null);
            SignupsuccessDialog("Success in Creating Profile");


        } else {
//                    SignupdisplayDialog("WRONG MFLCODE, TRY AGAIN");

            sweetdialog.showErrorDialogRegistration("error occured, try again", "Registration Error");


        }
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

            int permissionCheck = ContextCompat.checkSelfPermission(CreateUser.this, Manifest.permission.SEND_SMS);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        CreateUser.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1235);
            } else {

            }
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
}
