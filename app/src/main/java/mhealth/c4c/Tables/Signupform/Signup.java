package mhealth.c4c.Tables.Signupform;

import com.orm.SugarRecord;

public class Signup extends SugarRecord {

    String phone,fname,lname,uname,password,secqn,answer;

    public Signup() {
    }

    public Signup(String phone, String fname, String lname, String uname, String password, String secqn, String answer) {
        this.phone = phone;
        this.fname = fname;
        this.lname = lname;
        this.uname = uname;
        this.password = password;
        this.secqn = secqn;
        this.answer = answer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecqn() {
        return secqn;
    }

    public void setSecqn(String secqn) {
        this.secqn = secqn;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
