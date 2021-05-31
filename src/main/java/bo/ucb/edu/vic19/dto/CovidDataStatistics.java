package bo.ucb.edu.vic19.dto;

public class CovidDataStatistics {
    private Statistics deathStatistics;
    private Statistics confirmedStatistics;
    private Statistics vaccinatedStatistics;
    private Statistics recuperatedStatistics;

    public Statistics getDeathStatistics() {
        return deathStatistics;
    }

    public void setDeathStatistics(Statistics deathStatistics) {
        this.deathStatistics = deathStatistics;
    }

    public Statistics getConfirmedStatistics() {
        return confirmedStatistics;
    }

    public void setConfirmedStatistics(Statistics confirmedStatistics) {
        this.confirmedStatistics = confirmedStatistics;
    }

    public Statistics getVaccinatedStatistics() {
        return vaccinatedStatistics;
    }

    public void setVaccinatedStatistics(Statistics vaccinatedStatistics) {
        this.vaccinatedStatistics = vaccinatedStatistics;
    }

    public Statistics getRecuperatedStatistics() {
        return recuperatedStatistics;
    }

    public void setRecuperatedStatistics(Statistics recuperatedStatistics) {
        this.recuperatedStatistics = recuperatedStatistics;
    }

    @Override
    public String toString() {
        return "CovidDataStatistics{" +
                "deathStatistics=" + deathStatistics +
                ", confirmedStatistics=" + confirmedStatistics +
                ", vaccinatedStatistics=" + vaccinatedStatistics +
                ", recuperatedStatistics=" + recuperatedStatistics +
                '}';
    }
}
