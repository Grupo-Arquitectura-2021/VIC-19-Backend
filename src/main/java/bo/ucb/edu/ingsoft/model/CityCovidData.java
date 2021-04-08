package bo.ucb.edu.ingsoft.model;

public class CityCovidData {
    private Integer idCityCovidData;
    private Integer idCity;
    private Integer idCovidData;
    private Integer status;
    private Transaction transaction;

    public Integer getIdCityCovidData() {
        return idCityCovidData;
    }

    public void setIdCityCovidData(Integer idCityCovidData) {
        this.idCityCovidData = idCityCovidData;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
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
                "idCityCovidData=" + idCityCovidData +
                ", idCity=" + idCity +
                ", idCovidData=" + idCovidData +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
