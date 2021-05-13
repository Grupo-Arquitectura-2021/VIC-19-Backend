package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCity;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
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

    public List<LocationResponse> getCities(){
        return cityDao.cities();
    }

    public CovidDataRequest covidDataCity(Integer cityId, String dateCovid){
        CovidDataRequest covidDataCity=cityDao.covidDataCity(cityId, dateCovid);
        return covidDataCity;
    }

    public List<CovidDataRequest> covidDataListCity(String dateCovid){
        List<CovidDataRequest> covidDataListCity=cityDao.covidDataListCity(dateCovid);
        return covidDataListCity;
    }

    public List<CovidDataRequest> covidDataListCityMunicipality(String nameCity,String dateCovid){
        List<CovidDataRequest> covidDataListCityMunicipality=cityDao.covidDataListCityMunicipality(nameCity, dateCovid);
        return covidDataListCityMunicipality;
    }

    public List<CovidDataRequest> covidDataListCityByIdMunicipality(Integer cityId,String dateCovid){
        List<CovidDataRequest> covidDataListCityByIdMunicipality=cityDao.covidDataListCityByIdMunicipality(cityId, dateCovid);
        return covidDataListCityByIdMunicipality;
    }

    public List<CovidDataRequest> covidDataCityAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataListCityAllInfo(cityId, dateCovid);
        return covidDataListCityAllInfo;
    }

    public CovidDataRequestMedia mediaCovidDataCityAllInfo(int cityId, String dateCovid) {
        MediaCovidDataCity mediaCovidDataCity = new MediaCovidDataCity(cityDao);
        return mediaCovidDataCity.mediaCovidDataCityAllInfo(cityId, dateCovid);
    }
}
