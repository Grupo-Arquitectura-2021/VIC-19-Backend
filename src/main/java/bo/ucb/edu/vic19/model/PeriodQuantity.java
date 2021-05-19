package bo.ucb.edu.vic19.model;

public class PeriodQuantity {
    private int period;
    private float quantity;

    public PeriodQuantity(int period, float quantity){
        this.period = period;
        this.quantity = quantity;
    }

    public float getPeriod() {
        return period;
    }

    public float getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "PeriodQuantity{" +
                "period=" + period +
                ", quantity=" + quantity +
                '}';
    }
}
