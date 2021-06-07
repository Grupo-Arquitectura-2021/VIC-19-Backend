package bo.ucb.edu.vic19.dto;

public class FunctionsRequest {
    private LogisticFunction logisticFunction;
    private LeastSquaresFunction leastSquaresFunction;

    public FunctionsRequest(LogisticFunction logisticFunction, LeastSquaresFunction leastSquaresFunction) {
        this.logisticFunction = logisticFunction;
        this.leastSquaresFunction = leastSquaresFunction;
    }

    public FunctionsRequest() {
    }

    public LogisticFunction getLogisticFunction() {
        return logisticFunction;
    }

    public void setLogisticFunction(LogisticFunction logisticFunction) {
        this.logisticFunction = logisticFunction;
    }

    public LeastSquaresFunction getLeastSquaresFunction() {
        return leastSquaresFunction;
    }

    public void setLeastSquaresFunction(LeastSquaresFunction leastSquaresFunction) {
        this.leastSquaresFunction = leastSquaresFunction;
    }

    @Override
    public String toString() {
        return "FunctionsRequest{" +
                "logisticFunction=" + logisticFunction +
                ", leastSquaresFunction=" + leastSquaresFunction +
                '}';
    }
}
