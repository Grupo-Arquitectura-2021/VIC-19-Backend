package bo.ucb.edu.vic19.util;

import bo.ucb.edu.vic19.model.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class TransactionUtil {

    //Creando la funcion de tipo Transaction createTransaction para crear una transaccion nueva
    //con los datos de auditoria que son el txId, txUserId, txHost, txDate, txUpdate
    public static Transaction createTransaction(HttpServletRequest request){
        Transaction transaction = new Transaction();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Date tempDate = cal.getTime();

        //Le cambiamos la hora y minutos
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        tempDate = cal.getTime();

        transaction.setTxDate(tempDate); // Fecha Actual
        transaction.setTxUpdate(tempDate);
        transaction.setTxHost(request.getRemoteHost()); // Direccion Ip
        transaction.setTxId(0); // Id de la transaccion
        return transaction;
    }

}

