package bo.ucb.edu.vic19.dto;

public class CovidDataRequestConfidenceInterval {
    private String nameLocationCovid;
    private String dateLocationCovid;
    private float deathCasesUpperLimit;
    private float deathCasesLowerLimit;
    private float confirmedCasesUpperLimit;
    private float confirmedCasesLowerLimit;
    private float recuperatedUpperLimit;
    private float recuperatedLowerLimit;
    private float vaccinatedUpperLimit;
    private float vaccinatedLowerLimit;
    private float percentage;


    public String getNameLocationCovid() {
        return nameLocationCovid;
    }

    public void setNameLocationCovid(String nameLocationCovid) {
        this.nameLocationCovid = nameLocationCovid;
    }

    public String getDateLocationCovid() {
        return dateLocationCovid;
    }

    public void setDateLocationCovid(String dateLocationCovid) {
        this.dateLocationCovid = dateLocationCovid;
    }

    public float getDeathCasesUpperLimit() {
        return deathCasesUpperLimit;
    }

    public void setDeathCasesUpperLimit(float deathCasesUpperLimit) {
        this.deathCasesUpperLimit = deathCasesUpperLimit;
    }

    public float getDeathCasesLowerLimit() {
        return deathCasesLowerLimit;
    }

    public void setDeathCasesLowerLimit(float deathCasesLowerLimit) {
        this.deathCasesLowerLimit = deathCasesLowerLimit;
    }

    public float getConfirmedCasesUpperLimit() {
        return confirmedCasesUpperLimit;
    }

    public void setConfirmedCasesUpperLimit(float confirmedCasesUpperLimit) {
        this.confirmedCasesUpperLimit = confirmedCasesUpperLimit;
    }

    public float getConfirmedCasesLowerLimit() {
        return confirmedCasesLowerLimit;
    }

    public void setConfirmedCasesLowerLimit(float confirmedCasesLowerLimit) {
        this.confirmedCasesLowerLimit = confirmedCasesLowerLimit;
    }

    public float getRecuperatedUpperLimit() {
        return recuperatedUpperLimit;
    }

    public void setRecuperatedUpperLimit(float recuperatedUpperLimit) {
        this.recuperatedUpperLimit = recuperatedUpperLimit;
    }

    public float getRecuperatedLowerLimit() {
        return recuperatedLowerLimit;
    }

    public void setRecuperatedLowerLimit(float recuperatedLowerLimit) {
        this.recuperatedLowerLimit = recuperatedLowerLimit;
    }

    public float getVaccinatedUpperLimit() {
        return vaccinatedUpperLimit;
    }

    public void setVaccinatedUpperLimit(float vaccinatedUpperLimit) {
        this.vaccinatedUpperLimit = vaccinatedUpperLimit;
    }

    public float getVaccinatedLowerLimit() {
        return vaccinatedLowerLimit;
    }

    public void setVaccinatedLowerLimit(float vaccinatedLowerLimit) {
        this.vaccinatedLowerLimit = vaccinatedLowerLimit;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "CovidDataRequestConfidenceInterval{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", deathCasesUpperLimit=" + deathCasesUpperLimit +
                ", deathCasesLowerLimit=" + deathCasesLowerLimit +
                ", confirmedCasesUpperLimit=" + confirmedCasesUpperLimit +
                ", confirmedCasesLowerLimit=" + confirmedCasesLowerLimit +
                ", recuperatedUpperLimit=" + recuperatedUpperLimit +
                ", recuperatedLowerLimit=" + recuperatedLowerLimit +
                ", vaccinatedUpperLimit=" + vaccinatedUpperLimit +
                ", vaccinatedLowerLimit=" + vaccinatedLowerLimit +
                ", percentage=" + percentage +
                '}';
    }
}
