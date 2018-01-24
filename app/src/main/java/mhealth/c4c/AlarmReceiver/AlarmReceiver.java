package mhealth.c4c.AlarmReceiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import mhealth.c4c.Login;
import mhealth.c4c.R;
import mhealth.c4c.Tables.ProfileCompletion;
import mhealth.c4c.UserProfile;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by root on 1/24/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


            /* Setting the alarm here */
//            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
//
//            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            int interval = 1000 * 60 * 1;
//
//
//            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//
//            Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();

        loadMyAlarm(context);

//        Toast.makeText(context, "I'm running from receiver", Toast.LENGTH_SHORT).show();

    }



    public void loadMyAlarm(Context ctx){

        try{

            String completedValue="0";

            List<ProfileCompletion> uplist=ProfileCompletion.findWithQuery(ProfileCompletion.class,"select * from Profile_completion");
            if(uplist.size()>0){
                for(int x=0;x<uplist.size();x++){

                    completedValue= uplist.get(x).getMyvalue();
                }
            }
            int val=Integer.parseInt(completedValue);
            if(val<100){

                myNotification(ctx);
            }
            else{
//                cancel();
            }


        }
        catch(Exception e){


        }
    }

    public void myNotification(Context ctx){

        try{

            String completedValue="0";

            List<ProfileCompletion> uplist=ProfileCompletion.findWithQuery(ProfileCompletion.class,"select * from Profile_completion");
            if(uplist.size()>0){
                for(int x=0;x<uplist.size();x++){

                    completedValue= uplist.get(x).getMyvalue();
                }
            }

            Intent intent = new Intent(ctx, Login.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
            PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);


            Notification n  = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                n = new Notification.Builder(ctx)
                        .setContentTitle("C4C PROFILE COMPLETION")
                        .setContentText("your profile is "+completedValue+" % complete, tap to complete profile")
                        .setSmallIcon(R.drawable.c4c_logo)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
//                        .addAction(R.drawable.c4c_logo, "Complete Profile", pIntent)
                        .build();
            }


            NotificationManager notificationManager =
                    (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
        catch(Exception e){


        }
    }
}
