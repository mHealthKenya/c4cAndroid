package mhealth.c4c.config;

public class Config {

    public static final String shortcode="40145";

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

    public static final String[] SPINNERLISTWHERE = {"Medical Ward", "Surgical Ward", "Theater", "Maternity", "Dental Clinic", "OP/MCH", "laundry", "Laboratory","Other"};
    public static final String[] SPINNERLISTWHAT = {"Needle Stick", "Cuts", "Splash on mucosa", "Non-intact Skin", "Bite", "Other"};
    public static final String[] SPINNERLISTDEVICE = {
            "Syringe/needle IM/SC injection",
            "Syring/needle blood drawing",
            "phlebotomy needle/ vacuum set",
            "IV catheter/canula",
            "Neddle on IV line",
            "Unused needle",
            "lancet",
            "Suture needle",
            "Scalpel",
            "Capillary tube",
            "Glass slide",
            "Pippete tip",
            "Other"
    };

    public static final String[] SPINNERLISTSAFETY = {"Yes", "No", "Not Known"};

    public static final String[] SPINNERLISTAUTODISABLE = {"Yes", "No", "Not Known"};

    public static final String[] SPINNERLISTDEEP = {"Superficial", "Deep"};
    public static final String[] SPINNERLISTDEEPALGORITHM = {"Little or no bleeding", "Deep stick/cut, profuse bleeding"};


    public static final String[] SPINNERLISTPURPOSE = {
            "Injections",
            "IV fluid",
            "Suturing",
            "Blood Collection",
            "Surgery",
            "Other"
    };

    public static final String[] SPINNERLISTWHEN = {
            "During procedure",
            "Disassembling device",
            "Recapping",
            "Cleaning after procedure",
            "Device left on table, floor etc.",
            "Device placed in appropriate trash bin",
            "Device protruded from trash container",
            "During disposal",
            "Other"
    };

    public static final String[] SPINNERLISTWHENALGORITHM = {
            "during use",
            "after use",
            "between steps of a procedure",
            "after use",
            "after use",
            "after disposal",
            "after disposal",
            "during disposal",
            "Other"
    };

    public static final String[] SPINNERLISTHIVSTATUS = {"HIV+", "HIV-", "Unknown"};

    public static final String[] SPINNERLISTPEPINIT = {"Yes", "No"};

//
    public static final String[] SPINNERLISTEXPOSURERESULT = {
            "patient contact","leaking specimen container","faulty apparatus","contaminated surfaces","Other"};

    public static final String[] SPINNERLISTHBVSTATUS = {"HBV+", "HBV-", "Unknown"};








}
