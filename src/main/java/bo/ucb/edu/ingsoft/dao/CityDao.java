package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.dto.LocationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    public List<LocationRequest> cities();
}
