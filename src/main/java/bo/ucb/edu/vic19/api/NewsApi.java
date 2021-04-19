package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CountryBl;
import bo.ucb.edu.vic19.bl.NewsBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsApi {
    private NewsBl newsBl;

    @Autowired
    public NewsApi(NewsBl newsBl){
        this.newsBl= newsBl;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NewsResponse> getNews(){
        return newsBl.getNews();
    }


}
