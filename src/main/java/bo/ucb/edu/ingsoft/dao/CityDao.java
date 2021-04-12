package bo.ucb.edu.ingsoft.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityDao {
    public Integer getCityId(String cityName);
}
