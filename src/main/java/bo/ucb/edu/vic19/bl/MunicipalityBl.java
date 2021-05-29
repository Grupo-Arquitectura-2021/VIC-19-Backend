package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CovidDataDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.CovidData;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCity;
import bo.ucb.edu.vic19.statistics.absoluteIncrease.AbsoluteIncreaseMethod;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalMunicipality;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataMunicipality;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataMunicipality;
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
public class MunicipalityBl {
    private MunicipalityDao municipalityDao;
    private CovidDataDao covidDataDao;

    @Autowired
    public MunicipalityBl(MunicipalityDao municipalityDao,CovidDataDao covidDataDao){
        this.municipalityDao = municipalityDao;
        this.covidDataDao = covidDataDao;
    }

    public Integer getMunicipalityIdWithName(List<LocationResponse> municipalities,String name){
        Integer municipalityId=null;
        for(LocationResponse c:municipalities){
            if(c.getLocationName().equalsIgnoreCase(name)){
                municipalityId=c.getIdLocation();
                break;
            }
        }
        return municipalityId;
    }
    public void saveDataCSV(MultipartFile file, Transaction transaction, boolean replace) {
        try {
            List<CovidDataRequest> csvToList = CovidDataCSVUtil.csvToDataCsvRequest(file.getInputStream());
            List<CovidData> covidDataList= new ArrayList();
            List<MunicipalityCovidData> municipalityCovidDataList=new ArrayList<>();
            List<LocationResponse> municipalities=municipalityDao.municipalities();
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
        catch (IOException | ParseException e){
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

    public CovidDataRequestMedia mediaCovidDataMunicipalityAllInfo(int municipalityId, String dateCovid) {
        MediaCovidDataMunicipality mediaCovidDataMunicipality = new MediaCovidDataMunicipality(municipalityDao);
        return  mediaCovidDataMunicipality.mediaCovidDataMunicipalityAllInfo(municipalityId, dateCovid);
    }

    public CovidDataRequestVariance varianceCovidDataMunAllInfo(int munId, String dateCovid) {
        VarianceCovidDataMunicipality varianceCovidDataMun = new VarianceCovidDataMunicipality(municipalityDao);
        return varianceCovidDataMun.varianceCovidDataMunAllInfo(munId, dateCovid);
    }

    public CovidDataRequestConfidenceInterval confidenceIntervalCovidDataMunAllInfo(int munId, String dateCovid) {
        ConfidenceIntervalMunicipality confidenceIntervalMun = new ConfidenceIntervalMunicipality(municipalityDao);
        return confidenceIntervalMun.condifenceIntervalMunicipality(munId, dateCovid);
    }

    public CovidDataRequestLeastSquares leastSquaresCovidDataCityAllInfo(int munId, String forecastDate, String dateCovid){
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(null, null, municipalityDao,forecastDate, this.getClass().getSimpleName(),munId, dateCovid);
        return leastSquaresMethod.assignCovidDataAccordingToBlName();
    }

    public CovidDataRequestAbsoluteIncrease absoluteIncreaseCovidDataMunAllInfo(int munId, String forecastDate){
        AbsoluteIncreaseMethod absoluteIncreaseMethod = new AbsoluteIncreaseMethod(null, null, municipalityDao, this.getClass().getSimpleName(),munId, forecastDate);
        return absoluteIncreaseMethod.assignCovidDataAccordingToBlName();
    }
}
