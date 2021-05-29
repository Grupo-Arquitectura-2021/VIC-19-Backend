package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.CityBl;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import bo.ucb.edu.vic19.util.data.CovidDataCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityApi {
    final private CityBl cityBl;

    @Autowired
    public CityApi(CityBl cityBl){
        this.cityBl = cityBl;
    }

    @PostMapping(path = "/csv")
    public HttpStatus uploadDataCSV(@RequestParam("file") MultipartFile file, boolean replace, HttpServletRequest request) {

        if (CovidDataCSVUtil.hasCSVFormat(file)) {
            try {
                TransactionUtil transactionUtil = new TransactionUtil();
                Transaction transaction = transactionUtil.createTransaction(request);
                cityBl.saveDataCSV(file,transaction,replace);
                return HttpStatus.OK;
            }
            catch (Exception e) {
                return HttpStatus.EXPECTATION_FAILED;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CitySimpleResponse> getCities(){
        return cityBl.getCities();
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getCitiesLocation(){
        return cityBl.getCitiesLocation();
    }

    @GetMapping(path = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataCity(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date,@PathVariable String cityId){
        return cityBl.covidDataCity(Integer.parseInt(cityId),date);
    }

    @GetMapping(path = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListCity(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        return cityBl.covidDataListCity(date);
    }

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
    public CovidDataRequestConfidenceInterval covidDataCityConfidenceIntervalAllInfo(@PathVariable String cityId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.confidenceIntervalCovidDataCityAllInfo(Integer.parseInt(cityId),dateCovid);
    }

    @GetMapping(path = "/leastSquaresAllInfo/{cityId}/{forecastDate}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestLeastSquares covidDataCityLeastSquaresAllInfo(@PathVariable String cityId,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return cityBl.leastSquaresCovidDataCityAllInfo(Integer.parseInt(cityId),forecastDate, dateCovid);
    }

    @GetMapping(path = "/absoluteIncreaseAllInfo/{cityId}/{forecastDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestAbsoluteIncrease covidDataCityAbsoluteIncreaseAllInfo(@PathVariable String cityId,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate){
        return cityBl.absoluteIncreaseCovidDataCityAllInfo(Integer.parseInt(cityId), forecastDate);
    }

}
