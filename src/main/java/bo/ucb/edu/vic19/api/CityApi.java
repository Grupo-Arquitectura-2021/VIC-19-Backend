package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CityBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityApi {
    private CityBl cityBl;

    @Autowired
    public CityApi(CityBl cityBl){
        this.cityBl = cityBl;
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getCities(){
        return cityBl.getCities();
    }

    @GetMapping(path = "/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataCity(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.covidDataCity(Integer.parseInt(cityId),dateCovid);
    }

    @GetMapping(path = "/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCity(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        List<CovidDataRequest> covidDataListCity=cityBl.covidDataListCity(dateCovid);
        return covidDataListCity;
    }

    @GetMapping(path = "/{nameCity}/municipality/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCityMunicipality(@PathVariable String nameCity, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        List<CovidDataRequest> covidDataListCityMunicipality=cityBl.covidDataListCityMunicipality(nameCity,dateCovid);
        return covidDataListCityMunicipality;
    }

    @GetMapping(path = "/allInfo/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataCityAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.covidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

    @GetMapping(path = "/mediaAllInfo/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestMedia covidDataCountryMediaAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.mediaCovidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

}
