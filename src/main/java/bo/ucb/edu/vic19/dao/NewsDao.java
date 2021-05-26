package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.dto.NewsResponse;
import bo.ucb.edu.vic19.model.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsDao {
    public void addNews(News news);

    public List<NewsResponse> getNews(Integer n, Integer i, String search);
    public void updateNews(News news);
    public void deleteNews(Integer newsId);
    public Integer getTotalNews();
}
