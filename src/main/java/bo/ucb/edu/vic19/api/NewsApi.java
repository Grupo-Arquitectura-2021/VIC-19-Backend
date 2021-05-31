package bo.ucb.edu.vic19.api;
import bo.ucb.edu.vic19.bl.NewsBl;
import bo.ucb.edu.vic19.bl.TransactionBl;
import bo.ucb.edu.vic19.dto.NewsDataRequest;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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

    // El api donde se agrega una noticia
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public News createNews(@RequestBody News news, HttpServletRequest request){
        Transaction transaction = TransactionUtil.createTransaction(request);
        newsBl.addNews(news,transaction);
        return news;
    }


    // El api donde se muestra en una lista las noticias
    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsDataRequest getNews(@RequestParam Integer n, @RequestParam Integer i, @RequestParam(required = false) String search){
        return newsBl.getNews(n,i,search);
    }


    // El api donde se elimina una noticia
    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteNews(@RequestParam Integer newsId) {
        newsBl.newsDelete(newsId);
    }



    // El api donde se actualizar uma noticia
    @RequestMapping( method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public News updateNews(@RequestBody News news, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        return newsBl.updateNews(news,transaction);
    }

}
