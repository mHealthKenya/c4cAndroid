package mhealth.c4c.Tables;

import com.orm.SugarRecord;

/**
 * Created by root on 11/6/17.
 */

public class Messages extends SugarRecord {

    public String mAddress;
    public String mBody;
    public String mTimeStamp;
    public String read;
    public String dateRead;

    public Messages(String mAddress, String mBody, String mTimeStamp, String read, String dateRead) {
        this.mAddress = mAddress;
        this.mBody = mBody;
        this.mTimeStamp = mTimeStamp;
        this.read = read;
        this.dateRead = dateRead;
    }

    public Messages() {
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBody() {
        return mBody;
    }

    public void setmBody(String mBody) {
        this.mBody = mBody;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDateRead() {
        return dateRead;
    }

    public void setDateRead(String dateRead) {
        this.dateRead = dateRead;
    }
}
