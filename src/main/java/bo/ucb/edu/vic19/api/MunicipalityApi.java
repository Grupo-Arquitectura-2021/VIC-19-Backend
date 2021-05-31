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

    @GetMapping(path = "/statistics/{munId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataStatistics covidDataMunStatisticsAllInfo(@PathVariable Integer munId,  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date){
        return municipalityBl.statisticsMunicipality(munId,date);
    }

    @GetMapping(path = "/leastSquaresAllInfo/{munId}/{forecastDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestLeastSquares covidDataMunLeastSquaresAllInfo(@PathVariable String munId,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateCovid){
        return municipalityBl.leastSquaresCovidDataCityAllInfo(Integer.parseInt(munId),forecastDate);
    }

    @GetMapping(path = "/absoluteIncreaseAllInfo/{munId}/{forecastDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestIncreaseMethod covidDataMunLeastSquaresAllInfo(@PathVariable String munId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate){
        return municipalityBl.absoluteIncreaseCovidDataMunAllInfo(Integer.parseInt(munId),forecastDate);
    }

    @GetMapping(path = "/percentageIncreaseAllInfo/{munId}/{forecastDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CovidDataRequestIncreaseMethod covidDataMunPercentageIncreaseAllInfo(@PathVariable String munId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String forecastDate){
        return municipalityBl.percentageIncreaseCovidDataMunAllInfo(Integer.parseInt(munId), forecastDate);
    }


}
