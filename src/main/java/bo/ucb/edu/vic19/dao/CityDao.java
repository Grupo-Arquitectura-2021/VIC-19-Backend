package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.LocationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    public List<LocationResponse> cities();
    public CovidDataRequest covidDataCity(Integer cityId, String dateCovid);
    public String cityName(Integer cityId);

    public List<CovidDataRequest> covidDataListCity(String dateCovid);

    public List<CovidDataRequest> covidDataListCityMunicipality(String nameCity, String dateCovid);
    public List<CovidDataRequest> covidDataListCityByIdMunicipality(Integer cityId, String dateCovid);
    List<CovidDataRequest> covidDataListCityAllInfo(Integer cityId, String dateCovid);
}
