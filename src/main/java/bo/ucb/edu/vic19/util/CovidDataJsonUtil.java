package bo.ucb.edu.vic19.util;
import bo.ucb.edu.vic19.dao.*;
import bo.ucb.edu.vic19.dto.CaseData;
import bo.ucb.edu.vic19.dto.CaseHistory;
import bo.ucb.edu.vic19.dto.ContagionData;
import bo.ucb.edu.vic19.dto.VaccineData;
import bo.ucb.edu.vic19.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


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

@Component
public class CovidDataJsonUtil {

    private CityDao cityDao;
    private MunicipalityDao municipalityDao;
    private CityCovidDataDao cityCovidDataDao;
    private MunicipalityCovidDataDao municipalityCovidDataDao;
    private CovidDataCSVDao covidDataCSVDao;
    private Transaction transaction;

    @Autowired
    public void CovidDataU(CovidDataCSVDao covidDataCSVDao,CityDao cityDao,MunicipalityDao municipalityDao,CityCovidDataDao cityCovidDataDao,MunicipalityCovidDataDao municipalityCovidDataDao) {
        this.cityDao =cityDao;
        this.municipalityDao = municipalityDao;
        this.cityCovidDataDao = cityCovidDataDao;
        this.municipalityCovidDataDao = municipalityCovidDataDao;
        this.covidDataCSVDao = covidDataCSVDao;

    }
    //@Scheduled(cron = "5 10 * * 1")
    //@Scheduled(cron = "0 15 15 L * ?")
    //@Scheduled(fixedRate = 3000L)
    @GetMapping(value = "/swagger")
    public void swaggerCovidData() throws ParseException {
        String url = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays";
        readDataJsonSwagger(url);
        System.out.println("Se compilo si o no ===================== ");
    }

    public static ArrayList<CaseData> getJson2(){
        ArrayList<CaseData> caseData = new ArrayList();
        JSONParser parser = new JSONParser();
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/historical/Bolivia?lastdays");
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            CaseHistory caseHistory = mapper.readValue(responseStream, CaseHistory.class);
            String str11 = caseHistory.getTimeline().toString();
            //remove white spaces
            String str21 = str11.replaceAll("\\\\", "");

            Object obj = parser.parse(str21);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject innerObjectRec = (JSONObject) jsonObject.get("recovered");
            JSONObject innerObjectCas = (JSONObject) jsonObject.get("cases");
            JSONObject innerObjectDea = (JSONObject) jsonObject.get("deaths");

            Calendar cal = Calendar.getInstance();
            Date date;
            cal.add(Calendar.DAY_OF_YEAR, -30);
            date = cal.getTime();
            CaseData caseData1;
            //DatosCasos datosCasos1;

            for(int i=0;i<innerObjectRec.size();i++){
                caseData1 = new CaseData(caseHistory.getCountry(),date,
                        Integer.parseInt(""+innerObjectCas.get(formatearCalendar(cal))),
                        Integer.parseInt(""+innerObjectDea.get(formatearCalendar(cal))),
                        Integer.parseInt(""+innerObjectRec.get(formatearCalendar(cal))));
                caseData.add(caseData1);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                date = cal.getTime();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseData;
    }

    public static void getJson() {
        ArrayList<CaseData> caseData = new ArrayList();
        caseData = getJson2();
        ArrayList<VaccineData> vaccineData = new ArrayList();
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            Vaccines vaccines = mapper.readValue(responseStream, Vaccines.class);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, -29);

            VaccineData vaccineData1;
            for(int i=0;i<vaccines.getTimeline().size();i++){
                vaccineData1 = new VaccineData(vaccines.getCountry(),formatearCalendar(c),(Integer) vaccines.getTimeline().get(formatearCalendar(c)));
                vaccineData.add(vaccineData1);
                c.add(Calendar.DAY_OF_YEAR, 1);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            Date date;
            //Restando 4 horas a la hora actual
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
            date = cal.getTime();
            CovidData covidData;
            Transaction transaction;

            for(int i=0;i<vaccineData.size();i++){
                covidData = new CovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);

                InetAddress ipAddress = InetAddress.getLocalHost();
                String localIP = ipAddress.getHostAddress();//ip como String

                transaction.setTxHost(localIP);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);
                covidData.setIdCountry(1);
                covidData.setIdPageUrl(3);
                covidData.setDeathCases(caseData.get(i).getDeaths());
                covidData.setConfirmedCases(caseData.get(i).getCases());
                covidData.setVaccinated(vaccineData.get(i).getAmountData());
                covidData.setCumulativeCases(caseData.get(i).getCases());
                covidData.setRecuperated(caseData.get(i).getRecovered());
                covidData.setDate(caseData.get(i).getDateCas());
                covidData.setTransaction(transaction);
                //covidDataCSVDao.insertData(covidData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readDataJsonSwagger(String urlJson) {
        ArrayList<CaseData> caseData = new ArrayList();
        caseData = getJson2();
        ArrayList<VaccineData> vaccineData = new ArrayList();
        try {
            //URL url = new URL("https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays");
            URL url = new URL(urlJson);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            Vaccines vaccines = mapper.readValue(responseStream, Vaccines.class);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, -29);

            VaccineData vaccineData1;
            for(int i=0;i<vaccines.getTimeline().size();i++){
                vaccineData1 = new VaccineData(vaccines.getCountry(),formatearCalendar(c),(Integer) vaccines.getTimeline().get(formatearCalendar(c)));
                vaccineData.add(vaccineData1);
                c.add(Calendar.DAY_OF_YEAR, 1);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            Date date;
            //Restando 4 horas a la hora actual
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
            date = cal.getTime();
            CovidData covidData;
            Transaction transaction;

            for(int i=0;i<vaccineData.size();i++){
                covidData = new CovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);

                InetAddress ipAddress = InetAddress.getLocalHost();
                String localIP = ipAddress.getHostAddress();//ip como String

                transaction.setTxHost(localIP);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);
                covidData.setIdCountry(1);
                covidData.setIdPageUrl(3);
                covidData.setDeathCases(caseData.get(i).getDeaths());
                covidData.setConfirmedCases(caseData.get(i).getCases());
                covidData.setVaccinated(vaccineData.get(i).getAmountData());
                covidData.setCumulativeCases(caseData.get(i).getCases());
                covidData.setRecuperated(caseData.get(i).getRecovered());
                covidData.setDate(caseData.get(i).getDateCas());
                covidData.setTransaction(transaction);
                covidDataCSVDao.insertData(covidData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(fixedRate = 3000L)
    @GetMapping(value = "/siip")
    public void siipCovidData() throws ParseException {
        String url = "https://siip.produccion.gob.bo/repSIIP2/JsonAjaxCovid.php?flag=contagiados";
        readDataJsonSiip(url);
    }

    public void readDataJsonSiip(String urlJson){
        String cityData,municipalityData;
        Integer cityId = 0;
        Integer covidDataId = 0;
        Integer municipalityId = 0;
        Integer selectData = 0;
        try {
            //URL url = new URL("https://siip.produccion.gob.bo/repSIIP2/JsonAjaxCovid.php?flag=contagiados");
            URL url = new URL(urlJson);
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            ContagionData contagionData =  mapper.readValue(responseStream, ContagionData.class);
            DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");Date convertedDate = fecha.parse("2021-04-04");
            String dateSelect = fecha.format(convertedDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            Date date;
            //Restando 4 horas a la hora actual
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
            date = cal.getTime();
            City city;
            Municipality municipality;
            CovidData covidData;
            CityCovidData cityCovidData;
            MunicipalityCovidData municipalityCovidData;
            Transaction transaction;
            for(int i=0;i<contagionData.getData_mapa().get("features").size();i++){
            //for(int i=0;i<2;i++){

                covidData = new CovidData();
                city = new City();
                municipality = new Municipality();
                cityCovidData = new CityCovidData();
                municipalityCovidData = new MunicipalityCovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);
                InetAddress ipAddress = InetAddress.getLocalHost();
                String localIP = ipAddress.getHostAddress();//ip como String
                transaction.setTxHost(localIP);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);

                covidData.setIdPageUrl(3);
                covidData.setIdCountry(1);
                covidData.setDeathCases(-1);
                covidData.setConfirmedCases(contagionData.getData_mapa().get("features").get(i).get("properties").get("_f_0709202").asInt());
                covidData.setVaccinated(-1);
                covidData.setCumulativeCases(-1);
                covidData.setRecuperated(-1);
                selectData = covidDataCSVDao.selectDataExist(covidData,dateSelect);
                covidData.setDate(convertedDate);
                covidData.setTransaction(transaction);
                if(selectData == 0){
                    covidDataCSVDao.insertData(covidData);

                    cityData = contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_dept").asText();
                    if(!cityData.equals("Unassigned")) {
                        cityId = cityDao.getCityId(cityData);
                        covidDataId = covidDataCSVDao.getCovidDataId();

                        cityCovidData = new CityCovidData();
                        cityCovidData.setIdCity(cityId);
                        cityCovidData.setIdCovidData(covidDataId);
                        cityCovidData.setTransaction(transaction);

                        cityCovidDataDao.insertCityCovidData(cityCovidData);
                    }

                    municipalityData = contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_mun").asText();
                    if(!municipalityData.equals("Unassigned")) {
                        municipalityId = municipalityDao.getMunicipalityMaxId(cityId, municipalityData);
                        municipalityCovidData.setIdMunicipality(municipalityId);
                        municipalityCovidData.setIdCovidData(covidDataId);
                        municipalityCovidData.setTransaction(transaction);
                        municipalityCovidDataDao.insertMunicipalityCovidData(municipalityCovidData);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getJsonMunicipios(){
        String cityData;
        Integer cityId = 0;
        Integer covidDataId = 0;
        Integer municipalityId = 0;
        try {
            URL url = new URL("https://siip.produccion.gob.bo/repSIIP2/JsonAjaxCovid.php?flag=contagiados");
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            ContagionData contagionData =  mapper.readValue(responseStream, ContagionData.class);
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");Date convertedDate = fecha.parse("04/04/2021");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            Date date;
            //Restando 4 horas a la hora actual
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
            date = cal.getTime();

            City city;
            Municipality municipality;
            CovidData covidData;
            CityCovidData cityCovidData;
            MunicipalityCovidData municipalityCovidData;
            Transaction transaction;
            for(int i=0;i<contagionData.getData_mapa().get("features").size();i++){

                covidData = new CovidData();
                city = new City();
                municipality = new Municipality();
                cityCovidData = new CityCovidData();
                municipalityCovidData = new MunicipalityCovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);
                InetAddress ipAddress = InetAddress.getLocalHost();
                String localIP = ipAddress.getHostAddress();//ip como String
                transaction.setTxHost(localIP);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);

                covidData.setIdCountry(1);
                covidData.setIdPageUrl(3);
                covidData.setDeathCases(-1);
                covidData.setConfirmedCases(contagionData.getData_mapa().get("features").get(i).get("properties").get("_f_0709202").asInt());
                covidData.setVaccinated(-1);
                covidData.setCumulativeCases(-1);
                covidData.setRecuperated(-1);
                covidData.setDate(convertedDate);
                covidData.setTransaction(transaction);
                //covidDataCSVDao.insertData(covidData);


                cityData = contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_dept").asText();
                if(!cityData.equals("Unassigned")) {
                    //cityId = cityDao.getCityId(cityData);
                    //covidDataId = covidDataCSVDao.getCovidDataId();

                    cityCovidData = new CityCovidData();
                    cityCovidData.setIdCity(cityId);
                    cityCovidData.setIdCovidData(covidDataId);
                    cityCovidData.setTransaction(transaction);

                    //cityCovidDataDao.insertCityCovidData(cityCovidData);
                }
                municipality.setIdDepartament(cityId);
                municipality.setMunicipality(contagionData.getData_mapa().get("features").get(i).get("properties").get("nom_mun").asText());
                municipality.setLat(-1);
                municipality.setLon(-1);
                municipality.setTransaction(transaction);
                //municipalityDao.insertMunicipalityData(municipality);

                if(!municipality.getMunicipality().equals("Unassigned")) {
                    //municipalityId = municipalityDao.getMunicipalityMaxId();
                    municipalityCovidData.setIdMunicipality(municipalityId);
                    municipalityCovidData.setIdCovidData(covidDataId);
                    municipalityCovidData.setTransaction(transaction);
                    //municipalityCovidDataDao.insertMunicipalityCovidData(municipalityCovidData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String formatearCalendar(Calendar c) {
        Date date = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        return dateFormat1.format(c.getTime());
    }
/*

    @Override
    public void run(String... args) throws Exception {
        //swaggerCovidData();
    }

 */
}
