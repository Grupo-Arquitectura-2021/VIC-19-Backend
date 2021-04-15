package bo.ucb.edu.vic19.model;

public class Shelter {
    private Integer idShelter;
    private Integer idDepartament;
    private String name;
    private Integer amount;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Shelter(){
        transaction = new Transaction();
    }

    public Integer getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(Integer idShelter) {
        this.idShelter = idShelter;
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
        return "Shelter{" +
                "idShelter=" + idShelter +
                ", idDepartament=" + idDepartament +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
