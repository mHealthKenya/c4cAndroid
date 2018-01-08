package mhealth.c4c.Tables;

import com.orm.SugarRecord;

/**
 * Created by root on 12/3/17.
 */

public class kmpdu extends SugarRecord {

    public String kmpduchecked;

    public kmpdu(String kmpduchecked) {
        this.kmpduchecked = kmpduchecked;
    }

    public kmpdu() {
    }

    public String getKmpduchecked() {
        return kmpduchecked;
    }

    public void setKmpduchecked(String kmpduchecked) {
        this.kmpduchecked = kmpduchecked;
    }
}
