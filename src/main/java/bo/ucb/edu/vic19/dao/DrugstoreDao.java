package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Drugstore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugstoreDao {
    public void createDrugstore(Drugstore drugstore);
    public LocationResponse getDrugstoreLocation(Integer drugstoreId);
    List<LocationResponse> getDrugstores();
    List<LocationResponse> getDrugstoresByCity(Integer cityId);
    public void updateDrugstore(Drugstore drugstore);
    public void deleteDrugstore(Drugstore drugstore);
}
