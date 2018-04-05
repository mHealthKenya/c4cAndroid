package mhealth.c4c;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mhealth.c4c.Tables.Messages;

/**
 * Created by root on 11/5/17.
 */

public class MessagesTabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    ProgressDialog progressDialog;

    String[] tabTitle = {"Unread", "Read"};
    int[] Counts = {0, 0};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_tabs);
        setToolBar();
//        MyViewPager();
//
//        pagerListener();
//
//
//        refreshSmsInbox();
//
//
//        Counts[0] = getReadMessages();
//        Counts[1] = getUnReadMessages();
//        setupTabIcons();




    }

    public int getReadMessages(){
        int x=0;

        try{

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

            for(int y=0;y<bdy.size();y++){

                String read=bdy.get(y).getRead();

                if(read.contentEquals("read")){
                    x+=1;
                }

                }

       }
        catch(Exception e){


        }
        return x;
    }

    public int getUnReadMessages(){
        int x=0;

        try{

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

            for(int y=0;y<bdy.size();y++){

                String read=bdy.get(y).getRead();

                if(!read.contentEquals("read")){
                    x+=1;
                }

            }

        }
        catch(Exception e){


        }
        return x;
    }



    public void pagerListener(){
        try{

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try{

                    }
                    catch(Exception e){
                        Toast.makeText(MessagesTabs.this, "error updating fragment "+e, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        catch(Exception e){


        }
    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Messages");

        }
        catch(Exception e){


        }
    }

    private void setupTabIcons() {

        for (int i = 0; i < tabTitle.length; i++) {

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }



    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab_messages, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title_messages);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count_messages);
        tv_title.setText(tabTitle[pos]);
        if (Counts[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + Counts[pos]);
        } else
            tv_count.setVisibility(View.GONE);


        return view;
    }


    public void MyViewPager(){

        try{

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);


            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
            tabLayout.setSelectedTabIndicatorHeight(5);
        }
        catch(Exception e){


        }
    }




    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentUnRead(), "Unread");
        adapter.addFragment(new FragmentRead(), "Read");





        viewPager.setAdapter(adapter);
    }





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public Fragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);//enables displaying the title
            //return null;//disables the displaying of title on tabs
        }

    }





    public void refreshSmsInbox() {
        try {
            ContentResolver contentResolver = getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address=?", new String[]{"40145"}, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
            int indexDate = smsInboxCursor.getColumnIndex("date");



            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String addr = smsInboxCursor.getString(indexAddress);
                String datee = smsInboxCursor.getString(indexDate);
                Long mydate=Long.parseLong(datee);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mydate);
                String mytimestamp=formatter.format(calendar.getTime());

                Messages ms=new Messages(addr,str,mytimestamp,"unread","null");
                ms.save();

            } while (smsInboxCursor.moveToNext());

        }
        catch(Exception e){

        }


    }



    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        new AsyncTaskRunner().execute();
    }





    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            method();
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

//            Toast.makeText(MainActivity.this, "getting messages", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, "done getting messages", Toast.LENGTH_SHORT).show();
        }
    }






    public void method(){

        runOnUiThread(new Runnable()
        {
            public void run()
            {
                try {
                    LoadHeavyStuff();




                } catch (Exception e) {
                    e.printStackTrace();
                }

//                LoadHeavyStuff();

            }
        });
    }





    public void LoadHeavyStuff(){


        try{
            progressDialog = new ProgressDialog(MessagesTabs.this);
            progressDialog.setTitle("Getting Messages...");
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.show();




            Handler mHand  = new Handler();
            mHand.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub


                   DisplayContent();

                    progressDialog.dismiss();




                    //Dismiss progressBar here

                }
            }, 5000);






//

//            pr.DissmissProgress();
        }
        catch(Exception e){




        }
    }




    public void DisplayContent(){

        try{



            MyViewPager();

            pagerListener();

            List<Messages> myl=Messages.findWithQuery(Messages.class,"select * from Messages",null);

            if(myl.size()==0){
                refreshSmsInbox();


            }



            Counts[1] = getReadMessages();
            Counts[0] = getUnReadMessages();
            setupTabIcons();

        }
        catch(Exception e){


        }
    }
}
