package bo.ucb.edu.vic19.statistics.confidenceInterval;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestConfidenceInterval;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCity;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCity;

import java.util.List;

public class ConfidenceIntervalCity {
    CityDao cityDao;

    public ConfidenceIntervalCity(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public CovidDataRequestConfidenceInterval condifenceIntervalCity(int cityId, String dateCovid){
        VarianceCovidDataCity varianceCovidDataCity = new VarianceCovidDataCity(cityDao);
        MediaCovidDataCity mediaCovidDataCity = new MediaCovidDataCity(cityDao);
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidDataCity.varianceCovidDataCityAllInfo(cityId, dateCovid);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataCity.mediaCovidDataCityAllInfo(cityId, dateCovid);

        List<CovidDataRequest> covidDataListCityAllInfo=cityDao.covidDataListCityAllInfo(cityId, dateCovid);
        int size=covidDataListCityAllInfo.size();
        float standardErrorRec=0, standardErrorVac=0, standardErrorConf=0, standardDeath=0;
        float errorRangeRec=0, errorRangeVac=0, errorRangeConf=0, errorRangeDeath=0;
        float ciRecUp=0, ciRecLow=0, ciVacUp=0, ciVacLow=0, ciConfUp=0, ciConfLow=0, ciDeathUp=0, ciDeathLow=0;
        float z = 2.05f;

        standardErrorConf = (float) (covidDataRequestVariance.getConfirmedCases()/(Math.sqrt(size)));
        standardErrorRec = (float) (covidDataRequestVariance.getRecuperated()/(Math.sqrt(size)));
        standardErrorVac = (float) (covidDataRequestVariance.getVaccinated()/(Math.sqrt(size)));
        standardDeath = (float) (covidDataRequestVariance.getDeathCases()/(Math.sqrt(size)));

        errorRangeConf = z * standardErrorConf;
        errorRangeRec = z * standardErrorRec;
        errorRangeVac = z * standardErrorVac;
        errorRangeDeath = z * standardDeath;

        ciConfUp = covidDataRequestMedia.getConfirmedCases()+errorRangeConf;
        ciConfLow = covidDataRequestMedia.getConfirmedCases()-errorRangeConf;
        ciRecUp = covidDataRequestMedia.getRecuperated()+errorRangeRec;
        ciRecLow = covidDataRequestMedia.getRecuperated()-errorRangeRec;
        ciVacUp = covidDataRequestMedia.getVaccinated()+errorRangeVac;
        ciVacLow = covidDataRequestMedia.getVaccinated()-errorRangeVac;
        ciDeathUp = covidDataRequestMedia.getDeathCases()+errorRangeDeath;
        ciDeathLow = covidDataRequestMedia.getDeathCases()-errorRangeDeath;

        CovidDataRequestConfidenceInterval covidDataRequestConfidenceInterval = new CovidDataRequestConfidenceInterval();
        covidDataRequestConfidenceInterval.setConfirmedCasesUpperLimit(ciConfUp);
        covidDataRequestConfidenceInterval.setConfirmedCasesLowerLimit(ciConfLow);
        covidDataRequestConfidenceInterval.setRecuperatedUpperLimit(ciRecUp);
        covidDataRequestConfidenceInterval.setRecuperatedLowerLimit(ciRecLow);
        covidDataRequestConfidenceInterval.setVaccinatedUpperLimit(ciVacUp);
        covidDataRequestConfidenceInterval.setVaccinatedLowerLimit(ciVacLow);
        covidDataRequestConfidenceInterval.setDeathCasesUpperLimit(ciDeathUp);
        covidDataRequestConfidenceInterval.setDeathCasesLowerLimit(ciDeathLow);
        covidDataRequestConfidenceInterval.setPercentage(96);
        covidDataRequestConfidenceInterval.setDateLocationCovid(dateCovid);
        covidDataRequestConfidenceInterval.setNameLocationCovid(cityDao.cityName(cityId));

        return covidDataRequestConfidenceInterval;

    }
}
