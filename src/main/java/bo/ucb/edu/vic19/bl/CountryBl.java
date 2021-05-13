package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;

    @Autowired
    public CountryBl(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public List<LocationResponse> getCountries(){
        return countryDao.countries();
    }

    public CovidDataRequest covidDataCountry(Integer countryId, String dateCovid){
        CovidDataRequest covidDataCountry=countryDao.covidDataCountry(countryId, dateCovid);
        return covidDataCountry;
    }

    public List<CovidDataRequest> covidDataCountryAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(cityId, dateCovid);
        return covidDataListCountryAllInfo;
    }

    public CovidDataRequestMedia mediaCovidDataCountryAllInfo(int countryId, String dateCovid) {
        MediaCovidDataCountry mediaCovidDataCountry = new MediaCovidDataCountry(countryDao);
        return mediaCovidDataCountry.mediaCovidDataCountryAllInfo(countryId, dateCovid);
    }

    public CovidDataRequestVariance varianceCovidDataCountryAllInfo(int countryId, String dateCovid) {
        VarianceCovidDataCountry varianceCovidDataCountry = new VarianceCovidDataCountry(countryDao);
        return varianceCovidDataCountry.varianceCovidDataCountryAllInfo(countryId, dateCovid);
    }

    public CovidDataRequestConfidenceInterval confidenceIntervalCovidDataCountryAllInfo(int countryId, String dateCovid) {
        ConfidenceIntervalCountry confidenceIntervalCountry = new ConfidenceIntervalCountry(countryDao);
        return confidenceIntervalCountry.condifenceIntervalCountry(countryId, dateCovid);
    }
}
