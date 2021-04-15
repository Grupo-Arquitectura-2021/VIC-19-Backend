package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.CovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CovidDataCSVDao {
    public void insertData(CovidData covidData);
    public Integer getCovidDataId();
}
