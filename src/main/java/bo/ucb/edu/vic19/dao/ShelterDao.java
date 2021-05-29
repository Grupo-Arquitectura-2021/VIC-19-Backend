package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.ShelterRequest;
import bo.ucb.edu.vic19.model.Shelter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShelterDao {
    public List<LocationResponse> getShelterLocation(Integer shelterId);
    public List<LocationResponse> getShelterLocationsByCityId(Integer cityId);

    public void insertShelter(Shelter shelter);

    public void deleteShelter(Integer shelterId);

    public void updateShelter(Shelter shelter);

    List<LocationResponse> getShelterLocations();

    List<ShelterRequest> getShelterAllInfo(Integer n, Integer i, String search);

    Integer getTotalShelter();
}
