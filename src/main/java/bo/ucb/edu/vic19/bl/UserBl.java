package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.NewsDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.dao.UserDao;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.dto.UserDataRequest;
import bo.ucb.edu.vic19.dto.UserResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBl {
    private UserDao userDao;
    private TransactionDao transactionDao;

    @Autowired
    public UserBl(UserDao userDao,TransactionDao transactionDao){
        this.userDao = userDao;
        this.transactionDao=transactionDao;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponse addUser(UserResponse user,Transaction transaction){
        User userAdd=new User();
        userAdd.setUserName(user.getUserName());
        userAdd.setLastName(user.getLastName());
        userAdd.setEmail(user.getEmail());
        userAdd.setPassword(passwordEncoder.encode(user.getPassword()));
        userAdd.setTransaction(transaction);
        userDao.addUser(userAdd);
        Integer userId = transactionDao.getLastInsertId();
        user.setIdUser(userId);
        return user;
    }

    public UserResponse updateUser(UserResponse user,Transaction transaction){
        User userAdd=new User();
        userAdd.setIdUser(user.getIdUser());
        userAdd.setUserName(user.getUserName());
        userAdd.setLastName(user.getLastName());
        userAdd.setEmail(user.getEmail());
        if(user.getPassword()!=null&&user.getPassword()!=""&&user.getPassword().length()>6){
            userAdd.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else{
            userAdd.setPassword(null);
        }
        userAdd.setTransaction(transaction);
        userDao.updateUser(userAdd);
        return user;
    }
    public UserDataRequest getUsers(Integer i, Integer n, String search){
        UserDataRequest userDataRequest=new UserDataRequest();
        userDataRequest.setUsers(userDao.getUsers(i,n,search));
        userDataRequest.setTotal(userDao.getTotalUser(search));
        return userDataRequest;
    }

    public void userDelete(Integer idUser,Transaction transaction){
        User userAdd=new User();
        userAdd.setIdUser(idUser);
        userAdd.setTransaction(transaction);
        userDao.deleteUser(userAdd);
    }

}
