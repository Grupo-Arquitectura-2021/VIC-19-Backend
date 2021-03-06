package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dto.*;

import bo.ucb.edu.vic19.model.CityCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.statistics.brownModel.BrownModel;
import bo.ucb.edu.vic19.util.statistics.confidenceInterval.ConfidenceInterval;
import bo.ucb.edu.vic19.util.statistics.increaseMethods.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.util.statistics.leastSquaresMethod.LeastSquaresLib;
import bo.ucb.edu.vic19.util.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.util.statistics.increaseMethods.PercentageIncreaseMethod;
import bo.ucb.edu.vic19.util.statistics.media.AverageCovidData;
import bo.ucb.edu.vic19.util.statistics.variance.VarianceCovidData;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    public Integer getCityIdWithName(List<CitySimpleResponse> countries,String name){
        Integer cityId=null;
        for(CitySimpleResponse c:countries){
            if(c.getCity().equalsIgnoreCase(name)){
                cityId=c.getIdCity();
                break;
            }
        }
        return cityId;
    }
    public void saveDataCSV(MultipartFile file, Transaction transaction,boolean replace) {
        try {
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream(),false);
            List<CovidData> covidDataList= new ArrayList();
            List<CityCovidData> cityCovidDataList=new ArrayList<>();
            List<CitySimpleResponse> cities=cityDao.cities();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
            Integer covidDataId;
            for(CovidDataRequest cdr:csvToList)
            {
                Integer cityId=getCityIdWithName(cities,cdr.getNameLocationCovid());
                if(cityId!=null){
                    covidDataId = covidDataDao.getCovidDataCityIdDate(sdfDB.format(sdf.parse(cdr.getDateLocationCovid())),cityId);

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

    public CovidDataStatistics statisticsCity(Integer cityId,String dateCovid){
        List<CovidDataRequest> covidDataRequests=cityDao.covidDataListCityAllInfo(cityId,dateCovid);
        AverageCovidData averageCovidData = new AverageCovidData(covidDataRequests);
        VarianceCovidData varianceCovidData = new VarianceCovidData(covidDataRequests);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = averageCovidData.mediaCovidDataCountryAllInfo(cityId, dateCovid, cityDao.cityName(cityId));
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidData.varianceCovidDataCountryAllInfo(covidDataRequestMedia);
        ConfidenceInterval confidenceIntervalCountry=new ConfidenceInterval(covidDataRequests);
        CovidDataRequestConfidenceInterval confidenceInterval;
        confidenceInterval = confidenceIntervalCountry.condifenceIntervalCountry(covidDataRequestMedia, covidDataRequestVariance);

        CovidDataStatistics covidDataStatistics = new CovidDataStatistics();
        covidDataStatistics.setLocationName(cityDao.cityName(cityId));
        covidDataStatistics.setDate(dateCovid);
        covidDataStatistics.setConfirmedStatistics(new Statistics(covidDataRequestMedia.getConfirmedCases(), covidDataRequestVariance.getConfirmedCases(), new double[]{confidenceInterval.getConfirmedCasesLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setRecuperatedStatistics(new Statistics(covidDataRequestMedia.getRecuperated(), covidDataRequestVariance.getRecuperated(), new double[]{confidenceInterval.getRecuperatedLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setDeathStatistics(new Statistics(covidDataRequestMedia.getDeathCases(), covidDataRequestVariance.getDeathCases(), new double[]{confidenceInterval.getDeathCasesLowerLimit(), confidenceInterval.getDeathCasesUpperLimit()}));
        covidDataStatistics.setVaccinatedStatistics(new Statistics(covidDataRequestMedia.getVaccinated(), covidDataRequestVariance.getVaccinated(), new double[]{confidenceInterval.getVaccinatedLowerLimit(), confidenceInterval.getVaccinatedUpperLimit()}));
        return covidDataStatistics;
    }

    public CovidDataRequestLeastSquares leastSquaresCovidDataCityAllInfo(int cityId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = cityDao.covidDataListCityAllInfoNoDate(cityId);
        List<CovidDataRequest> covidDataRequests1 = cityDao.covidDataListCityAllInfoNoDateDESC(cityId);
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(covidDataRequests, covidDataRequests1,forecastDate,cityDao.cityName(cityId));
        return leastSquaresMethod.getCovidDataRequestLeastSquares();
    }

    public CovidDataRequestIncreaseMethod absoluteIncreaseCovidDataCityAllInfo(int cityId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = cityDao.covidDataListCityAllInfoNoDate(cityId);
        List<CovidDataRequest> covidDataRequests1 = cityDao.covidDataListCityAllInfoNoDateDESC(cityId);
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(covidDataRequests,covidDataRequests1,forecastDate, cityDao.cityName(cityId));
        return absoluteIncreaseMethod.getCovidDataRequestAbsoluteIncrease();
    }

    public CovidDataRequestIncreaseMethod percentageIncreaseCovidDataCityAllInfo(int cityId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = cityDao.covidDataListCityAllInfoNoDate(cityId);
        List<CovidDataRequest> covidDataRequests1 = cityDao.covidDataListCityAllInfoNoDateDESC(cityId);
        PercentageIncreaseMethod percentageIncreaseMethod=new PercentageIncreaseMethod(covidDataRequests,covidDataRequests1,forecastDate,cityDao.cityName(cityId));
        return percentageIncreaseMethod.getCovidDataRequestPercentageIncrease();
    }

    public CovidDataRequestBrownModel brownModelCovidDataCityAllInfo(int cityId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = cityDao.covidDataListCityAllInfoNoDate(cityId);
        List<CovidDataRequest> covidDataRequests1 = cityDao.covidDataListCityAllInfoNoDateDESC(cityId);
        BrownModel brownModel=new BrownModel(covidDataRequests, covidDataRequests1,forecastDate,cityDao.cityName(cityId));
        return brownModel.getCovidDataRequestBrownModel();
    }
    public FunctionsRequest getFunction(int cityId,String date) throws ParseException {
        List<CovidDataRequest> list=cityDao.covidDataListCityAllInfo(cityId, date);

        final String uri = "http://localhost:1080/gompertz";
        RestTemplate restTemplate = new RestTemplate();
        LogisticFunction logisticFunction =
                restTemplate.postForObject(uri, list, LogisticFunction.class);
        FunctionsRequest functionsRequest=new FunctionsRequest();
        functionsRequest.setLeastSquaresFunction(new LeastSquaresLib().getFunction(list));
        functionsRequest.setLogisticFunction(logisticFunction);

        return functionsRequest;

    }

}
