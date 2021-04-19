package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.NewsDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.model.News;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsBl {
    private NewsDao newsDao;

    @Autowired
    public NewsBl(NewsDao newsDao){
        this.newsDao = newsDao;
    }

    public News addnews(NewsResponse news, Transaction transaction){
        News news2=new News();
        news2.setTitle(news.getTitle());
        news2.setContent(news.getContent());
        news2.setDateNews(news.getDateNews());
        news2.setUrlImage(news.getUrlImage());
        news2.setSource(news.getSource());
        news2.setStatus(1);
        news2.setTransaction(transaction);
        newsDao.addNews(news2);
        return news2;
    }

    public List<NewsResponse> getNews(){
        return newsDao.getNews();
    }

    public void newsDelete(Integer idNews){
        newsDao.deleteNews(idNews);
    }

}
