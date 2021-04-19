package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionBl {

    private TransactionDao transactionDao;

    @Autowired
    public TransactionBl(TransactionDao transactionDao){
        this.transactionDao = transactionDao;
    }

    public Transaction createTransaction (Transaction transaction){
        // Registrar la transaccion en la Base de Datos
        this.transactionDao.create(transaction);

        // Obtenemos la llave primaria generada
        Integer lastPrimaryKey = this.transactionDao.getLastInsertId();

        transaction.setTxId(lastPrimaryKey);
        return transaction;
    }

}
