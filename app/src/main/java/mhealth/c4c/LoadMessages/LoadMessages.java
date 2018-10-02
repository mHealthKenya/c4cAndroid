package mhealth.c4c.LoadMessages;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.config.Config;
import mhealth.c4c.encryption.Base64Encoder;

public class LoadMessages {

    Context ctx;
    Base64Encoder encoder;


    public LoadMessages(Context ctx) {
        this.ctx = ctx;
        encoder=new Base64Encoder();

    }



    public void loadInboxMessages() {
        try {

            int count=0;
            ContentResolver contentResolver = ctx.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),null, null, null,null);
            int indexBody = smsInboxCursor.getColumnIndex("body");

            int indexDate = smsInboxCursor.getColumnIndex("date");



            System.out.println("***********message****::");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String addr = smsInboxCursor.getString(2);
                String datee = smsInboxCursor.getString(indexDate);
                Long mydate=Long.parseLong(datee);

                if(addr.contentEquals(Config.shortcode)){


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    String mytimestamp=formatter.format(calendar.getTime());

                    count++;

                    if(str.contains("CHKRIGHTS")){

                        String[] decryptedPiece=str.split("\\*");
                        String messToDec=decryptedPiece[1];


                        String decryptedmess =encoder.decryptedString(messToDec.getBytes());

                        String hasRights=decryptedmess;

                        List<Broadcastsmsrights> myl=Broadcastsmsrights.findWithQuery(Broadcastsmsrights.class,"select * from Broadcastsmsrights limit 1");
                            if(myl.size()>0){

                            }
                            else{

                                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                                tr.save();

                            }


                    }

                    else{

                            String hasRights="no";


                            List<Broadcastsmsrights> myl=Broadcastsmsrights.findWithQuery(Broadcastsmsrights.class,"select * from Broadcastsmsrights");
                            if(myl.size()>0){

                            }
                            else{

                                Broadcastsmsrights tr=new Broadcastsmsrights(hasRights);
                                tr.save();

                            }



                    }
                }


            } while (smsInboxCursor.moveToNext());

        }
        catch(Exception e){


        }


    }




}

