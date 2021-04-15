package bo.ucb.edu.ingsoft.bl;

import bo.ucb.edu.ingsoft.api.CityApi;
import bo.ucb.edu.ingsoft.dao.CityDao;
import bo.ucb.edu.ingsoft.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityBl {
    private CityDao cityDao;

    @Autowired
    public CityBl(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public List<LocationRequest> getCities(){
        return cityDao.cities();
    }

}
