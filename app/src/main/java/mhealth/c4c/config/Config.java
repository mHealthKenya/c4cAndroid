package mhealth.c4c.config;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

    //******TEST SHORTCODE****************
    public static final String shortcode="40149";

    //******LIVE SHORTCODE****************
//    public static final String shortcode="40145";

    //*************TEST DB CONNECTION URL***********************

    public static final String SIGNUP_URL = "https://c4c-test.mhealthkenya.co.ke/Core/AppReg";
    public static final String CREATPROFILE_URL = "https://c4c-test.mhealthkenya.co.ke/Core/SignUp";
    public static final String REPORTEXPOSURE_URL = "https://c4c-test.mhealthkenya.co.ke/Core/RptExp";

//    public static final String GETFACILITY_URL = "https://c4c-test.mhealthkenya.co.ke/Core/get_facility_info";

    public static final String GETCOUNTY_URL = "http://c4c-test.mhealthkenya.org/c4c-dev/core/CountySearch";

//    public static final String GETSUBCOUNTY_URL = "http://c4c-test.mhealthkenya.org/c4c-dev/core/SubCountySearch";\
    public static final String GETSUBCOUNTY_URL = "https://api.mhealthkenya.co.ke/api/get_sub_county";

    public static final String VERIFYNUMBER_URL = "https://c4c-test.mhealthkenya.co.ke/Core/chkMobile";

    public static final String GETFACILITY_URL = "http://c4c-test.mhealthkenya.org/c4c-dev/core/FacSearch";


    //*************TEST DB CONNECTION URL***********************



//*************LIVE DB CONNECTION URL***********************
//    public static final String SIGNUP_URL = "https://c4c.mhealthkenya.co.ke/Core/AppReg";
//    public static final String CREATPROFILE_URL = "https://c4c.mhealthkenya.co.ke/Core/CreateProf";
//    public static final String REPORTEXPOSURE_URL = "https://c4c.mhealthkenya.co.ke/Core/RptExp";

//    public static final String GETFACILITY_URL = "https://c4c.mhealthkenya.co.ke/Core/get_facility_info";
//*************LIVE DB CONNECTION URL***********************


    public static final String JSON_ARRAYRESULTS = "result";

    public static final String KEY_COUNTYID="id";
    public static final String KEY_COUNTYNAME="name";

    public static final String KEY_SUBCOUNTYID="id";
    public static final String KEY_SUBCOUNTYNAME="name";

    public static final String KEY_FACILITYCODE="code";
    public static final String KEY_FACILITYNAME="name";

    public static final String KEY_CODE = "code";
    public static final String KEY_FACILITY_NAME = "facility_name";
    public static final String KEY_COUNTY_ID = "county_id";
    public static final String KEY_SUBCOUNTY_ID = "Sub_County_ID";
    public static final String KEY_COUNTY_NAME = "county_name";
    public static final String KEY_SUBCOUNTY_NAME = "subcounty_name";

    public static final String[] SPINNERLISTWHERE = {"Medical Ward", "Surgical Ward", "Theater", "Maternity", "Dental Clinic", "OP/MCH", "Laundry", "Laboratory","Other"};
    public static final String[] SPINNERLISTWHAT = {"Needle Stick", "Cuts", "Splash on Mucosa", "Non-intact Skin", "Bite", "Other"};
    public static final String[] SPINNERLISTDEVICE = {
            "Syringe/Needle IM/SC Injection",
            "Syring/Needle Blood Drawing",
            "Phlebotomy Needle/ Vacuum set",
            "IV Catheter/Canula",
            "Needle on IV Line",
            "Unused Needle",
            "Lancet",
            "Suture Needle",
            "Scalpel",
            "Capillary Tube",
            "Glass Slide",
            "Pippete Tip",
            "Other"
    };

    public static final String[] SPINNERLISTSAFETY = {"Yes", "No", "Not Known"};

    public static final String[] SPINNERLISTAUTODISABLE = {"Yes", "No", "Not Known"};

    public static final String[] SPINNERLISTDEEP = {"Superficial", "Deep"};
    public static final String[] SPINNERLISTDEEPALGORITHM = {"Little or no Bleeding", "Deep Stick/Cut, Profuse Bleeding"};


    public static final String[] SPINNERLISTPURPOSE = {
            "Injections",
            "Blood Collection",
            "Cannulation/Strat IV",
            "Connect IV Line",
            "Place Arterial/Central Line",
            "Fingerstick/Heel stick",
            "Other"
    };



    public static final String[] SPINNERLISTWHEN = {
            "During procedure",
            "Disassembling device",
            "Recapping",
            "Cleaning after procedure",
            "Device left on Table, Floor etc.",
            "Device placed in appropriate trash bin",
            "Device protruded from trash container",
            "During disposal",
            "Other"
    };

    public static final String[] SPINNERLISTWHENALGORITHM = {
            "During use",
            "After use",
            "Between steps of a procedure",
            "After use",
            "After use",
            "After disposal",
            "After disposal",
            "During disposal",
            "Other"
    };

    public static final String[] SPINNERLISTHIVSTATUS = {"HIV+", "HIV-", "Unknown"};

    public static final String[] SPINNERLISTPEPINIT = {"Yes", "No"};

    public static final ArrayList<String> YESNOARRAY = new ArrayList<>(Arrays.asList("Yes", "No"));

//
    public static final String[] SPINNERLISTEXPOSURERESULT = {
            "Patient Contact","Leaking Specimen Container","Faulty Apparatus","Contaminated Surfaces","Other"};

    public static final String[] SPINNERLISTHBVSTATUS = {"HBV+", "HBV-", "Unknown"};

    public static final String[] itemsorg = {"MOH","KNH", "KMTC", "EGPAF","GIS","AMREF","UMB","FHI360","CHS","UNITID",
            "KMPDB", "Not Applicable"};
    public static final String[] itemsspecialisation = {"Anaesthesia", "Cardiothoracic surgery", "Dermatology", "Ear Nose And Throat",
            "Internal Medicine", "Microbiology", "Neurosurgery", "Obstetrics and Gynaecology", "Occupational Medicine",
            "Ophthalmology", "Orthopaedic Surgery", "Paediatrics and Child Health", "Palliative Medicine",
            "Pathology", "Psychiatry", "Plastic and Reconstructive Surgery", "Public Health", "Radiology",
            "Surgery", "Immunology", "Infectious Diseases", "Clinical Medical Genetics", "Emergency Medicine",
            "Opthalmology"};



    public static final String KEY_SIGNUP_FNAME = "fname";
    public static final String KEY_SIGNUP_LNAME = "lname";
    public static final String KEY_SIGNUP_UNAME = "uname";
    public static final String KEY_SIGNUP_PWD = "pwd";
    public static final String KEY_SIGNUP_SECQN = "secqn";
    public static final String KEY_SIGNUP_ANS = "ans";
    public static final String KEY_SIGNUP_PHONE = "phone_no";


    public static final String KEY_CREATEPROFILE_PARTNER = "partner";
    public static final String KEY_CREATEPROFILE_SPECS = "specs";
    public static final String KEY_CREATEPROFILE_GENDER = "gender";
    public static final String KEY_CREATEPROFILE_CDR = "cdr";
    public static final String KEY_CREATEPROFILE_IDNO = "idno";
    public static final String KEY_CREATEPROFILE_DOB = "dob";
    public static final String KEY_CREATEPROFILE_MFLNO = "mflno";
    public static final String KEY_CREATEPROFILE_PHONENO = "phone_no";




    public static final String KEY_REPORTEXPOSURE_ELOC = "eloc";
    public static final String KEY_REPORTEXPOSURE_ETYPE = "etype";
    public static final String KEY_REPORTEXPOSURE_PURP = "purp";
    public static final String KEY_REPORTEXPOSURE_WHENITHAPND = "whenithapnd";
    public static final String KEY_REPORTEXPOSURE_HIVSTATUS = "HivStatus";
    public static final String KEY_REPORTEXPOSURE_HBVSTATUS = "HbvStatus";
    public static final String KEY_REPORTEXPOSURE_EXPNO = "expno";
    public static final String KEY_REPORTEXPOSURE_PEPINIT = "pepinit";
    public static final String KEY_REPORTEXPOSURE_DATEEXPD = "dateexpd";
    public static final String KEY_REPORTEXPOSURE_DEVICE = "device";
    public static final String KEY_REPORTEXPOSURE_DEVICESAFETY = "deviceSafety";
    public static final String KEY_REPORTEXPOSURE_DEEP = "deep";
    public static final String KEY_REPORTEXPOSURE_DATEPEP = "datepep";
    public static final String KEY_REPORTEXPOSURE_EXPRESULT = "expresult";
    public static final String KEY_REPORTEXPOSURE_PHONENO = "phone_no";


}
