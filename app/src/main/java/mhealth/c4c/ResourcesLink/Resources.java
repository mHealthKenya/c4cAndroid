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

    TextView lk1,lk2,lk3,lk4,lk5,lk6,lk7,lk8,lk9;

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
    }

    private void setLinks(){

        try{
            String link1="https://healthservices.uonbi.ac.ke/sites/default/files/centraladmin/healthservices/An-Orientation-Guide-for-Health-Workers-in-Health-Care-Waste-Management.pdf";
            String link2="http://www.health.go.ke/wp-content/uploads/2016/01/HCWM-Strategic-Plan-2015-2020-.pdf";
            String link3="http://cquin.icap.columbia.edu/wp-content/uploads/2017/04/ICAP_CQUIN_Kenya-ARV-Guidelines-2018-Final_20thAug2018.pdf";
            String link4="https://www.nascop.or.ke/?p=1058";
            String link5="http://www.health.go.ke/wp-content/uploads/2015/09/OCCUPATIONAL%20HEALTH%20AND%20SAFETY%20POLICY%20GUIDELINES%20FOR%20THE%20HEALTH%20SECTOR%20IN%20KENYA.pdf";
            String link6="http://extwprlegs1.fao.org/docs/pdf/ken171510.pdf";
            String link7="https://www.google.com";
            String link8="https://www.google.com";
            String link9="https://www.google.com";


            setLinkToText(lk1.getText().toString(),lk1.getText().toString().length(),lk1,link1);
            setLinkToText(lk2.getText().toString(),lk2.getText().toString().length(),lk2,link2);
            setLinkToText(lk3.getText().toString(),lk3.getText().toString().length(),lk3,link3);
            setLinkToText(lk4.getText().toString(),lk4.getText().toString().length(),lk4,link4);
            setLinkToText(lk5.getText().toString(),lk5.getText().toString().length(),lk5,link5);
            setLinkToText(lk6.getText().toString(),lk6.getText().toString().length(),lk6,link6);
            setLinkToText(lk7.getText().toString(),lk7.getText().toString().length(),lk7,link7);
            setLinkToText(lk8.getText().toString(),lk8.getText().toString().length(),lk8,link8);
            setLinkToText(lk9.getText().toString(),lk9.getText().toString().length(),lk9,link9);

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
