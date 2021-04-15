package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.LocationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
    public List<LocationRequest> cities();
}
