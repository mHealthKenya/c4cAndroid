package mhealth.c4c;

/**
 * Created by kennedy on 9/13/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mhealth.c4c.Tables.Broadcastsmsrights;
import mhealth.c4c.Tables.Partners;
import mhealth.c4c.dialogs.Dialogs;
import mhealth.c4c.vaccinationstab.VaccinationTabs;

/**
 * Created by cmukami on 8/1/2017.
 */

public class LandingPageAdapter extends RecyclerView.Adapter<LandingPageAdapter.ViewHolder> {
    private Context mContext;
    private boolean checkedKmpdu;
    Dialogs sweetdiaog;


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


    public boolean isKmtcAvailable(){

        try{
            List<Partners> myl=Partners.findWithQuery(Partners.class,"select * from Partners where partnername=?","KMTC");
            if(myl.size()>0){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){

            return false;
        }
    }

    public boolean isMohAvailable(){

        try{
            List<Partners> myl=Partners.findWithQuery(Partners.class,"select * from Partners where partnername=?","MOH");
            if(myl.size()>0){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){

            return false;
        }
    }

    public boolean isKmpdbAvailable(){

        try{
            List<Partners> myl=Partners.findWithQuery(Partners.class,"select * from Partners where partnername=?","KMPDB");
            if(myl.size()>0){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){

            return false;
        }
    }

//    private String[] titles = {
//            "REPORT EXPOSURE",
//            "INFORMATION CENTER",
//            "CHECK IN",
//            "BROADCAST SMS",
//            "USER PROFILE"};

    private String[] titles = {
            "REPORT EXPOSURE",
            "CHECK IN",
            "BROADCAST SMS",
            "IMMUNISATION PROFILE",
            "INFORMATION CENTER"
            };

    private String[] titlesnobroadcast = {
            "REPORT EXPOSURE",
            "CHECK IN",
            "IMMUNISATION PROFILE",
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
            "REPORT EXPOSURE",
            "IMMUNISATION PROFILE",
            "BROADCAST SMS",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE",
            "INFORMATION CENTER"
            };

    private String[] titles2nobroadcast = {
            "REPORT EXPOSURE",
            "IMMUNISATION PROFILE",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE",
            "INFORMATION CENTER"
    };

    private String[] titles3 = {
            "REPORT EXPOSURE",
            "CHECK IN",
            "BROADCAST SMS",
            "IMMUNISATION PROFILE",
            "INFORMATION CENTER",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE"
    };

    private String[] titles3nobroadcast= {
            "REPORT EXPOSURE",
            "CHECK IN",
            "IMMUNISATION PROFILE",
            "INFORMATION CENTER",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE"
    };

    private String[] titlesmoh = {
            "REPORT EXPOSURE",
            "BROADCAST SMS",
            "IMMUNISATION PROFILE",
            "INFORMATION CENTER",
            "ANNUAL CHECK UP & VACCINATION SCHEDULE"
    };

    private int[] images = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.nascopsite2,
            R.drawable.broadcast,
    R.drawable.report};

    private int[] imagesnobroadcast = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.nascopsite2,
            R.drawable.report};

    private int[] images2 = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.report,
            R.drawable.broadcast,
            R.drawable.report};


    private int[] images2nobroadcast = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.report,
            R.drawable.report};

    private int[] images3 = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.report,
            R.drawable.broadcast,
            R.drawable.report,
            R.drawable.report};


    private int[] images3nobroadcast = {
            R.drawable.report,
            R.drawable.faq,
            R.drawable.report,
            R.drawable.report,
            R.drawable.report};

    private int[] imagesmoh = {
            R.drawable.report,
            R.drawable.report,
            R.drawable.broadcast,
            R.drawable.report,
            R.drawable.report};

    private int[] thumbnail = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.checked,
            R.drawable.broadcast1,
            R.drawable.report};

    private int[] thumbnailnobroadcast = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.checked,
            R.drawable.report};

    private int[] thumbnail2 = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.reporting,
            R.drawable.broadcast1,
            R.drawable.report};


    private int[] thumbnail2nobroadcast = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.reporting,

            R.drawable.report};

    private int[] thumbnail3 = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.reporting,
            R.drawable.broadcast1,
            R.drawable.report,
            R.drawable.report};


    private int[] thumbnail3nobroadcast = {
            R.drawable.reporting,
            R.drawable.faqim,
            R.drawable.reporting,
            R.drawable.report,
            R.drawable.report};

    private int[] thumbnailmoh = {
            R.drawable.reporting,
            R.drawable.reporting,
            R.drawable.broadcast1,
            R.drawable.report,
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

    private String[] tints3 = {
            "#3F51B5",
            "#303F9F",
            "#3F51B5",
            "#33009688",
            "#303F9F",
            "#303F9F"};

    public LandingPageAdapter(Context mContext, boolean kmpduChecked) {
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


        if((!(checkedKmpdu) && (!isMohAvailable()) && (!isKmpdbAvailable()) )&& isKmtcAvailable()){

            int thereturn=0;
            if(canAccessBroadcast()){
                thereturn=titles3.length;

            }
            else{

                thereturn=titles3nobroadcast.length;


            }

            return thereturn;

        }

        else{

            int thereturn=0;
            if(canAccessBroadcast()){
                thereturn=titles.length;

            }
            else{

                thereturn=titlesnobroadcast.length;


            }

            return thereturn;

//            return titles.length;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if(checkedKmpdu||isMohAvailable()||isKmpdbAvailable()){

            if(canAccessBroadcast()){


                viewHolder.itemTitle.setText(titles2[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnail2[i]);
                viewHolder.itemBg.setBackgroundResource(images2[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
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

                else if(i==3) {
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

                else if(i==1) {
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

//            if user does not have the rights to access broadcast
            else{



                viewHolder.itemTitle.setText(titles2nobroadcast[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnail2nobroadcast[i]);
                viewHolder.itemBg.setBackgroundResource(images2nobroadcast[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext = v.getContext();

                            Intent intent = new Intent(mContext, Report.class);
                            mContext.startActivity(intent);

                        }
                    });
                }
                else if(i==3) {
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
                            Intent myint=new Intent(mContext,ImmunisationProfile.class);
                            mContext.startActivity(myint);

                        }
                    });
                }


            }







        }


        else if(((!checkedKmpdu)&&(!isMohAvailable()) &&(!isKmpdbAvailable()))&& isKmtcAvailable()){

//           if user has broadcast rights
            if(canAccessBroadcast()){


                viewHolder.itemTitle.setText(titles3[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnail3[i]);
                viewHolder.itemBg.setBackgroundResource(images3[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
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


                            sweetdiaog=new Dialogs(mContext);
                            sweetdiaog.showConfirmCheckIn("Are you sure you want to check in ?","Confirm Check in");


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


                else if(i==3) {
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

                else if(i==5) {
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



            }

//            if user does not have access to brodcast
            else{



                viewHolder.itemTitle.setText(titles3nobroadcast[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnail3nobroadcast[i]);
                viewHolder.itemBg.setBackgroundResource(images3nobroadcast[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext = v.getContext();

                            Intent intent = new Intent(mContext, Report.class);
                            mContext.startActivity(intent);

                        }
                    });
                }
                else if(i==3) {
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


                            sweetdiaog=new Dialogs(mContext);
                            sweetdiaog.showConfirmCheckIn("Are you sure you want to check in ?","Confirm Check in");


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
                            Intent myint=new Intent(mContext,ImmunisationProfile.class);
                            mContext.startActivity(myint);

                        }
                    });
                }

                else if(i==4) {
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


            }







        }

       else{



//            if user has brodacast rights
            if(canAccessBroadcast()){



                viewHolder.itemTitle.setText(titles[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnail[i]);
                viewHolder.itemBg.setBackgroundResource(images[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
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


                            sweetdiaog=new Dialogs(mContext);
                            sweetdiaog.showConfirmCheckIn("Are you sure you want to check in ?","Confirm Check in");


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


                else if(i==3) {
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

//            if user does not have broadcast rights
            else{




                viewHolder.itemTitle.setText(titlesnobroadcast[i]);
                //viewHolder.itemDetail.setText(details[i]);
                viewHolder.itemImage.setImageResource(thumbnailnobroadcast[i]);
                viewHolder.itemBg.setBackgroundResource(imagesnobroadcast[i]);
                // viewHolder.itemBg.setCardBackgroundColor(Co2lor.parseColor(tints[i]));


                if(i==0) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext = v.getContext();

                            Intent intent = new Intent(mContext, Report.class);
                            mContext.startActivity(intent);

                        }
                    });
                }
                else if(i==3) {
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


                            sweetdiaog=new Dialogs(mContext);
                            sweetdiaog.showConfirmCheckIn("Are you sure you want to check in ?","Confirm Check in");


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
                            Intent myint=new Intent(mContext,ImmunisationProfile.class);
                            mContext.startActivity(myint);

                        }
                    });
                }


            }




        }


    }

    public boolean canAccessBroadcast(){

        boolean canAccess=false;

        List<Broadcastsmsrights> myl=Broadcastsmsrights.findWithQuery(Broadcastsmsrights.class,"select * from Broadcastsmsrights limit 1");
        for(int x=0;x<myl.size();x++){
            String canacces=myl.get(x).getCanbroadcast();
            if(canacces.equalsIgnoreCase("yes")){
                canAccess=true;
            }
            else{
                canAccess=false;
            }
        }

        return canAccess;
    }
}

