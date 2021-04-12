package bo.ucb.edu.ingsoft.util;

import bo.ucb.edu.ingsoft.dao.CovidDataJsonDao;
import bo.ucb.edu.ingsoft.dto.DatosCasos;
import bo.ucb.edu.ingsoft.dto.DatosVacuna;
import bo.ucb.edu.ingsoft.dto.HistorialCasos;
import bo.ucb.edu.ingsoft.dto.Vacunas;
import bo.ucb.edu.ingsoft.model.CovidData;
import bo.ucb.edu.ingsoft.model.Transaction;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CovidDataUrlUtil {

    private static CovidDataJsonDao covidDataJsonDao;

    private Transaction transaction;

    @Autowired
    public void CovidDataU(CovidDataJsonDao covidDataJsonDao) {
        this.covidDataJsonDao = covidDataJsonDao;

    }

    public static ArrayList<DatosCasos> getJson2(){
        ArrayList<DatosCasos> datosCasos = new ArrayList();
        JSONParser parser = new JSONParser();
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/historical/Bolivia?lastdays");
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            HistorialCasos historialcasos = mapper.readValue(responseStream, HistorialCasos.class);

            String str11 = historialcasos.getTimeline().toString();
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
            DatosCasos datosCasos1;

            for(int i=0;i<innerObjectRec.size();i++){
                datosCasos1 = new DatosCasos(historialcasos.getCountry(),date,
                        Integer.parseInt(""+innerObjectCas.get(formatearCalendar(cal))),
                        Integer.parseInt(""+innerObjectDea.get(formatearCalendar(cal))),
                        Integer.parseInt(""+innerObjectRec.get(formatearCalendar(cal))));
                datosCasos.add(datosCasos1);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                date = cal.getTime();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datosCasos;
    }
    public static void getJson() {
        ArrayList<DatosCasos> datoscasos = new ArrayList();
        datoscasos = getJson2();
        ArrayList<DatosVacuna> datosmodelo = new ArrayList();
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            Vacunas vacuna = mapper.readValue(responseStream, Vacunas.class);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, -29);

            DatosVacuna modelonuevo;
            for(int i=0;i<vacuna.getTimeline().size();i++){
                modelonuevo = new DatosVacuna(vacuna.getCountry(),formatearCalendar(c),(Integer) vacuna.getTimeline().get(formatearCalendar(c)));
                datosmodelo.add(modelonuevo);
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

            for(int i=0;i<datosmodelo.size();i++){
                covidData = new CovidData();
                transaction = new Transaction();
                transaction.setTxDate(date);

                InetAddress direccion = InetAddress.getLocalHost();
                String IP_local = direccion.getHostAddress();//ip como String

                transaction.setTxHost(IP_local);
                transaction.setTxId(1);
                transaction.setTxUpdate(date);
                covidData.setIdCountry(1);
                covidData.setIdPageUrl(3);
                covidData.setDeathCases(datoscasos.get(i).getDeaths());
                covidData.setConfirmedCases(datoscasos.get(i).getCases());
                covidData.setVaccinated(datosmodelo.get(i).getAmountData());
                covidData.setCumulativeCases(datoscasos.get(i).getCases());
                covidData.setRecuperated(datoscasos.get(i).getRecovered());
                covidData.setDate(datoscasos.get(i).getDateCas());
                covidData.setTransaction(transaction);
                covidDataJsonDao.insertDataJson(covidData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getJsonUrls() throws IOException {
                // Datos de Vacunas en bolivia
        //URL url = new URL("https://disease.sh/v3/covid-19/vaccine/coverage/countries/Bolivia?lastdays");
                // Datos de Casos Muertes y Recuperados de Bolivia
        URL url = new URL("https://disease.sh/v3/covid-19/historical/Bolivia?lastdays=1");
                // Datos de todos los paises registrados en el swagger
        //URL url = new URL("https://disease.sh/v3/covid-19/historical?lastdays=1");

        //URL url = new URL("https://opendata.arcgis.com/datasets/89873d02cfef44928668711cae827105_0.geojson?where=ISO3_CODE%20%3D%20'BOL'");
        //URL url = new URL("http://observatoriocovid19.lapaz.bo/obsadmin/sit.php");

        HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        con.setRequestMethod("GET");
        //con.setRequestProperty("User-Agent","Mozilla/5.0");
        con.setRequestProperty("accept", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("\n Sending 'GET' request to URL :"+url);
        System.out.println("\n Response Code :"+responseCode);


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
    }

    public static String formatearCalendar(Calendar c) {
        Date date = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        return dateFormat1.format(c.getTime());
    }
}
