package mhealth.c4c.config;

public class Config {

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

    public static final String[] SPINNERLISTPURPOSE = {
            "Injections",
            "IV fluid",
            "Suturing",
            "Blood Collection",
            "Surgery",
            "Other"
    };

    public static final String[] SPINNERLISTWHEN = {
            "Before Use",
            "During Use",
            "Between Steps of a procedure",
            "After use",
            "During disposal",
            "After Disposal",
            "Other"
    };

    public static final String[] SPINNERLISTHIVSTATUS = {"HIV+", "HIV-", "Unknown"};

    public static final String[] SPINNERLISTHBVSTATUS = {"HBV+", "HBV-", "Unknown"};








}
