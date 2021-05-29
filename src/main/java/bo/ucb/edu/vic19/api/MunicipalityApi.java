package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.MunicipalityBl;
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
@RequestMapping(value = "/municipality")
public class MunicipalityApi {
    private MunicipalityBl municipalityBl;

    @Autowired
    public MunicipalityApi(MunicipalityBl municipalityBl){
        this.municipalityBl = municipalityBl;
    }

    @PostMapping(path = "/csv")
    public HttpStatus uploadDataCSV(@RequestParam("file") MultipartFile file, boolean replace, HttpServletRequest request) {

        if (CovidDataCSVUtil.hasCSVFormat(file)) {
            try {
                TransactionUtil transactionUtil = new TransactionUtil();
                Transaction transaction = transactionUtil.createTransaction(request);
                municipalityBl.saveDataCSV(file,transaction,replace);
                return HttpStatus.OK;
            }
            catch (Exception e) {
                return HttpStatus.EXPECTATION_FAILED;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }


    @RequestMapping(path = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getMunicipalities(@RequestParam Integer cityId){
        return municipalityBl.getMunicipalities(cityId);
    }

    @GetMapping(path = "/{municipalityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequest covidDataMunicipality(@PathVariable String municipalityId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.covidDataMunicipality(Integer.parseInt(municipalityId),dateCovid);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataListMunicipalityByCityId(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid,@RequestParam Integer cityId){
        List<CovidDataRequest> covidDataListMunicipalityByCityId=municipalityBl.covidDataListMunicipalityByCityId(dateCovid,cityId);
        return covidDataListMunicipalityByCityId;
    }

    @GetMapping(path = "/allInfo/{municipalityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CovidDataRequest> covidDataMuniAllInfo(@PathVariable Integer municipalityId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        return municipalityBl.covidDataMunAllInfo(municipalityId,date);
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

    @GetMapping(path = "/leastSquaresAllInfo/{munId}/{forecastDate}/{dateCovid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestLeastSquares covidDataCountryLeastSquaresAllInfo(@PathVariable String munId,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.leastSquaresCovidDataCityAllInfo(Integer.parseInt(munId),forecastDate, dateCovid);
    }

}
