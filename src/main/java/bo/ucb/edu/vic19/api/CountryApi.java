package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CountryBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestMedia;
import bo.ucb.edu.vic19.dto.LocationResponse;
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
    public java.util.List<LocationResponse> getCountries(){
        return countryBl.getCountries();
    }
    @GetMapping(path = "/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataCountry(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){

        CovidDataRequest covidDataCountry=countryBl.covidDataCountry(Integer.parseInt(countryId),dateCovid);
        return covidDataCountry;
    }

    @GetMapping(path = "/allInfo/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataCountryAllInfo(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.covidDataCountryAllInfo(Integer.parseInt(countryId),dateCovid);
    }

    @GetMapping(path = "/mediaAllInfo/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestMedia covidDataCountryMediaAllInfo(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.mediaCovidDataCountryAllInfo(Integer.parseInt(countryId),dateCovid);
    }
}
