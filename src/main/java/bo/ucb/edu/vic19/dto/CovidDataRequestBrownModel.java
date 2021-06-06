package bo.ucb.edu.vic19.dto;

public class CovidDataRequestBrownModel {
    private String nameLocationCovid;
    private String dateLocationCovid;
    private float DAM;
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

    public float getDAM() {
        return DAM;
    }

    public void setDAM(float DAM) {
        this.DAM = DAM;
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
        return "CovidDataRequestBrownModel{" +
                "nameLocationCovid='" + nameLocationCovid + '\'' +
                ", dateLocationCovid='" + dateLocationCovid + '\'' +
                ", DAM=" + DAM +
                ", deathForecast=" + deathForecast +
                ", confForecast=" + confForecast +
                ", vacForecast=" + vacForecast +
                ", recForecast=" + recForecast +
                '}';
    }
}
