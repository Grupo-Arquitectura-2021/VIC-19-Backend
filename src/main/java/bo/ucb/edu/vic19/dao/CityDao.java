package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CitySimpleResponse;
import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.LocationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    public List<LocationResponse> citiesLocation();
    public List<CitySimpleResponse> cities();
    public CovidDataRequest covidDataCity(Integer cityId, String dateCovid);
    public String cityName(Integer cityId);
    public List<CovidDataRequest> covidDataListCity(String dateCovid);
    List<CovidDataRequest> covidDataListCityAllInfo(Integer cityId, String dateCovid);
    List<CovidDataRequest> covidDataListCityAllInfoDESC(Integer cityId, String dateCovid);
    List<CovidDataRequest> covidDataAllInfo(Integer cityId, String dateCovid);
}
