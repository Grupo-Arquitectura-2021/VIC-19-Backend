package bo.ucb.edu.vic19.api;

import bo.ucb.edu.vic19.bl.HospitalBl;
import bo.ucb.edu.vic19.bl.MunicipalityBl;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hospital")
public class HospitalApi {
    private HospitalBl hospitalBl;

    @Autowired
    public HospitalApi(HospitalBl hospitalBl){
        this.hospitalBl = hospitalBl;
    }

    @RequestMapping(path = "/location/{hospitalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getHospitalLocation(@PathVariable Integer hospitalId){
        return hospitalBl.getHospitalLocation(hospitalId);
    }

    @RequestMapping(path = "/locationsByCity/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationResponse> getHospitalLocationsByCity(@PathVariable Integer cityId){
        return hospitalBl.getHospitalLocationsByCityId(cityId);
    }

}
