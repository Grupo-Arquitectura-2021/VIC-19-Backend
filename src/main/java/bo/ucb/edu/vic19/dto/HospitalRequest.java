package bo.ucb.edu.vic19.dto;

public class HospitalRequest {
    private String nameCity;
    private String name;
    private float lon;
    private float lat;

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
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
        return "HospitalRequest{" +
                "idCity=" + nameCity +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
