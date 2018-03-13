package mhealth.c4c.checkupstatustable;

import com.orm.SugarRecord;

/**
 * Created by root on 3/13/18.
 */

public class Status extends SugarRecord {

    public String name;
    public String category;

    public Status() {
    }

    public Status(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
