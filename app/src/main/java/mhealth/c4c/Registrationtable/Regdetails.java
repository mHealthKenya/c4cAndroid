package mhealth.c4c.Registrationtable;

import com.orm.SugarRecord;

/**
 * Created by root on 2/22/18.
 */

public class Regdetails extends SugarRecord {

    public String doctorUnionNumber;
    public String specialisation;
    public String gender;
    public String cadre;
    public String idnumber;
    public String dob;
    public String mfl;
    public String hpbvaccination;
    public String hpbdose1;
    public String hpbdose2;
    public String username;
    public String password;
    public String securityqn;
    public String answer;

    public Regdetails() {
    }

    public Regdetails(String doctorUnionNumber, String specialisation, String gender, String cadre, String idnumber, String dob, String mfl, String hpbvaccination, String hpbdose1, String hpbdose2, String username, String password, String securityqn, String answer) {
        this.doctorUnionNumber = doctorUnionNumber;
        this.specialisation = specialisation;
        this.gender = gender;
        this.cadre = cadre;
        this.idnumber = idnumber;
        this.dob = dob;
        this.mfl = mfl;
        this.hpbvaccination = hpbvaccination;
        this.hpbdose1 = hpbdose1;
        this.hpbdose2 = hpbdose2;
        this.username = username;
        this.password = password;
        this.securityqn = securityqn;
        this.answer = answer;
    }

    public String getDoctorUnionNumber() {
        return doctorUnionNumber;
    }

    public void setDoctorUnionNumber(String doctorUnionNumber) {
        this.doctorUnionNumber = doctorUnionNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCadre() {
        return cadre;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMfl() {
        return mfl;
    }

    public void setMfl(String mfl) {
        this.mfl = mfl;
    }

    public String getHpbvaccination() {
        return hpbvaccination;
    }

    public void setHpbvaccination(String hpbvaccination) {
        this.hpbvaccination = hpbvaccination;
    }

    public String getHpbdose1() {
        return hpbdose1;
    }

    public void setHpbdose1(String hpbdose1) {
        this.hpbdose1 = hpbdose1;
    }

    public String getHpbdose2() {
        return hpbdose2;
    }

    public void setHpbdose2(String hpbdose2) {
        this.hpbdose2 = hpbdose2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityqn() {
        return securityqn;
    }

    public void setSecurityqn(String securityqn) {
        this.securityqn = securityqn;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
