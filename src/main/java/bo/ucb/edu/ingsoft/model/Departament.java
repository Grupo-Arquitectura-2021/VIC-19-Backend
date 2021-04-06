package bo.ucb.edu.ingsoft.model;

public class Departament {
    private Integer idDepartament;
    private String departament;
    private float lon;
    private float lat;
    private Integer status;
    private Transaction transaction;

    public Departament(){
        transaction = new Transaction();
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
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
        return "Departament{" +
                "idDepartament=" + idDepartament +
                ", departament='" + departament + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}

