package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.DrugstoreDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.dto.DrugstoreDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Drugstore;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugstoreBl {
    private DrugstoreDao drugstoreDao;
    private TransactionDao transactionDao;

    @Autowired
    public DrugstoreBl(DrugstoreDao drugstoreDao, TransactionDao transactionDao){
        this.drugstoreDao = drugstoreDao;
        this.transactionDao = transactionDao;
    }

    public Drugstore createDrugstore(Drugstore drugstore, Transaction transaction){
        drugstore.setTransaction(transaction);
        drugstoreDao.createDrugstore(drugstore);
        Integer drugstoreId = transactionDao.getLastInsertId();
        drugstore.setIdDrugstore(drugstoreId);
        return drugstore;
    }

    public DrugstoreDataRequest getDrugstoreAllInfo(Integer n, Integer i, String search){
        DrugstoreDataRequest drugstoreDataRequest = new DrugstoreDataRequest();
        drugstoreDataRequest.setDrugstores(drugstoreDao.getDrugstoreAllInfo(n,i,search));
        drugstoreDataRequest.setTotal(drugstoreDao.getTotalDrugstore());
        return drugstoreDataRequest;
    }

    public List<LocationResponse> getDrugstores(){
        return drugstoreDao.getDrugstores();
    }


    public LocationResponse getDrugstoreLocation(Integer drugstoreId){
        LocationResponse getDrugstoreLocation=drugstoreDao.getDrugstoreLocation(drugstoreId);
        return getDrugstoreLocation;
    }

    public List<LocationResponse> getDrugstoresByCity(Integer cityId){
        return drugstoreDao.getDrugstoresByCity(cityId);
    }

    public void deleteDrugstore(Integer drugstoreId,Transaction transaction){
        Drugstore drugstore = new Drugstore();
        drugstore.setStatus(0);
        drugstore.setIdDrugstore(drugstoreId);
        drugstore.setTransaction(transaction);
        drugstoreDao.deleteDrugstore(drugstore);
    }

    public Drugstore updateDrugstore(Drugstore drugstore, Transaction transaction){
        drugstore.setTransaction(transaction);
        drugstoreDao.updateDrugstore(drugstore);
        return drugstore;
    }
}
