package bo.ucb.edu.vic19.dto;

public class LogisticFunction extends PredictFunction{

    private LogisticVariables deathCases;
    private LogisticVariables confirmedCases;
    private LogisticVariables vaccinated;
    private LogisticVariables recuperated;

    public LogisticVariables getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(LogisticVariables deathCases) {
        this.deathCases = deathCases;
    }

    public LogisticVariables getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(LogisticVariables confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public LogisticVariables getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(LogisticVariables vaccinated) {
        this.vaccinated = vaccinated;
    }

    public LogisticVariables getRecuperated() {
        return recuperated;
    }

    public void setRecuperated(LogisticVariables recuperated) {
        this.recuperated = recuperated;
    }

    @Override
    public String toString() {
        return "GompertzFunction{" +
                "deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", recuperated=" + recuperated +
                '}';
    }
}
