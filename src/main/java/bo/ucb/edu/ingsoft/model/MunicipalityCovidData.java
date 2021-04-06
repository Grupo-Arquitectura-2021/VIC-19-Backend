package bo.ucb.edu.ingsoft.model;

public class MunicipalityCovidData {
    private Integer idMunicipalityCovidData;
    private Integer idMunicipality;
    private Integer idCovidData;
    private Integer status;
    private Transaction transaction;

    public MunicipalityCovidData(){
        transaction = new Transaction();
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