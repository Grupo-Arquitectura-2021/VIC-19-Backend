package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.statistics.brownModel.BrownModel;
import bo.ucb.edu.vic19.util.statistics.confidenceInterval.ConfidenceInterval;
import bo.ucb.edu.vic19.util.statistics.increaseMethods.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.util.statistics.leastSquaresMethod.LeastSquaresLib;
import bo.ucb.edu.vic19.util.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.util.statistics.media.AverageCovidData;
import bo.ucb.edu.vic19.util.statistics.increaseMethods.PercentageIncreaseMethod;
import bo.ucb.edu.vic19.util.statistics.variance.VarianceCovidData;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
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
        AverageCovidData averageCovidData = new AverageCovidData(covidDataRequests);
        VarianceCovidData varianceCovidData = new VarianceCovidData(covidDataRequests);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = averageCovidData.mediaCovidDataCountryAllInfo(munId, dateCovid, municipalityDao.municipalityName(munId));
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
        List<CovidDataRequest> covidDataRequests = municipalityDao.covidDataListMunAllInfoNoDate(munId);
        List<CovidDataRequest> covidDataRequests1 = municipalityDao.covidDataListMunAllInfoNoDateDESC(munId);
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(covidDataRequests,covidDataRequests1,forecastDate, municipalityDao.municipalityName(munId));
        return absoluteIncreaseMethod.getCovidDataRequestAbsoluteIncrease();
    }

    public CovidDataRequestIncreaseMethod percentageIncreaseCovidDataMunAllInfo(int munId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = municipalityDao.covidDataListMunAllInfoNoDate(munId);
        List<CovidDataRequest> covidDataRequests1 = municipalityDao.covidDataListMunAllInfoNoDateDESC(munId);
        PercentageIncreaseMethod percentageIncreaseMethod=new PercentageIncreaseMethod(covidDataRequests,covidDataRequests1,forecastDate, municipalityDao.municipalityName(munId));
        return percentageIncreaseMethod.getCovidDataRequestPercentageIncrease();
    }

    public CovidDataRequestBrownModel brownModelCovidDataMunAllInfo(int munId, String forecastDate){
        List<CovidDataRequest> covidDataRequests = municipalityDao.covidDataListMunAllInfoNoDate(munId);
        List<CovidDataRequest> covidDataRequests1 = municipalityDao.covidDataListMunAllInfoNoDateDESC(munId);
        BrownModel brownModel=new BrownModel(covidDataRequests, covidDataRequests1,forecastDate,municipalityDao.municipalityName(munId));
        return brownModel.getCovidDataRequestBrownModel();
    }
    public FunctionsRequest getFunction(int municipalityId,String date) throws ParseException {
        List<CovidDataRequest> list=municipalityDao.covidDataListMunAllInfo(municipalityId, date);

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
