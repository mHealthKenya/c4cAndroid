package mhealth.c4c.getImmunisationsaveddata;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import mhealth.c4c.config.Config;
import mhealth.c4c.sendMessages.SendMessage;
import mhealth.c4c.systemstatetables.Hepatitis;
import mhealth.c4c.systemstatetables.Influenza;
import mhealth.c4c.systemstatetables.Measles;
import mhealth.c4c.systemstatetables.Meningoco;
import mhealth.c4c.systemstatetables.Tdap;
import mhealth.c4c.systemstatetables.Varicella;

/**
 * Created by root on 3/6/18.
 */

public class getAllImmunisationData {

    Context ctx;
    SendMessage sm;

    public getAllImmunisationData(Context ctx) {
        this.ctx = ctx;
        sm=new SendMessage(ctx);
    }

    public void displayAllData(){

        try{

            List<Hepatitis> mylhep=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");
            List<Influenza> mylinf=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            List<Measles> mylmeas=Measles.findWithQuery(Measles.class,"select * from Measles");
            List<Meningoco> mylmen=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
            List<Tdap> myltda=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
            List<Varicella> mylvar=Varicella.findWithQuery(Varicella.class,"select * from Varicella");
            String message="";

            System.out.println("************Hepatitis*********");
            for(int x=0;x<mylhep.size();x++){
                String firstdoseval=mylhep.get(x).getImmunisedvaluedose1();
                String seconddoseval=mylhep.get(x).getImmunisedvaluedose2();
                String firstdosedate=mylhep.get(x).getFirstdosedate();
                String seconddosedate=mylhep.get(x).getSeconddosedate();
                System.out.println("dose 1 value"+mylhep.get(x).getImmunisedvaluedose1());
                System.out.println("dose 2 value "+mylhep.get(x).getImmunisedvaluedose2());
                System.out.println("first dose date "+mylhep.get(x).getFirstdosedate());
                System.out.println("second dose date "+mylhep.get(x).getSeconddosedate());
                message+=returnValue(firstdoseval)+"*"+returnValue(seconddoseval)+"*"+returnDateValue(firstdosedate)+"*"+returnDateValue(seconddosedate);


            }

            System.out.println("************influenza*********");
            for(int x=0;x<mylinf.size();x++){

                String vaccineval=mylinf.get(x).getInfluenzavaccinevalue();
                String date=mylinf.get(x).getDosedate();

                System.out.println("gender "+mylinf.get(x).getGender());
                System.out.println("pregnant id "+mylinf.get(x).getPregnantid());
                System.out.println("pregnant value "+mylinf.get(x).getPregnantvalue());
                System.out.println("influenza id "+mylinf.get(x).getInfluenzavaccineid());
                System.out.println("influenza value "+mylinf.get(x).getInfluenzavaccinevalue());
                System.out.println("dose date "+mylinf.get(x).getDosedate());

                message+="*"+returnValue(vaccineval)+"*"+returnDateValue(date);


            }

            System.out.println("************varicella*********");
            for(int x=0;x<mylvar.size();x++){

                String vaccvalue=mylvar.get(x).getVaccinevalue();
                String histval=mylvar.get(x).getHistoryvalue();
                String date1=mylvar.get(x).getFirstdosedate();
                String date2=mylvar.get(x).getSeconddosedate();
                System.out.println("vaccine id"+mylvar.get(x).getVaccineid());
                System.out.println("vaccine value"+mylvar.get(x).getVaccinevalue());
                System.out.println("history id"+mylvar.get(x).getHistoryid());
                System.out.println("history value"+mylvar.get(x).getHistoryvalue());
                System.out.println("first dose date"+mylvar.get(x).getFirstdosedate());
                System.out.println("second dose date"+mylvar.get(x).getSeconddosedate());

                message+="*"+returnValue(vaccvalue)+"*"+returnValue(histval)+"*"+returnDateValue(date1)+"*"+returnDateValue(date2);


            }


            System.out.println("************tdap*********");
            for(int x=0;x<myltda.size();x++){

                String imuneval=myltda.get(x).getImmunisedvalue();
                String boosterval=myltda.get(x).getImmunisedboostervalue();
                String imunedate=myltda.get(x).getDosedate();
                String boosterdate=myltda.get(x).getDoseboosterdate();

                System.out.println("imunised value "+myltda.get(x).getImmunisedvalue());
                System.out.println("immunised id "+myltda.get(x).getImmunisedid());
                System.out.println("booster date "+myltda.get(x).getDoseboosterdate());
                System.out.println("booster value "+myltda.get(x).getImmunisedboostervalue());
                System.out.println("dose date "+myltda.get(x).getDosedate());

                message+="*"+returnValue(imuneval)+"*"+returnValue(boosterval)+"*"+returnDateValue(imunedate)+"*"+returnDateValue(boosterdate);

            }

            System.out.println("************measles*********");
            for(int x=0;x<mylmeas.size();x++){
                String vaccineval=mylmeas.get(x).getImmunisedvalue();
                String date1=mylmeas.get(x).getFirstdosedate();
                String date2=mylmeas.get(x).getSeconddosedate();

                System.out.println("imunised id "+mylmeas.get(x).getImmunisedid());
                System.out.println("imunised value "+mylmeas.get(x).getImmunisedvalue());
                System.out.println("first dose date "+mylmeas.get(x).getFirstdosedate());
                System.out.println("second dose date "+mylmeas.get(x).getSeconddosedate());

                message+="*"+returnValue(vaccineval)+"*"+returnDateValue(date1)+"*"+returnDateValue(date2);


            }

            System.out.println("************meningoco*********");
            for(int x=0;x<mylmen.size();x++){
                String imuneval=mylmen.get(x).getImmunisedvalue();
                String date1=mylmen.get(x).getFirstdosedate();
                String date2=mylmen.get(x).getSeconddosedate();

                System.out.println("imunised id "+mylmen.get(x).getImmunisedid());
                System.out.println("imunised value "+mylmen.get(x).getImmunisedvalue());
                System.out.println("first dose date "+mylmen.get(x).getFirstdosedate());
                System.out.println("second dose date "+mylmen.get(x).getSeconddosedate());
                message+="*"+returnValue(imuneval)+"*"+returnDateValue(date1)+"*"+returnDateValue(date2);


            }


//            Toast.makeText(ctx, "sending "+message, Toast.LENGTH_SHORT).show();

//            SendMessage.sendMessage("IMMUNE*"+message, Config.shortcode);
            sm.sendMessageApi("IMMUNE*"+message, Config.shortcode);


        }
        catch(Exception e){

            Toast.makeText(ctx, "exception sending "+e, Toast.LENGTH_SHORT).show();
            System.out.println("***********sending error************");
            System.out.println(e);

        }
    }

    private String returnValue(String value){

        String myval="-1";

        if(value==null||value.isEmpty()){
            myval="-1";

        }
        else if(value.equalsIgnoreCase("No")){
            myval="2";
        }
        else if(value.equalsIgnoreCase("Yes")){

            myval="1";
        }
        else if(value.equalsIgnoreCase("Partially")){
            myval="3";
        }

        return myval;


    }

    private String returnDateValue(String date){
        String dateVal="-1";

        if(date==null||date.isEmpty()){
            dateVal="-1";
        }
        else{
            dateVal=date;
        }

        return dateVal;

    }
}
