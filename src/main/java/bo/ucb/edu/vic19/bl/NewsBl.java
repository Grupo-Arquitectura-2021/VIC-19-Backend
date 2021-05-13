package bo.ucb.edu.vic19.bl;


import bo.ucb.edu.vic19.dao.NewsDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsBl {
    private NewsDao newsDao;
    private TransactionDao transactionDao;

    @Autowired
    public NewsBl(NewsDao newsDao,TransactionDao transactionDao){
        this.newsDao = newsDao;
        this.transactionDao = transactionDao;
    }

    public News addNews(News news, Transaction transaction){
        news.setTransaction(transaction);
        newsDao.addNews(news);
        Integer newsId = transactionDao.getLastInsertId();
        news.setIdNews(newsId);
        return news;
    }

    public List<NewsResponse> getNews(){
        return newsDao.getNews();
    }

    public void newsDelete(Integer idNews){
        newsDao.deleteNews(idNews);
    }

    public News updateNews(News news, Transaction transaction){
        news.setTransaction(transaction);
        newsDao.updateNews(news);
        return news;
    }
}
