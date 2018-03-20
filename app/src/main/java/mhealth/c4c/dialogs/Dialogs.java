package mhealth.c4c.dialogs;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mhealth.c4c.Login;
import mhealth.c4c.Recycler;

/**
 * Created by root on 3/16/18.
 */

public class Dialogs {

    Context ctx;
    SweetAlertDialog mdialog;

    public Dialogs(Context ctx) {
        this.ctx = ctx;
    }

    public void showErrorDialogRegistration(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }


    public void showErrorDialogLogin(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showErrorDialogForgotPassword(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showErrorDialogImmunisation(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showConfirmCheckIn(String title, String message){
        try{

            mdialog=new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);
                    mdialog.setTitleText(title);
                    mdialog.setContentText(message);
                    mdialog.setCancelable(false);
                    mdialog.setConfirmText("Proceed to Check in");
                    mdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

//                            Intent i = new Intent(ctx,Login.class);
//                            // Closing all the Activities
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            ctx.startActivity(i);



                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                            String mytime=timestamp.toString();

                            String sendMessage="CHK*"+mytime;
                            SmsManager sm = SmsManager.getDefault();


                            ArrayList<String> parts = sm.divideMessage(sendMessage);
                            sm.sendMultipartTextMessage("40149", null, parts, null, null);

                            sDialog.dismissWithAnimation();
//                            Toast.makeText(mContext, "YOU HAVE SUCCESSFULLY CHECKED IN", Toast.LENGTH_SHORT).show();
                            showSuccessDialogCheckIn("YOU HAVE SUCCESSFULLY CHECKED IN","Success");

                        }
                    })
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        }
        catch(Exception e){


            Toast.makeText(ctx, "success showing dialog", Toast.LENGTH_SHORT).show();

        }
    }

    public void showSuccessDialogCheckIn(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showSuccessDialogImmunisation(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent myint=new Intent(ctx, Recycler.class);
                    ctx.startActivity(myint);

                }
            });
            mdialog.show();


        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showSuccessDialogReportExposure(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showErrorDialogReportExposure(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }

    public void showCalendarCheckup(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }


    public void showSuccessDialogCalendarCheckup(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }


    public void showSuccessDialogForgotPassword(String title, String message){
        try{

            new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(title)
                    .setContentText(message)
                    .setConfirmText("Proceed to login")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent i = new Intent(ctx,Login.class);
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(i);

                        }
                    })
                    .setCancelButton("Exit App", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        }
        catch(Exception e){


            Toast.makeText(ctx, "success showing dialog", Toast.LENGTH_SHORT).show();

        }
    }


    public void showSuccessDialogLogin(String title, String message){
        try{

            new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(title)
                    .setContentText(message)
                    .setConfirmText("Proceed to login")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent i = new Intent(ctx,Login.class);
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(i);

                        }
                    })
                    .setCancelButton("Exit App", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        }
        catch(Exception e){


            Toast.makeText(ctx, "success showing dialog", Toast.LENGTH_SHORT).show();

        }
    }

    public void showSuccessDialogRegistration(String title, String message){
        try{

            mdialog=new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setConfirmText("Proceed to login");
            mdialog.setCancelable(false);
            mdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    Intent i = new Intent(ctx,Login.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(i);

                }
            })
                    .setCancelButton("Exit App", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .show();

        }
        catch(Exception e){


            Toast.makeText(ctx, "success showing dialog", Toast.LENGTH_SHORT).show();

        }
    }
}