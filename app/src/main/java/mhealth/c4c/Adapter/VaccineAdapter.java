package mhealth.c4c.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mhealth.c4c.R;
import mhealth.c4c.models.VaccineModel;

/**
 * Created by root on 3/13/18.
 */


public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.MyviewHolder> {

    List<VaccineModel> mylist;
    Context ctx;

    public VaccineAdapter(Context ctx, List<VaccineModel> mylist) {
        this.mylist = mylist;
        this.ctx = ctx;
    }

    @Override
    public VaccineAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vaccine_status_row, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        try{

            VaccineModel itm = mylist.get(position);

            holder.vname.setText(itm.getVaccinename());
            holder.vstatus.setText(itm.getVaccinestatus());




        }
        catch(Exception e){


        }



    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        public TextView vname, vstatus;

        public CardView vcd;


        public MyviewHolder(View itemView) {
            super(itemView);

            vname =(TextView) itemView.findViewById(R.id.vaccine_name);
            vstatus =(TextView) itemView.findViewById(R.id.vaccine_status);

            vcd =(CardView) itemView.findViewById(R.id.vaccine_card_view);
        }
    }
}


