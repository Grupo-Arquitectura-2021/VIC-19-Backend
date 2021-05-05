package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.LocationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HospitalDao {

    public List<LocationResponse> getHospitalLocation(Integer hospitalId);
    public List<LocationResponse> getHospitalLocationsByCityId(Integer cityId);
}
