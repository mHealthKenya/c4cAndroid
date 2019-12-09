package mhealth.c4c.Adapter;

/**
 * Created by root on 11/6/17.
 */


import android.content.Context;
import android.graphics.Color;

import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mhealth.c4c.Mydata;
import mhealth.c4c.R;

public class MessagesAdapter extends BaseAdapter implements Filterable{

    private Context mycont;
    private List<Mydata> mylist;
    CustomFilter filter;
    private List<Mydata> filterList;
    boolean chkd;


    public MessagesAdapter(Context cont,List<Mydata> mlist){

        this.mycont=cont;
        this.mylist=mlist;
        this.filterList=mlist;


    }
    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mycont, R.layout.listview_row, null);

        try {
            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            TextView tvtitle = (TextView) v.findViewById(R.id.mstitle);
            TextView tvmycount = (TextView) v.findViewById(R.id.msvcount);
            TextView tvbdy = (TextView) v.findViewById(R.id.msgbdy);
            TextView tvdate = (TextView) v.findViewById(R.id.msgdte);
            ImageView imgv = (ImageView) v.findViewById(R.id.imgv);
            ImageView imgchk = (ImageView) v.findViewById(R.id.iv_check_box);
            CardView cview=(CardView) v.findViewById(R.id.ly_root);

            String mybdyString=mylist.get(position).getMsgbody();

//            String[] splitBdy=mybdyString.split("Result",5);
            String subBodyString="";

            subBodyString=mybdyString.substring(0,20);



            tvtitle.setText(mylist.get(position).getRead());
//            tvbdy.setText(mylist.get(position).getMsgbody());
            tvbdy.setText(subBodyString+" .........");
            String mydate = mylist.get(position).getDate();

            System.out.println("the date is " + mydate);
            String[] splitDate = mydate.split("/");
            String splitedDate = splitDate[1];
            String mysplitday = splitDate[0];
            String myNewDate = "";
            switch (splitedDate) {

                case "01":
                    myNewDate = months[0];
                    break;
                case "02":
                    myNewDate = months[1];
                    break;
                case "03":
                    myNewDate = months[2];
                    break;
                case "04":
                    myNewDate = months[3];
                    break;
                case "05":
                    myNewDate = months[4];
                    break;
                case "06":
                    myNewDate = months[5];
                    break;
                case "07":
                    myNewDate = months[6];
                    break;
                case "08":
                    myNewDate = months[7];
                    break;
                case "09":
                    myNewDate = months[8];
                    break;
                case "10":
                    myNewDate = months[9];
                    break;
                case "11":
                    myNewDate = months[10];
                    break;
                case "12":
                    myNewDate = months[11];
                    break;
                default:


            }

            tvdate.setText(mysplitday + " " + myNewDate);

            v.setTag(mylist);


            if (tvtitle.getText().toString().contentEquals("read")) {

                tvbdy.setTextColor(Color.parseColor("#696969"));
//            tvbdy.setText(Html.fromHtml("<h1><b>"+mylist.get(position).getMsgbody()+"</b></h1>"));
                imgv.setVisibility(View.GONE);

            }


//            Mydata model = mylist.get(position);
//
//
//            if (model.isSelected()){
//
//                imgchk.setBackgroundResource(R.drawable.checked);
//            }
//
//
//            else{
//
//                imgchk.setBackgroundResource(R.drawable.check);
//
//            }


        }
        catch(Exception e){


        }

        return v;

    }

    @Override
    public Filter getFilter() {

        if(filter==null){

            filter=new CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<Mydata> filters=new ArrayList<Mydata>();

                for(int i=0;i<filterList.size();i++){

                    if(filterList.get(i).getMsgbody().toUpperCase().contains(constraint)){

//                        chkd=filterList.get(i).isSelected();

                        Mydata md=new Mydata(filterList.get(i).getMsgbody(),filterList.get(i).getDate(),filterList.get(i).getRead());
                        filters.add(md);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }

            else{

                results.count=filterList.size();
                results.values=filterList;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mylist= (List<Mydata>) results.values;
            notifyDataSetChanged();

        }
    }
}
