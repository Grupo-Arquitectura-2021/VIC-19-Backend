package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CityBl;
import bo.ucb.edu.vic19.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
/*    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CitySimpleResponse> getCities(){
        return cityBl.getCities();
    }*/

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getCitiesLocation(){
        return cityBl.getCitiesLocation();
    }

    @GetMapping(path = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataCity(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date,@PathVariable String cityId){
        return cityBl.covidDataCity(Integer.parseInt(cityId),date);
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCity(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        List<CovidDataRequest> covidDataListCity=cityBl.covidDataListCity(date);
        return covidDataListCity;
    }

/*    @GetMapping(path = "/name/{nameCity}/municipality/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCityMunicipality(@PathVariable String nameCity, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        List<CovidDataRequest> covidDataListCityMunicipality=cityBl.covidDataListCityMunicipality(nameCity,dateCovid);
        return covidDataListCityMunicipality;
    }*/

/*    @GetMapping(path = "/{cityId}/municipality/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCityByIdMunicipality(@PathVariable Integer cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        List<CovidDataRequest> covidDataListCityByIdMunicipality=cityBl.covidDataListCityByIdMunicipality(cityId,dateCovid);
        return covidDataListCityByIdMunicipality;
    }*/

    @GetMapping(path = "/allInfo/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataCityAllInfo(@PathVariable Integer cityId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        return cityBl.covidDataCityAllInfo(cityId,date);
    }

    @GetMapping(path = "/mediaAllInfo/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestMedia covidDataCountryMediaAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.mediaCovidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

    @GetMapping(path = "/varianceAllInfo/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestVariance covidDataCityVarianceAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.varianceCovidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

    @GetMapping(path = "/confidenceIntervalAllInfo/{cityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestConfidenceInterval covidDataCountryConfidenceIntervalAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.confidenceIntervalCovidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

}
