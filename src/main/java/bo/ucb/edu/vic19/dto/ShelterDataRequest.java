package bo.ucb.edu.vic19.dto;

import java.util.List;

public class ShelterDataRequest {
    private List<ShelterRequest> shelters;
    private Integer total;

    public List<ShelterRequest> getShelters() {
        return shelters;
    }

    public void setShelters(List<ShelterRequest> shelters) {
        this.shelters = shelters;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ShelterDataRequest{" +
                "shelters=" + shelters +
                ", total=" + total +
                '}';
    }
}
