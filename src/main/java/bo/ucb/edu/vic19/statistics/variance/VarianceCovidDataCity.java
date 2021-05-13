package bo.ucb.edu.vic19.statistics.variance;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCity;

import java.util.List;

public class VarianceCovidDataCity {
    CityDao cityDao;

    public VarianceCovidDataCity(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public CovidDataRequestVariance varianceCovidDataCityAllInfo (int cityId, String dateCovid){
        MediaCovidDataCity mediaCovidDataCity = new MediaCovidDataCity(cityDao);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataCity.mediaCovidDataCityAllInfo(cityId, dateCovid);

        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataListCityAllInfo(cityId, dateCovid);
        int size=covidDataListCityAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float varianceVac=0, varianceRec=0, varianceConf=0, varianceDeath=0;
        for(int i=0; i<size; i++){
            if(covidDataListCityAllInfo.get(i).getVaccinated() >= 0){
                float vac = covidDataListCityAllInfo.get(i).getVaccinated();
                sumVac+= Math.pow((vac-covidDataRequestMedia.getVaccinated()),2);
            }

            if(covidDataListCityAllInfo.get(i).getRecuperated() >= 0){
                float rec = covidDataListCityAllInfo.get(i).getRecuperated();
                sumRec+=Math.pow((rec-covidDataRequestMedia.getRecuperated()),2);
            }

            if(covidDataListCityAllInfo.get(i).getConfirmedCases() >= 0){
                float conf = covidDataListCityAllInfo.get(i).getConfirmedCases();
                System.out.println(conf);
                sumConf+=Math.pow((conf-covidDataRequestMedia.getConfirmedCases()),2);
            }

            if(covidDataListCityAllInfo.get(i).getDeathCases() >= 0){
                float death = covidDataListCityAllInfo.get(i).getDeathCases();
                sumDeath+=Math.pow((death-covidDataRequestMedia.getDeathCases()),2);
            }
        }
        varianceConf = (float) Math.sqrt((float) sumConf/(size-1));
        varianceDeath = (float) Math.sqrt((float) sumDeath/(size-1));
        varianceRec = (float) Math.sqrt((float) sumRec/(size-1));
        varianceVac= (float) Math.sqrt((float) sumVac/(size-1));

        CovidDataRequestVariance covidDataRequestVariance = new CovidDataRequestVariance();
        covidDataRequestVariance.setConfirmedCases(varianceConf);
        covidDataRequestVariance.setDeathCases(varianceDeath);
        covidDataRequestVariance.setRecuperated(varianceRec);
        covidDataRequestVariance.setVaccinated(varianceVac);
        covidDataRequestVariance.setDateLocationCovid(dateCovid);
        covidDataRequestVariance.setNameLocationCovid(cityDao.cityName(cityId));

        return covidDataRequestVariance;
    }
}
