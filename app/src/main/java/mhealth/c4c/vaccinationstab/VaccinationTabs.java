package mhealth.c4c.vaccinationstab;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mhealth.c4c.ImmunisationProfile;
import mhealth.c4c.R;
import mhealth.c4c.Tables.Messages;
import mhealth.c4c.Vaccinationfragments.AnnualCheckup;
import mhealth.c4c.Vaccinationfragments.NotVaccinated;
import mhealth.c4c.Vaccinationfragments.PendingVaccination;
import mhealth.c4c.Vaccinationfragments.Vaccinated;
import mhealth.c4c.checkupstatustable.UpdateStatusTable;

/**
 * Created by root on 2/22/18.
 */

public class VaccinationTabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    ProgressDialog progressDialog;
    UpdateStatusTable ut;

    String[] tabTitle = {"Pending Vaccination", "Vaccinated","Not Vaccinated","Annual Checkup Calendar"};
    int[] Counts = {0, 0,0,0};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination_tabs);
        initialise();
        setToolBar();
        DisplayContent();
        saveAllStatusVaccine();
    }

    public void initialise(){

        try{

            ut=new UpdateStatusTable(VaccinationTabs.this);
        }
        catch(Exception e){


        }
    }

    public void saveAllStatusVaccine(){

        try{

            ut.updateInfluenza();
            ut.updateMeasles();
            ut.updateMeningoco();
            ut.updateTdap();
            ut.updateVaricella();
        }
        catch(Exception e){


        }
    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.vacctoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Vaccination Checkup");

        }
        catch(Exception e){


        }
    }


    public void pagerListener(){
        try{

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try{

                    }
                    catch(Exception e){
                        Toast.makeText(VaccinationTabs.this, "error updating fragment "+e, Toast.LENGTH_SHORT).show();
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


    private void setupTabIcons() {

        for (int i = 0; i < tabTitle.length; i++) {

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }



    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
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

            viewPager = (ViewPager) findViewById(R.id.vaccviewpager);
            setupViewPager(viewPager);


            tabLayout = (TabLayout) findViewById(R.id.vacctabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
            tabLayout.setSelectedTabIndicatorHeight(5);
        }
        catch(Exception e){


        }
    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        "Pending Vaccination", "Vaccinated","Not Vaccinated","Annual Checkup Calendar"}
        adapter.addFragment(new PendingVaccination(), "Pending Vaccination");
        adapter.addFragment(new Vaccinated(), "Vaccinated");
        adapter.addFragment(new NotVaccinated(), "Not Vaccinated");
        adapter.addFragment(new AnnualCheckup(), "Annual Checkup Calendar");





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





    public void DisplayContent(){

        try{

            MyViewPager();
            pagerListener();
            setupTabIcons();

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying content "+e, Toast.LENGTH_SHORT).show();


        }
    }

}
