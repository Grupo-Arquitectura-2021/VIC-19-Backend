package bo.ucb.edu.ingsoft.dto;

import java.util.Date;

public class DatosCasos {
    private String country;
    private Date dateCas;
    private Integer cases;
    private Integer deaths;
    private Integer recovered;

    public DatosCasos(String country, Date dateCas, Integer cases, Integer deaths, Integer recovered) {
        this.country = country;
        this.dateCas = dateCas;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public DatosCasos() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateCas() {
        return dateCas;
    }

    public void setDateCas(Date dateCas) {
        this.dateCas = dateCas;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    @Override
    public String toString() {
        return "DatosCasos{" +
                "country='" + country + '\'' +
                ", dateCas=" + dateCas +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                '}';
    }
}
