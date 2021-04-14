package bo.ucb.edu.ingsoft.dto;

import java.util.Date;

public class MunicipalityData {
    private String nameDep;
    private String nameMun;
    private Date dateCov;
    private Integer dataMun;

    public MunicipalityData(String nameDep, String nameMun, Date dateCov, Integer dataMun) {
        this.nameDep = nameDep;
        this.nameMun = nameMun;
        this.dateCov = dateCov;
        this.dataMun = dataMun;
    }

    public MunicipalityData() {
    }

    public String getNameDep() {
        return nameDep;
    }

    public void setNameDep(String nameDep) {
        this.nameDep = nameDep;
    }

    public String getNameMun() {
        return nameMun;
    }

    public void setNameMun(String nameMun) {
        this.nameMun = nameMun;
    }

    public Date getDateCov() {
        return dateCov;
    }

    public void setDateCov(Date dateCov) {
        this.dateCov = dateCov;
    }

    public Integer getDataMun() {
        return dataMun;
    }

    public void setDataMun(Integer dataMun) {
        this.dataMun = dataMun;
    }

    @Override
    public String toString() {
        return "MunicipalityData{" +
                "nameDep='" + nameDep + '\'' +
                ", nameMun='" + nameMun + '\'' +
                ", dateCov=" + dateCov +
                ", dataMun=" + dataMun +
                '}';
    }
}
