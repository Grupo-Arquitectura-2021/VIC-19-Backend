package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthDao {

    public User findAdminByEmail(String email);

}
