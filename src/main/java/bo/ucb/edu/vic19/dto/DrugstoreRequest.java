package bo.ucb.edu.vic19.dto;

public class DrugstoreRequest {
    private int idDrugstore;
    private Integer idCity;
    private String name;
    private float lon;
    private float lat;

    public DrugstoreRequest() {
    }

    public int getIdDrugstore() {
        return idDrugstore;
    }

    public void setIdDrugstore(int idDrugstore) {
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

    @Override
    public String toString() {
        return "DrugstoreRequest{" +
                "idDrugstore=" + idDrugstore +
                ", idCity=" + idCity +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
