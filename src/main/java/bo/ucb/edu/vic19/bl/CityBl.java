package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityBl {
    private CityDao cityDao;

    @Autowired
    public CityBl(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public List<LocationRequest> getCities(){
        return cityDao.cities();
    }

    public List<CovidDataRequest> covidDataCity(Integer cityId, String dateCovid){
        List<CovidDataRequest> covidDataCity=cityDao.covidDataCity(cityId, dateCovid);
        return covidDataCity;
    }

}
