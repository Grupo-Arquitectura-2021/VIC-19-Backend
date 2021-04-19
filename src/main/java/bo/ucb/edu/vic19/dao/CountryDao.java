package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryDao {
    public List<LocationResponse> countries();
    public CovidDataRequest covidDataCountry(Integer countryId, String dateCovid);
    public Integer countryId(String country);
    public String countryName(Integer countryId);

    List<CovidDataRequest> covidDataListCountryAllInfo(int countryId, String dateCovid);
}
