package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;

import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityBl {
    private CityDao cityDao;

    @Autowired
    public CityBl(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public List<LocationResponse> getCities(){
        return cityDao.cities();
    }

    public CovidDataRequest covidDataCity(Integer cityId, String dateCovid){
        CovidDataRequest covidDataCity=cityDao.covidDataCity(cityId, dateCovid);
        return covidDataCity;
    }

    public List<CovidDataRequest> covidDataListCity(String dateCovid){
        List<CovidDataRequest> covidDataListCity=cityDao.covidDataListCity(dateCovid);
        return covidDataListCity;
    }

    public List<CovidDataRequest> covidDataListCityMunicipality(String nameCity,String dateCovid){
        List<CovidDataRequest> covidDataListCityMunicipality=cityDao.covidDataListCityMunicipality(nameCity, dateCovid);
        return covidDataListCityMunicipality;
    }

    public List<CovidDataRequest> covidDataCityAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataListCityAllInfo(cityId, dateCovid);
        return covidDataListCityAllInfo;
    }

    public CovidDataRequestMedia mediaCovidDataCityAllInfo(int cityId, String dateCovid) {
        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataListCityAllInfo(cityId, dateCovid);
        int size=covidDataListCityAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float mediaVac=0, mediaRec=0, mediaConf=0, mediaDeath=0;
        for(int i=0; i<size; i++){
            sumVac+=covidDataListCityAllInfo.get(i).getVaccinated();
            sumRec+=covidDataListCityAllInfo.get(i).getRecuperated();
            sumConf+=covidDataListCityAllInfo.get(i).getConfirmedCases();
            sumDeath+=covidDataListCityAllInfo.get(i).getDeathCases();
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
