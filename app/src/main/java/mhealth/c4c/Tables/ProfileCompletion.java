package mhealth.c4c.Tables;

import com.orm.SugarRecord;

/**
 * Created by root on 1/24/18.
 */

public class ProfileCompletion extends SugarRecord {

    public String myvalue;

    public ProfileCompletion(String myvalue) {
        this.myvalue = myvalue;
    }

    public ProfileCompletion() {
    }

    public String getMyvalue() {
        return myvalue;
    }

    public void setMyvalue(String myvalue) {
        this.myvalue = myvalue;
    }
}
