package mhealth.c4c.Vaccinationfragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mhealth.c4c.Adapter.VaccineAdapter;
import mhealth.c4c.R;
import mhealth.c4c.checkupstatustable.Status;
import mhealth.c4c.models.VaccineModel;
import mhealth.c4c.recyclerTouchListener.RecyclerTouchListener;

/**
 * Created by root on 2/22/18.
 */

public class NotVaccinated extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private VaccineAdapter adapter;
    private List<VaccineModel> itemsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.not_vaccinated_fragment,container,false);
        initialise();
        populateList();
        setRecyclerClickListener();
        return v;
    }

    public void initialise(){

        try{

            recyclerView = (RecyclerView) v.findViewById(R.id.notvaccinated_recycler_view);

            itemsList = new ArrayList<>();
            adapter = new VaccineAdapter(getActivity(), itemsList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        catch(Exception e){


        }
    }

    public void populateList(){

        try{

            List<Status> myl=Status.findWithQuery(Status.class,"select * from Status where category=?","not vaccinated");
            if(myl.size()>0){

                String vname="";
                String vstatus="";

                for(int x=0;x<myl.size();x++){

                    vname=myl.get(x).getName();
                    vstatus=myl.get(x).getCategory();

                    VaccineModel vm=new VaccineModel(vname,vstatus);
                    itemsList.add(vm);
                }

            }
            else{


            }

            adapter.notifyDataSetChanged();
        }
        catch(Exception e){


        }
    }



    public void setRecyclerClickListener(){

        try{




            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    VaccineModel dev=itemsList.get(position);


//                    Toast.makeText(getActivity(), "clicked on "+dev.getVaccinename(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onLongClick(View view, int position) {

                    VaccineModel dev=itemsList.get(position);

//                    Toast.makeText(getActivity(), "long clicked on "+dev.getVaccinename(), Toast.LENGTH_SHORT).show();


                }
            }));



        }
        catch(Exception e){



        }
    }





    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
