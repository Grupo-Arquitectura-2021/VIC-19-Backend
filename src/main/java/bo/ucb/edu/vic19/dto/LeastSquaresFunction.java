package bo.ucb.edu.vic19.dto;

public class LeastSquaresFunction extends PredictFunction{
    private LeastSquaresVariables confirmedCases;
    private LeastSquaresVariables recuperated;
    private LeastSquaresVariables  deathCases;
    private LeastSquaresVariables vaccinated;

    public LeastSquaresFunction(LeastSquaresVariables recuperated,LeastSquaresVariables confirmedCases,  LeastSquaresVariables deathCases, LeastSquaresVariables vaccinated) {
        this.deathCases = deathCases;
        this.confirmedCases = confirmedCases;
        this.vaccinated = vaccinated;
        this.recuperated = recuperated;
    }

    public LeastSquaresFunction() {
    }

    public LeastSquaresVariables getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(LeastSquaresVariables deathCases) {
        this.deathCases = deathCases;
    }

    public LeastSquaresVariables getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(LeastSquaresVariables confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public LeastSquaresVariables getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(LeastSquaresVariables vaccinated) {
        this.vaccinated = vaccinated;
    }

    public LeastSquaresVariables getRecuperated() {
        return recuperated;
    }

    public void setRecuperated(LeastSquaresVariables recuperated) {
        this.recuperated = recuperated;
    }

    @Override
    public String toString() {
        return "LeastSquaresFunction{" +
                "deathCases=" + deathCases +
                ", confirmedCases=" + confirmedCases +
                ", vaccinated=" + vaccinated +
                ", recuperated=" + recuperated +
                '}';
    }
}
