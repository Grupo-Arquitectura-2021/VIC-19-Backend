package bo.ucb.edu.vic19.statistics.confidenceInterval;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestConfidenceInterval;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;
import bo.ucb.edu.vic19.model.Municipality;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataCountry;
import bo.ucb.edu.vic19.statistics.media.MediaCovidDataMunicipality;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataCountry;
import bo.ucb.edu.vic19.statistics.variance.VarianceCovidDataMunicipality;

import java.util.List;

public class ConfidenceIntervalMunicipality {
    MunicipalityDao municipalityDao;

    public ConfidenceIntervalMunicipality(MunicipalityDao municipalityDao){
        this.municipalityDao = municipalityDao;
    }

    public CovidDataRequestConfidenceInterval condifenceIntervalMunicipality(int munId, String dateCovid){
        VarianceCovidDataMunicipality varianceCovidDataMun = new VarianceCovidDataMunicipality(municipalityDao);
        MediaCovidDataMunicipality mediaCovidDataMunicipality= new MediaCovidDataMunicipality(municipalityDao);
        CovidDataRequestVariance covidDataRequestVariance;
        covidDataRequestVariance = varianceCovidDataMun.varianceCovidDataMunAllInfo(munId, dateCovid);
        CovidDataRequestMedia covidDataRequestMedia;
        covidDataRequestMedia = mediaCovidDataMunicipality.mediaCovidDataMunicipalityAllInfo(munId, dateCovid);

        List<CovidDataRequest> covidDataListMunAllInfo=municipalityDao.covidDataListMunAllInfo(munId, dateCovid);
        int size=covidDataListMunAllInfo.size();
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
        covidDataRequestConfidenceInterval.setNameLocationCovid(municipalityDao.municipalityName(munId));

        return covidDataRequestConfidenceInterval;

    }
}
