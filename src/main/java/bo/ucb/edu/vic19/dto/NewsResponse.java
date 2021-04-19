package bo.ucb.edu.vic19.dto;

import java.util.Date;

public class NewsResponse {
    private Integer idNews;
    private String title;
    private String content;
    private Date dateNews;
    private String urlImage;
    private String source;

    public NewsResponse(Integer idNews, String title,String content, Date dateNews, String urlImage,String source) {
        this.idNews = idNews;
        this.title = title;
        this.content = content;
        this.dateNews = dateNews;
        this.urlImage = urlImage;
        this.source = source;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "idNews='" + idNews + '\'' +
                ", title=" + title + '\'' +
                ", content=" + content + '\'' +
                ", dateNews=" + dateNews + '\'' +
                ", urlImage=" + urlImage + '\'' +
                ", source=" + source +
                '}';
    }
}
