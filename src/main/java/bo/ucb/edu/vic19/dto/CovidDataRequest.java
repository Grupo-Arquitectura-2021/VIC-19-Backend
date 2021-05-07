package bo.ucb.edu.vic19.dto;

import java.util.Date;

public class CovidDataRequest {
    private String nameLocationCovid;
    private String dateLocationCovid;
    private Integer deathCases;
    private Integer confirmedCases;
    private Integer vaccinated;
    private Integer recuperated;
    private Integer cumulativeCases;

    public Integer getCumulativeCases() {
        return cumulativeCases;
    }

    public void setCumulativeCases(Integer cumulativeCases) {
        this.cumulativeCases = cumulativeCases;
    }

    public CovidDataRequest() {
    }

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

    public Integer getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(Integer deathCases) {
        this.deathCases = deathCases;
    }

    public Integer getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(Integer confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public Integer getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Integer vaccinated) {
        this.vaccinated = vaccinated;
    }

    public Integer getRecuperated() {
        return recuperated;
    }

    public void setRecuperated(Integer recuperated) {
        this.recuperated = recuperated;
    }

    @Override
    public String toString() {
        return "CovidDataRequest{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", recuperated=" + recuperated +
                ", cumulativeCases=" + cumulativeCases +
                '}';
    }
}
