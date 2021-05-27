package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;

    @Autowired
    public CountryBl(CountryDao countryDao){
        this.countryDao = countryDao;
    }



    public void saveData(MultipartFile file, Integer id, Transaction transaction) {
        try {

            List<CovidDataRequest> dataDepartmentCsvRequestList = CovidDataCSVUtil.csvToDataCountryCsvRequest(file.getInputStream());
        }
        catch (IOException e){
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
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

    public CovidDataRequestLeastSquares leastSquaresCovidDataCountryAllInfo(int countryId, String forecastDate, String dateCovid){
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(countryDao, null, null, forecastDate, this.getClass().getSimpleName(),countryId, dateCovid);
        return leastSquaresMethod.assignCovidDataAccordingToBlName();
    }


}
