package mhealth.c4c.ResourcesLink;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mhealth.c4c.R;

public class Resources extends AppCompatActivity {

    TextView lk1,lk2,lk3,lk4,lk5,lk6,lk7,lk8,lk9,lk10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resources_link);
        setToolBar();
        initialise();
        setLinks();
    }

    private void initialise(){

        lk1=(TextView) findViewById(R.id.link1);
        lk2=(TextView) findViewById(R.id.link2);
        lk3=(TextView) findViewById(R.id.link3);
        lk4=(TextView) findViewById(R.id.link4);
        lk5=(TextView) findViewById(R.id.link5);
        lk6=(TextView) findViewById(R.id.link6);
        lk7=(TextView) findViewById(R.id.link7);
        lk8=(TextView) findViewById(R.id.link8);
        lk9=(TextView) findViewById(R.id.link9);
        lk10=(TextView) findViewById(R.id.link10);
    }

    private void setLinks(){

        try{
            String link1="https://c4c.mhealthkenya.co.ke/assets/IPC/national.pdf";
            String link2="https://c4c.mhealthkenya.co.ke/assets/IPC/Cardiovascular-guidelines-2018.pdf";
            String link3="https://c4c.mhealthkenya.co.ke/assets/IPC/clinical guidelines diagnosis and treatment.pdf";
            String link4="https://c4c.mhealthkenya.co.ke/assets/IPC/HealthActNo.21of2017.pdf";
            String link5="https://c4c.mhealthkenya.co.ke/assets/IPC/kenya-strategy-ncds-2015-2020.pdf";
            String link6="https://c4c.mhealthkenya.co.ke/assets/IPC/kenya_health_policy_2014_to_2030.pdf";
            String link7="https://c4c.mhealthkenya.co.ke/assets/IPC/National-Cancer-Screening-Guidelines-2018.pdf";
            String link8="https://c4c.mhealthkenya.co.ke/assets/IPC/National-Cancer-Treatment-Guidelines.pdf";
            String link9="https://c4c.mhealthkenya.co.ke/assets/IPC/NATIONAL-GUIDELINES-FOR-HEALTHY-DIETS-AND-PHYSICAL-ACTIVITY-2017-NEW-EDIT.pdf";
            String link10="https://c4c.mhealthkenya.co.ke/assets/IPC/National-Palliative-Care-Guidelines.pdf";

            setLinkToText(lk1.getText().toString(),lk1.getText().toString().length(),lk1,link1);
            setLinkToText(lk2.getText().toString(),lk2.getText().toString().length(),lk2,link2);
            setLinkToText(lk3.getText().toString(),lk3.getText().toString().length(),lk3,link3);
            setLinkToText(lk4.getText().toString(),lk4.getText().toString().length(),lk4,link4);
            setLinkToText(lk5.getText().toString(),lk5.getText().toString().length(),lk5,link5);
            setLinkToText(lk6.getText().toString(),lk6.getText().toString().length(),lk6,link6);
            setLinkToText(lk7.getText().toString(),lk7.getText().toString().length(),lk7,link7);
            setLinkToText(lk8.getText().toString(),lk8.getText().toString().length(),lk8,link8);
            setLinkToText(lk9.getText().toString(),lk9.getText().toString().length(),lk9,link9);
            setLinkToText(lk10.getText().toString(),lk10.getText().toString().length(),lk10,link10);

        }
        catch(Exception e){

            Toast.makeText(this, "error setting links "+e, Toast.LENGTH_SHORT).show();

        }
    }


    private void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.resourcestoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("RESOURCES LINK");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch(Exception e){


        }
    }

    private void setLinkToText(String message, int meslength, TextView tv, final String link){

        try{

            SpannableString spannableString = new SpannableString(message);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }
            };
            spannableString.setSpan(clickableSpan, spannableString.length() - meslength,
                    spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(spannableString, TextView.BufferType.SPANNABLE);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
        catch(Exception e){

        }
    }
}
