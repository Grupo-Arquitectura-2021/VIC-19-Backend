package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Hospital;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HospitalDao {

    public List<LocationResponse> getHospitalLocation(Integer hospitalId);
    public List<LocationResponse> getHospitalLocationsByCityId(Integer cityId);

    public void insertHospital(Hospital hospital);

    public void deleteHospital(Hospital hospital);

    List<LocationResponse> getHospitalLocations();
}
