package bo.ucb.edu.ingsoft.api;

import bo.ucb.edu.ingsoft.bl.DataBl;
import bo.ucb.edu.ingsoft.dto.DatosVacuna;
import bo.ucb.edu.ingsoft.model.Transaction;
import bo.ucb.edu.ingsoft.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/data")
public class DataApi {
    private DataBl dataBl;
    @Autowired
    public DataApi(DataBl dataBl){
        this.dataBl = dataBl;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createData(@RequestParam List<DatosVacuna> datos, HttpServletRequest request){
        TransactionUtil transactionUtil=new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        dataBl.createData(datos,transaction);
        return "datos correctos";
    }

}
