package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.HospitalDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.dto.HospitalDataRequest;
import bo.ucb.edu.vic19.dto.HospitalRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.Hospital;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalBl {

    private HospitalDao hospitalDao;
    private TransactionDao transactionDao;

    @Autowired
    public HospitalBl(HospitalDao hospitalDao, TransactionDao transactionDao){
        this.hospitalDao = hospitalDao;
        this.transactionDao = transactionDao;
    }

    public List<LocationResponse> getHospitalLocation(Integer hospitalId) {
        return hospitalDao.getHospitalLocation(hospitalId);
    }

    public List<LocationResponse> getHospitalLocationsByCityId(Integer cityId) {
        return hospitalDao.getHospitalLocationsByCityId(cityId);
    }

    public Hospital insertHospital(Hospital hospital, Transaction transaction) {
        hospital.setTransaction(transaction);
        hospitalDao.insertHospital(hospital);
        Integer hospitalId = transactionDao.getLastInsertId();
        hospital.setIdHospital(hospitalId);
        return hospital;
    }

    public void deleteHospital(Integer hospitalId) {
        hospitalDao.deleteHospital(hospitalId);
    }

    public Hospital updateHospital(Hospital hospital) {
        hospitalDao.updateHospital(hospital);
        return hospital;
    }

    public List<LocationResponse> getHospitalLocations() {
        return hospitalDao.getHospitalLocations();
    }

    public HospitalDataRequest getHospitalAllInfo(Integer n, Integer i,String search) {
        HospitalDataRequest hospitalDataRequest=new HospitalDataRequest();
        hospitalDataRequest.setHospitals(hospitalDao.getHospitalAllInfo(n,i,search));
        hospitalDataRequest.setTotal(hospitalDao.getTotalHospital(search));
        return hospitalDataRequest;
    }
}
