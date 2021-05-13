package bo.ucb.edu.vic19.statistics.media;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MediaCovidDataMunicipality {
    MunicipalityDao municipalityDao;

    @Autowired
     public MediaCovidDataMunicipality(MunicipalityDao municipalityDao){
        this.municipalityDao = municipalityDao;
    }

    public CovidDataRequestMedia mediaCovidDataMunicipalityAllInfo(int municipalityId, String dateCovid) {
        List<CovidDataRequest> covidDataListMunicipalityAllInfo=municipalityDao.covidDataListMunAllInfo(municipalityId, dateCovid);
        int size=covidDataListMunicipalityAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;

        for(int i=0; i<size; i++){
            if(covidDataListMunicipalityAllInfo.get(i).getVaccinated() >= 0){
                sumVac+=covidDataListMunicipalityAllInfo.get(i).getVaccinated();
            }

            if(covidDataListMunicipalityAllInfo.get(i).getRecuperated() >= 0){
                sumRec+=covidDataListMunicipalityAllInfo.get(i).getRecuperated();
            }

            if(covidDataListMunicipalityAllInfo.get(i).getConfirmedCases() >= 0){
                sumConf+=covidDataListMunicipalityAllInfo.get(i).getConfirmedCases();
            }

            if(covidDataListMunicipalityAllInfo.get(i).getDeathCases() >= 0){
                sumDeath+=covidDataListMunicipalityAllInfo.get(i).getDeathCases();
            }
        }

        mediaConf = (float) sumConf/size;
        mediaDeath = (float) sumDeath/size;
        mediaRec = (float) sumRec/size;
        mediaVac= (float) sumVac/size;

        CovidDataRequestMedia covidDataRequestMedia = new CovidDataRequestMedia();
        covidDataRequestMedia.setConfirmedCases(mediaConf);
        covidDataRequestMedia.setDeathCases(mediaDeath);
        covidDataRequestMedia.setRecuperated(mediaRec);
        covidDataRequestMedia.setVaccinated(mediaVac);
        covidDataRequestMedia.setDateLocationCovid(dateCovid);
        covidDataRequestMedia.setNameLocationCovid(municipalityDao.municipalityName(municipalityId));

        return covidDataRequestMedia;
    }
}
