package bo.ucb.edu.ingsoft.dao;


import bo.ucb.edu.ingsoft.model.Municipality;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MunicipalityDao {
    public Integer getMunicipalityId(String municipalityName);
    public void insertMunicipalityData(Municipality municipality);
    public Integer getMunicipalityMaxId();
}
