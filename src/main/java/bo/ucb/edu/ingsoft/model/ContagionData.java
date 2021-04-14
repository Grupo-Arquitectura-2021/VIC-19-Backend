package bo.ucb.edu.ingsoft.model;

import com.fasterxml.jackson.databind.JsonNode;

public class ContagionData {
    private JsonNode data_mapa;


    public ContagionData() {
    }

    public JsonNode getData_mapa() {
        return data_mapa;
    }

    public void setData_mapa(JsonNode data_mapa) {
        this.data_mapa = data_mapa;
    }

    @Override
    public String toString() {
        return "ContagionData{" +
                "data_mapa=" + data_mapa +
                '}';
    }
}
