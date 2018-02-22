package mhealth.c4c.Registrationtable;

import com.orm.SugarRecord;

/**
 * Created by root on 2/22/18.
 */

public class Regpartners extends SugarRecord {

    public String name;

    public Regpartners() {
    }

    public Regpartners(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
