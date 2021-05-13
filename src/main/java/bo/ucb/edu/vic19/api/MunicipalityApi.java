package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.MunicipalityBl;
import bo.ucb.edu.vic19.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/municipality")
public class MunicipalityApi {
    private MunicipalityBl municipalityBl;

    @Autowired
    public MunicipalityApi(MunicipalityBl municipalityBl){
        this.municipalityBl = municipalityBl;
    }

    @RequestMapping(path = "/location/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getMunicipalities(@PathVariable Integer cityId){
        return municipalityBl.getMunicipalities(cityId);
    }

    @GetMapping(path = "/{municipalityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataMunicipality(@PathVariable String municipalityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.covidDataMunicipality(Integer.parseInt(municipalityId),dateCovid);
    }

    @GetMapping(path = "/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListMunicipality(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){

        List<CovidDataRequest> covidDataListMunicipality=municipalityBl.covidDataListMunicipality(dateCovid);
        return covidDataListMunicipality;
    }

    @GetMapping(path = "/allInfo/{muniId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataMuniAllInfo(@PathVariable String muniId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.covidDataMunAllInfo(Integer.parseInt(muniId),dateCovid);
    }

    @GetMapping(path = "/mediaAllInfo/{municipalityId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestMedia covidDataCountryMediaAllInfo(@PathVariable String municipalityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.mediaCovidDataMunicipalityAllInfo(Integer.parseInt(municipalityId),dateCovid);
    }

    @GetMapping(path = "/varianceAllInfo/{munId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestVariance covidDataCountryVarianceAllInfo(@PathVariable String munId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.varianceCovidDataMunAllInfo(Integer.parseInt(munId),dateCovid);
    }

    @GetMapping(path = "/confidenceIntervalAllInfo/{munId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestConfidenceInterval covidDataCountryConfidenceIntervalAllInfo(@PathVariable String munId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.confidenceIntervalCovidDataMunAllInfo(Integer.parseInt(munId),dateCovid);
    }

}
