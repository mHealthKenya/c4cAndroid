package mhealth.c4c.models;

/**
 * Created by root on 3/13/18.
 */

public class VaccineModel {

    public String vaccinename;
    public String vaccinestatus;

    public VaccineModel() {
    }

    public VaccineModel(String vaccinename, String vaccinestatus) {
        this.vaccinename = vaccinename;
        this.vaccinestatus = vaccinestatus;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
    }

    public String getVaccinestatus() {
        return vaccinestatus;
    }

    public void setVaccinestatus(String vaccinestatus) {
        this.vaccinestatus = vaccinestatus;
    }
}
