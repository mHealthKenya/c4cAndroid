package mhealth.c4c;

/**
 * Created by kennedy on 9/13/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;

import mhealth.c4c.vaccinationstab.VaccinationTabs;

/**
 * Created by cmukami on 8/1/2017.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private Context mContext;
    private boolean checkedKmpdu;

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemImage;
        public TextView itemTitle;
        public CardView itemBg;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage =
                    (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle =
                    (TextView)itemView.findViewById(R.id.item_title);
            itemBg =
                    (CardView)itemView.findViewById(R.id.card_view);
        }
    }

//    private String[] titles = {
//            "REPORT EXPOSURE",
//            "INFORMATION CENTER",
//            "CHECK IN",
//            "BROADCAST SMS",
//            "USER PROFILE"};

    private String[] titles = {
            "IMMUNISATION PROFILE",
            "CHECK IN",
            "BROADCAST SMS",
            "REPORT EXPOSURE",
            "INFORMATION CENTER"
            };

//old design
//    private String[] titles2 = {
//            "REPORT EXPOSURE",
//            "INFORMATION CENTER",
//            "ANNUAL CHECK UP & VACCINATION CALENDAR",
//            "BROADCAST SMS",
//            "USER PROFILE"};

    private String[] titles2 = {
            "IMMUNISATION PROFILE",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE",
            "BROADCAST SMS",
            "REPORT EXPOSURE",
            "INFORMATION CENTER"
            };

    private int[] images = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.nascopsite2,
            R.drawable.broadcast,
    R.drawable.report};
    private int[] images2 = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.report,
            R.drawable.broadcast,
            R.drawable.report};

    private int[] thumbnail = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.checked,
            R.drawable.broadcast1,
            R.drawable.report};

    private int[] thumbnail2 = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.reporting,
            R.drawable.broadcast1,
            R.drawable.report};

    private String[] tints = {
            "#3F51B5",
            "#303F9F",
            "#FF4081",
            "#33009688",
            "#303F9F"};

    private String[] tints2 = {
            "#3F51B5",
            "#303F9F",
            "#3F51B5",
            "#33009688",
            "#303F9F"};

    public RvAdapter(Context mContext,boolean kmpduChecked) {
        this.mContext = mContext;
        this.checkedKmpdu=kmpduChecked;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if(checkedKmpdu){


            viewHolder.itemTitle.setText(titles2[i]);
            //viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(thumbnail2[i]);
            viewHolder.itemBg.setBackgroundResource(images2[i]);
            // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


            if(i==3) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        Intent intent = new Intent(mContext, Report.class);
                        mContext.startActivity(intent);

                    }
                });
            }
            else if(i==4) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        Intent myint=new Intent(mContext,Info_Center.class);
                        mContext.startActivity(myint);

                    }
                });
            }

            else if(i==1) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        mContext = v.getContext();

                        Intent myint=new Intent(mContext, VaccinationTabs.class);
                        mContext.startActivity(myint);

                    }
                });
            }


            else if(i==2) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        //Intent intent = new Intent(mContext, Report.class);
                        //mContext.startActivity(intent);
                        Intent myint=new Intent(mContext,BroadcastSms.class);
                        mContext.startActivity(myint);

                    }
                });
            }

            else if(i==0) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        //Intent intent = new Intent(mContext, Report.class);
                        //mContext.startActivity(intent);
                        Intent myint=new Intent(mContext,ImmunisationProfile.class);
                        mContext.startActivity(myint);

                    }
                });
            }





        }
        else{




            viewHolder.itemTitle.setText(titles[i]);
            //viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(thumbnail[i]);
            viewHolder.itemBg.setBackgroundResource(images[i]);
            // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


            if(i==3) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        Intent intent = new Intent(mContext, Report.class);
                        mContext.startActivity(intent);

                    }
                });
            }
            else if(i==4) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        Intent myint=new Intent(mContext,Info_Center.class);
                        mContext.startActivity(myint);

                    }
                });
            }

            else if(i==1) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();


                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

                        String sendMessage="CHK*"+mytime;
                        SmsManager sm = SmsManager.getDefault();


                        ArrayList<String> parts = sm.divideMessage(sendMessage);
                        sm.sendMultipartTextMessage("40149", null, parts, null, null);

                        Toast.makeText(mContext, "YOU HAVE SUCCESSFULLY CHECKED IN", Toast.LENGTH_SHORT).show();

                    }
                });
            }


            else if(i==2) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        //Intent intent = new Intent(mContext, Report.class);
                        //mContext.startActivity(intent);
                        Intent myint=new Intent(mContext,BroadcastSms.class);
                        mContext.startActivity(myint);

                    }
                });
            }


            else if(i==0) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext = v.getContext();

                        //Intent intent = new Intent(mContext, Report.class);
                        //mContext.startActivity(intent);
                        Intent myint=new Intent(mContext,ImmunisationProfile.class);
                        mContext.startActivity(myint);

                    }
                });
            }



        }


    }
}

