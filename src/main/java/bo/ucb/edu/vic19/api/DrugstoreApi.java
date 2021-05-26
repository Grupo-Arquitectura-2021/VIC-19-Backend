package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.DrugstoreBl;
import bo.ucb.edu.vic19.dto.DrugstoreDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Drugstore;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteDrugstore(@RequestParam Integer drugstoreId, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        drugstoreBl.deleteDrugstore(drugstoreId,transaction);
    }

    @RequestMapping( method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Drugstore updateDrugstore(@RequestBody Drugstore drugstore, HttpServletRequest request){
        TransactionUtil transactionUtil = new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        return drugstoreBl.updateDrugstore(drugstore,transaction);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DrugstoreDataRequest getDrugstores(@RequestParam Integer n,
                                              @RequestParam Integer i,
                                              @RequestParam(required = false) String search){
        return drugstoreBl.getDrugstoreAllInfo(n,i,search);
    }

/*    @GetMapping(path = "/locations",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getDrugstores(){
        return drugstoreBl.getDrugstores();
    }*/

    /*@GetMapping(path = "/location/{drugstoreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LocationResponse getDrugstoreLocation(@PathVariable Integer drugstoreId){
        return drugstoreBl.getDrugstoreLocation(drugstoreId);
    }*/

    @GetMapping(path = "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getDrugstoresByCity(@RequestParam Integer cityId){
        return drugstoreBl.getDrugstoresByCity(cityId);
    }


}
