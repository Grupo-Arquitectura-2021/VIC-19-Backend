package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.model.MunicipalityCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MunicipalityCovidDataDao {
    public void insertMunicipalityCovidData(MunicipalityCovidData municipalityCovidData);
}
