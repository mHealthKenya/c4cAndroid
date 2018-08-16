package mhealth.c4c.GetRemoteData;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mhealth.c4c.ProgressOld;
import mhealth.c4c.Tables.Facilitydata;
import mhealth.c4c.config.Config;
import mhealth.c4c.progress.Progress;

import static com.android.volley.Request.Method.POST;

public class GetRemoteData {

    Context ctx;
    Progress pr;

    private JSONArray id_result;

    public GetRemoteData(Context ctx) {
        this.ctx = ctx;
        pr=new Progress(ctx);


    }


    public void getFacilityData(){
//        Toast.makeText(ctx, "i am called", Toast.LENGTH_SHORT).show();
        pr.showProgress("loading..");


        try{



            StringRequest stringRequest = new StringRequest(POST, Config.GETFACILITY_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pr.dissmissProgress();



                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMyFacilityData(id_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                           pr.dissmissProgress();


                            Toast.makeText(ctx, "error occured "+error, Toast.LENGTH_SHORT).show();
                            System.out.println("*******error*** "+error);
                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting remote data "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyFacilityData(JSONArray j){
        System.out.println("******db data****************");
        Facilitydata.deleteAll(Facilitydata.class);

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String facility_name=json.getString(Config.KEY_FACILITY_NAME);
                String mfl_code=json.getString(Config.KEY_CODE);
                String county_id=json.getString(Config.KEY_COUNTY_ID);
                String subcounty_id=json.getString(Config.KEY_SUBCOUNTY_ID);
                String county_name=json.getString(Config.KEY_COUNTY_NAME);
                String subcounty_name=json.getString(Config.KEY_SUBCOUNTY_NAME);

                Facilitydata fd=new Facilitydata();
                fd.setCountyid(county_id);
                fd.setCountyname(county_name);
                fd.setFacilityname(facility_name);
                fd.setMflcode(mfl_code);
                fd.setSubcountyid(subcounty_id);
                fd.setSubcountyname(subcounty_name);
                fd.save();





            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Internet connection required", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
