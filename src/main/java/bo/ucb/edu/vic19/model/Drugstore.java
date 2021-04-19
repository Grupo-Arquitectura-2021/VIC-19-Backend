package bo.ucb.edu.vic19.model;

public class Drugstore {
    private Integer idDrugstore;
    private Integer idCity;
    private String name;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Drugstore(){
        transaction = new Transaction();
    }

    public Integer getIdDrugstore() {
        return idDrugstore;
    }

    public void setIdDrugstore(Integer idDrugstore) {
        this.idDrugstore = idDrugstore;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Drugstore{" +
                "idDrugstore=" + idDrugstore +
                ", idDepartament=" + idCity +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
