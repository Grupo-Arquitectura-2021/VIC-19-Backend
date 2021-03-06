package bo.ucb.edu.vic19.dao;


import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.MunicipalitySimpleResponse;
import bo.ucb.edu.vic19.model.CountryCovidData;
import bo.ucb.edu.vic19.model.Municipality;
import bo.ucb.edu.vic19.model.MunicipalityCovidData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MunicipalityDao {
    public List<MunicipalitySimpleResponse> municipalitiesWithCities();
    public Integer getMunicipalityIdWithName(String municipalityDataName,Integer IdCity);
    public List<LocationResponse> municipalities(Integer cityId);
    public CovidDataRequest covidDataMunicipality(Integer municipalityId, String dateCovid);
    public List<CovidDataRequest> covidDataListMunicipalityByCityId(String dateCovid,Integer cityId);

    List<CovidDataRequest> covidDataListMunAllInfo(int muniId, String dateCovid);

    List<CovidDataRequest> covidDataListMunAllInfoDESC(int muniId, String dateCovid);

    String municipalityName(int municipalityId);

    List<CovidDataRequest> covidDataListMunAllInfoNoDate(int munId);

    List<CovidDataRequest> covidDataListMunAllInfoNoDateDESC(int munId);
    public void insertMultiMunicipality(List<MunicipalityCovidData> list);
}
