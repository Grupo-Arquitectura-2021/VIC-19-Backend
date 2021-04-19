package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.dto.UserResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public Integer addUser(User user);

    public UserResponse getUser(String email, String password);

    public void deleteUser(Integer idUser);

}
