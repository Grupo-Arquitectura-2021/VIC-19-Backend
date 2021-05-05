package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.HospitalDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalBl {

    private HospitalDao hospitalDao;

    @Autowired
    public HospitalBl(HospitalDao hospitalDao){
        this.hospitalDao = hospitalDao;
    }

    public List<LocationResponse> getHospitalLocation(Integer hospitalId) {
        return hospitalDao.getHospitalLocation(hospitalId);
    }

    public List<LocationResponse> getHospitalLocationsByCityId(Integer cityId) {
        return hospitalDao.getHospitalLocationsByCityId(cityId);
    }
}
