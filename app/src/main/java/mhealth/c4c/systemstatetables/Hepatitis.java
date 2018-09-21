package mhealth.c4c.systemstatetables;

import com.orm.SugarRecord;

/**
 * Created by root on 3/6/18.
 */

public class Hepatitis extends SugarRecord {

    public String immunisediddose1;
    public String immunisedvaluedose1;
    public String immunisediddose2;
    public String immunisedvaluedose2;
    public String firstdosedate;
    public String seconddosedate;

    public Hepatitis() {

    }

    public Hepatitis(String immunisediddose1, String immunisedvaluedose1, String immunisediddose2, String immunisedvaluedose2, String firstdosedate, String seconddosedate) {
        this.immunisediddose1 = immunisediddose1;
        this.immunisedvaluedose1 = immunisedvaluedose1;
        this.immunisediddose2 = immunisediddose2;
        this.immunisedvaluedose2 = immunisedvaluedose2;
        this.firstdosedate = firstdosedate;
        this.seconddosedate = seconddosedate;
    }


    public String getImmunisediddose1() {
        return immunisediddose1;
    }

    public void setImmunisediddose1(String immunisediddose1) {
        this.immunisediddose1 = immunisediddose1;
    }

    public String getImmunisedvaluedose1() {
        return immunisedvaluedose1;
    }

    public void setImmunisedvaluedose1(String immunisedvaluedose1) {
        this.immunisedvaluedose1 = immunisedvaluedose1;
    }

    public String getImmunisediddose2() {
        return immunisediddose2;
    }

    public void setImmunisediddose2(String immunisediddose2) {
        this.immunisediddose2 = immunisediddose2;
    }

    public String getImmunisedvaluedose2() {
        return immunisedvaluedose2;
    }

    public void setImmunisedvaluedose2(String immunisedvaluedose2) {
        this.immunisedvaluedose2 = immunisedvaluedose2;
    }

    public String getFirstdosedate() {
        return firstdosedate;
    }

    public void setFirstdosedate(String firstdosedate) {
        this.firstdosedate = firstdosedate;
    }

    public String getSeconddosedate() {
        return seconddosedate;
    }

    public void setSeconddosedate(String seconddosedate) {
        this.seconddosedate = seconddosedate;
    }
}
