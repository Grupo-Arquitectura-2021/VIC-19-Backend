package bo.ucb.edu.ingsoft.dto;


import org.json.simple.JSONObject;

public class Vacunas {
    private String country;
    private JSONObject timeline;

    public Vacunas() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public JSONObject getTimeline() {
        return timeline;
    }

    public void setTimeline(JSONObject timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString() {
        return "Vacuna{" +
                "country='" + country + '\'' +
                ", timeline=" + timeline +
                '}';
    }
}