package bo.ucb.edu.vic19.dto;

public class CitySimpleResponse {
    Integer idCity;
    String city;

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CitySimpleResponse{" +
                "idCity=" + idCity +
                ", city='" + city + '\'' +
                '}';
    }
}
