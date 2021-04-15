package bo.ucb.edu.ingsoft.api;

import bo.ucb.edu.ingsoft.bl.CityBl;
import bo.ucb.edu.ingsoft.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@RestController
@RequestMapping(value = "/city")
public class CityApi {
    private CityBl cityBl;

    @Autowired
    public CityApi(CityBl cityBl){
        this.cityBl = cityBl;
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<LocationRequest> getCities(){
        return cityBl.getCities();
    }

}
