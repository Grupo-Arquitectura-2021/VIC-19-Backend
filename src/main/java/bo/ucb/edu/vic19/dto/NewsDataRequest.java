package bo.ucb.edu.vic19.dto;

import java.util.List;

public class NewsDataRequest {
    private List<NewsResponse> newsList;
    private Integer total;

    public NewsDataRequest() {
    }

    public List<NewsResponse> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsResponse> newsList) {
        this.newsList = newsList;
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
                "newsList=" + newsList +
                ", total=" + total +
                '}';
    }
}
