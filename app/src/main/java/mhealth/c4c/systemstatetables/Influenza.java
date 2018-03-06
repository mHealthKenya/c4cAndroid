package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/5/18.
 */

public class Influenza extends SugarRecord {

    public String gender;
    public String pregnantid;
    public String pregnantvalue;
    public String influenzavaccineid;
    public String influenzavaccinevalue;
    public String dosedate;

    public Influenza() {
    }

    public Influenza(String gender, String pregnantid, String pregnantvalue, String influenzavaccineid, String influenzavaccinevalue, String dosedate) {
        this.gender = gender;
        this.pregnantid = pregnantid;
        this.pregnantvalue = pregnantvalue;
        this.influenzavaccineid = influenzavaccineid;
        this.influenzavaccinevalue = influenzavaccinevalue;
        this.dosedate = dosedate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPregnantid() {
        return pregnantid;
    }

    public void setPregnantid(String pregnantid) {
        this.pregnantid = pregnantid;
    }

    public String getPregnantvalue() {
        return pregnantvalue;
    }

    public void setPregnantvalue(String pregnantvalue) {
        this.pregnantvalue = pregnantvalue;
    }

    public String getInfluenzavaccineid() {
        return influenzavaccineid;
    }

    public void setInfluenzavaccineid(String influenzavaccineid) {
        this.influenzavaccineid = influenzavaccineid;
    }

    public String getInfluenzavaccinevalue() {
        return influenzavaccinevalue;
    }

    public void setInfluenzavaccinevalue(String influenzavaccinevalue) {
        this.influenzavaccinevalue = influenzavaccinevalue;
    }

    public String getDosedate() {
        return dosedate;
    }

    public void setDosedate(String dosedate) {
        this.dosedate = dosedate;
    }
}
