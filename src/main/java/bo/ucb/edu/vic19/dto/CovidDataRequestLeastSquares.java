package bo.ucb.edu.vic19.dto;

public class CovidDataRequestLeastSquares {
    private String nameLocationCovid;
    private String dateLocationCovid;
    private float deathpercetage;
    private float confPercentage;
    private float vacPercentage;
    private float recPercentage;
    private float deathForecast;
    private float confForecast;
    private float vacForecast;
    private float recForecast;

    public String getNameLocationCovid() {
        return nameLocationCovid;
    }

    public void setNameLocationCovid(String nameLocationCovid) {
        this.nameLocationCovid = nameLocationCovid;
    }

    public String getDateLocationCovid() {
        return dateLocationCovid;
    }

    public void setDateLocationCovid(String dateLocationCovid) {
        this.dateLocationCovid = dateLocationCovid;
    }

    public float getDeathpercetage() {
        return deathpercetage;
    }

    public void setDeathpercetage(float deathpercetage) {
        this.deathpercetage = deathpercetage;
    }

    public float getConfPercentage() {
        return confPercentage;
    }

    public void setConfPercentage(float confPercentage) {
        this.confPercentage = confPercentage;
    }

    public float getVacPercentage() {
        return vacPercentage;
    }

    public void setVacPercentage(float vacPercentage) {
        this.vacPercentage = vacPercentage;
    }

    public float getRecPercentage() {
        return recPercentage;
    }

    public void setRecPercentage(float recPercentage) {
        this.recPercentage = recPercentage;
    }

    public float getDeathForecast() {
        return deathForecast;
    }

    public void setDeathForecast(float deathForecast) {
        this.deathForecast = deathForecast;
    }

    public float getConfForecast() {
        return confForecast;
    }

    public void setConfForecast(float confForecast) {
        this.confForecast = confForecast;
    }

    public float getVacForecast() {
        return vacForecast;
    }

    public void setVacForecast(float vacForecast) {
        this.vacForecast = vacForecast;
    }

    public float getRecForecast() {
        return recForecast;
    }

    public void setRecForecast(float recForecast) {
        this.recForecast = recForecast;
    }

    @Override
    public String toString() {
        return "CovidDataRequestLeastSquares{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", deathpercetage=" + deathpercetage +
                ", confPercentage=" + confPercentage +
                ", vacPercentage=" + vacPercentage +
                ", recPercentage=" + recPercentage +
                ", deathForecast=" + deathForecast +
                ", confForecast=" + confForecast +
                ", vacForecast=" + vacForecast +
                ", recForecast=" + recForecast +
                '}';
    }
}
