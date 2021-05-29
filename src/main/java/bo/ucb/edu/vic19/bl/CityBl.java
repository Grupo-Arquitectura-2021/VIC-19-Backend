package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dto.*;

import bo.ucb.edu.vic19.model.CityCovidData;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.absoluteIncrease.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCity;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCity;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCity;
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
public class CityBl {
    private CityDao cityDao;
    private CovidDataDao covidDataDao;


    @Autowired
    public CityBl(CityDao cityDao, CovidDataDao covidDataDao) {
        this.cityDao = cityDao;
        this.covidDataDao = covidDataDao;
    }
    public Integer getCityIdWithName(List<LocationResponse> countries,String name){
        Integer cityId=null;
        for(LocationResponse c:countries){
            if(c.getLocationName().equalsIgnoreCase(name)){
                cityId=c.getIdLocation();
                break;
            }
        }
        return cityId;
    }
    public void saveDataCSV(MultipartFile file, Transaction transaction,boolean replace) {
        try {
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream());
            List<CovidData> covidDataList= new ArrayList();
            List<CityCovidData> cityCovidDataList=new ArrayList<>();
            List<LocationResponse> countries=cityDao.countries();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
            Integer covidDataId;
            for(CovidDataRequest cdr:csvToList)
            {
                Integer cityId=getCityIdWithName(countries,cdr.getNameLocationCovid());
                if(cityId!=null){
                    covidDataId = covidDataDao.getCovidDataCountryIdDate(sdfDB.format(sdf.parse(cdr.getDateLocationCovid())),cityId);

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
                        //cityCovidDataList.add(new CityCovidData(null, cityId, null, 1,transaction));
                        CityCovidData cityCovidData=new CityCovidData();
                        cityCovidData.setIdCity(cityId);
                        cityCovidData.setIdCovidData(null);
                        cityCovidData.setStatus(1);
                        cityCovidData.setTransaction(transaction);
                        cityCovidDataList.add(cityCovidData);
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
                cityCovidDataList.get(i).setIdCovidData(covidDataList.get(i).getIdCovidData());
            }
            if(cityCovidDataList.size()!=0)cityDao.insertMultiCity(cityCovidDataList);
        }
        catch (IOException | ParseException e){
            throw new RuntimeException("Fail to store csv data: " + e.getMessage());
        }
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

    public CovidDataRequestLeastSquares leastSquaresCovidDataCityAllInfo(int cityId, String forecastDate, String dateCovid){
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(null, cityDao, null,forecastDate, this.getClass().getSimpleName(),cityId, dateCovid);
        return leastSquaresMethod.assignCovidDataAccordingToBlName();
    }

    public CovidDataRequestAbsoluteIncrease absoluteIncreaseCovidDataCityAllInfo(int cityId, String forecastDate){
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(null, cityDao, null, this.getClass().getSimpleName(),cityId, forecastDate);
        return absoluteIncreaseMethod.assignCovidDataAccordingToBlName();
    }


}
