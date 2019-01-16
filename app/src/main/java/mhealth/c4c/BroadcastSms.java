package mhealth.c4c;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import mhealth.c4c.SSLTrustCertificate.SSLTrust;
import mhealth.c4c.Tables.Edittable;
import mhealth.c4c.config.Config;
import mhealth.c4c.encryption.Base64Encoder;

/**
 * Created by kennedy on 9/13/17.
 */

public class BroadcastSms extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText msg,dte,name,cdreselect;
//    Spinner cdre;
    int selectedCadre;
    final ArrayList itemsSelectedSpecialisation = new ArrayList();
    Dialog specialisationdialog;

    String[] cadres={"Please Select Cadre","Student","Doctor","Nurse","Clinical officer","Laboratory technologist","Cleaner","Waste Handlers","Vct Counsellor"};
    String[] itemsspecialisation={"Student","Doctor","Nurse","Clinical officer","Laboratory technologist","Cleaner","Waste Handlers","Vct Counsellor"};

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_sms);
        setToolBar();
        initialise();

        SSLTrust.nuke();


        CheckDateListener();
        populateCadres();
        setSpecialisationClickListener();
//        cdre.setOnItemSelectedListener(this);
    }


    public void clearFields(){

        try{

            msg.setText("");
            dte.setText("");
            name.setText("");
            populateCadres();
        }
        catch(Exception e){


        }
    }

    public void setSpecialisationClickListener(){

        try{

            cdreselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cdreselect.setText("");
                    itemsSelectedSpecialisation.clear();
                    displayMultiselectForSpecialisation();


                }
            });

        }
        catch(Exception e){



        }
    }


    public void displayMultiselectForSpecialisation(){

        try{


            final boolean selected[] = new boolean[]{false, false, false, false,false,false,false,false};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Select Your Cadre: ");
            builder.setMultiChoiceItems(itemsspecialisation, selected,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {

                            if (isSelected) {



                                //logic

                                for (int i = 0; i < selected.length; i++) {
                                    if (i == selectedItemId) {

                                        selected[i]=true;

                                    }

                                }



                                //logic

                                itemsSelectedSpecialisation.add(selectedItemId);
                            }

                            else if (itemsSelectedSpecialisation.contains(selectedItemId)) {
                                itemsSelectedSpecialisation.remove(Integer.valueOf(selectedItemId));
                                selected[selectedItemId]=false;

                            }
                        }
                    })
                    .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked

                            Iterator<String> it=itemsSelectedSpecialisation.iterator();

                            while(it.hasNext()){

                                cdreselect.append(itemsspecialisation[Integer.parseInt(String.valueOf(it.next()))]);


                                if(it.hasNext()){
                                    cdreselect.append(",");
                                }




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


        }
        catch(Exception e){


        }
    }


    public void sending(View v){

        try{

            String txt,mydte,mycdre,myname;


            txt=msg.getText().toString().trim();
            mydte=dte.getText().toString().trim();
            mycdre=Integer.toString(selectedCadre);
            myname=name.getText().toString().trim();

            if(txt.isEmpty()){

                Toast.makeText(this, "Message is required", Toast.LENGTH_SHORT).show();
            }
            else if(mydte.isEmpty()){

                Toast.makeText(this, "date is required", Toast.LENGTH_SHORT).show();
            }
            else if(itemsSelectedSpecialisation.size()==0){

                Toast.makeText(this, "cadre is required", Toast.LENGTH_SHORT).show();

            }

            else if(myname.isEmpty()){

                Toast.makeText(this, "broadcast name is required", Toast.LENGTH_SHORT).show();
            }
            else{
                StringBuilder theCadres=new StringBuilder("");
                if(itemsSelectedSpecialisation.size()==1){
                    String val=Integer.toString((Integer.parseInt(itemsSelectedSpecialisation.get(0).toString()))+1);
                    theCadres.append(val);


                }
                else if(itemsSelectedSpecialisation.size()>1){


                    for (int x = 0; x < itemsSelectedSpecialisation.size(); x++) {
                        String val=Integer.toString((Integer.parseInt(itemsSelectedSpecialisation.get(x).toString()))+1);

                        theCadres.append(val+"*");

                    }


                }

                String bmes="BM*"+txt+"*"+mydte+"*cdre*"+theCadres+"cdre*"+myname;
                displayDialogue(bmes);



            }
        }
        catch(Exception e){


        }
    }





    public void SignupsuccessDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("MESSAGE SENT SUCCESSFULLY");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    clearFields();

                }
            });






            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }


    public void initialise(){

        try{
            msg=(EditText) findViewById(R.id.bmessage);
            dte=(EditText) findViewById(R.id.bdate);
            name=(EditText) findViewById(R.id.bname);
//            cdre=(Spinner) findViewById(R.id.cadrespinner);
            cdreselect=(EditText) findViewById(R.id.cadreselect);
        }
        catch(Exception e){


        }
    }




    public void CheckDateListener(){

        try{

            dte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker partnerdialog
                    datePickerDialog = new DatePickerDialog(BroadcastSms.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    dte.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);


                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
        catch(Exception e){


        }
    }


    public void populateCadres(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),cadres);

//            cdre.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }



    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.broadcasttoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("BROADCAST MESSAGE");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch(Exception e){


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        Spinner spin=(Spinner) parent;

//        if(spin.getId()==R.id.cadrespinner){
//
//            selectedCadre=position;
//            System.out.println("selected cadre is "+selectedCadre);
//
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }





    public void displayDialogue(final String bmes){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(BroadcastSms.this);
            final View promptsView = li.inflate(R.layout.my_profile, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    BroadcastSms.this);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(BroadcastSms.this, "broadcast not sent", Toast.LENGTH_SHORT).show();

                }
            });

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SmsManager smsM=SmsManager.getDefault();
                    smsM.sendTextMessage(Config.shortcode,null,bmes,null,null);
                    SignupsuccessDialog("Success in sending broadcast message");

                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final EditText mymfl = (EditText) promptsView
                    .findViewById(R.id.brdmfl);


            List<Edittable> myle=Edittable.findWithQuery(Edittable.class,"select * from Edittable limit 1");
            for(int x=0;x<myle.size();x++){

                String themfl=myle.get(x).getMfl();
                mymfl.setText(themfl);
            }


            alertDialog.show();
        }
        catch(Exception e){


        }
    }
}
