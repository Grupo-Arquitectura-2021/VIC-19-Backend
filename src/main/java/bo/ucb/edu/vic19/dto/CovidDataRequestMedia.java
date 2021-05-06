package bo.ucb.edu.vic19.dto;

public class CovidDataRequestMedia {
    private String nameLocationCovid;
    private String dateLocationCovid;
    private float deathCases;
    private float confirmedCases;
    private float vaccinated;

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

    public float getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(float deathCases) {
        this.deathCases = deathCases;
    }

    public float getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(float confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public float getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(float vaccinated) {
        this.vaccinated = vaccinated;
    }

    public float getRecuperated() {
        return recuperated;
    }

    public void setRecuperated(float recuperated) {
        this.recuperated = recuperated;
    }

    private float recuperated;

    public CovidDataRequestMedia() {
    }

    @Override
    public String toString() {
        return "CovidDataRequestMedia{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", recuperated=" + recuperated +
                '}';
    }
}
