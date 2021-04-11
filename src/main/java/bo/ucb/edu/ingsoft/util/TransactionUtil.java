package bo.ucb.edu.ingsoft.util;

import bo.ucb.edu.ingsoft.model.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TransactionUtil {
    public Transaction createTransaction(HttpServletRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTxDate(new Date());
        transaction.setTxHost(request.getRemoteHost());
        transaction.setTxUpdate(new Date());
        // transaction.setTxUserId(request.getUserPrincipal().getName()); TODO fix username instead of userId
        return transaction;
    }
}
