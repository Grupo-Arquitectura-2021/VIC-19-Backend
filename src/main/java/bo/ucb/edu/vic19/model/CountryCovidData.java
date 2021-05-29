package bo.ucb.edu.vic19.model;

public class CountryCovidData {
    private Integer idCountryCovidData;
    private Integer idCountry;
    private Integer idCovidData;
    private Integer status;
    private Transaction transaction;

    public CountryCovidData() {
    }

    public CountryCovidData(Integer idCountryCovidData, Integer idCountry, Integer idCovidData, Integer status, Transaction transaction) {
        this.idCountryCovidData = idCountryCovidData;
        this.idCountry = idCountry;
        this.idCovidData = idCovidData;
        this.status = status;
        this.transaction = transaction;
    }

    public Integer getIdCountryCovidData() {
        return idCountryCovidData;
    }

    public void setIdCountryCovidData(Integer idCountryCovidData) {
        this.idCountryCovidData = idCountryCovidData;
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public Integer getIdCovidData() {
        return idCovidData;
    }

    public void setIdCovidData(Integer idCovidData) {
        this.idCovidData = idCovidData;
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
        return "CityCovidData{" +
                "idCityCovidData=" + idCountryCovidData +
                ", idCity=" + idCountry +
                ", idCovidData=" + idCovidData +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
