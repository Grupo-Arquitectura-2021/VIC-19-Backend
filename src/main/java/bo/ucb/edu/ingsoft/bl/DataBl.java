package bo.ucb.edu.ingsoft.bl;

import bo.ucb.edu.ingsoft.api.DataApi;
import bo.ucb.edu.ingsoft.dao.DataDao;
import bo.ucb.edu.ingsoft.dao.TransactionDao;
import bo.ucb.edu.ingsoft.dto.DatosVacuna;
import bo.ucb.edu.ingsoft.model.CovidData;
import bo.ucb.edu.ingsoft.model.Transaction;
import bo.ucb.edu.ingsoft.util.CovidDataUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static bo.ucb.edu.ingsoft.util.CovidDataUrlUtil.getJson;

@Service
public class DataBl {
    private DataDao dataDao;
    private TransactionDao transactionDao;

    @Autowired
    public DataBl(DataDao dataDao,TransactionDao transactionDao){
        this.dataDao = dataDao;
        this.transactionDao= transactionDao;
    }

    public void createData(List<DatosVacuna> datoLis, Transaction transaction){
        ArrayList<DatosVacuna> datosVacunas = new ArrayList();
        datosVacunas = getJson();

        for(int i=0;i<datosVacunas.size();i++){
            System.out.println(datosVacunas.get(i));
        }
        System.out.println("Entra al Bl");
        List<CovidData> covidDatas=new ArrayList<>();
        //ArrayList<DatosVacuna> finalDatosVacunas = datosVacunas;
        Arrays.asList(datoLis).forEach(datosVacunas1 -> {
            CovidData covidData = new CovidData();
            covidData.setIdCountry(1);
            covidData.setIdPageUrl(1);
            covidData.setDeathCases(2939);
            covidData.setConfirmedCases(2121);
            covidData.setVaccinated(2122);
            covidData.setCumulativeCases(2323);
            covidData.setRecuperated(3312);
            covidData.setDate(new Date());
            covidData.setStatus(1);
            covidData.setTransaction(transaction);
            covidDatas.add(covidData);
        });
        if(datoLis.size()>0){
            dataDao.createNewData(covidDatas);
            System.out.println("Entra al Bl");
        }
        /*
        CovidDataUrlUtil dataUrlUtil = new CovidDataUrlUtil();

        List<CovidData> covidDatas=new ArrayList<>();

        Arrays.asList(datos).stream().forEach(country -> {
            CovidData covidData = new CovidData();

            //covidData
            //ImagePublication imagePublication= new ImagePublication();
            //imagePublication.setPath(nombre);
            //imagePublication.setIdPublication(idPublication);
            //imagePublication.setTransaction(transaction);

            covidData.setTransaction(transaction);
            covidDatas.add(covidData);
        });
        if(datos.length>0){
            dataDao.createNewData(covidDatas);}

         */
    }
}
