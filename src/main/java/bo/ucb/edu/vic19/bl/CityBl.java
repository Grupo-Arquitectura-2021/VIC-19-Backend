package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.*;

import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCity;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCity;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCity;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
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

    public List<CitySimpleResponse> getCities(){
        return cityDao.cities();
    }

    public List<LocationResponse> getCitiesLocation(){
        return cityDao.citiesLocation();
    }

    public CovidDataRequest covidDataCity(Integer cityId, String dateCovid){
        CovidDataRequest covidDataCity=cityDao.covidDataCity(cityId, dateCovid);
        return covidDataCity;
    }

    public List<CovidDataRequest> covidDataListCity(String dateCovid){
        List<CovidDataRequest> covidDataListCity=cityDao.covidDataListCity(dateCovid);
        return covidDataListCity;
    }

    public List<CovidDataRequest> covidDataCityAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataAllInfo(cityId, dateCovid);
        return covidDataListCityAllInfo;
    }

    public CovidDataRequestMedia mediaCovidDataCityAllInfo(int cityId, String dateCovid) {
        MediaCovidDataCity mediaCovidDataCity = new MediaCovidDataCity(cityDao);
        return mediaCovidDataCity.mediaCovidDataCityAllInfo(cityId, dateCovid);
    }

    public CovidDataRequestVariance varianceCovidDataCityAllInfo(int cityId, String dateCovid) {
        VarianceCovidDataCity varianceCovidDataCity = new VarianceCovidDataCity(cityDao);
        return varianceCovidDataCity.varianceCovidDataCityAllInfo(cityId, dateCovid);
    }

    public CovidDataRequestConfidenceInterval confidenceIntervalCovidDataCityAllInfo(int cityId, String dateCovid) {
        ConfidenceIntervalCity confidenceIntervalCity = new ConfidenceIntervalCity(cityDao);
        return confidenceIntervalCity.condifenceIntervalCity(cityId, dateCovid);
    }

    public CovidDataRequestLeastSquares leastSquaresCovidDataCityAllInfo(int countryId, String forecastDate, String dateCovid){
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(null, cityDao, null,forecastDate, this.getClass().getSimpleName(),countryId, dateCovid);
        return leastSquaresMethod.assignCovidDataAccordingToBlName();
    }
}
