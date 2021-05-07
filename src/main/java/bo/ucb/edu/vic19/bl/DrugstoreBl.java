package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.DrugstoreDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.model.Drugstore;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
