package bo.ucb.edu.vic19.statistics.media;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;

import java.util.List;

public class MediaCovidDataCity {

    CityDao cityDao;

    public MediaCovidDataCity(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public CovidDataRequestMedia mediaCovidDataCityAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataAllInfo(cityId, dateCovid);
        int size=covidDataListCityAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;
        for(int i=0; i<size; i++){
            if(covidDataListCityAllInfo.get(i).getVaccinated() >= 0){
                sumVac+=covidDataListCityAllInfo.get(i).getVaccinated();
            }

            if(covidDataListCityAllInfo.get(i).getRecuperated() >= 0){
                sumRec+=covidDataListCityAllInfo.get(i).getRecuperated();
            }

            if(covidDataListCityAllInfo.get(i).getConfirmedCases() >= 0){
                sumConf+=covidDataListCityAllInfo.get(i).getConfirmedCases();
            }

            if(covidDataListCityAllInfo.get(i).getDeathCases() >= 0){
                sumDeath+=covidDataListCityAllInfo.get(i).getDeathCases();
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
        covidDataRequestMedia.setNameLocationCovid(cityDao.cityName(cityId));

        return covidDataRequestMedia;
    }
}
