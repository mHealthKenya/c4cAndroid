package mhealth.c4c.checkupstatustable;

import android.content.Context;

import java.util.List;

import mhealth.c4c.systemstatetables.Hepatitis;
import mhealth.c4c.systemstatetables.Influenza;
import mhealth.c4c.systemstatetables.Measles;
import mhealth.c4c.systemstatetables.Meningoco;
import mhealth.c4c.systemstatetables.Tdap;
import mhealth.c4c.systemstatetables.Varicella;

/**
 * Created by root on 3/13/18.
 */

public class UpdateStatusTable {

    Context ctx;

    public UpdateStatusTable(Context ctx) {
        this.ctx = ctx;
    }

    List<Influenza> inflList=Influenza.findWithQuery(Influenza.class,"select * from Influenza");
    List<Measles> measlList=Measles.findWithQuery(Measles.class,"select * from Measles");
    List<Meningoco> meniList=Meningoco.findWithQuery(Meningoco.class,"select * from Meningoco");
    List<Tdap> tdapList=Tdap.findWithQuery(Tdap.class,"select * from Tdap");
    List<Varicella> variList=Varicella.findWithQuery(Varicella.class,"select * from Varicella");

    List<Hepatitis> hepaList=Hepatitis.findWithQuery(Hepatitis.class,"select * from Hepatitis");

    public void updateInfluenza(){



        try{

            String dosedate="";

            if(inflList.size()>0){
                for(int x=0;x<inflList.size();x++){

                    dosedate=inflList.get(x).dosedate;



                }


            }
            else{

                dosedate="";

            }
            if(dosedate!=null && dosedate.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","influenza");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","influenza");

                }
                else{

                    Status st=new Status("influenza","vaccinated");
                    st.save();

                }
            }
            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","influenza");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","influenza");

                }
                else{

                    Status st=new Status("influenza","not vaccinated");
                    st.save();

                }

            }


        }
        catch(Exception e){


        }
    }

    public void updateMeasles(){

        try{
            String dosedate1="";
            String dosedate2="";

            if(measlList.size()>0){
                for(int x=0;x<measlList.size();x++){

                    dosedate1=measlList.get(x).firstdosedate;
                    dosedate2=measlList.get(x).seconddosedate;

                }
            }
            else{

                dosedate1="";
                dosedate2="";

            }

            if(dosedate1!=null && dosedate1.trim().length()>5 && dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","measles");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","measles");

                }
                else{

                    Status st=new Status("measles","vaccinated");
                    st.save();

                }
            }


            else if(!(dosedate1!=null && dosedate1.trim().length()>5 )&& dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","measles");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","measles");

                }
                else{

                    Status st=new Status("measles","pending vaccination");
                    st.save();

                }
            }


            else if(dosedate1!=null && dosedate1.trim().length()>5 && !(dosedate2!=null && dosedate2.trim().length()>5)) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","measles");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","measles");

                }
                else{

                    Status st=new Status("measles","pending vaccination");
                    st.save();

                }
            }


            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","measles");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","measles");

                }
                else{

                    Status st=new Status("measles","not vaccinated");
                    st.save();

                }

            }

        }
        catch(Exception e){


        }
    }

    public void updateMeningoco(){

        try{
            String dosedate1="";
            String dosedate2="";

            if(meniList.size()>0){
                for(int x=0;x<meniList.size();x++){

                    dosedate1=meniList.get(x).firstdosedate;
                    dosedate2=meniList.get(x).seconddosedate;

                }
            }
            else{

                dosedate1="";
                dosedate2="";

            }

            if(dosedate1!=null && dosedate1.trim().length()>5 && dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","meningoco");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","meningoco");

                }
                else{

                    Status st=new Status("meningoco","vaccinated");
                    st.save();

                }
            }


            else if(!(dosedate1!=null && dosedate1.trim().length()>5 )&& dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","meningoco");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","meningoco");

                }
                else{

                    Status st=new Status("meningoco","pending vaccination");
                    st.save();

                }
            }


            else if(dosedate1!=null && dosedate1.trim().length()>5 && !(dosedate2!=null && dosedate2.trim().length()>5)) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","meningoco");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","meningoco");

                }
                else{

                    Status st=new Status("meningoco","pending vaccination");
                    st.save();

                }
            }


            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","meningoco");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","meningoco");

                }
                else{

                    Status st=new Status("meningoco","not vaccinated");
                    st.save();

                }

            }

        }
        catch(Exception e){


        }
    }





    public void updateHepatits(){

        try{
            String dosedate1="";
            String dosedate2="";

            if(hepaList.size()>0){
                for(int x=0;x<hepaList.size();x++){

                    dosedate1=hepaList.get(x).firstdosedate;
                    dosedate2=hepaList.get(x).seconddosedate;

                }
            }
            else{

                dosedate1="";
                dosedate2="";

            }

            if(dosedate1!=null && dosedate1.trim().length()>5 && dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","hepatitis");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","hepatitis");

                }
                else{

                    Status st=new Status("hepatitis","vaccinated");
                    st.save();

                }
            }


            else if(!(dosedate1!=null && dosedate1.trim().length()>5 )&& dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","hepatitis");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","hepatitis");

                }
                else{

                    Status st=new Status("hepatitis","pending vaccination");
                    st.save();

                }
            }


            else if(dosedate1!=null && dosedate1.trim().length()>5 && !(dosedate2!=null && dosedate2.trim().length()>5)) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","hepatitis");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","hepatitis");

                }
                else{

                    Status st=new Status("hepatitis","pending vaccination");
                    st.save();

                }
            }


            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","hepatitis");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","hepatitis");

                }
                else{

                    Status st=new Status("hepatitis","not vaccinated");
                    st.save();

                }

            }

        }
        catch(Exception e){


        }
    }



    public void updateTdap(){



        try{

            String dosedate="";

            if(tdapList.size()>0){
                for(int x=0;x<tdapList.size();x++){

                    dosedate=tdapList.get(x).dosedate;



                }


            }
            else{

                dosedate="";

            }
            if(dosedate!=null && dosedate.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","tdap");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","tdap");

                }
                else{

                    Status st=new Status("tdap","vaccinated");
                    st.save();

                }
            }
            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","tdap");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","tdap");

                }
                else{

                    Status st=new Status("tdap","not vaccinated");
                    st.save();

                }

            }


        }
        catch(Exception e){


        }
    }

    public void updateVaricella(){

        try{
            String dosedate1="";
            String dosedate2="";

            if(variList.size()>0){
                for(int x=0;x<variList.size();x++){

                    dosedate1=variList.get(x).firstdosedate;
                    dosedate2=variList.get(x).seconddosedate;

                }
            }
            else{

                dosedate1="";
                dosedate2="";

            }

            if(dosedate1!=null && dosedate1.trim().length()>5 && dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","varicella");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","vaccinated","varicella");

                }
                else{

                    Status st=new Status("varicella","vaccinated");
                    st.save();

                }
            }


            else if(!(dosedate1!=null && dosedate1.trim().length()>5 )&& dosedate2!=null && dosedate2.trim().length()>5) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","varicella");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","varicella");

                }
                else{

                    Status st=new Status("varicella","pending vaccination");
                    st.save();

                }
            }


            else if(dosedate1!=null && dosedate1.trim().length()>5 && !(dosedate2!=null && dosedate2.trim().length()>5)) {

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","varicella");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","pending vaccination","varicella");

                }
                else{

                    Status st=new Status("varicella","pending vaccination");
                    st.save();

                }
            }


            else{

                List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where name=?","varicella");


                if(myl.size()>0){

                    Status.executeQuery("update Status set category=? where name=?","not vaccinated","varicella");

                }
                else{

                    Status st=new Status("varicella","not vaccinated");
                    st.save();

                }

            }

        }
        catch(Exception e){


        }
    }
}
