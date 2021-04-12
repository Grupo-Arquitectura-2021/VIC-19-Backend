package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.model.CovidData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface CovidDataUpdateDao {
    public void insertData(CovidData covidData);
}
