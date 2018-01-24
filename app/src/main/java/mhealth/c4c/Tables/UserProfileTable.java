package mhealth.c4c.Tables;

import com.orm.SugarRecord;

/**
 * Created by root on 1/23/18.
 */

public class UserProfileTable extends SugarRecord {

    public String influenzapregnant,influenzadose,varicellahistory,varicellafirstdose,varicellaseconddose,tdapimmunised,tdapdose,measlesimmunised,measlesfirstdose,measlesseconddose,meningocodose;


    public UserProfileTable() {
    }

    public UserProfileTable(String influenzapregnant, String influenzadose, String varicellahistory, String varicellafirstdose, String varicellaseconddose, String tdapimmunised, String tdapdose, String measlesimmunised, String measlesfirstdose, String measlesseconddose, String meningocodose) {
        this.influenzapregnant = influenzapregnant;
        this.influenzadose = influenzadose;
        this.varicellahistory = varicellahistory;
        this.varicellafirstdose = varicellafirstdose;
        this.varicellaseconddose = varicellaseconddose;
        this.tdapimmunised = tdapimmunised;
        this.tdapdose = tdapdose;
        this.measlesimmunised = measlesimmunised;
        this.measlesfirstdose = measlesfirstdose;
        this.measlesseconddose = measlesseconddose;
        this.meningocodose = meningocodose;
    }

    public String getInfluenzapregnant() {
        return influenzapregnant;
    }

    public void setInfluenzapregnant(String influenzapregnant) {
        this.influenzapregnant = influenzapregnant;
    }

    public String getInfluenzadose() {
        return influenzadose;
    }

    public void setInfluenzadose(String influenzadose) {
        this.influenzadose = influenzadose;
    }

    public String getVaricellahistory() {
        return varicellahistory;
    }

    public void setVaricellahistory(String varicellahistory) {
        this.varicellahistory = varicellahistory;
    }

    public String getVaricellafirstdose() {
        return varicellafirstdose;
    }

    public void setVaricellafirstdose(String varicellafirstdose) {
        this.varicellafirstdose = varicellafirstdose;
    }

    public String getVaricellaseconddose() {
        return varicellaseconddose;
    }

    public void setVaricellaseconddose(String varicellaseconddose) {
        this.varicellaseconddose = varicellaseconddose;
    }

    public String getTdapimmunised() {
        return tdapimmunised;
    }

    public void setTdapimmunised(String tdapimmunised) {
        this.tdapimmunised = tdapimmunised;
    }

    public String getTdapdose() {
        return tdapdose;
    }

    public void setTdapdose(String tdapdose) {
        this.tdapdose = tdapdose;
    }

    public String getMeaslesimmunised() {
        return measlesimmunised;
    }

    public void setMeaslesimmunised(String measlesimmunised) {
        this.measlesimmunised = measlesimmunised;
    }

    public String getMeaslesfirstdose() {
        return measlesfirstdose;
    }

    public void setMeaslesfirstdose(String measlesfirstdose) {
        this.measlesfirstdose = measlesfirstdose;
    }

    public String getMeaslesseconddose() {
        return measlesseconddose;
    }

    public void setMeaslesseconddose(String measlesseconddose) {
        this.measlesseconddose = measlesseconddose;
    }

    public String getMeningocodose() {
        return meningocodose;
    }

    public void setMeningocodose(String meningocodose) {
        this.meningocodose = meningocodose;
    }
}
