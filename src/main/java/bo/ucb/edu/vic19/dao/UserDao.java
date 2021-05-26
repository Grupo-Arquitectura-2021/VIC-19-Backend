package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.dto.UserResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public void addUser(User user);
    public List<UserResponse> getUsers(Integer i,Integer n,String search);
    public Integer getTotalUser(String search);
    public void deleteUser(User user);
    public void updateUser(User user);

}
