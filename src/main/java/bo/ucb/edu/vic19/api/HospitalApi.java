package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.HospitalBl;
import bo.ucb.edu.vic19.bl.MunicipalityBl;
import bo.ucb.edu.vic19.dto.HospitalDataRequest;
import bo.ucb.edu.vic19.dto.HospitalRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Hospital;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/hospital")
public class HospitalApi {
    private HospitalBl hospitalBl;

    @Autowired
    public HospitalApi(HospitalBl hospitalBl){
        this.hospitalBl = hospitalBl;
    }
/*
    @RequestMapping(path = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getHospitalLocation(@RequestParam Integer cityId){
        return hospitalBl.getHospitalLocation(cityId);
    }*/

 /*   @RequestMapping(path = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getHospitalLocation(){
        return hospitalBl.getHospitalLocations();
    }*/

    @RequestMapping(path = "/allInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HospitalDataRequest getHospitalAllInfo(@RequestParam Integer n, @RequestParam Integer i, @RequestParam(required = false) String search){
        return hospitalBl.getHospitalAllInfo(n,i,search);
    }

    @RequestMapping(path = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationResponse> getHospitalLocationsByCity(@RequestParam Integer cityId){
        return hospitalBl.getHospitalLocationsByCityId(cityId);
    }

    //Metodo que agrega un hospital a traves del requestMethod POST
    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Hospital insertHospital(@RequestBody Hospital hospital, HttpServletRequest request) {
        TransactionUtil transactionUtil= new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        hospitalBl.insertHospital(hospital,transaction);
        return hospital;
    }

    //Metodo que elimina un hospital a traves del requestMethod PUT es decir cambia el status a 0
    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteHospital(@RequestParam Integer hospitalId) {
        hospitalBl.deleteHospital(hospitalId);
    }

    //Metodo que actualiza un hospital a traves del requestMethod PUT
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Hospital updateHospital(@RequestBody Hospital hospital, HttpServletRequest request) {
        return hospitalBl.updateHospital(hospital);
    }

}
