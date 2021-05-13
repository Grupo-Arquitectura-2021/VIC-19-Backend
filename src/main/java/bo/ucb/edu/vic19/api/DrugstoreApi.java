package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.DrugstoreBl;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Drugstore;
import bo.ucb.edu.vic19.model.Hospital;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/drugstore")
public class DrugstoreApi {
    private DrugstoreBl drugstoreBl;

    @Autowired DrugstoreApi(DrugstoreBl drugstoreBl){
        this.drugstoreBl = drugstoreBl;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Drugstore createDrugstore(@RequestBody Drugstore drugstore, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        drugstoreBl.createDrugstore(drugstore,transaction);
        return drugstore;
    }

    @RequestMapping(path ="/deleteDrugstore" ,method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteDrugstore(@RequestParam Integer drugstoreId, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        drugstoreBl.deleteDrugstore(drugstoreId,transaction);
    }

    @RequestMapping(path = "/updateDrugstore", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Drugstore updateDrugstore(@RequestBody Drugstore drugstore, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        return drugstoreBl.updateDrugstore(drugstore,transaction);
    }

    @GetMapping(path = "/locations",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getDrugstores(){
        return drugstoreBl.getDrugstores();
    }

    @GetMapping(path = "/location/{drugstoreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LocationResponse getDrugstoreLocation(@PathVariable Integer drugstoreId){
        return drugstoreBl.getDrugstoreLocation(drugstoreId);
    }

    @GetMapping(path = "/location/city/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getDrugstoresByCity(@PathVariable Integer cityId){
        return drugstoreBl.getDrugstoresByCity(cityId);
    }


}
