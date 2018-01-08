package mhealth.c4c;

/**
 * Created by root on 11/6/17.
 */

import java.util.Comparator;

/**
 * Created by KENWEEZY on 2017-03-10.
 */

public class Mydata {

    private String msgbody;
    private String date;
    private String read;



    public Mydata(){

    }

    public Mydata(String mbdy,String date,String read){

        this.msgbody=mbdy;
        this.date=date;
        this.read=read;


    }


    public void setMsgbody(String msgbdy){

        this.msgbody=msgbdy;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setRead(String read){
        this.read=read;
    }

    public String getDate() {
        return date;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public String getRead() {
        return read;
    }


}
