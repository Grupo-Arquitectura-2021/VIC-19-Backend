package bo.ucb.edu.ingsoft.util;

import bo.ucb.edu.ingsoft.dao.CovidDataUpdateDao;
import bo.ucb.edu.ingsoft.dto.*;
import bo.ucb.edu.ingsoft.model.ContagionData;
import bo.ucb.edu.ingsoft.model.CovidData;
import bo.ucb.edu.ingsoft.model.Transaction;
import bo.ucb.edu.ingsoft.model.Vaccines;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Component
public class CovidDataJsonUtil {

    private static CovidDataUpdateDao covidDataUpdateDao;

    private Transaction transaction;

    @Autowired
    public void CovidDataU(CovidDataUpdateDao covidDataUpdateDao) {
        this.covidDataUpdateDao = covidDataUpdateDao;

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
                covidDataUpdateDao.insertData(covidData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getJsonMunicipios(){
        try {
            URL url = new URL("https://siip.produccion.gob.bo/repSIIP2/JsonAjaxCovid.php?flag=contagiados");
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            ContagionData contagionData =  mapper.readValue(responseStream, ContagionData.class);
            //String stringFecha = municipios1.getData_mapa().get("features").get(4).get("properties").get("_fecha_ultimo").asText();
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");Date converted = fecha.parse("04/04/2021");
            System.out.println(converted);
            ArrayList<MunicipalityData> municipalityData = new ArrayList();
            MunicipalityData municipalityData1;
            for(int i=0;i<contagionData.getDataMap().get("features").size();i++){
                municipalityData1 = new MunicipalityData(contagionData.getDataMap().get("features").get(i).get("properties").get("nom_dept").asText(),
                        contagionData.getDataMap().get("features").get(i).get("properties").get("nom_mun").asText(), converted,
                        contagionData.getDataMap().get("features").get(i).get("properties").get("_f_0709202").asInt());
                municipalityData.add(municipalityData1);
                //System.out.println(municipalityData.get(i));
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


}
