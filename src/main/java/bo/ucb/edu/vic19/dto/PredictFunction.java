package bo.ucb.edu.vic19.dto;

abstract public class PredictFunction {
    private String function;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "PredictFunction{" +
                "function='" + function + '\'' +
                '}';
    }
}
