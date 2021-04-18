package bo.ucb.edu.vic19.dao;


import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Municipality;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MunicipalityDao {
    public Integer getMunicipalityId(String municipalityName);
    public void insertMunicipalityData(Municipality municipality);
    public Integer getMunicipalityMaxId(Integer cityId, String municipalityDataName);
    public List<CovidDataRequest> covidDataListMunicipality(String dateCovid);
    public List<LocationResponse> municipalities();
    public List<CovidDataRequest> covidDataMunicipality(Integer municipalityId, String dateCovid);
}
