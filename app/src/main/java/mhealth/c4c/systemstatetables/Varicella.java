package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/6/18.
 */

public class Varicella extends SugarRecord {
    public String vaccineid;
    public String vaccinevalue;
    public String historyid;
    public String historyvalue;
    public String firstdosedate;
    public String seconddosedate;


    public Varicella() {
    }

    public Varicella(String vaccineid, String vaccinevalue, String historyid, String historyvalue, String firstdosedate, String seconddosedate) {
        this.vaccineid = vaccineid;
        this.vaccinevalue = vaccinevalue;
        this.historyid = historyid;
        this.historyvalue = historyvalue;
        this.firstdosedate = firstdosedate;
        this.seconddosedate = seconddosedate;
    }

    public String getVaccineid() {
        return vaccineid;
    }

    public void setVaccineid(String vaccineid) {
        this.vaccineid = vaccineid;
    }

    public String getVaccinevalue() {
        return vaccinevalue;
    }

    public void setVaccinevalue(String vaccinevalue) {
        this.vaccinevalue = vaccinevalue;
    }

    public String getHistoryid() {
        return historyid;
    }

    public void setHistoryid(String historyid) {
        this.historyid = historyid;
    }

    public String getHistoryvalue() {
        return historyvalue;
    }

    public void setHistoryvalue(String historyvalue) {
        this.historyvalue = historyvalue;
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
