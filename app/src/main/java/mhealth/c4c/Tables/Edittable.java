package mhealth.c4c.Tables;

import com.orm.SugarRecord;

public class Edittable extends SugarRecord {

    String mfl,phone,idnumber;

    public Edittable() {
    }

    public Edittable(String mfl, String phone, String idnumber) {
        this.mfl = mfl;
        this.phone = phone;
        this.idnumber = idnumber;
    }

    public String getMfl() {
        return mfl;
    }

    public void setMfl(String mfl) {
        this.mfl = mfl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
