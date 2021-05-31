package bo.ucb.edu.vic19.dto;

public class MunicipalitySimpleResponse {
    Integer idMunicipality;
    String municipality;
    String city;

    public Integer getIdMunicipality() {
        return idMunicipality;
    }

    public void setIdMunicipality(Integer idMunicipality) {
        this.idMunicipality = idMunicipality;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "MunicipalitySimpleResponse{" +
                "idMunicipality=" + idMunicipality +
                ", municipality='" + municipality + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
