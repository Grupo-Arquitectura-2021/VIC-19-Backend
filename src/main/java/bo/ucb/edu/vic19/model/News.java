package bo.ucb.edu.vic19.model;
import java.util.Date;
public class News {
    private Integer idNews;
    private String title;
    private String content;
    private Date dateNews;
    private String urlImage;
    private String source;
    private Integer status;
    private Transaction transaction;

    public News() {
        transaction = new Transaction();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "News{" +
                "idNews=" + idNews +'\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime='" + dateNews + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}

