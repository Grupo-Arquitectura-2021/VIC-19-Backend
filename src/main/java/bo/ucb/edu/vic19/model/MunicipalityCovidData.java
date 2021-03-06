package bo.ucb.edu.vic19.model;

public class MunicipalityCovidData {
    private Integer idMunicipalityCovidData;
    private Integer idMunicipality;
    private Integer idCovidData;
    private Integer status;
    private Transaction transaction;

    public MunicipalityCovidData(){
        transaction = new Transaction();
    }

    public MunicipalityCovidData(Integer idMunicipalityCovidData, Integer idMunicipality, Integer idCovidData, Integer status, Transaction transaction) {
        this.idMunicipalityCovidData = idMunicipalityCovidData;
        this.idMunicipality = idMunicipality;
        this.idCovidData = idCovidData;
        this.status = status;
        this.transaction = transaction;
    }

    public Integer getIdMunicipalityCovidData() {
        return idMunicipalityCovidData;
    }

    public void setIdMunicipalityCovidData(Integer idMunicipalityCovidData) {
        this.idMunicipalityCovidData = idMunicipalityCovidData;
    }

    public Integer getIdMunicipality() {
        return idMunicipality;
    }

    public void setIdMunicipality(Integer idMunicipality) {
        this.idMunicipality = idMunicipality;
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
        return "MunicipalityCovidData{" +
                "idMunicipalityCovidData=" + idMunicipalityCovidData +
                ", idMunicipality=" + idMunicipality +
                ", idCovidData=" + idCovidData +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}