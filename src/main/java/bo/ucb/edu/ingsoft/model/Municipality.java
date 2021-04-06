package bo.ucb.edu.ingsoft.model;

public class Municipality {
    private Integer idMunicipality;
    private Integer idDepartament;
    private String municipality;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Municipality(){
        transaction = new Transaction();
    }

    public Integer getIdMunicipality() {
        return idMunicipality;
    }

    public void setIdMunicipality(Integer idMunicipality) {
        this.idMunicipality = idMunicipality;
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
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
        return "Municipality{" +
                "idMunicipality=" + idMunicipality +
                ", idDepartament=" + idDepartament +
                ", municipality='" + municipality + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
