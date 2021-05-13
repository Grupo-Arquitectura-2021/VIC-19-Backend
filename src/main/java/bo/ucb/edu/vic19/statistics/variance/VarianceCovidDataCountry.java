package bo.ucb.edu.vic19.statistics.variance;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VarianceCovidDataCountry {
    CountryDao countryDao;

    public VarianceCovidDataCountry(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public CovidDataRequestVariance varianceCovidDataCountryAllInfo (int countryId, String dateCovid){
        MediaCovidDataCountry mediaCovidDataCountry = new MediaCovidDataCountry(countryDao);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataCountry.mediaCovidDataCountryAllInfo(countryId, dateCovid);

        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
        int size=covidDataListCountryAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float varianceVac=0, varianceRec=0, varianceConf=0, varianceDeath=0;
        for(int i=0; i<size; i++){
            if(covidDataListCountryAllInfo.get(i).getVaccinated() >= 0){
                float vac = covidDataListCountryAllInfo.get(i).getVaccinated();
                sumVac+= Math.pow((vac-covidDataRequestMedia.getVaccinated()),2);
            }

            if(covidDataListCountryAllInfo.get(i).getRecuperated() >= 0){
                float rec = covidDataListCountryAllInfo.get(i).getRecuperated();
                sumRec+=Math.pow((rec-covidDataRequestMedia.getRecuperated()),2);
            }

            if(covidDataListCountryAllInfo.get(i).getConfirmedCases() >= 0){
                float conf = covidDataListCountryAllInfo.get(i).getConfirmedCases();
                System.out.println(conf);
                sumConf+=Math.pow((conf-covidDataRequestMedia.getConfirmedCases()),2);
            }

            if(covidDataListCountryAllInfo.get(i).getDeathCases() >= 0){
                float death = covidDataListCountryAllInfo.get(i).getDeathCases();
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
        covidDataRequestVariance.setNameLocationCovid(countryDao.countryName(countryId));

        return covidDataRequestVariance;
    }

}
