package bo.ucb.edu.vic19.model;

public class QuantitiesBrown {
    private float yt;
    private float at;
    private float atp;
    private float atpp;
    private float bt;
    private float ytp;
    private float et;

    public QuantitiesBrown(float yt) {
        this.yt = yt;
    }

    public float getYt() {
        return yt;
    }

    public void setYt(float yt) {
        this.yt = yt;
    }

    public float getAt() {
        return at;
    }

    public void setAt(float at) {
        this.at = at;
    }

    public float getAtp() {
        return atp;
    }

    public void setAtp(float atp) {
        this.atp = atp;
    }

    public float getAtpp() {
        return atpp;
    }

    public void setAtpp(float atpp) {
        this.atpp = atpp;
    }

    public float getBt() {
        return bt;
    }

    public void setBt(float bt) {
        this.bt = bt;
    }

    public float getYtp() {
        return ytp;
    }

    public void setYtp(float ytp) {
        this.ytp = ytp;
    }

    public float getEt() {
        return et;
    }

    public void setEt(float et) {
        this.et = et;
    }

    @Override
    public String toString() {
        return "QuantitiesBrown{" +
                "yt=" + yt +
                ", at=" + at +
                ", atp=" + atp +
                ", atpp=" + atpp +
                ", bt=" + bt +
                ", ytp=" + ytp +
                ", et=" + et +
                '}';
    }
}
