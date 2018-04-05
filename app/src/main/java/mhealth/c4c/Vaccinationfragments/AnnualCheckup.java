package mhealth.c4c.Vaccinationfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mhealth.c4c.CreateUser;
import mhealth.c4c.R;
import mhealth.c4c.checkupstatustable.checkupcalendar;
import mhealth.c4c.dateCalculator.DateCalculator;
import mhealth.c4c.dialogs.Dialogs;

/**
 * Created by root on 2/22/18.
 */

public class AnnualCheckup extends Fragment {
    View v;
    EditText gcheckup,pcheckup;
    DatePickerDialog datePickerDialog;
    DateCalculator dcalc;
    Dialogs mydialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.annualcheckup_fragment,container,false);
        initialise();

        populateDates();






        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


        PhysicalCheckupDateListener();
        GeneralCheckupDateListener();

        setGeneralCheckupInputListener();
        setPhysicalCheckupInputListener();

    }

    public void populateDates(){

        try{

            List<checkupcalendar> myl=checkupcalendar.findWithQuery(checkupcalendar.class,"select * from checkupcalendar limit 1");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String gdate=myl.get(x).getGeneraldate();
                    String pdate=myl.get(x).getPhysicaldate();
                    gcheckup.setText(gdate);
                    pcheckup.setText(pdate);


                }

            }
            else{


            }
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting dates "+e, Toast.LENGTH_SHORT).show();

            System.out.println("***********set date error************************");
            System.out.println(e);

        }
    }


    public void populateGeneralDate(){

        try{

            List<checkupcalendar> myl=checkupcalendar.findWithQuery(checkupcalendar.class,"select * from checkupcalendar limit 1");
            if(myl.size()==1){

                for(int x=0;x<=1;x++){

                    String gdate=myl.get(x).getGeneraldate();

                    gcheckup.setText(gdate);

                }
            }
            else{

                gcheckup.setText("");

            }
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting dates", Toast.LENGTH_SHORT).show();


        }
    }

    public void populatePhysicalDate(){

        try{

            List<checkupcalendar> myl=checkupcalendar.findWithQuery(checkupcalendar.class,"select * from checkupcalendar limit 1");
            if(myl.size()==1){

                for(int x=0;x<=1;x++){


                    String pdate=myl.get(x).getPhysicaldate();

                    pcheckup.setText(pdate);
                }
            }
            else{


                pcheckup.setText("");
            }
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting dates", Toast.LENGTH_SHORT).show();


        }
    }

    public void initialise(){

        try{

            gcheckup=(EditText) v.findViewById(R.id.generalcheckup);
            pcheckup=(EditText) v.findViewById(R.id.physicalcheckup);
            dcalc=new DateCalculator();
            mydialog=new Dialogs(getActivity());
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error initialising variables", Toast.LENGTH_SHORT).show();
        }
    }

    public void setGeneralCheckupInputListener(){

        try{
            gcheckup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        List<checkupcalendar> myl=checkupcalendar.findWithQuery(checkupcalendar.class,"select * from checkupcalendar limit 1");
                        if(myl.size()==1){


                            checkupcalendar cp = checkupcalendar.findById(checkupcalendar.class, 1);
                            cp.setGeneraldate(s.toString());
                            cp.save();
                            mydialog.showSuccessDialogCalendarCheckup("success setting general checkup date","success");


                        }
                        else{
                            checkupcalendar cp=new checkupcalendar();
                            cp.setGeneraldate(s.toString());
                            cp.save();
                            mydialog.showSuccessDialogCalendarCheckup("success setting general checkup date","success");



                        }
                    }
                    else{
//                        Toast.makeText(getActivity(), "provide a date greater than today", Toast.LENGTH_SHORT).show();
//                        populateGeneralDate();
                        mydialog.showCalendarCheckup("provide a date greater than today","Error");
                    }

                }
            });


        }
        catch(Exception e){

        }
    }




    public void setPhysicalCheckupInputListener(){

        try{
            pcheckup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(dcalc.checkDateDifferenceWithCurrentDate(s.toString())){
                        List<checkupcalendar> myl=checkupcalendar.findWithQuery(checkupcalendar.class,"select * from checkupcalendar limit 1");
                        if(myl.size()==1){


                            checkupcalendar cp = checkupcalendar.findById(checkupcalendar.class, 1);
                            cp.setPhysicaldate(s.toString());
                            cp.save();
                            mydialog.showSuccessDialogCalendarCheckup("success setting physical checkup date","success");

                        }
                        else{
                            checkupcalendar cp=new checkupcalendar();
                            cp.setPhysicaldate(s.toString());
                            cp.save();

                            mydialog.showSuccessDialogCalendarCheckup("success setting physical checkup date","success");



                        }
                    }
                    else{

                        mydialog.showCalendarCheckup("provide a date greater than today","Error");
//                        populatePhysicalDate();
                    }

                }
            });


        }
        catch(Exception e){

        }
    }

    public void PhysicalCheckupDateListener(){

        try{

            pcheckup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text


                                    String dom=String.format("%02d", dayOfMonth);
                                    String moy=String.format("%02d", (monthOfYear + 1));
                                    pcheckup.setText(dom+ "/"
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

    public void GeneralCheckupDateListener(){

        try{

            gcheckup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text


                                    String dom=String.format("%02d", dayOfMonth);
                                    String moy=String.format("%02d", (monthOfYear + 1));
                                    gcheckup.setText(dom+ "/"
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
}
