package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.NewsDao;
import bo.ucb.edu.vic19.dao.UserDao;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.dto.UserResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBl {
    private UserDao userDao;

    @Autowired
    public UserBl(UserDao userDao){
        this.userDao = userDao;
    }

    public User addUser(UserResponse user,Transaction transaction){
        User user2=new User();
        user2.setUserName(user.getUserName());
        user2.setLastName(user.getLastName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setTransaction(transaction);
        userDao.addUser(user2);
        return  user2;
    }


    public UserResponse getUser(String email, String password){
        UserResponse userResponse=userDao.getUser(email,password);
        return userResponse;
    }

    public void userDelete(Integer idUser){
        userDao.deleteUser(idUser);
    }

}
