package bo.ucb.edu.vic19.dto;

import java.util.Date;

public class CovidDataRequest {
    private Integer idCity;
    private String nameCity;
    private Date dateCity;
    private Integer deathCases;
    private Integer confirmedCases;
    private Integer vaccinated;
    private Integer cumulativeCases;
    private Integer recuperated;

    public CovidDataRequest() {
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public Date getDateCity() {
        return dateCity;
    }

    public void setDateCity(Date dateCity) {
        this.dateCity = dateCity;
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

    public Integer getCumulativeCases() {
        return cumulativeCases;
    }

    public void setCumulativeCases(Integer cumulativeCases) {
        this.cumulativeCases = cumulativeCases;
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
                "idCity=" + idCity +
                ", nameCity='" + nameCity + '\'' +
                ", dateCity=" + dateCity +
                ", deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", cumulativeCases=" + cumulativeCases +
                ", recuperated=" + recuperated +
                '}';
    }
}
