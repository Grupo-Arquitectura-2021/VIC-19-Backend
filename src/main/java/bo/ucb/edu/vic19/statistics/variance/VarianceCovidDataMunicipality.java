package bo.ucb.edu.vic19.statistics.variance;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataMunicipality;

import java.util.List;

public class VarianceCovidDataMunicipality {
    MunicipalityDao municipalityDao;

    public VarianceCovidDataMunicipality(MunicipalityDao municipalityDao){
        this.municipalityDao = municipalityDao;
    }

    public CovidDataRequestVariance varianceCovidDataMunAllInfo (int munId, String dateCovid){
        MediaCovidDataMunicipality mediaCovidDataMunicipality = new MediaCovidDataMunicipality(municipalityDao);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataMunicipality.mediaCovidDataMunicipalityAllInfo(munId, dateCovid);

        List<CovidDataRequest> covidDataListMunicipalityAllInfo=municipalityDao.covidDataListMunAllInfo(munId, dateCovid);
        int size=covidDataListMunicipalityAllInfo.size(), sumVac=0,sumRec=0,sumConf=0, sumDeath=0;
        float varianceVac=0, varianceRec=0, varianceConf=0, varianceDeath=0;
        for(int i=0; i<size; i++){
            if(covidDataListMunicipalityAllInfo.get(i).getVaccinated() >= 0){
                float vac = covidDataListMunicipalityAllInfo.get(i).getVaccinated();
                sumVac+= Math.pow((vac-covidDataRequestMedia.getVaccinated()),2);
            }

            if(covidDataListMunicipalityAllInfo.get(i).getRecuperated() >= 0){
                float rec = covidDataListMunicipalityAllInfo.get(i).getRecuperated();
                sumRec+=Math.pow((rec-covidDataRequestMedia.getRecuperated()),2);
            }

            if(covidDataListMunicipalityAllInfo.get(i).getConfirmedCases() >= 0){
                float conf = covidDataListMunicipalityAllInfo.get(i).getConfirmedCases();
                System.out.println(conf);
                sumConf+=Math.pow((conf-covidDataRequestMedia.getConfirmedCases()),2);
            }

            if(covidDataListMunicipalityAllInfo.get(i).getDeathCases() >= 0){
                float death = covidDataListMunicipalityAllInfo.get(i).getDeathCases();
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
        covidDataRequestVariance.setNameLocationCovid(municipalityDao.municipalityName(munId));

        return covidDataRequestVariance;
    }
}