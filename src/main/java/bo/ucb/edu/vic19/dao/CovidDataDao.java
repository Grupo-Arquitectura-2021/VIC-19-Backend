package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.CityCovidData;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CovidDataDao {
    public void insertCovidData(CovidData covidData);
    public void updateCovidData(CovidData covidData);
    public void insertMultiCovidData(List<CovidData> list);
    public void insertCityCovidData(CityCovidData cityCovidData);
    public Integer verifyCityCovidData(String date,Integer IdCity);
    public Integer verifyCountryCovidData(String date,Integer idCountry);
    public Integer getCovidDataCountryIdDate(String date, Integer idCountry);
    public Integer getLastIdCovidData();
}
