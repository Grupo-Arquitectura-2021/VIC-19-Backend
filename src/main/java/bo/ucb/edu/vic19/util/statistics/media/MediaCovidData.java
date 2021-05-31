package bo.ucb.edu.vic19.util.statistics.media;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.model.City;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MediaCovidData {
    private List<CovidDataRequest> covidDataListCountryAllInfo;
    private float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;

    public List<CovidDataRequest> getCovidDataListCountryAllInfo() {
        return covidDataListCountryAllInfo;
    }

    public void setCovidDataListCountryAllInfo(List<CovidDataRequest> covidDataListCountryAllInfo) {
        this.covidDataListCountryAllInfo = covidDataListCountryAllInfo;
    }

    public float getMediaVac() {
        return mediaVac;
    }

    public void setMediaVac(float mediaVac) {
        this.mediaVac = mediaVac;
    }

    public float getMediaRec() {
        return mediaRec;
    }

    public void setMediaRec(float mediaRec) {
        this.mediaRec = mediaRec;
    }

    public float getMediaConf() {
        return mediaConf;
    }

    public void setMediaConf(float mediaConf) {
        this.mediaConf = mediaConf;
    }

    public float getMediaDeath() {
        return mediaDeath;
    }

    public void setMediaDeath(float mediaDeath) {
        this.mediaDeath = mediaDeath;
    }

    public MediaCovidData(List<CovidDataRequest> covidDataRequests){
        this.covidDataListCountryAllInfo=covidDataRequests;
    }

    public CovidDataRequestMedia mediaCovidDataCountryAllInfo(int countryId, String dateCovid, String name) {
        int size=covidDataListCountryAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
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
        covidDataRequestMedia.setNameLocationCovid(name);

        return covidDataRequestMedia;
    }
}
