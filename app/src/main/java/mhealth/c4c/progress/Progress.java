package mhealth.c4c.progress;


import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Progress{


    Context ctx;
    public SweetAlertDialog pDialog;


    public Progress(Context ctx) {
        this.ctx = ctx;
        pDialog=new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
    }


    public void showProgress(String title){

        try{

            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.getProgressHelper().setBarWidth(3);
            pDialog.getProgressHelper().setRimColor(Color.parseColor("#ff0000"));
            pDialog.getProgressHelper().setSpinSpeed(3);
            pDialog.getProgressHelper().setCircleRadius(100);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#000000"));
            pDialog.setTitleText(title);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        catch(Exception e){

        }
    }



    public void dissmissProgress(){

        pDialog.dismiss();
    }


}
