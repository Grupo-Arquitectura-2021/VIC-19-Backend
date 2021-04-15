package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityBl {
    private MunicipalityDao municipalityDao;

    @Autowired
    public MunicipalityBl(MunicipalityDao municipalityDao){
        this.municipalityDao = municipalityDao;
    }

    public List<LocationRequest> getMunicipalities(){
        return municipalityDao.municipalities();
    }
}
