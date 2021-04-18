package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


    public CovidDataRequest covidDataMunicipality(Integer municipalityId, String dateCovid){
        CovidDataRequest covidDataMunicipality=municipalityDao.covidDataMunicipality(municipalityId, dateCovid);
        return covidDataMunicipality;
    }

    public List<CovidDataRequest> covidDataListMunicipality(String dateCovid){
        List<CovidDataRequest> covidDataListMunicipality=municipalityDao.covidDataListMunicipality(dateCovid);
        return covidDataListMunicipality;
    }

}
