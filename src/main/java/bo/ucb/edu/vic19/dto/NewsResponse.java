package bo.ucb.edu.vic19.dto;

import java.util.Date;

public class NewsResponse {
    private Integer idNews;
    private String title;
    private String content;
    private Date dateNews;
    private String newsImages;
    private String newsUrl;

    public NewsResponse(Integer idNews, String title,String content, Date dateNews, String newsImages,String newsUrl) {
        this.idNews = idNews;
        this.title = title;
        this.content = content;
        this.dateNews = dateNews;
        this.newsImages = newsImages;
        this.newsUrl = newsUrl;
    }

    public NewsResponse() {
    }


    public Integer getIdNews() {
        return idNews;
    }

    public void setIdNews(Integer idNews) {
        this.idNews = idNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateNews() {
        return dateNews;
    }

    public void setDateNews(Date dateNews) {
        this.dateNews = dateNews;
    }

    public String getNewsImages() {
        return newsImages;
    }

    public void setNewsImages(String newsImages) {
        this.newsImages = newsImages;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "idNews=" + idNews +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateNews=" + dateNews +
                ", newsImages='" + newsImages + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }
}
