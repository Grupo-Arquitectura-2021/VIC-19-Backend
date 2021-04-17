package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationCovidDataRequest;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    //public List<LocationRequest> cities();
    public List<CovidDataRequest> covidDataListCity(Integer idCity, Date dateCity);
}
