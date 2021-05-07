package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.DrugstoreBl;
import bo.ucb.edu.vic19.model.Drugstore;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

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
}
