package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataMunicipality;
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
}
