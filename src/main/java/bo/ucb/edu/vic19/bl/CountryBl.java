package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.increaseMethods.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.increaseMethods.PercentageIncreaseMethod;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;
    private CovidDataDao covidDataDao;

    @Autowired
    public CountryBl(CountryDao countryDao){
        this.countryDao = countryDao;
        this.covidDataDao=covidDataDao;
    }


    public Integer getCountryIdWithName(List<LocationResponse> countries,String name){
        Integer countryId=null;
        for(LocationResponse c:countries){
            if(c.getLocationName().equalsIgnoreCase(name)){
                countryId=c.getIdLocation();
                break;
            }
        }
        return countryId;
    }
    public void saveDataCSV(MultipartFile file, Transaction transaction,boolean replace) {
        try {
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream());
            List<CovidData> covidDataList= new ArrayList();
            List<CountryCovidData> countryCovidDataList=new ArrayList<>();
            List<LocationResponse> countries=countryDao.countries();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
            Integer covidDataId;
            for(CovidDataRequest cdr:csvToList)
            {
                Integer countryId=getCountryIdWithName(countries,cdr.getNameLocationCovid());
                if(countryId!=null){
                    covidDataId = covidDataDao.getCovidDataCountryIdDate(sdfDB.format(sdf.parse(cdr.getDateLocationCovid())),countryId);

                   if(covidDataId==null){
                       covidDataList.add(
                               new CovidData(
                                       null,
                                       1,
                                       cdr.getDeathCases(),
                                       cdr.getConfirmedCases(),
                                       cdr.getVaccinated(),
                                       cdr.getCumulativeCases(),
                                       cdr.getRecuperated(),
                                       sdf.parse(cdr.getDateLocationCovid()),
                                       1,
                                       transaction));
                       countryCovidDataList.add(new CountryCovidData(null, countryId, null, 1,transaction));
                   }
                   else{
                       if(replace){
                           covidDataDao.updateCovidData(
                                   new CovidData(
                                           covidDataId,
                                           1,
                                           cdr.getDeathCases(),
                                           cdr.getConfirmedCases(),
                                           cdr.getVaccinated(),
                                           cdr.getCumulativeCases(),
                                           cdr.getRecuperated(),
                                           null,
                                           1,
                                           transaction));
                       }
                   }
                }
            }
            if(covidDataList.size()!=0)covidDataDao.insertMultiCovidData(covidDataList);
            for(int i=0;i<covidDataList.size();i++)
            {
                countryCovidDataList.get(i).setIdCovidData(covidDataList.get(i).getIdCovidData());
            }
            if(countryCovidDataList.size()!=0)countryDao.insertMultiCountry(countryCovidDataList);
        }
        catch (IOException | ParseException e){
            throw new RuntimeException("Fail to store csv data: " + e.getMessage());
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

    public CovidDataRequestLeastSquares leastSquaresCovidDataCountryAllInfo(int countryId, String forecastDate){
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(countryDao, null, null, forecastDate, this.getClass().getSimpleName(),countryId);
        return leastSquaresMethod.assignCovidDataAccordingToBlName();
    }

    public CovidDataRequestIncreaseMethod absoluteIncreaseCovidDataCountryAllInfo(int countryId, String forecastDate){
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(countryDao, null, null, this.getClass().getSimpleName(),countryId, forecastDate);
        return absoluteIncreaseMethod.assignCovidDataAccordingToBlName();
    }

    public CovidDataRequestIncreaseMethod percentageIncreaseCovidDataCountryAllInfo(int countryId, String forecastDate){
        PercentageIncreaseMethod percentageIncreaseMethod=new PercentageIncreaseMethod(countryDao, null, null, this.getClass().getSimpleName(),countryId, forecastDate);
        return percentageIncreaseMethod.assignCovidDataAccordingToBlName();
    }
}
