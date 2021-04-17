package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.LocationRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.binding.BindingException;

import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    public List<LocationRequest> cities();
    public List<CovidDataRequest> covidDataCity(Integer cityId, String dateCovid);
}
