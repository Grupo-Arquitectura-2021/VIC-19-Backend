package bo.ucb.edu.ingsoft.util;

import bo.ucb.edu.ingsoft.dao.CityCovidDataDao;
import bo.ucb.edu.ingsoft.dao.CityDao;
import bo.ucb.edu.ingsoft.dao.CovidDataUpdateDao;
import bo.ucb.edu.ingsoft.model.CityCovidData;
import bo.ucb.edu.ingsoft.model.CovidData;
import bo.ucb.edu.ingsoft.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

@Component
public class CovidDataUpdateUtil {

    private CovidDataUpdateDao covidDao;
    private CityCovidDataDao cityCovidDataDao;
    private CityDao cityDao;

    @Autowired
    public void ScheduledTasks(CovidDataUpdateDao covidDao, CityCovidDataDao cityCovidDataDao, CityDao cityDao) {
        this.covidDao = covidDao;
        this.cityCovidDataDao = cityCovidDataDao;
        this.cityDao = cityDao;
    }


    @Scheduled(fixedRate = 10000L)
    @GetMapping(value="/who")
    public void whoCovidData() throws ParseException {
        String filePath ="C:/Users/Marioly/Desktop/datoscovidwho.csv";
        String url = "https://covid19.who.int/WHO-COVID-19-global-table-data.csv";
        creatingEmptyFile(filePath,url);
        readingFileData(filePath);
    }

    @Scheduled(fixedRate = 10000L)
    @GetMapping(value="/arcGis")
    public void arcGisCovidData() throws ParseException {
        String filePath ="C:/Users/Marioly/Desktop/datoscovidarcgis.csv";
        String url = "https://opendata.arcgis.com/datasets/89873d02cfef44928668711cae827105_0.csv?outSR=%7B%22latestWkid%22%3A4326%2C%22wkid%22%3A4326%7D";
        creatingEmptyFile(filePath,url);
        readingFileDataArcGis(filePath);
    }


    //FUNCIONES
    public void creatingEmptyFile(String filePath, String url) throws ParseException {
        try {
            File file = new File(filePath);
            file.createNewFile();
            System.out.println("Se crea archivo csv vacio " + file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Se descarga el archivo
        URL urlObj = null;
        ReadableByteChannel rbcObj = null;
        FileOutputStream fOutStream  = null;

        // Se verifica si el archivo existe en la locacion especificada
        Path filePathObj = Paths.get(filePath);
        boolean fileExists = Files.exists(filePathObj);
        if(fileExists) {
            System.out.println("Archivo: "+filePathObj);
            try {
                urlObj = new URL(url);
                rbcObj = Channels.newChannel(urlObj.openStream());
                fOutStream = new FileOutputStream(filePath);

                fOutStream.getChannel().transferFrom(rbcObj, 0, Long.MAX_VALUE);
                //Where member variables are declared:
                System.out.println("El archivo se descargó correctamente");
            } catch (IOException ioExObj) {
                System.out.println("Error al descargar el archivo: " + ioExObj.getMessage());
            } finally {
                try {
                    if(fOutStream != null){
                        fOutStream.close();
                    }
                    if(rbcObj != null) {
                        rbcObj.close();
                    }
                } catch (IOException ioExObj) {
                    System.out.println("Error al cerrar el objeto: " + ioExObj.getMessage());
                }
            }
        } else {
            System.out.println("El archivo no se encuentra");
        }
    }

    public void readingFileData(String filePath) throws ParseException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] datos = null;
        String datoString = null;
        CovidData covidData;
        Transaction transaction;
        String country = "Bolivia (Plurinational State of)";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date date;

        //Restando 4 horas a la hora actual
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                datos = line.split(cvsSplitBy); // todos los datos
                for (int i=0; i< datos.length; i++) {
                    datoString = datos[i];
                    if (datoString.equals(country)) {
                        //System.out.println("Encuentra Bolivia");
                        transaction = new Transaction();
                        transaction.setTxDate(date);

                        InetAddress direccion = InetAddress.getLocalHost();
                        String IP_local = direccion.getHostAddress();//ip como String

                        transaction.setTxHost(IP_local);
                        transaction.setTxId(1);
                        transaction.setTxUpdate(date);


                        covidData = new CovidData();
                        covidData.setConfirmedCases(Integer.parseInt(datos[6]));
                        covidData.setCumulativeCases(Integer.parseInt(datos[2]));
                        covidData.setDeathCases(Integer.parseInt(datos[11]));
                        covidData.setRecuperated(0);
                        covidData.setVaccinated(0);
                        covidData.setIdCountry(1);
                        covidData.setIdPageUrl(2);
                        covidData.setDate(date);
                        covidData.setStatus(1);
                        covidData.setTransaction(transaction);

                        covidDao.insertData(covidData);

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readingFileDataArcGis(String filePath) throws ParseException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] datos = null;
        String datoString = null;
        CovidData covidData;
        Transaction transaction;
        String country = "BOLIVIA (PLURINATIONAL STATE OF)";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date date;
        int lastId = 0, cityId = 0;
        String city;
        CityCovidData cityCovidData;

        //Restando 4 horas a la hora actual
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                datos = line.split(cvsSplitBy); // todos los datos
                for (int i=0; i< datos.length; i++) {
                    datoString = datos[i];
                    if (datoString.equals(country)) {
                        //System.out.println("Encuentra Bolivia");
                        transaction = new Transaction();
                        transaction.setTxDate(date);

                        InetAddress direccion = InetAddress.getLocalHost();
                        String IP_local = direccion.getHostAddress();//ip como String

                        transaction.setTxHost(IP_local);
                        transaction.setTxId(1);
                        transaction.setTxUpdate(date);


                        covidData = new CovidData();
                        covidData.setConfirmedCases(Integer.parseInt(datos[13]));
                        covidData.setCumulativeCases(Integer.parseInt(datos[11]));
                        covidData.setDeathCases(Integer.parseInt(datos[14]));
                        covidData.setRecuperated(0);
                        covidData.setVaccinated(0);
                        covidData.setIdCountry(1);
                        covidData.setIdPageUrl(1);
                        covidData.setDate(date);
                        covidData.setStatus(1);
                        covidData.setTransaction(transaction);

                        covidDao.insertData(covidData);


                        city = datos[6];
                        if(!city.equals("Unassigned")) {
                            System.out.println(city);
                            cityId = cityDao.getCityId(datos[6]); //sale null, por que?
                            System.out.println(cityId);
                            lastId = covidDao.getCovidDataId();
                            System.out.println(lastId);

                        cityCovidData = new CityCovidData();
                        cityCovidData.setIdCity(cityId);
                        cityCovidData.setIdCovidData(lastId);
                        cityCovidData.setTransaction(transaction);


                        System.out.println("city id: "+cityCovidData.getIdCity());
                        System.out.println("idcoviddata: "+cityCovidData.getIdCovidData());

                        cityCovidDataDao.insertCityCovidData(cityCovidData);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
