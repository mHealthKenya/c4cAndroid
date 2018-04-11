package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/6/18.
 */

public class Tdap extends SugarRecord {

    public String immunisedid;
    public String immunisedvalue;
    public String dosedate;
    public String immunisedboosterid;
    public String immunisedboostervalue;
    public String doseboosterdate;

    public Tdap() {
    }


    public Tdap(String immunisedid, String immunisedvalue, String dosedate, String immunisedboosterid, String immunisedboostervalue, String doseboosterdate) {
        this.immunisedid = immunisedid;
        this.immunisedvalue = immunisedvalue;
        this.dosedate = dosedate;
        this.immunisedboosterid = immunisedboosterid;
        this.immunisedboostervalue = immunisedboostervalue;
        this.doseboosterdate = doseboosterdate;
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

    public String getImmunisedboosterid() {
        return immunisedboosterid;
    }

    public void setImmunisedboosterid(String immunisedboosterid) {
        this.immunisedboosterid = immunisedboosterid;
    }

    public String getImmunisedboostervalue() {
        return immunisedboostervalue;
    }

    public void setImmunisedboostervalue(String immunisedboostervalue) {
        this.immunisedboostervalue = immunisedboostervalue;
    }

    public String getDoseboosterdate() {
        return doseboosterdate;
    }

    public void setDoseboosterdate(String doseboosterdate) {
        this.doseboosterdate = doseboosterdate;
    }
}
