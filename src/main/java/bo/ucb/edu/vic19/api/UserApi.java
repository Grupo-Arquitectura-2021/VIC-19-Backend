package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.NewsBl;
import bo.ucb.edu.vic19.bl.TransactionBl;
import bo.ucb.edu.vic19.bl.UserBl;
import bo.ucb.edu.vic19.dto.LogInRequest;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.dto.UserResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.model.User;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserApi {
    private UserBl userBl;
    private TransactionBl transactionBl;

    @Autowired
    public UserApi(UserBl userBl, TransactionBl transactionBl){
        this.userBl= userBl;
        this.transactionBl=transactionBl;
    }
    @RequestMapping(value = "/adduser",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserResponse user, HttpServletRequest request){
        Transaction transaction = TransactionUtil.createTransaction(request);
        transactionBl.createTransaction(transaction);
        //LOGGER.error(transaction.getTxId().toString());
        User user2=userBl.addUser(user,transaction);
        return user2;
    }
    @RequestMapping(value = "login",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
        public UserResponse logIn(@RequestBody LogInRequest user, HttpServletRequest request){
        System.out.println(user);
        return new UserResponse(1,"ALvin","Poma","pomaalvin@gmail.com","");
    }


//    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<NewsResponse> getNews(){
//        return newsBl.getNews();
//    }
//
//    @RequestMapping(value = "/delete/{idNews}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void productDelete(@PathVariable("idNews") Integer idNews, HttpServletRequest request) {
////        Transaction transaction = TransactionUtil.createTransaction(request);
////        transactionBl.createTransaction(transaction);
//        newsBl.newsDelete(idNews);
//    }

}
