package bo.ucb.edu.vic19.statistics.confidenceInterval;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestConfidenceInterval;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.CovidDataRequestVariance;

import java.util.List;

public class ConfidenceInterval {
    private float ciRecUp=0, ciRecLow=0, ciVacUp=0, ciVacLow=0, ciConfUp=0, ciConfLow=0, ciDeathUp=0, ciDeathLow=0;

    private List<CovidDataRequest> covidDataListCountryAllInfo;

    public ConfidenceInterval(List<CovidDataRequest> covidDataRequests){
        this.covidDataListCountryAllInfo=covidDataRequests;
    }

    public float getCiRecUp() {
        return ciRecUp;
    }

    public void setCiRecUp(float ciRecUp) {
        this.ciRecUp = ciRecUp;
    }

    public float getCiRecLow() {
        return ciRecLow;
    }

    public void setCiRecLow(float ciRecLow) {
        this.ciRecLow = ciRecLow;
    }

    public float getCiVacUp() {
        return ciVacUp;
    }

    public void setCiVacUp(float ciVacUp) {
        this.ciVacUp = ciVacUp;
    }

    public float getCiVacLow() {
        return ciVacLow;
    }

    public void setCiVacLow(float ciVacLow) {
        this.ciVacLow = ciVacLow;
    }

    public float getCiConfUp() {
        return ciConfUp;
    }

    public void setCiConfUp(float ciConfUp) {
        this.ciConfUp = ciConfUp;
    }

    public float getCiConfLow() {
        return ciConfLow;
    }

    public void setCiConfLow(float ciConfLow) {
        this.ciConfLow = ciConfLow;
    }

    public float getCiDeathUp() {
        return ciDeathUp;
    }

    public void setCiDeathUp(float ciDeathUp) {
        this.ciDeathUp = ciDeathUp;
    }

    public float getCiDeathLow() {
        return ciDeathLow;
    }

    public void setCiDeathLow(float ciDeathLow) {
        this.ciDeathLow = ciDeathLow;
    }

    public CovidDataRequestConfidenceInterval condifenceIntervalCountry(CovidDataRequestMedia covidDataRequestMedia, CovidDataRequestVariance covidDataRequestVariance){
        int size=covidDataListCountryAllInfo.size();
        float standardErrorRec=0, standardErrorVac=0, standardErrorConf=0, standardDeath=0;
        float errorRangeRec=0, errorRangeVac=0, errorRangeConf=0, errorRangeDeath=0;
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
        covidDataRequestConfidenceInterval.setDateLocationCovid(covidDataRequestMedia.getDateLocationCovid());
        covidDataRequestConfidenceInterval.setNameLocationCovid(covidDataRequestMedia.getNameLocationCovid());

        return covidDataRequestConfidenceInterval;

    }
}

