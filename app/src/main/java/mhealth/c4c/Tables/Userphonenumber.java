package mhealth.c4c.Tables;

import com.orm.SugarRecord;

public class Userphonenumber extends SugarRecord {

    String phone;

    public Userphonenumber() {
    }

    public Userphonenumber(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
