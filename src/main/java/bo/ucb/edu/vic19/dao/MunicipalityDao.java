package bo.ucb.edu.vic19.dao;


import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Municipality;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MunicipalityDao {
    public Municipality getMunicipalityWithName(String municipalityDataName,Integer IdCity);
    public List<LocationResponse> municipalities(Integer cityId);
    public CovidDataRequest covidDataMunicipality(Integer municipalityId, String dateCovid);
    public List<CovidDataRequest> covidDataListMunicipality(String dateCovid);
}
