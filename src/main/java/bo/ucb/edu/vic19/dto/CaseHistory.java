package bo.ucb.edu.vic19.dto;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class CaseHistory {
    private String country;
    private ArrayList<String> province;
    private JSONObject timeline;

    public CaseHistory() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<String> province) {
        this.province = province;
    }

    public JSONObject getTimeline() {
        return timeline;
    }

    public void setTimeline(JSONObject timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString() {
        return "CaseHistory{" +
                "country='" + country + '\'' +
                ", province=" + province +
                ", timeline=" + timeline +
                '}';
    }
}
