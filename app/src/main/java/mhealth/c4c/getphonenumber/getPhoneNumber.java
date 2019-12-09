package mhealth.c4c.getphonenumber;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by root on 5/8/18.
 */

public class getPhoneNumber {

    Context ctx;

    public getPhoneNumber(Context ctx) {
        this.ctx = ctx;
    }

    public String getMyNumber() {

        try {


            TelephonyManager telemamanger = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return "";
            }
            String getSimNumber = telemamanger.getLine1Number();
            String getSimSerialNumber = telemamanger.getSimSerialNumber();

            return getSimNumber;
        }
        catch(Exception e){
            return "";


        }
    }
}
