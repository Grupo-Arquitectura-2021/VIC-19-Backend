package bo.ucb.edu.vic19.dto;

public class LocationResponse {
    private Integer idLocation;
    private String name;
    private float lon;
    private float lat;

    public LocationResponse() {
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
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
        return "LocationRequest{" +
                "idLocation=" + idLocation +
                ", locationName='" + name + '\'' +
                ", longitude=" + lon +
                ", latitude=" + lat +
                '}';
    }
}
