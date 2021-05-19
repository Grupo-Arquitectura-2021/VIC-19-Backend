package bo.ucb.edu.vic19.dto;

import java.util.List;

public class DrugstoreDataRequest {
    private List<DrugstoreRequest> drugstores;
    private Integer total;

    public DrugstoreDataRequest() {
    }

    public List<DrugstoreRequest> getDrugstores() {
        return drugstores;
    }

    public void setDrugstores(List<DrugstoreRequest> drugstores) {
        this.drugstores = drugstores;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "DrugstoreDataRequest{" +
                "drugstores=" + drugstores +
                ", total=" + total +
                '}';
    }
}
