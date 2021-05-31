package bo.ucb.edu.vic19.dto;

public class Statistics {
    double media;
    double variance;
    double [] confidenceInterval;

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double[] getConfidenceInterval() {
        return confidenceInterval;
    }

    public void setConfidenceInterval(double[] confidenceInterval) {
        this.confidenceInterval = confidenceInterval;
    }

    public Statistics(double media, double variance, double [] confidenceInterval) {
        this.media = media;
        this.variance = variance;
        this.confidenceInterval = confidenceInterval;
    }

    @Override
    public String toString() {
        return "CovidDataStatistics{" +
                "media=" + media +
                ", variance=" + variance +
                ", confidenceInterval=" + confidenceInterval +
                '}';
    }
}
