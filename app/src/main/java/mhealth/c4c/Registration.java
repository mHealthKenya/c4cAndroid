package mhealth.c4c;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 12/11/2015.
 */
public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner myspinner;
    Spinner myspinner2;
    String selected_item="";
    String myselected="";
    String selected_item2="";
    String myselected2="";
    EditText tname,tIdnum,tage,tgender,txtmfl;
    String selectedQn;


    String[] genders={"Please Select Gender","Male","Female","Transgender"};
    String[] cadres={"Please Select Cadre","Single","Married","Divorced","Widowed","Cohabiting"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(mhealth.c4c.R.layout.activity_registration);
        // components from activity_registration.xml


        initialise();



        populateSpinner();
        populateSpinner2();




        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(mhealth.c4c.R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);



        myspinner.setOnItemSelectedListener(this);
        myspinner2.setOnItemSelectedListener(this);

    }



   public void initialise(){

       try{

           tname=(EditText) findViewById(R.id.txtName);
           tIdnum=(EditText) findViewById(R.id.txtIDNum);
           tage=(EditText) findViewById(R.id.txtRAge);
//           tgender=(EditText) findViewById(R.id.txtgender);
           txtmfl=(EditText) findViewById(R.id.txtMFL);
           myspinner=(Spinner) findViewById(R.id.spinner);
           myspinner2=(Spinner) findViewById(R.id.spinner2);


       }
       catch(Exception e){


       }
   }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin=(Spinner) parent;

        if(spin.getId()==R.id.spinner){


           selected_item=parent.getItemAtPosition(position).toString();

                myselected=Integer.toString(position);


            actOnSelected();

        }
        else if (spin.getId()==R.id.spinner2){

            selected_item2=parent.getItemAtPosition(position).toString();

                myselected2=Integer.toString(position);




            actOnSelected();

        }


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void actOnSelected(){

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselectedgender, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }

    public void populateSpinner(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),genders);



            myspinner.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void populateSpinner2(){

        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),cadres);

            myspinner2.setAdapter(customAdapter);

        }

        catch(Exception e){


        }
    }


    public void submitClicked(View v){

        try{
            String tnames,tIdnums,tages,tgenders,txtmfls;
            tnames=tname.getText().toString();
            tIdnums=tIdnum.getText().toString();
            tages=tage.getText().toString();
//            tgenders=tgender.getText().toString();
            txtmfls=txtmfl.getText().toString();

            Toast.makeText(this, "name: "+tnames+" idnum "+tIdnums+" "+tages+" "+txtmfls, Toast.LENGTH_LONG).show();

//            Toast.makeText(this, "submit clicked", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(this, "error in submission "+e, Toast.LENGTH_SHORT).show();


        }
    }




    public void LogindisplayDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message);
            adb.setCancelable(false);

            adb.setPositiveButton("Error, Please try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });
            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }
}

