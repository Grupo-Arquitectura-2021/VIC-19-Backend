package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.model.CityCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityCovidDataDao {
    public void insertCityCovidData(CityCovidData cityCovidData);
}
