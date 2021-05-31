package bo.ucb.edu.vic19.dto;

public class CovidDataRequestIncreaseMethod {
    private String nameLocationCovid;
    private String dateLocationCovid;
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
        return "CovidDataRequestAbsoluteIncrease{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", deathForecast=" + deathForecast +
                ", confForecast=" + confForecast +
                ", vacForecast=" + vacForecast +
                ", recForecast=" + recForecast +
                '}';
    }
}
