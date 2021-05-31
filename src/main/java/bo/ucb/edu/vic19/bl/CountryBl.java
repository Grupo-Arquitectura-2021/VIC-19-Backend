package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.absoluteIncrease.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCountry;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;
    private CovidDataDao covidDataDao;

    @Autowired
    public CountryBl(CountryDao countryDao,CovidDataDao covidDataDao){
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
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream(),false);
            System.out.println(csvToList.toString());
            List<CovidData> covidDataList= new ArrayList();
            List<CountryCovidData> countryCovidDataList=new ArrayList<>();
            List<LocationResponse> countries=countryDao.countries();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
            Integer covidDataId;
            System.out.println(csvToList.toString());
            for(CovidDataRequest cdr:csvToList)
            {
                Integer countryId=getCountryIdWithName(countries,cdr.getNameLocationCovid());
                if(countryId!=null){
                    covidDataId = covidDataDao.getCovidDataCountryIdDate(sdfDB.format(sdf.parse(cdr.getDateLocationCovid())),countryId);

                    System.out.println(covidDataId);
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

                           System.out.println("update");
                       }
                   }
                }
            }
            System.out.println(covidDataList);
            if(covidDataList.size()!=0)covidDataDao.insertMultiCovidData(covidDataList);
            for(int i=0;i<covidDataList.size();i++)
            {
                countryCovidDataList.get(i).setIdCovidData(covidDataList.get(i).getIdCovidData());
            }
            if(countryCovidDataList.size()!=0)countryDao.insertMultiCountry(countryCovidDataList);
        }
        catch (Exception e){

            System.out.println(e.getCause());
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
    public CovidDataStatistics statisticsCountry(Integer countryId,String dateCovid){
        List<CovidDataRequest> covidDataRequests=countryDao.covidDataListCountryAllInfo(countryId,dateCovid);
        MediaCovidData mediaCovidData = new MediaCovidData(covidDataRequests);
        VarianceCovidData varianceCovidData = new VarianceCovidData(covidDataRequests);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidData.mediaCovidDataCountryAllInfo(countryId, dateCovid, countryDao.countryName(countryId));
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidData.varianceCovidDataCountryAllInfo(covidDataRequestMedia);
        ConfidenceInterval confidenceIntervalCountry=new ConfidenceInterval(covidDataRequests);
        CovidDataRequestConfidenceInterval confidenceInterval;
        confidenceInterval = confidenceIntervalCountry.condifenceIntervalCountry(covidDataRequestMedia, covidDataRequestVariance);

        CovidDataStatistics covidDataStatistics = new CovidDataStatistics();
        covidDataStatistics.setLocationName(countryDao.countryName(countryId));
        covidDataStatistics.setDate(dateCovid);
        covidDataStatistics.setConfirmedStatistics(new Statistics(covidDataRequestMedia.getConfirmedCases(), covidDataRequestVariance.getConfirmedCases(), new double[]{confidenceInterval.getConfirmedCasesLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setRecuperatedStatistics(new Statistics(covidDataRequestMedia.getRecuperated(), covidDataRequestVariance.getRecuperated(), new double[]{confidenceInterval.getRecuperatedLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setDeathStatistics(new Statistics(covidDataRequestMedia.getDeathCases(), covidDataRequestVariance.getDeathCases(), new double[]{confidenceInterval.getDeathCasesLowerLimit(), confidenceInterval.getDeathCasesUpperLimit()}));
        covidDataStatistics.setVaccinatedStatistics(new Statistics(covidDataRequestMedia.getVaccinated(), covidDataRequestVariance.getVaccinated(), new double[]{confidenceInterval.getVaccinatedLowerLimit(), confidenceInterval.getVaccinatedUpperLimit()}));
        return covidDataStatistics;
    }

    public CovidDataRequestLeastSquares leastSquaresCovidDataCountryAllInfo(int countryId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = countryDao.covidDataListCountryAllInfoNoDate(countryId);
        List<CovidDataRequest> covidDataRequests1 = countryDao.covidDataListCountryAllInfoNoDateDESC(countryId);
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(covidDataRequests, covidDataRequests1,forecastDate,countryDao.countryName(countryId));
        return leastSquaresMethod.getCovidDataRequestLeastSquares();
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
