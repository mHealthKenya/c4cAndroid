package mhealth.c4c.Myprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mhealth.c4c.R;
import mhealth.c4c.Tables.Profiletable;
import mhealth.c4c.config.Config;
import mhealth.c4c.encryption.Base64Encoder;

public class Myprofile {

    Context ctx;

    public Myprofile(Context ctx) {
        this.ctx = ctx;
    }


    public void displayProfile(){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            final View promptsView = li.inflate(R.layout.my_profile, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final EditText newmflC = (EditText) promptsView
                    .findViewById(R.id.newmfl);

            final TextView existingmflC = (TextView) promptsView
                    .findViewById(R.id.existingmfl);

            final Button btnUpdate = (Button) promptsView
                    .findViewById(R.id.btnUpdateProfile);

            List<Profiletable> myl=Profiletable.findWithQuery(Profiletable.class,"select * from Profiletable limit 1");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String themfl=myl.get(x).getMflcode();
                    existingmflC.setText(themfl);
                }

            }

            else{

                existingmflC.setText("N/A");

            }

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newmflS=newmflC.getText().toString();
                    if(newmflS.trim().isEmpty()){

                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                    }
                    else{

                        Profiletable book = Profiletable.findById(Profiletable.class, 1);
                        book.mflcode = newmflS; // modify the values
                        book.save();

                        String Message="UPDT*"+ Base64Encoder.encryptString(newmflS);
//                        String Message = "Rep*"+where+"*"+nature+"*"+myhour;

                        SmsManager sm = SmsManager.getDefault();
                        ArrayList<String> parts = sm.divideMessage(Message);

                        sm.sendMultipartTextMessage(Config.shortcode, null, parts, null, null);

                        Toast.makeText(ctx, "success updating mfl code", Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();
                    }
                }
            });




            alertDialog.show();
        }
        catch(Exception e){


        }
    }
}
