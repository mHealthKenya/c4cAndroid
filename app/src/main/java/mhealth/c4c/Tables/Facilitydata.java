package mhealth.c4c.Tables;

import com.orm.SugarRecord;

public class Facilitydata extends SugarRecord {

    String mflcode,facilityname,countyid,subcountyid,countyname,subcountyname;

    public Facilitydata() {
    }

    public Facilitydata(String mflcode, String facilityname, String countyid, String subcountyid, String countyname, String subcountyname) {
        this.mflcode = mflcode;
        this.facilityname = facilityname;
        this.countyid = countyid;
        this.subcountyid = subcountyid;
        this.countyname = countyname;
        this.subcountyname = subcountyname;
    }

    public String getMflcode() {
        return mflcode;
    }

    public void setMflcode(String mflcode) {
        this.mflcode = mflcode;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getCountyid() {
        return countyid;
    }

    public void setCountyid(String countyid) {
        this.countyid = countyid;
    }

    public String getSubcountyid() {
        return subcountyid;
    }

    public void setSubcountyid(String subcountyid) {
        this.subcountyid = subcountyid;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public String getSubcountyname() {
        return subcountyname;
    }

    public void setSubcountyname(String subcountyname) {
        this.subcountyname = subcountyname;
    }
}
