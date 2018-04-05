package mhealth.c4c.Tables;

import com.orm.SugarRecord;

/**
 * Created by root on 4/5/18.
 */

public class Partners extends SugarRecord {

    public String partnername;

    public Partners() {
    }

    public Partners(String partnername) {
        this.partnername = partnername;
    }

    public String getPartnername() {
        return partnername;
    }

    public void setPartnername(String partnername) {
        this.partnername = partnername;
    }
}
