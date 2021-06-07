package bo.ucb.edu.vic19.dto;

public class LogisticVariables {
    private double b;
    private double r;
    private double k;

    public LogisticVariables(double b, double r, double k) {
        this.b = b;
        this.r = r;
        this.k = k;
    }

    public LogisticVariables() {
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "LogisticVariables{" +
                "b=" + b +
                ", r=" + r +
                ", k=" + k +
                '}';
    }
}
