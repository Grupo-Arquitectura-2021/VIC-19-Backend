package bo.ucb.edu.vic19.dto;

public class LeastSquaresVariables {
    double m;
    double b;

    public LeastSquaresVariables(double m, double b) {
        this.m = m;
        this.b = b;
    }

    public LeastSquaresVariables() {
    }
    @Override
    public String toString() {
        return "LeastSquaresVariables{" +
                "m=" + m +
                ", b=" + b +
                '}';
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
