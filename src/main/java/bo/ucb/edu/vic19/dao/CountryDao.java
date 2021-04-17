package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryDao {
    public List<LocationRequest> countries();
    public List<CovidDataRequest> covidDataCountry(Integer countryId, String dateCovid);
}
