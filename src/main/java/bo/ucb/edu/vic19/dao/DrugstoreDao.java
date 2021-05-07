package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.Drugstore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DrugstoreDao {
    public void createDrugstore(Drugstore drugstore);

}
