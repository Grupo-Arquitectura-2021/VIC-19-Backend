package bo.ucb.edu.ingsoft.util;

import bo.ucb.edu.ingsoft.bl.DataBl;
import bo.ucb.edu.ingsoft.dto.DatosVacuna;
import bo.ucb.edu.ingsoft.dto.HistorialCasos;
import bo.ucb.edu.ingsoft.dto.Vacunas;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CovidDataUrlUtil {
    private DataBl dataBl;

    public CovidDataUrlUtil(DataBl dataBl) {
        this.dataBl = dataBl;
    }

    public static ArrayList<DatosVacuna> getJson(){
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
            //System.out.println(vacuna.getCountry());
            //JSONObject casos = vacuna.getTimeline().get("3/31/21");
            //System.out.println(vacuna.getTimeline().size());
            //System.out.println(vacuna.getTimeline().get("3/30/21"));
            Calendar c = Calendar.getInstance();

            //System.out.println("Fecha original: " + formatearCalendar(c));
            // Restar 30 días
            c.add(Calendar.DAY_OF_YEAR, -29);
            //System.out.println("-30 días: " + formatearCalendar(c));

            DatosVacuna modelonuevo;
            for(int i=0;i<vacuna.getTimeline().size();i++){
                modelonuevo = new DatosVacuna(vacuna.getCountry(),formatearCalendar(c),(Integer) vacuna.getTimeline().get(formatearCalendar(c)));
                datosmodelo.add(modelonuevo);
                c.add(Calendar.DAY_OF_YEAR, 1);
                //System.out.println(datosmodelo.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //DatosBl.create(Datosmodelo);
        return datosmodelo;
    }

    public static void getJsonHistorico() {
        try {
            URL url = new URL("https://disease.sh/v3/covid-19/historical/Bolivia?lastdays=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            HistorialCasos historialcasos = mapper.readValue(responseStream, HistorialCasos.class);
            JSONObject casos = new JSONObject();
            Map tem = new LinkedHashMap();
            tem.put("cases",historialcasos.getTimeline().get("cases"));
            tem.put("deaths",historialcasos.getTimeline().get("deaths"));
            tem.put("recovered",historialcasos.getTimeline().get("recovered"));

            casos.put("timeline", tem);
            String jsonString = JSONValue.toJSONString(casos);
            System.out.println(historialcasos.getCountry());
            System.out.println(historialcasos.getProvince());
            System.out.println(casos.get("timeline"));
            System.out.println(jsonString.toString().trim());

            //System.out.println(responseStream);
            //System.out.println(casos.get("4/9/21"));
            //System.out.println(historialcasos.getTimeline().getJSONObject("deaths").get("3/31/21"));
            //System.out.println(historialcasos.getTimeline().getJSONObject("recovered").get("3/31/21"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String formatearCalendar(Calendar c) {
        Date date = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //System.out.println("Fecha: "+dateFormat1.format(date));
        //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return dateFormat1.format(c.getTime());
    }
}
