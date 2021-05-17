package bo.ucb.edu.vic19.dto;

public class HospitalRequest {
    private int idHospital;
    private Integer idCity;
    private String name;
    private float lon;
    private float lat;

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

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    @Override
    public String toString() {
        return "HospitalRequest{" +
                "idHospital=" + idHospital +
                ", idCity='" + idCity + '\'' +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
