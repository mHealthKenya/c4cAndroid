package mhealth.c4c.Tables;

import com.orm.SugarRecord;

public class Profiletable extends SugarRecord {

    public String mflcode;

    public Profiletable() {
    }

    public Profiletable(String mflcode) {
        this.mflcode = mflcode;
    }

    public String getMflcode() {
        return mflcode;
    }

    public void setMflcode(String mflcode) {
        this.mflcode = mflcode;
    }

}
