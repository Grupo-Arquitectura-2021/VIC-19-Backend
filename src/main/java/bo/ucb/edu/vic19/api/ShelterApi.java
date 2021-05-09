package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.HospitalBl;
import bo.ucb.edu.vic19.bl.ShelterBl;
import bo.ucb.edu.vic19.dto.HospitalRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.dto.ShelterRequest;
import bo.ucb.edu.vic19.model.Hospital;
import bo.ucb.edu.vic19.model.Shelter;
import bo.ucb.edu.vic19.model.Transaction;
import bo.ucb.edu.vic19.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/hospital")
public class ShelterApi {
    private ShelterBl shelterBl;

    @Autowired
    public ShelterBl(ShelterBl shelterBl){
        this.shelterBl = shelterBl;
    }

    @RequestMapping(path = "/location/{shelterId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getHospitalLocation(@PathVariable Integer shelterId){
        return shelterBl.getShelterLocation(shelterId);
    }

    @RequestMapping(path = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getHospitalLocation(){
        return shelterBl.getShelterLocations();
    }

    @RequestMapping(path = "/allInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<ShelterRequest> getShelterAllInfo(){
        return shelterBl.getShelterAllInfo();
    }

    @RequestMapping(path = "/locationsByCity/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getShelterLocationsByCity(@PathVariable Integer cityId){
        return shelterBl.getShelterLocationsByCityId(cityId);
    }

    //Metodo que agrega un albergue a traves del requestMethod POST
    @RequestMapping(path="/addShelter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Shelter insertShelter(@RequestBody Shelter shelter, HttpServletRequest request) {
        TransactionUtil transactionUtil= new TransactionUtil();
        Transaction transaction = transactionUtil.createTransaction(request);
        shelterBl.insertShelter(shelter,transaction);
        return shelter;
    }

    //Metodo que elimina un albergue a traves del requestMethod PUT es decir cambia el status a 0
    @RequestMapping(path="/deleteShelter",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Shelter deleteHospital(@RequestBody Shelter shelter, HttpServletRequest request) {
        return shelterBl.deleteShelter(shelter);
    }

    //Metodo que actualiza un albergue a traves del requestMethod PUT
    @RequestMapping(path="/updateShelter",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Shelter updateHospital(@RequestBody Shelter shelter, HttpServletRequest request) {
        return shelterBl.updateShelter(shelter);
    }

}
