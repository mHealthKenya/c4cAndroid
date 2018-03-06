package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/6/18.
 */

public class Meningoco extends SugarRecord {

    public String immunisedid;
    public String immunisedvalue;
    public String firstdosedate;
    public String seconddosedate;

    public Meningoco() {
    }

    public Meningoco(String immunisedid, String immunisedvalue, String firstdosedate, String seconddosedate) {
        this.immunisedid = immunisedid;
        this.immunisedvalue = immunisedvalue;
        this.firstdosedate = firstdosedate;
        this.seconddosedate = seconddosedate;
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

    public String getFirstdosedate() {
        return firstdosedate;
    }

    public void setFirstdosedate(String firstdosedate) {
        this.firstdosedate = firstdosedate;
    }

    public String getSeconddosedate() {
        return seconddosedate;
    }

    public void setSeconddosedate(String seconddosedate) {
        this.seconddosedate = seconddosedate;
    }
}
