package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalCity;
import bo.ucb.edu.vic19.statistics.confidenceInterval.ConfidenceIntervalMunicipality;
import bo.ucb.edu.vic19.statistics.leastSquaresMethod.LeastSquaresMethod;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataMunicipality;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCity;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataMunicipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityBl {
    private MunicipalityDao municipalityDao;

    @Autowired
    public MunicipalityBl(MunicipalityDao municipalityDao){
        this.municipalityDao = municipalityDao;
    }

    public List<LocationResponse> getMunicipalities(Integer cityId){
        return municipalityDao.municipalities(cityId);
    }


    public CovidDataRequest covidDataMunicipality(Integer municipalityId, String dateCovid){
        return municipalityDao.covidDataMunicipality(municipalityId, dateCovid);
    }

    public List<CovidDataRequest> covidDataListMunicipality(String dateCovid){
        List<CovidDataRequest> covidDataListMunicipality=municipalityDao.covidDataListMunicipality(dateCovid);
        return covidDataListMunicipality;
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
}
