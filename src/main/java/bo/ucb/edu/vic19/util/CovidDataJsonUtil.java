package bo.ucb.edu.vic19.util;
import bo.ucb.edu.vic19.dao.*;
import bo.ucb.edu.vic19.dto.*;
import bo.ucb.edu.vic19.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CovidDataJsonUtil {

    private CityDao cityDao;
    private MunicipalityDao municipalityDao;
    private CovidDataDao covidDataDao;
    private Transaction transaction;
    private CountryDao countryDao;

    @Autowired
    public void CovidDataU( CityDao cityDao, MunicipalityDao municipalityDao,  CovidDataDao covidDataDao,CountryDao countryDao) {
        this.cityDao =cityDao;
        this.municipalityDao = municipalityDao;
        this.covidDataDao = covidDataDao;
        this.countryDao=countryDao;

    }

    @Scheduled(fixedRate = 300000L)
    @GetMapping(value = "/siip")
    public void siipCovidData() throws ParseException {
        String url = "https://siip.produccion.gob.bo/repSIIP2/JsonAjaxCovid.php?flag=contagiados";
        readDataJsonSiip(url);
    }

    public void readDataJsonSiip(String urlJson){
        String cityData,municipalityName;
        Integer covidDataId = 0;
        Integer municipality;
        Integer selectData = 0;
        try {
            URL url = new URL(urlJson);
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            ContagionData contagionData =  mapper.readValue(responseStream, ContagionData.class);
            DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = fecha.parse("2021-04-04");
            String dateSelect = fecha.format(convertedDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());Date date;
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
            date = cal.getTime();
            Integer idCity;
            CovidData covidData;
            CityCovidData cityCovidData;
            MunicipalityCovidData municipalityCovidData;
            Transaction transaction;
            for(int i=0;i<contagionData.getData_mapa().get("features").size();i++){

                covidData = new CovidData();
                municipalityCovidData = new MunicipalityCovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);
                InetAddress ipAddress = InetAddress.getLocalHost();
                String localIP = ipAddress.getHostAddress();//ip como String
                transaction.setTxHost(localIP);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);
                municipalityName = contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_mun").asText();

                cityData = contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_dept").asText();
                idCity=cityDao.getCityId(cityData);
                municipality = municipalityDao.getMunicipalityIdWithName(municipalityName,idCity);
                if(municipality!=null){
                    selectData = covidDataDao.verifyMunicipalityCovidData(dateSelect,municipality);

                    if(selectData == 0){
                        covidData.setIdPageUrl(6);
                        covidData.setDeathCases(-1);
                        covidData.setConfirmedCases(-1);
                        covidData.setVaccinated(-1);
                        covidData.setCumulativeCases(contagionData.getData_mapa().get("features").get(i).get("properties").get("_f_0709202").asInt());
                        covidData.setRecuperated(-1);
                        covidData.setDate(convertedDate);
                        covidData.setTransaction(transaction);
                        covidDataDao.insertCovidData(covidData);
                        covidDataId=covidDataDao.getLastIdCovidData();
                        municipalityCovidData.setIdMunicipality(municipality);
                        municipalityCovidData.setIdCovidData(covidDataId);
                        municipalityCovidData.setTransaction(transaction);
                        covidDataDao.insertMunicipalityCovidData(municipalityCovidData);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @Scheduled(fixedRate = 300000L)
    @GetMapping(value = "/swagger")
    public void swaggerCovidData() throws ParseException {
        String url = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays";
    }*/

    public static ArrayList<ArrayList> getJson(String country,Integer length){
        ArrayList<ArrayList> caseData = new ArrayList();
        JSONParser parser = new JSONParser();
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/historical/"+country+"?lastdays="+length.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            CaseHistory caseHistory = mapper.readValue(responseStream, CaseHistory.class);
            String str11 = caseHistory.getTimeline().toString();
            String str21 = str11.replaceAll("\\\\", "");
            Object obj = parser.parse(str21);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject innerObjectRec = (JSONObject) jsonObject.get("recovered");
            JSONObject innerObjectCas = (JSONObject) jsonObject.get("cases");
            JSONObject innerObjectDea = (JSONObject) jsonObject.get("deaths");
            Calendar cal = Calendar.getInstance();
            Date date;
            cal.add(Calendar.DAY_OF_YEAR, -length);
            date = cal.getTime();
            ArrayList arr;
            for(int i=0;i<innerObjectRec.size();i++){
                arr=new ArrayList();
                arr.add(date);
                if(innerObjectCas.get(formatearCalendar(cal))==null){
                    arr.add(-1);
                    arr.add(-1);
                    arr.add(-1);
                }
                else{
                    arr.add(Integer.parseInt(""+innerObjectCas.get(formatearCalendar(cal))));
                    arr.add(Integer.parseInt(""+innerObjectDea.get(formatearCalendar(cal))));
                    arr.add(Integer.parseInt(""+innerObjectRec.get(formatearCalendar(cal))));
                }
                caseData.add(arr);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                date = cal.getTime();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseData;
    }

    public ArrayList<ArrayList> getJsonVaccine(String country,Integer length){
        ArrayList<ArrayList> vaccineData = new ArrayList();
        try{
            URL url = new URL("https://disease.sh/v3/covid-19/vaccine/coverage/countries/"+country+"?lastdays="+length.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            Vaccines vaccines = mapper.readValue(responseStream, Vaccines.class);
            Calendar c = Calendar.getInstance();
            Date date;
            c.add(Calendar.DAY_OF_YEAR, -length);
            date = c.getTime();
            ArrayList arr;
            for(int i=0;i<vaccines.getTimeline().size();i++){
                arr=new ArrayList();
                arr.add(date);
                if((Integer) vaccines.getTimeline().get(formatearCalendar(c))==null){
                    arr.add(-1);
                }
                else{

                    arr.add((Integer) vaccines.getTimeline().get(formatearCalendar(c)));
                }
                vaccineData.add(arr);
                c.add(Calendar.DAY_OF_YEAR, 1);
                date = c.getTime();
            }
            return vaccineData;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Scheduled(fixedRate = 300000L)
    @GetMapping(value = "/swagger")
    public void readDataJsonSwagger() {
        try {
            List<LocationResponse> countries=countryDao.countries();
            for(int i=0;i<countries.size();i++){
                var general=getJson(countries.get(i).getLocationName(),100);
                var vaccine=getJsonVaccine(countries.get(i).getLocationName(),100);
                Date date;
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                DateFormat fechaSelect = new SimpleDateFormat("yyyy-MM-dd");
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
                date = cal.getTime();
                CovidData covidData;
                CountryCovidData countryCovidData;
                Transaction transaction;
                Integer selectData;
                Integer covidDataId;
                String dateString;
                for(int j=0;j<general.size();j++){
                    covidData = new CovidData();
                    transaction = new Transaction();
                    transaction.setTxDate(date);
                    InetAddress ipAddress = InetAddress.getLocalHost();
                    String localIP = ipAddress.getHostAddress();
                    transaction.setTxHost(localIP);
                    transaction.setTxId(1);
                    transaction.setTxUpdate(date);
                    covidData.setIdPageUrl(4);
                    if(j== 0){
                        covidData.setDeathCases((Integer)general.get(j).get(2));
                        covidData.setConfirmedCases((Integer) general.get(i).get(1));
                        covidData.setVaccinated((Integer)vaccine.get(i).get(1));
                        covidData.setCumulativeCases((Integer) general.get(j).get(1));
                        covidData.setRecuperated((Integer) general.get(i).get(3));
                    }
                    else{
                        covidData.setDeathCases((Integer) general.get(j).get(2)-(Integer) general.get(j-1).get(2));
                        covidData.setConfirmedCases((Integer) general.get(j).get(1)-(Integer) general.get(j-1).get(1));
                        covidData.setVaccinated((Integer)vaccine.get(j).get(1)-(Integer)vaccine.get(j-1).get(1));
                        covidData.setCumulativeCases((Integer) general.get(j).get(1));
                        covidData.setRecuperated((Integer) general.get(j).get(3)-(Integer) general.get(j-1).get(3));

                    }
                    covidData.setDate((Date) general.get(j).get(0));
                    covidData.setTransaction(transaction);
                    dateString=fechaSelect.format((Date) general.get(j).get(0));
                    selectData = covidDataDao.verifyCountryCovidData(dateString,countries.get(i).getIdLocation());
                    if (selectData == 0){
                        covidDataDao.insertCovidData(covidData);
                        covidDataId = covidDataDao.getLastIdCovidData();
                        countryCovidData = new CountryCovidData();
                        countryCovidData.setIdCountry(countries.get(i).getIdLocation());
                        countryCovidData.setIdCovidData(covidDataId);
                        countryCovidData.setTransaction(transaction);
                        covidDataDao.insertCountryCovidData(countryCovidData);
                    }
            }
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String formatearCalendar(Calendar c) {
        Date date = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        return dateFormat1.format(c.getTime());
    }

}
