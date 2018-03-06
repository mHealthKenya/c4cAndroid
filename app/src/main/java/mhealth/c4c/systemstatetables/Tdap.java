package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/6/18.
 */

public class Tdap extends SugarRecord {

    public String immunisedid;
    public String immunisedvalue;
    public String dosedate;

    public Tdap() {
    }

    public Tdap(String immunisedid, String immunisedvalue, String dosedate) {
        this.immunisedid = immunisedid;
        this.immunisedvalue = immunisedvalue;
        this.dosedate = dosedate;
    }

    public String getImmunisedid() {
        return immunisedid;
    }

    public void setImmunisedid(String immunisedid) {
        this.immunisedid = immunisedid;
    }

    public String getImmunisedvalue() {
        return immunisedvalue;
    }

    public void setImmunisedvalue(String immunisedvalue) {
        this.immunisedvalue = immunisedvalue;
    }

    public String getDosedate() {
        return dosedate;
    }

    public void setDosedate(String dosedate) {
        this.dosedate = dosedate;
    }
}
