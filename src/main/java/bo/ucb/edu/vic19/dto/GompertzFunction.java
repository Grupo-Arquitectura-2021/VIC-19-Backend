package bo.ucb.edu.vic19.dto;

public class GompertzFunction extends PredictFunction{

    private GompertzVariables deathCases;
    private GompertzVariables confirmedCases;
    private GompertzVariables vaccinated;
    private GompertzVariables recuperated;

    public GompertzVariables getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(GompertzVariables deathCases) {
        this.deathCases = deathCases;
    }

    public GompertzVariables getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(GompertzVariables confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public GompertzVariables getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(GompertzVariables vaccinated) {
        this.vaccinated = vaccinated;
    }

    public GompertzVariables getRecuperated() {
        return recuperated;
    }

    public void setRecuperated(GompertzVariables recuperated) {
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
