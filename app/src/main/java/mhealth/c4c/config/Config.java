package mhealth.c4c.config;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

    public static final String shortcode="40149";

    public static final String CHECKFACILITY_URL = "http://197.248.10.20/C4CANDROID/checkfacility.php";
    public static final String GETCOUNTIES_URL = "http://197.248.10.20/C4CANDROID/get_counties.php";
    public static final String GETSUBCOUNTY_URL = "http://197.248.10.20/C4CANDROID/get_sub_counties.php";
    public static final String GETFACILITY_URL = "http://197.248.10.20/c4c_backend/index.php/core/get_facility_info";

    public static final String JSON_ARRAYRESULTS = "result";

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

    public static final String[] itemsorg = {"MOH", "KMTC", "EGPAF", "CHS", "UNITID",
            "KMPDU", "KMPDB", "Not Applicable"};
    public static final String[] itemsspecialisation = {"Anaesthesia", "Cardiothoracic surgery", "Dermatology", "Ear Nose And Throat",
            "Internal Medicine", "Microbiology", "Neurosurgery", "Obstetrics and Gynaecology", "Occupational Medicine",
            "Ophthalmology", "Orthopaedic Surgery", "Paediatrics and Child Health", "Palliative Medicine",
            "Pathology", "Psychiatry", "Plastic and Reconstructive Surgery", "Public Health", "Radiology",
            "Surgery", "Immunology", "Infectious Diseases", "Clinical Medical Genetics", "Emergency Medicine",
            "Opthalmology"};






}
