package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.CityCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityCovidDataDao {
    public void insertCityCovidData(CityCovidData cityCovidData);
}
