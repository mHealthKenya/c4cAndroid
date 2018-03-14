package mhealth.c4c.checkupstatustable;

import com.orm.SugarRecord;

/**
 * Created by root on 3/14/18.
 */

public class checkupcalendar extends SugarRecord {

    public String generaldate;
    public String physicaldate;

    public checkupcalendar() {
    }

    public checkupcalendar(String generaldate, String physicaldate) {
        this.generaldate = generaldate;
        this.physicaldate = physicaldate;
    }

    public String getGeneraldate() {
        return generaldate;
    }

    public void setGeneraldate(String generaldate) {
        this.generaldate = generaldate;
    }

    public String getPhysicaldate() {
        return physicaldate;
    }

    public void setPhysicaldate(String physicaldate) {
        this.physicaldate = physicaldate;
    }
}
