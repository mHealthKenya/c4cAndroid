package mhealth.c4c;

import com.orm.SugarRecord;

/**
 * Created by ADMIN on 5/16/2017.
 */

public class Registrationdatatable extends SugarRecord {
    String name,lname,idnumber,age,mflcode,myhepa,username,password,gender,cadre,securityqn,securityans;

    public Registrationdatatable(){

    }
    public Registrationdatatable(String nm, String lname, String gender, String cadre, String idnum, String age, String code, String hepa, String uname, String pass, String securityQn, String securityAns){

        this.name=nm;
        this.lname=lname;
        this.idnumber=idnum;
        this.age=age;
        this.mflcode=code;
        this.username=uname;
        this.myhepa=hepa;
        this.password=pass;
        this.gender=gender;
        this.cadre=cadre;
        this.securityqn=securityQn;
        this.securityans=securityAns;
    }

    public String getSecurityqn() {
        return securityqn;
    }

    public void setSecurityqn(String securityqn) {
        this.securityqn = securityqn;
    }

    public String getSecurityans() {
        return securityans;
    }

    public void setSecurityans(String securityans) {
        this.securityans = securityans;
    }

    public String getAge() {
        return age;
    }

    public String getLname() {
        return lname;
    }

    public String getCadre() {
        return cadre;
    }

    public String getGender() {
        return gender;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getMflcode() {
        return mflcode;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public void setMflcode(String mflcode) {
        this.mflcode = mflcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMyhepa() {
        return myhepa;
    }

    public void setMyhepa(String myhepa) {
        this.myhepa = myhepa;
    }
}
