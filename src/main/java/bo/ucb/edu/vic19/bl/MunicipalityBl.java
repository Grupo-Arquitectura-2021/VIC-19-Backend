package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceInterval;
import bo.ucb.edu.vic19.statistics.increaseMethods.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidData;
import bo.ucb.edu.vic19.statistics.increaseMethods.PercentageIncreaseMethod;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidData;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MunicipalityBl {
    private MunicipalityDao municipalityDao;
    private CovidDataDao covidDataDao;

    @Autowired
    public MunicipalityBl(MunicipalityDao municipalityDao,CovidDataDao covidDataDao){
        this.municipalityDao = municipalityDao;
        this.covidDataDao = covidDataDao;
    }

    public Integer getMunicipalityIdWithName(List<MunicipalitySimpleResponse> municipalities,String name){
        Integer municipalityId=null;
        for(MunicipalitySimpleResponse c:municipalities){
            String []names=name.split("-");
            if(c.getMunicipality().equalsIgnoreCase(names[1])&&c.getCity().equalsIgnoreCase(names[0])){
                municipalityId=c.getIdMunicipality();
                break;
            }
        }
        return municipalityId;
    }
    public void saveDataCSV(MultipartFile file, Transaction transaction, boolean replace) {
        try {
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream(),true);
            List<CovidData> covidDataList= new ArrayList();
            List<MunicipalityCovidData> municipalityCovidDataList=new ArrayList<>();
            List<MunicipalitySimpleResponse> municipalities=municipalityDao.municipalitiesWithCities();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
            Integer covidDataId;
            for(CovidDataRequest cdr:csvToList)
            {
                Integer municipalityId=getMunicipalityIdWithName(municipalities,cdr.getNameLocationCovid());
                if(municipalityId!=null){
                    covidDataId = covidDataDao.getCovidDataMunicipalityIdDate(sdfDB.format(sdf.parse(cdr.getDateLocationCovid())),municipalityId);
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
                        municipalityCovidDataList.add(new MunicipalityCovidData(null, municipalityId, null, 1,transaction));
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
                municipalityCovidDataList.get(i).setIdCovidData(covidDataList.get(i).getIdCovidData());
            }
            if(municipalityCovidDataList.size()!=0)municipalityDao.insertMultiMunicipality(municipalityCovidDataList);
        }
        catch (Exception e){

            System.out.println(e);
            throw new RuntimeException("Fail to store csv data: " + e.getMessage());
        }
    }


    public List<LocationResponse> getMunicipalities(Integer cityId){
        return municipalityDao.municipalities(cityId);
    }


    public CovidDataRequest covidDataMunicipality(Integer municipalityId, String dateCovid){
        return municipalityDao.covidDataMunicipality(municipalityId, dateCovid);
    }

    public List<CovidDataRequest> covidDataListMunicipalityByCityId(String dateCovid,Integer cityId){
        List<CovidDataRequest> covidDataListMunicipalityByCityId=municipalityDao.covidDataListMunicipalityByCityId(dateCovid,cityId);
        return covidDataListMunicipalityByCityId;
    }

    public List<CovidDataRequest> covidDataMunAllInfo(int muniId, String dateCovid) {
        List<CovidDataRequest> covidDataListMunAllInfo=municipalityDao.covidDataListMunAllInfo(muniId, dateCovid);
        return covidDataListMunAllInfo;
    }

    public CovidDataStatistics statisticsMunicipality(Integer munId,String dateCovid){
        List<CovidDataRequest> covidDataRequests=municipalityDao.covidDataListMunAllInfo(munId,dateCovid);
        MediaCovidData mediaCovidData = new MediaCovidData(covidDataRequests);
        VarianceCovidData varianceCovidData = new VarianceCovidData(covidDataRequests);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidData.mediaCovidDataCountryAllInfo(munId, dateCovid, municipalityDao.municipalityName(munId));
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidData.varianceCovidDataCountryAllInfo(covidDataRequestMedia);
        ConfidenceInterval confidenceIntervalCountry=new ConfidenceInterval(covidDataRequests);
        CovidDataRequestConfidenceInterval confidenceInterval;
        confidenceInterval = confidenceIntervalCountry.condifenceIntervalCountry(covidDataRequestMedia, covidDataRequestVariance);

        CovidDataStatistics covidDataStatistics = new CovidDataStatistics();
        covidDataStatistics.setLocationName(municipalityDao.municipalityName(munId));
        covidDataStatistics.setDate(dateCovid);
        covidDataStatistics.setConfirmedStatistics(new Statistics(covidDataRequestMedia.getConfirmedCases(), covidDataRequestVariance.getConfirmedCases(), new double[]{confidenceInterval.getConfirmedCasesLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setRecuperatedStatistics(new Statistics(covidDataRequestMedia.getRecuperated(), covidDataRequestVariance.getRecuperated(), new double[]{confidenceInterval.getRecuperatedLowerLimit(), confidenceInterval.getConfirmedCasesUpperLimit()}));
        covidDataStatistics.setDeathStatistics(new Statistics(covidDataRequestMedia.getDeathCases(), covidDataRequestVariance.getDeathCases(), new double[]{confidenceInterval.getDeathCasesLowerLimit(), confidenceInterval.getDeathCasesUpperLimit()}));
        covidDataStatistics.setVaccinatedStatistics(new Statistics(covidDataRequestMedia.getVaccinated(), covidDataRequestVariance.getVaccinated(), new double[]{confidenceInterval.getVaccinatedLowerLimit(), confidenceInterval.getVaccinatedUpperLimit()}));
        return covidDataStatistics;
    }

    public CovidDataRequestLeastSquares leastSquaresCovidDataCityAllInfo(int munId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = municipalityDao.covidDataListMunAllInfoNoDate(munId);
        List<CovidDataRequest> covidDataRequests1 = municipalityDao.covidDataListMunAllInfoNoDateDESC(munId);
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(covidDataRequests, covidDataRequests1,forecastDate,municipalityDao.municipalityName(munId));
        return leastSquaresMethod.getCovidDataRequestLeastSquares();
    }

    public CovidDataRequestIncreaseMethod absoluteIncreaseCovidDataMunAllInfo(int munId, String forecastDate){
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(null, null, municipalityDao, this.getClass().getSimpleName(),munId, forecastDate);
        return absoluteIncreaseMethod.assignCovidDataAccordingToBlName();
    }

    public CovidDataRequestIncreaseMethod percentageIncreaseCovidDataMunAllInfo(int munId, String forecastDate){
        PercentageIncreaseMethod percentageIncreaseMethod=new PercentageIncreaseMethod(null, null, municipalityDao, this.getClass().getSimpleName(),munId, forecastDate);
        return percentageIncreaseMethod.assignCovidDataAccordingToBlName();
    }
}
