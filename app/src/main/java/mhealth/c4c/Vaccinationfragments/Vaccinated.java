package mhealth.c4c.Vaccinationfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mhealth.c4c.R;

/**
 * Created by root on 2/22/18.
 */

public class Vaccinated extends Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.vaccinated_fragment,container,false);
        return v;
    }
}
