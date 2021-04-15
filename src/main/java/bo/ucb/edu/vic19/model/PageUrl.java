package bo.ucb.edu.vic19.model;

public class PageUrl {
    private Integer idPageUrl;
    private String url;
    private Integer status;
    private Transaction transaction;

    public PageUrl(){
        transaction = new Transaction();
    }

    public Integer getIdPageUrl() {
        return idPageUrl;
    }

    public void setIdPageUrl(Integer idPageUrl) {
        this.idPageUrl = idPageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "PageUrl{" +
                "idPageUrl=" + idPageUrl +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}
