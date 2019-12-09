package mhealth.c4c.Myprofile;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
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
import mhealth.c4c.Tables.Edittable;
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
            final EditText newidC = (EditText) promptsView
                    .findViewById(R.id.newid);
            final EditText newphoneC = (EditText) promptsView
                    .findViewById(R.id.newphone);

            final TextView existingmflC = (TextView) promptsView
                    .findViewById(R.id.existingmfl);
            final TextView existingidC = (TextView) promptsView
                    .findViewById(R.id.existingid);
            final TextView existingphoneC = (TextView) promptsView
                    .findViewById(R.id.existingphone);

            final Button btnUpdate = (Button) promptsView
                    .findViewById(R.id.btnUpdateProfile);

            List<Edittable> myl=Edittable.findWithQuery(Edittable.class,"select * from Edittable limit 1");
            if(myl.size()>0){

                for(int x=0;x<myl.size();x++){

                    String themfl=myl.get(x).getMfl();
                    String theid=myl.get(x).getIdnumber();
                    String thephone=myl.get(x).getPhone();
                    existingmflC.setText(themfl);
                    existingidC.setText(theid);
                    existingphoneC.setText(thephone);

                }

            }

            else{

                existingmflC.setText("N/A");
                existingidC.setText("N/A");
                existingphoneC.setText("N/A");

            }

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newmflS=newmflC.getText().toString();
                    String newphoneS=newphoneC.getText().toString();
                    String newidS=newidC.getText().toString();
                    String Message="UPDT*-1*-1*-1";
                    boolean dataExists=false;

                    List<Edittable>myl=Edittable.findWithQuery(Edittable.class,"select * from Edittable");
                    if(myl.size()>0){
                        dataExists=true;

                    }
                    else{
                        dataExists=false;
                    }
                    if(!newmflS.trim().isEmpty()&&!newphoneS.trim().isEmpty()&&!newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();
                        if(dataExists){
                            Edittable.executeQuery("update Edittable set mfl=?,phone=?,idnumber=?",newmflS,newphoneS,newidS);


                        }
                        else{
                            Edittable et=new Edittable(newmflS,newphoneS,newidS);
                            et.save();
                        }

                        Message="UPDT*"+ Base64Encoder.encryptString(newmflS+"*"+newphoneS+"*"+newidS);
                    }
                    else if(!newmflS.trim().isEmpty() && newphoneS.trim().isEmpty() && !newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set mfl=?,idnumber=?",newmflS,newidS);

                        Message="UPDT*"+ Base64Encoder.encryptString(newmflS+"*-1"+newidS);
                    }
                    else if(!newmflS.trim().isEmpty() && newphoneS.trim().isEmpty() && newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set mfl=?",newmflS);

                        Message="UPDT*"+ Base64Encoder.encryptString(newmflS+"*-1*-1");
                    }
                    else if(!newmflS.trim().isEmpty() && !newphoneS.trim().isEmpty() && newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set mfl=?,phone=?",newmflS,newphoneS);

                        Message="UPDT*"+ Base64Encoder.encryptString(newmflS+"*"+newphoneS+"*-1");
                    }
                    else if(newmflS.trim().isEmpty() && !newphoneS.trim().isEmpty() && newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set phone=?",newphoneS);

                        Message="UPDT*"+ Base64Encoder.encryptString("-1*"+newphoneS+"*-1");
                    }

                    if(newmflS.trim().isEmpty()&&!newphoneS.trim().isEmpty()&&!newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set phone=?,idnumber=?",newphoneS,newidS);

                        Message="UPDT*"+ Base64Encoder.encryptString("-1*"+newphoneS+"*"+newidS);
                    }
                    else if(newmflS.trim().isEmpty() && newphoneS.trim().isEmpty() && !newidS.trim().isEmpty()){

//                        Toast.makeText(ctx, "specify the new mfl code", Toast.LENGTH_LONG).show();

                        Edittable.executeQuery("update Edittable set idnumber=?",newidS);

                        Message="UPDT*"+ Base64Encoder.encryptString("-1*-1*"+newidS);
                    }



                        SmsManager sm = SmsManager.getDefault();
                        ArrayList<String> parts = sm.divideMessage(Message);

                        sm.sendMultipartTextMessage(Config.shortcode, null, parts, null, null);

                        Toast.makeText(ctx, "success updating mfl code", Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();

                }
            });




            alertDialog.show();
        }
        catch(Exception e){


        }
    }
}
