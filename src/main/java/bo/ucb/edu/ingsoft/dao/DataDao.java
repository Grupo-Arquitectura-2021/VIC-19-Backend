package bo.ucb.edu.ingsoft.dao;

import bo.ucb.edu.ingsoft.model.CovidData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataDao {
    public void createNewData(List<CovidData> imagePublications);

}
