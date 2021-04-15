package bo.ucb.edu.vic19.model;

public class Hospital {
    private Integer idHospital;
    private Integer idDepartament;
    private String name;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Hospital(){
        transaction = new Transaction();
    }

    public Integer getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(Integer idHospital) {
        this.idHospital = idHospital;
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
        return "Hospital{" +
                "idHospital=" + idHospital +
                ", idDepartament=" + idDepartament +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
