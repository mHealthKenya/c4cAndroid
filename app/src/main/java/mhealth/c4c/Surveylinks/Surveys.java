package mhealth.c4c.Surveylinks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mhealth.c4c.R;

public class Surveys extends AppCompatActivity {

    TextView lk1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_link);
        setToolBar();
        initialise();
        setLinks();
    }

    private void initialise(){

        lk1=(TextView) findViewById(R.id.link1);

    }

    private void setLinks(){

        try{
            String link1="http://survey.mhealthkenya.org/survey";


            setLinkToText(lk1.getText().toString(),lk1.getText().toString().length(),lk1,link1);


        }
        catch(Exception e){

            Toast.makeText(this, "error setting links "+e, Toast.LENGTH_SHORT).show();

        }
    }


    private void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.resourcestoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("SURVEY LINK");
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
