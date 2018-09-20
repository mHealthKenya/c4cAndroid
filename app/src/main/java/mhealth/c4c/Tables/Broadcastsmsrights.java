package mhealth.c4c.Tables;

import com.orm.SugarRecord;

public class Broadcastsmsrights extends SugarRecord {

    public String canbroadcast;

    public Broadcastsmsrights() {
    }

    public Broadcastsmsrights(String canbroadcast) {
        this.canbroadcast = canbroadcast;
    }

    public String getCanbroadcast() {
        return canbroadcast;
    }

    public void setCanbroadcast(String canbroadcast) {
        this.canbroadcast = canbroadcast;
    }
}
