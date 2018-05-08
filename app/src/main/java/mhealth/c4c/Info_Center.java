package mhealth.c4c;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mhealth.c4c.Adapter.CustomAdapter;

public class Info_Center extends AppCompatActivity {
    private Context mContext;
    ListView listView;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_info_center);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        itemImage = (ImageView) findViewById(R.id.arrow);
        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[]{"FAQ", "ART GUIDELINES", "NASCOP WEBSITE","UCSF"};
        int [] prgmImages={R.drawable.arrow,R.drawable.arrow,R.drawable.arrow,R.drawable.arrow};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.info_center_row, R.id.label, values);
//        listView.setFocusable(false);

//        listView.setAdapter(adapter);

        listView.setAdapter(new CustomAdapter(this, values,prgmImages));

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}