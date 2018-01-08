package mhealth.c4c.Tables;

/**
 * Created by KENWEEZY on 2016-10-31.
 */

import com.orm.SugarRecord;


public class UserTable extends SugarRecord {
    private String username;
    private String password;

    public UserTable(){


    }

    public UserTable(String uname,String pass){

        this.username=uname;
        this.password=pass;
    }

    public String getUsername(){

        return username;
    }

    public String getPassword(){

        return password;
    }

}

