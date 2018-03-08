package mhealth.c4c.getImmunisationsaveddata;

import android.content.Context;

import java.util.List;

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

    public getAllImmunisationData(Context ctx) {
        this.ctx = ctx;
    }

    public void displayAllData(){

        try{

            List<Influenza> mylinf=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            List<Measles> mylmeas=Measles.findWithQuery(Measles.class,"select * from Measles");
            List<Meningoco> mylmen=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
            List<Tdap> myltda=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
            List<Varicella> mylvar=Varicella.findWithQuery(Varicella.class,"select * from Varicella");

            System.out.println("************influenza*********");
            for(int x=0;x<mylinf.size();x++){
                System.out.println("gender "+mylinf.get(x).getGender());
                System.out.println("pregnant id "+mylinf.get(x).getPregnantid());
                System.out.println("pregnant value "+mylinf.get(x).getPregnantvalue());
                System.out.println("influenza id "+mylinf.get(x).getInfluenzavaccineid());
                System.out.println("influenza value "+mylinf.get(x).getInfluenzavaccinevalue());
                System.out.println("dose date "+mylinf.get(x).getDosedate());


            }
            System.out.println("************measles*********");
            for(int x=0;x<mylmeas.size();x++){

                System.out.println("imunised id "+mylmeas.get(x).getImmunisedid());
                System.out.println("imunised value "+mylmeas.get(x).getImmunisedvalue());
                System.out.println("first dose date "+mylmeas.get(x).getFirstdosedate());
                System.out.println("second dose date "+mylmeas.get(x).getSeconddosedate());


            }

            System.out.println("************meningoco*********");
            for(int x=0;x<mylmen.size();x++){

                System.out.println("imunised id "+mylmen.get(x).getImmunisedid());
                System.out.println("imunised value "+mylmen.get(x).getImmunisedvalue());
                System.out.println("first dose date "+mylmen.get(x).getFirstdosedate());
                System.out.println("second dose date "+mylmen.get(x).getSeconddosedate());


            }

            System.out.println("************tdap*********");
            for(int x=0;x<myltda.size();x++){

                System.out.println("imunised value "+myltda.get(x).getImmunisedvalue());
                System.out.println("immunised id "+myltda.get(x).getImmunisedid());
                System.out.println("dose date "+myltda.get(x).getDosedate());

            }

            System.out.println("************varicella*********");
            for(int x=0;x<mylvar.size();x++){
                System.out.println("vaccine id"+mylvar.get(x).getVaccineid());
                System.out.println("vaccine value"+mylvar.get(x).getVaccinevalue());
                System.out.println("history id"+mylvar.get(x).getHistoryid());
                System.out.println("history value"+mylvar.get(x).getHistoryvalue());
                System.out.println("first dose date"+mylvar.get(x).getFirstdosedate());
                System.out.println("second dose date"+mylvar.get(x).getSeconddosedate());


            }


        }
        catch(Exception e){


        }
    }
}
