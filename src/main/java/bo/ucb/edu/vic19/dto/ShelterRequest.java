package bo.ucb.edu.vic19.dto;

import bo.ucb.edu.vic19.model.Transaction;

public class ShelterRequest {
    private String nameCity;
    private String name;
    private Integer amount;
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

    @Override
    public String toString() {
        return "ShelterRequest{" +
                "cityName='" + nameCity + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
