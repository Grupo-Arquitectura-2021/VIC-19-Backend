package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.HospitalDao;
import bo.ucb.edu.vic19.dao.ShelterDao;
import bo.ucb.edu.vic19.dao.TransactionDao;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.Hospital;
import bo.ucb.edu.vic19.model.Shelter;
import bo.ucb.edu.vic19.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterBl {

    private ShelterDao shelterDao;
    private TransactionDao transactionDao;

    @Autowired
    public ShelterBl(ShelterDao shelterDao, TransactionDao transactionDao){
        this.shelterDao = shelterDao;
        this.transactionDao = transactionDao;
    }

    public List<LocationResponse> getShelterLocation(Integer shelterId) {
        return shelterDao.getShelterLocation(shelterId);
    }

    public List<LocationResponse> getShelterLocationsByCityId(Integer cityId) {
        return shelterDao.getShelterLocationsByCityId(cityId);
    }

    public Shelter insertShelter(Shelter shelter, Transaction transaction) {
        shelter.setTransaction(transaction);
        shelterDao.insertShelter(shelter);
        Integer cityId = transactionDao.getLastInsertId();
        shelter.setIdCity(cityId);
        return shelter;
    }

    public void  deleteShelter(Integer shelterId) {
        shelterDao.deleteShelter(shelterId);
    }

    public Shelter updateShelter(Shelter shelter) {
        shelterDao.updateShelter(shelter);
        return shelter;
    }

    public List<LocationResponse> getShelterLocations() {
        return shelterDao.getShelterLocations();
    }

    public ShelterDataRequest getShelterAllInfo(Integer n, Integer i, String search) {
        ShelterDataRequest shelterDataRequest =new ShelterDataRequest();
        shelterDataRequest.setShelters(shelterDao.getShelterAllInfo(n,i,search));
        shelterDataRequest.setTotal(shelterDao.getTotalShelter());
        return shelterDataRequest;
    }
}
