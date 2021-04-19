package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.model.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsDao {
    public Integer addNews(News news);

    public List<NewsResponse> getNews();

    public void deleteNews(Integer idNews);

}
