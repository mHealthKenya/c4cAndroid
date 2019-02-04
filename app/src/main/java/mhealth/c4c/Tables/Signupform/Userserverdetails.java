package mhealth.c4c.Tables.Signupform;

import com.orm.SugarRecord;

public class Userserverdetails extends SugarRecord {

    String nationalid;
    String cadre;
    String partner;
    String gender;
    String dob;
    String mfl;

    public Userserverdetails() {
    }

    public Userserverdetails(String nationalid, String cadre, String partner, String gender, String dob, String mfl) {
        this.nationalid = nationalid;
        this.cadre = cadre;
        this.partner = partner;
        this.gender = gender;
        this.dob = dob;
        this.mfl = mfl;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    public String getCadre() {
        return cadre;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMfl() {
        return mfl;
    }

    public void setMfl(String mfl) {
        this.mfl = mfl;
    }
}
