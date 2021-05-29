package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CountryBl;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryApi {
    private CountryBl countryBl;

    @Autowired
    public CountryApi(CountryBl countryBl){
        this.countryBl = countryBl;
    }


    @PostMapping(path = "/csv")
    public HttpStatus uploadDataCSV(@RequestParam("file") MultipartFile file,boolean replace, HttpServletRequest request) {

        if (CovidDataCSVUtil.hasCSVFormat(file)) {
            try {
                TransactionUtil transactionUtil = new TransactionUtil();
                Transaction transaction = transactionUtil.createTransaction(request);
                countryBl.saveDataCSV(file,transaction,replace);
                return HttpStatus.OK;
            }
            catch (Exception e) {
                return HttpStatus.EXPECTATION_FAILED;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getCountries(){
        return countryBl.getCountries();
    }
    @GetMapping(path = "/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataCountry(@PathVariable String countryId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){

        CovidDataRequest covidDataCountry=countryBl.covidDataCountry(Integer.parseInt(countryId),date);
        return covidDataCountry;
    }

    @GetMapping(path = "/allInfo/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataCountryAllInfo(@PathVariable String countryId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        return countryBl.covidDataCountryAllInfo(Integer.parseInt(countryId),date);
    }

    @GetMapping(path = "/mediaAllInfo/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestMedia covidDataCountryMediaAllInfo(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.mediaCovidDataCountryAllInfo(Integer.parseInt(countryId),dateCovid);
    }

    @GetMapping(path = "/varianceAllInfo/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestVariance covidDataCountryVarianceAllInfo(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.varianceCovidDataCountryAllInfo(Integer.parseInt(countryId),dateCovid);
    }

    @GetMapping(path = "/confidenceIntervalAllInfo/{countryId}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestConfidenceInterval covidDataCountryConfidenceIntervalAllInfo(@PathVariable String countryId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.confidenceIntervalCovidDataCountryAllInfo(Integer.parseInt(countryId),dateCovid);
    }

    @GetMapping(path = "/leastSquaresAllInfo/{countryId}/{forecastDate}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestLeastSquares covidDataCountryLeastSquaresAllInfo(@PathVariable String countryId,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return countryBl.leastSquaresCovidDataCountryAllInfo(Integer.parseInt(countryId),forecastDate, dateCovid);
    }
}
