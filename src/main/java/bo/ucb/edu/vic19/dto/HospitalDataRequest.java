package bo.ucb.edu.vic19.dto;

import java.util.List;

public class HospitalDataRequest {
    private List<HospitalRequest> hospitals;
    private Integer total;

    public List<HospitalRequest> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<HospitalRequest> hospitals) {
        this.hospitals = hospitals;
    }

    public HospitalDataRequest() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "HospitalDataRequest{" +
                "hospitals=" + hospitals +
                ", total=" + total +
                '}';
    }
}
