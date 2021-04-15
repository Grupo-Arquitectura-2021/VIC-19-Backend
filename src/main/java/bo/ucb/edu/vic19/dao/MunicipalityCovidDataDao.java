package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MunicipalityCovidDataDao {
    public void insertMunicipalityCovidData(MunicipalityCovidData municipalityCovidData);
}
