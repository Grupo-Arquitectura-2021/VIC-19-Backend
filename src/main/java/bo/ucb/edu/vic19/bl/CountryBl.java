package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;

    @Autowired
    public CountryBl(CountryDao countryDao){
        this.countryDao = countryDao;
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

    public CovidDataRequestMedia mediaCovidDataCountryAllInfo(int countryId, String dateCovid) {
        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
        int size=covidDataListCountryAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;
        for(int i=0; i<size; i++){
            sumVac+=covidDataListCountryAllInfo.get(i).getVaccinated();
            sumRec+=covidDataListCountryAllInfo.get(i).getRecuperated();
            sumConf+=covidDataListCountryAllInfo.get(i).getConfirmedCases();
            sumDeath+=covidDataListCountryAllInfo.get(i).getDeathCases();
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
