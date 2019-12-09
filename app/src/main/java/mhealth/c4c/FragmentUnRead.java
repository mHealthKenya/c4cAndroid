package mhealth.c4c;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mhealth.c4c.Adapter.MessagesAdapter;
import mhealth.c4c.Tables.Messages;

/**
 * Created by root on 11/5/17.
 */

public class FragmentUnRead extends Fragment {
    private List<Mydata> mymesslist;
    View v;


    private static FragmentUnRead inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    TextView tvcontent;
    TextView tvRead;
    int counter=0;
    String smsMessage = "";


    public static final String READ_SETTINGS = "READ_SETTINGS";
    public static FragmentUnRead instance() {
        return (new FragmentUnRead());
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    ListView lv;
    private MessagesAdapter myadapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_unread,container,false);

        lv=(ListView) v.findViewById(R.id.mylvmes1);
//        fl=(FrameLayout) v.findViewById(R.id.eid);

        mymesslist=new ArrayList<>();
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);



        for(int x=0;x<bdy.size();x++){

            counter += 1;
            String messbdy=bdy.get(x).getmBody();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();

            if(!read.contentEquals("read")){




                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }

                mymesslist.add(new Mydata(messbdy,ndate,read));
            }


        }


        myadapter=new MessagesAdapter(getActivity(),mymesslist);
        lv.setAdapter(myadapter);


        ClickListener();

        return v;
    }





    public void refreshSmsInbox() {
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

            if (bdy.isEmpty())
                return;
//            myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();



                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();

               if(!read.contentEquals("read")){


                   String bdycont=messbdy+"@"+ndate;


                   String[] checkSplitdate=ndate.split("/");


                   if(checkSplitdate.length>1){

                   }
                   else{
                       DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                       Calendar calendar = Calendar.getInstance();
                       calendar.setTimeInMillis(Long.parseLong(ndate));
                       ndate = formatter.format(calendar.getTime());

                   }

                   mymesslist.add(new Mydata(messbdy,ndate,read));

//                myadapter.add(bdycont);
                   myadapter=new MessagesAdapter(getActivity(),mymesslist);
               }


            }

        }
        catch(Exception e){

        }


    }

    public void updateList() {
        try {
            refreshSmsInbox();
            myadapter.notifyDataSetChanged();
        }
        catch(Exception e){

        }
    }


    public void ClickListener(){

        try{



            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgdate=mymesslist.get(position).getDate();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


                        MydialogBuilder(msgbdy,msgdate);

//                    System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);
                        for(int x=0;x<myl.size();x++){

                            Messages ms=(Messages) myl.get(x);
                            if(ms.getmTimeStamp().contentEquals(msgdate)){

                                if(ms.getRead().contentEquals("read")){
                                    sending=false;

                                }
                                else{

                                    sending=true;
                                    ms.getId();
                                    ms.setRead("read");
                                    ms.setDateRead(mytime);
//                    Toast.makeText(getActivity(), "id: "+ms.getCode(), Toast.LENGTH_SHORT).show();
                                    ms.save();
                                }



                            }

                        }


                        if(sending){

//                            String sendMessage=msgbdy+"*"+mytime;
//                            SmsManager sm = SmsManager.getDefault();
////                            sm.sendTextMessage(msc.sendSmsShortcode, null,sendMessage, null, null);
//
//                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendMessage));
//
//                            ArrayList<String> parts = sm.divideMessage(encrypted);
//                            sm.sendMultipartTextMessage(msc.sendSmsShortcode, null, parts, null, null);

                        }





                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();

                           if(!read.contentEquals("read")){


                               String[] checkSplitdate=ndate.split("/");


                               if(checkSplitdate.length>1){

                               }
                               else{
                                   DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                   Calendar calendar = Calendar.getInstance();
                                   calendar.setTimeInMillis(Long.parseLong(ndate));
                                   ndate = formatter.format(calendar.getTime());

                               }




                               mymesslist.add(new Mydata(messbdy,ndate,read));
                           }





                        }

//                        Collections.sort(mymesslist,Mydata.VlcountComparator);

                        Mydata model = mymesslist.get(position);

                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}



                    myadapter.notifyDataSetChanged();

                }
            });
        }
        catch(Exception e){



        }
    }




    public void MydialogBuilder(final String message,final String date){

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setMessage(message+"\n"+date);
        b.setCancelable(false);

        b.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        b.setNeutralButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message+"\n"+date);
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, date);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
    }
}
