package bo.ucb.edu.vic19.statistics.confidenceInterval;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestConfidenceInterval;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.model.Country;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConfidenceIntervalCountry {
    CountryDao countryDao;

    @Autowired
    public ConfidenceIntervalCountry(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public CovidDataRequestConfidenceInterval condifenceIntervalCountry(int countryId, String dateCovid){
        VarianceCovidDataCountry varianceCovidDataCountry = new VarianceCovidDataCountry(countryDao);
        MediaCovidDataCountry mediaCovidDataCountry = new MediaCovidDataCountry(countryDao);
        CovidDataRequestVariance covidDataRequestVariance = new CovidDataRequestVariance();
        covidDataRequestVariance = varianceCovidDataCountry.varianceCovidDataCountryAllInfo(countryId, dateCovid);
        CovidDataRequestMedia covidDataRequestMedia = new CovidDataRequestMedia();
        covidDataRequestMedia = mediaCovidDataCountry.mediaCovidDataCountryAllInfo(countryId, dateCovid);

        List<CovidDataRequest> covidDataListCountryAllInfo=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
        int size=covidDataListCountryAllInfo.size();
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
        covidDataRequestConfidenceInterval.setNameLocationCovid(countryDao.countryName(countryId));

        return covidDataRequestConfidenceInterval;

    }
}

