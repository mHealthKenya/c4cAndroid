package mhealth.c4c.completionPercentage;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import mhealth.c4c.Registrationtable.Regdetails;
import mhealth.c4c.systemstatetables.Influenza;
import mhealth.c4c.systemstatetables.Varicella;

/**
 * Created by root on 3/12/18.
 */

public class Completion {

    Context ctx;

    public Completion(Context ctx) {
        this.ctx = ctx;
    }

    public void getInfluenzaPercentage(){

        float completion=0;
        int counter=0;
        String date="";
        String vaccinevalue="";
        String pregnantvalue="";
        boolean vaccineval=false;
        boolean dateval=false;
        boolean pregnantval=false;
        float total=0;
        try{

            List<Influenza> myl=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
            if(myl.size()>0){


                for(int x=0;x<myl.size();x++){

                    if(!isGenderFemale()){

                        total=2;

                        date=myl.get(x).getDosedate();
                        vaccinevalue=myl.get(x).getInfluenzavaccinevalue();

                        if(vaccinevalue!=null){

                            vaccineval=true;

                        }
                        if(date!=null){

                            dateval=true;
                        }

                        //calculate the total percentage here

                        if(vaccineval&&dateval){
                            completion=2;
                        }
                        else if(vaccineval&&!dateval){
                            completion=1;
                        }
                        else if(!vaccineval && !dateval){
                            completion=0;
                        }
                        else if(!vaccineval&&dateval){
                            completion=1;
                        }

                        //calculate the total percentage here

                    }

                    else{

                        total=3;

                        date=myl.get(x).getDosedate();
                        vaccinevalue=myl.get(x).getInfluenzavaccinevalue();
                        pregnantvalue=myl.get(x).getPregnantvalue();

                        if(vaccinevalue!=null){

                            vaccineval=true;

                        }
                        else{
                            vaccineval=false;

                        }

                        if(pregnantvalue!=null){

                            pregnantval=true;

                        }
                        else{

                            pregnantval=false;
                        }

                        if(date!=null && date.trim().length()>5){

                            dateval=true;
                        }
                        else{
                            dateval=false;
                        }


                        //calculate the total percentage here

                        if(vaccineval&&dateval&&pregnantval){
                            completion=3;
                        }
                        else if(vaccineval&&pregnantval&&!dateval){
                            completion=2;
                        }
                        else if(vaccineval&&!pregnantval&&!dateval){
                            completion=1;
                        }
                        else if(!vaccineval&&pregnantval&&!dateval){
                            completion=1;
                        }
                        else if(!vaccineval&&!pregnantval&&dateval){
                            completion=1;
                        }
                        else if(!vaccineval&&!pregnantval&&!dateval){
                            completion=0;
                        }
                        else if(!vaccineval&&pregnantval&&dateval){
                            completion=2;
                        }
                        else if(vaccineval&&!pregnantval&&dateval){
                            completion=2;
                        }



                        //calculate the total percentage here


                    }
                }
            }
            else{
                dateval=false;
                vaccineval=false;
                pregnantval=false;
                total=0;
                completion=0;

            }


            Toast.makeText(ctx, "percentage is"+((completion/total)*100)+" % "+"completion is"+completion, Toast.LENGTH_SHORT).show();


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting percentage "+e, Toast.LENGTH_SHORT).show();

        }
    }

    public void getVaricellaPercentage(){

        float completion=0;
        int counter=0;


        String varicellavalue="";
        String varicellahistvalue="";
        String varicelladose1datevalue="";
        String varicelladose2datevalue="";


        boolean varicellaval=false;
        boolean varicellahistval=false;
        boolean varicelladose1dateval=false;
        boolean varicelladose2dateval=false;

        float total=0;

        try{

            List<Varicella> myl=Varicella.findWithQuery(Varicella.class,"select * from Varicella");

            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    total=4;

                    varicelladose1datevalue=myl.get(x).getFirstdosedate();
                    varicelladose2datevalue=myl.get(x).getSeconddosedate();
                    varicellavalue=myl.get(x).getVaccinevalue();
                    varicellahistvalue=myl.get(x).getHistoryvalue();

                    if(varicelladose1datevalue!=null && varicelladose1datevalue.trim().length()>5){

                        varicelladose1dateval=true;

                    }
                    else{
                        varicelladose1dateval=false;

                    }
                    if(varicelladose2datevalue!=null && varicelladose2datevalue.trim().length()>5){

                        varicelladose2dateval=true;

                    }
                    else{

                        varicelladose2dateval=false;

                    }

                    if(varicellavalue!=null){

                        varicellaval=true;

                    }
                    else{

                        varicellaval=false;

                    }


                    if(varicellahistvalue!=null){

                        varicellahistval=true;

                    }
                    else{

                        varicellahistval=false;

                    }

//                    boolean varicellaval=false;
//                    boolean varicellahistval=false;
//                    boolean varicelladose1dateval=false;
//                    boolean varicelladose2dateval=false;

                    if(varicellaval && varicellahistval && varicelladose1dateval && varicelladose2dateval){

                        completion=4;
                    }
                    if(!varicellaval && varicellahistval && varicelladose1dateval && varicelladose2dateval){

                        completion=3;
                    }
                    if(varicellaval && !varicellahistval && varicelladose1dateval && varicelladose2dateval){

                        completion=3;
                    }
                    if(varicellaval && varicellahistval && !varicelladose1dateval && varicelladose2dateval){

                        completion=3;
                    }
                    if(varicellaval && varicellahistval && varicelladose1dateval && !varicelladose2dateval){

                        completion=3;
                    }
                    if(!varicellaval && !varicellahistval && varicelladose1dateval && varicelladose2dateval){

                        completion=2;
                    }
                    if(!varicellaval && varicellahistval && !varicelladose1dateval && varicelladose2dateval){

                        completion=2;
                    }
                    if(!varicellaval && varicellahistval && varicelladose1dateval && !varicelladose2dateval){

                        completion=2;
                    }
                    if(varicellaval && !varicellahistval && !varicelladose1dateval && varicelladose2dateval){

                        completion=2;
                    }
                    if(varicellaval && !varicellahistval && varicelladose1dateval && !varicelladose2dateval){

                        completion=2;
                    }
                    if(varicellaval && varicellahistval && !varicelladose1dateval && !varicelladose2dateval){

                        completion=2;
                    }
                    if(!varicellaval && !varicellahistval && !varicelladose1dateval && varicelladose2dateval){

                        completion=1;
                    }

                    if(varicellaval && !varicellahistval && !varicelladose1dateval && !varicelladose2dateval){

                        completion=1;
                    }
                    if(!varicellaval && varicellahistval && !varicelladose1dateval && !varicelladose2dateval){

                        completion=1;
                    }
                    if(!varicellaval && !varicellahistval && varicelladose1dateval && !varicelladose2dateval){

                        completion=1;
                    }
                    if(!varicellaval && !varicellahistval && !varicelladose1dateval && !varicelladose2dateval){

                        completion=0;
                    }


                }


            }
            else{

                varicellaval=false;
                varicellahistval=false;
                varicelladose1dateval=false;
                varicelladose2dateval=false;

                completion=0;
                total=0;

            }

            Toast.makeText(ctx, "percentage for varicella  is"+((completion/total)*100)+" % "+"completion is"+completion, Toast.LENGTH_SHORT).show();



        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting varicella total "+e, Toast.LENGTH_SHORT).show();

        }
    }



    public boolean isGenderFemale(){
        boolean isFemale=false;
        try{

            List<Regdetails> myl=Regdetails.findWithQuery(Regdetails.class,"select * from Regdetails");
            for(int x=0;x<myl.size();x++){
                if(myl.get(x).gender.equalsIgnoreCase("Female")){
                    isFemale=true;
                }
            }
            return isFemale;
        }
        catch (Exception e){
            isFemale=false;
            return isFemale;


        }
    }
}
