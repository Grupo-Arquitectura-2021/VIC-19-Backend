package bo.ucb.edu.vic19.dao;


import bo.ucb.edu.vic19.model.Municipality;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MunicipalityDao {
    public Integer getMunicipalityId(String municipalityName);
    public void insertMunicipalityData(Municipality municipality);
    public Integer getMunicipalityMaxId();
}
