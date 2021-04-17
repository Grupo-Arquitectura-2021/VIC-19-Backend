package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CityBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityApi {
    private CityBl cityBl;

    @Autowired
    public CityApi(CityBl cityBl){
        this.cityBl = cityBl;
    }
/*
    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationRequest> getCities(){
        return cityBl.getCities();
    }
*/
    @GetMapping(path="/{idCity}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCity(@PathVariable Integer idCity, @PathVariable Date dateCovid){
        List<CovidDataRequest> covidDataCity=cityBl.covidDataCity(idCity,dateCovid);
        return covidDataCity;
    }

}
