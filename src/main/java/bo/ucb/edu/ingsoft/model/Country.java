package bo.ucb.edu.ingsoft.model;

public class Country {
    private Integer idCountry;
    private String name;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Country(){
        transaction = new Transaction();
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
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
        return "Country{" +
                "idCountry=" + idCountry +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}