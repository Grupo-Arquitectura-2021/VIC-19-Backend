package bo.ucb.edu.vic19.dto;

import java.util.List;

public class NewsDataRequest {
    private List<NewsResponse> news;
    private Integer total;

    public NewsDataRequest() {
    }

    public List<NewsResponse> getNews() {
        return news;
    }

    public void setNews(List<NewsResponse> news) {
        this.news = news;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NewsDataRequest{" +
                "news=" + news +
                ", total=" + total +
                '}';
    }
}
