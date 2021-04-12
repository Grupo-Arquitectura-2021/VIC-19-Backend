package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.model.CovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CovidDataJsonDao {
    public void insertDataJson(CovidData covidData);
}
