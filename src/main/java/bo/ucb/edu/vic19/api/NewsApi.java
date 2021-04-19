package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CountryBl;
import bo.ucb.edu.vic19.bl.NewsBl;
import bo.ucb.edu.vic19.bl.TransactionBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    @RequestMapping(value = "/addnews",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public News createNews(@RequestBody NewsResponse news, HttpServletRequest request){
        Transaction transaction = TransactionUtil.createTransaction(request);
        transactionBl.createTransaction(transaction);
        //LOGGER.error(transaction.getTxId().toString());
        News news2=newsBl.addnews(news,transaction);
        return news2;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NewsResponse> getNews(){
        return newsBl.getNews();
    }

    @RequestMapping(value = "/delete/{idNews}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void productDelete(@PathVariable("idNews") Integer idNews, HttpServletRequest request) {
        Transaction transaction = TransactionUtil.createTransaction(request);
        transactionBl.createTransaction(transaction);
        newsBl.newsDelete(idNews);
    }

}
