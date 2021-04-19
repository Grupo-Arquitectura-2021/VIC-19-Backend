package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.CountryCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CountryCovidDataDao {
    public void insertCountryCovidData(CountryCovidData countryCovidData);
}
