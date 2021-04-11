package bo.ucb.edu.ingsoft.dto;

import java.util.Date;

public class DatosVacuna {
    private String country;
    private String dateIng;
    private Integer amountData;

    public DatosVacuna() {
    }

    public DatosVacuna(String country, String dateIng, Integer amountData) {
        this.country = country;
        this.dateIng = dateIng;
        this.amountData = amountData;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateIng() {
        return dateIng;
    }

    public void setDateIng(String dateIng) {
        this.dateIng = dateIng;
    }

    public Integer getAmountData() {
        return amountData;
    }

    public void setAmountData(Integer amountData) {
        this.amountData = amountData;
    }

    @Override
    public String toString() {
        return "DatosVacuna{" +
                "country='" + country + '\'' +
                ", dateIng='" + dateIng + '\'' +
                ", amountData=" + amountData +
                '}';
    }
}
