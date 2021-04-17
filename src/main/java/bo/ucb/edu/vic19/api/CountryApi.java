package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CountryBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryApi {
    private CountryBl countryBl;

    @Autowired
    public CountryApi(CountryBl countryBl){
        this.countryBl = countryBl;
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationRequest> getCountries(){
        return countryBl.getCountries();
    }
    @GetMapping(path = "/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataCountry(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){

        List<CovidDataRequest> covidDataCountry=countryBl.covidDataCountry(Integer.parseInt(countryId),dateCovid);
        return covidDataCountry;
    }
}
