package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.CityCovidData;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CovidDataDao {
    public void insertCovidData(CovidData covidData);
    public void updateCovidData(CovidData covidData);
    public void insertCountryCovidData(CountryCovidData covidData);
    public Integer verifyCountryCovidData(String date,Integer IdCountry);

    public void insertCityCovidData(CityCovidData cityCovidData);
    public Integer verifyCityCovidData(String date,Integer IdCity);

    public void insertMunicipalityCovidData(MunicipalityCovidData covidData);
    public Integer verifyMunicipalityCovidData(String date,Integer IdMunicipality);

    public Integer getLastIdCovidData();
}
