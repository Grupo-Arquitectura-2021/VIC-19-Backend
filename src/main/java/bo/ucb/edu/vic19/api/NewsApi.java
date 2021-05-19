package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.NewsBl;
import bo.ucb.edu.vic19.bl.TransactionBl;

import bo.ucb.edu.vic19.dto.NewsDataRequest;
import bo.ucb.edu.vic19.dto.NewsResponse;

import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsApi {
    private NewsBl newsBl;
    private TransactionBl transactionBl;

    @Autowired
    public NewsApi(NewsBl newsBl, TransactionBl transactionBl){
        this.newsBl= newsBl;
        this.transactionBl=transactionBl;
    }
    @RequestMapping(value = "/addNews",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public News createNews(@RequestBody News news, HttpServletRequest request){
        Transaction transaction = TransactionUtil.createTransaction(request);
        newsBl.addNews(news,transaction);
        return news;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsDataRequest getNews(@RequestParam Integer n, @RequestParam Integer i, @RequestParam(required = false) String search){
        return newsBl.getNews(n,i,search);
    }

    @RequestMapping(value = "/delete/{idNews}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void productDelete(@PathVariable("idNews") Integer idNews, HttpServletRequest request) {
//        Transaction transaction = TransactionUtil.createTransaction(request);
//        transactionBl.createTransaction(transaction);
        newsBl.newsDelete(idNews);
    }

    @RequestMapping(path = "/updateNews", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public News updateNews(@RequestBody News news, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        return newsBl.updateNews(news,transaction);
    }

}
