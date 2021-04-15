package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryBl {
    private CountryDao countryDao;

    @Autowired
    public CountryBl(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public List<LocationRequest> getCountries(){
        return countryDao.countries();
    }

}
