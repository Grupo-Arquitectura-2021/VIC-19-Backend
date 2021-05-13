package bo.ucb.edu.vic19.statistics.media;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.model.City;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MediaCovidDataCountry {
    CountryDao countryDao;

    @Autowired
    public MediaCovidDataCountry(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public CovidDataRequestMedia mediaCovidDataCountryAllInfo(int countryId, String dateCovid) {
        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
        int size=covidDataListCountryAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;
        for(int i=0; i<size; i++){
            if(covidDataListCountryAllInfo.get(i).getVaccinated() >= 0){
                sumVac+=covidDataListCountryAllInfo.get(i).getVaccinated();
            }

            if(covidDataListCountryAllInfo.get(i).getRecuperated() >= 0){
                sumRec+=covidDataListCountryAllInfo.get(i).getRecuperated();
            }

            if(covidDataListCountryAllInfo.get(i).getConfirmedCases() >= 0){
                sumConf+=covidDataListCountryAllInfo.get(i).getConfirmedCases();
            }

            if(covidDataListCountryAllInfo.get(i).getDeathCases() >= 0){
                sumDeath+=covidDataListCountryAllInfo.get(i).getDeathCases();
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
        covidDataRequestMedia.setNameLocationCovid(countryDao.countryName(countryId));

        return covidDataRequestMedia;
    }
}
