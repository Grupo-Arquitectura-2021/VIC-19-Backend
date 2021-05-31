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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        VarianceCovidDataCountry varianceCovidDataCountry = new VarianceCovidDataCountry(countryDao);
        MediaCovidDataCountry mediaCovidDataCountry = new MediaCovidDataCountry(countryDao);
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidDataCountry.varianceCovidDataCountryAllInfo(countryId, dateCovid);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataCountry.mediaCovidDataCountryAllInfo(countryId, dateCovid);

        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
        int size=covidDataListCountryAllInfo.size();
        float standardErrorRec, standardErrorVac, standardErrorConf, standardDeath;
        float errorRangeRec, errorRangeVac, errorRangeConf, errorRangeDeath;
        float ciRecUp, ciRecLow, ciVacUp, ciVacLow, ciConfUp, ciConfLow, ciDeathUp, ciDeathLow;
        float z = 2.05f;

        standardErrorConf = (float) (covidDataRequestVariance.getConfirmedCases()/(Math.sqrt(size)));
        standardErrorRec = (float) (covidDataRequestVariance.getRecuperated()/(Math.sqrt(size)));
        standardErrorVac = (float) (covidDataRequestVariance.getVaccinated()/(Math.sqrt(size)));
        standardDeath = (float) (covidDataRequestVariance.getDeathCases()/(Math.sqrt(size)));

        errorRangeConf = z * standardErrorConf;
        errorRangeRec = z * standardErrorRec;
        errorRangeVac = z * standardErrorVac;
        errorRangeDeath = z * standardDeath;

        ciConfUp = covidDataRequestMedia.getConfirmedCases()+errorRangeConf;
        ciConfLow = covidDataRequestMedia.getConfirmedCases()-errorRangeConf;
        ciRecUp = covidDataRequestMedia.getRecuperated()+errorRangeRec;
        ciRecLow = covidDataRequestMedia.getRecuperated()-errorRangeRec;
        ciVacUp = covidDataRequestMedia.getVaccinated()+errorRangeVac;
        ciVacLow = covidDataRequestMedia.getVaccinated()-errorRangeVac;
        ciDeathUp = covidDataRequestMedia.getDeathCases()+errorRangeDeath;
        ciDeathLow = covidDataRequestMedia.getDeathCases()-errorRangeDeath;
        CovidDataStatistics covidDataStatistics=new CovidDataStatistics();
        covidDataStatistics.setConfirmedStatistics(new Statistics(covidDataRequestMedia.getConfirmedCases(),covidDataRequestVariance.getConfirmedCases(), new double[]{ciConfLow,ciConfUp}));
        covidDataStatistics.setRecuperatedStatistics(new Statistics(covidDataRequestMedia.getRecuperated(),covidDataRequestVariance.getRecuperated(), new double[]{ciRecLow,ciRecUp}));
        covidDataStatistics.setDeathStatistics(new Statistics(covidDataRequestMedia.getDeathCases(),covidDataRequestVariance.getDeathCases(), new double[]{ciDeathLow,ciDeathUp}));
        covidDataStatistics.setVaccinatedStatistics(new Statistics(covidDataRequestMedia.getVaccinated(),covidDataRequestVariance.getVaccinated(), new double[]{ciVacLow,ciVacUp}));
        return covidDataStatistics;
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

    public CovidDataRequestAbsoluteIncrease absoluteIncreaseCovidDataCountryAllInfo(int countryId, String forecastDate){
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(countryDao, null, null, this.getClass().getSimpleName(),countryId, forecastDate);
        return absoluteIncreaseMethod.assignCovidDataAccordingToBlName();
    }
}
