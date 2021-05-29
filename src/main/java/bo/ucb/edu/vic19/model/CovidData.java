package bo.ucb.edu.vic19.model;

import java.util.Date;

public class CovidData {
    private Integer idCovidData;
    private Integer idPageUrl;
    private Integer deathCases;
    private Integer confirmedCases;
    private Integer vaccinated;
    private Integer cumulativeCases;
    private Integer recuperated;
    private Date date;
    private Integer status;
    private Transaction transaction;

    public CovidData(){
        transaction = new Transaction();
    }

    public CovidData(Integer idCovidData, Integer idPageUrl, Integer deathCases, Integer confirmedCases, Integer vaccinated, Integer cumulativeCases, Integer recuperated, Date date, Integer status, Transaction transaction) {
        this.idCovidData = idCovidData;
        this.idPageUrl = idPageUrl;
        this.deathCases = deathCases;
        this.confirmedCases = confirmedCases;
        this.vaccinated = vaccinated;
        this.cumulativeCases = cumulativeCases;
        this.recuperated = recuperated;
        this.date = date;
        this.status = status;
        this.transaction = transaction;
    }

    public Integer getIdCovidData() {
        return idCovidData;
    }

    public void setIdCovidData(Integer idCovidData) {
        this.idCovidData = idCovidData;
    }

    public Integer getIdPageUrl() {
        return idPageUrl;
    }

    public void setIdPageUrl(Integer idPageUrl) {
        this.idPageUrl = idPageUrl;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "CovidData{" +
                "idCovidData=" + idCovidData +
                ", idPageUrl=" + idPageUrl +
                ", deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", cumulativeCases=" + cumulativeCases +
                ", recuperated=" + recuperated +
                ", date=" + date +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}