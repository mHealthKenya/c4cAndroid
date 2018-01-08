package mhealth.c4c;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by kennedy on 9/13/17.
 */

public class BroadcastSms extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText msg,dte,name;
    Spinner cdre;
    int selectedCadre;

    String[] cadres={"Please Select Cadre","Student","Doctor","Nurse","Clinical officer","Laboratory technologist","Cleaner","Waste Handlers","Vct Counsellor"};

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_sms);
        setToolBar();
        initialise();
        CheckDateListener();
        populateCadres();
        cdre.setOnItemSelectedListener(this);
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
            else if(mycdre.isEmpty()){

                Toast.makeText(this, "cadre is required", Toast.LENGTH_SHORT).show();
            }
            else if(mycdre.contentEquals("0")){

                Toast.makeText(this, "cadre is required", Toast.LENGTH_SHORT).show();


            }
            else if(myname.isEmpty()){

                Toast.makeText(this, "broadcast name is required", Toast.LENGTH_SHORT).show();
            }
            else{

                String bmes="BM*"+txt+"*"+mydte+"*"+mycdre+"*"+myname;
                SmsManager smsM=SmsManager.getDefault();
                smsM.sendTextMessage("40149",null,bmes,null,null);
                SignupsuccessDialog("Success in sending broadcast message");


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
            cdre=(Spinner) findViewById(R.id.cadrespinner);
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
                    // date picker dialog
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

            cdre.setAdapter(customAdapter);


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

        if(spin.getId()==R.id.cadrespinner){

            selectedCadre=position;
            System.out.println("selected cadre is "+selectedCadre);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
