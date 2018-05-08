package mhealth.c4c.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mhealth.c4c.CardViewActivity;
import mhealth.c4c.R;
import mhealth.c4c.faqrecyclerview.FaqMainActivityRecycler;
import mhealth.c4c.ucsfrecyclerview.UcsfMainActivityRecycler;

/**
 * Created by root on 11/22/17.
 */

public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context ctx, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=ctx;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.info_center_row, null);
        holder.tv=(TextView) rowView.findViewById(R.id.label);
        holder.img=(ImageView) rowView.findViewById(R.id.arrow);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                if(position==0){

                    Intent myint=new Intent(context, FaqMainActivityRecycler.class);
                    context.startActivity(myint);

                }
                else if(position==1){

                    Uri uri = Uri.parse("https://www.youtube.com/user/nascopkenya1");
//
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);


                }
                else if(position==2){

                    Uri uri = Uri.parse("http://www.nascop.or.ke"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);


                }

                else if(position==3){

//                    Uri uri = Uri.parse("http://www.nascop.or.ke"); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    context.startActivity(intent);

//                    Toast.makeText(context, "ucsf clicked", Toast.LENGTH_SHORT).show();

                    Intent myint=new Intent(context, UcsfMainActivityRecycler.class);
                    context.startActivity(myint);


                }
                else{
//                    Toast.makeText(context, "nothing clicked", Toast.LENGTH_SHORT).show();


                }
            }
        });
        return rowView;
    }

}
