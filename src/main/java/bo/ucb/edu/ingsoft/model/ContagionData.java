package bo.ucb.edu.ingsoft.model;

import com.fasterxml.jackson.databind.JsonNode;

public class ContagionData {
    private JsonNode dataMap;


    public ContagionData() {
    }

    public JsonNode getDataMap() {
        return dataMap;
    }

    public void setDataMap(JsonNode dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        return "ContagionData{" +
                "dataMap=" + dataMap +
                '}';
    }
}
