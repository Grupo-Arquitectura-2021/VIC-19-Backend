package bo.ucb.edu.ingsoft.model;

import org.json.simple.JSONObject;

public class Vaccines {
    private String country;
    private JSONObject timeline;

    public Vaccines() {

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
        return "Vaccines{" +
                "country='" + country + '\'' +
                ", timeline=" + timeline +
                '}';
    }
}
