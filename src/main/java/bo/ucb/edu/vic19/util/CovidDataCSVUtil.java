package bo.ucb.edu.vic19.util;

import bo.ucb.edu.vic19.dao.*;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class CovidDataCSVUtil {

    private CovidDataCSVDao covidDao;
    private CityCovidDataDao cityCovidDataDao;
    private CityDao cityDao;
    private MunicipalityDao municipalityDao;
    private MunicipalityCovidDataDao municipalityCovidDataDao;
    private CountryDao countryDao;
    private CountryCovidDataDao countryCovidDataDao;


    @Autowired
    public void ScheduledTasks(CovidDataCSVDao covidDao, CityCovidDataDao cityCovidDataDao, CityDao cityDao, MunicipalityDao municipalityDao, MunicipalityCovidDataDao municipalityCovidDataDao,CountryDao countryDao, CountryCovidDataDao countryCovidDataDao) {
        this.covidDao = covidDao;
        this.cityCovidDataDao = cityCovidDataDao;
        this.cityDao = cityDao;
        this.municipalityDao = municipalityDao;
        this.municipalityCovidDataDao = municipalityCovidDataDao;
        this.countryDao=countryDao;
        this.countryCovidDataDao = countryCovidDataDao;
    }


/*
   @Scheduled(fixedRate = 30000L)
    @GetMapping(value="/who")
    public void whoCovidData() throws ParseException {
        String filePath ="C:/Users/Marioly/Desktop/datoscovidwho.csv";
        String url = "https://covid19.who.int/WHO-COVID-19-global-table-data.csv";
        if (creatingEmptyFile(filePath,url)){
            //readingFileData(filePath);
        }else{
            System.out.println("El archivo no se creó correctamente");
        }
    }

    @Scheduled(fixedRate = 30000L)
    @GetMapping(value="/arcGis")
    public void arcGisCovidData() throws ParseException {
        String filePath ="C:/Users/Marioly/Desktop/datoscovidarcgis.csv";
        String url = "https://opendata.arcgis.com/datasets/89873d02cfef44928668711cae827105_0.csv?outSR=%7B%22latestWkid%22%3A4326%2C%22wkid%22%3A4326%7D";
        if (creatingEmptyFile(filePath,url)){
            //readingFileDataArcGis(filePath);
        }else{
            System.out.println("El archivo no se creó correctamente");
        }
    }

    @Scheduled(fixedRate = 30000L)
    @GetMapping(value="/datosGob")
    public void datosGobCovidData() throws ParseException {
        String filePath ="C:/Users/Marioly/Desktop/datoscovidgob.csv";
        String url = "https://datos.gob.bo/dataset/8e2ef657-cfc3-44e1-8552-4a4883aa484e/resource/30cdcb68-ec87-42fb-b4a9-087d81f186c6/download/consolidado_covid_19-mun_se.csv";
        if (creatingEmptyFile(filePath,url)){
            readingFileDataDatosGob(filePath);
        }else{
            System.out.println("El archivo no se creó correctamente");
        }
    }*/

    @Async
    //@Scheduled(fixedRate = 30000L)
    @GetMapping(value="/csse")
    public void CSSECovidData() throws ParseException {
        String filePath = "C:/Users/Marioly/Desktop/datoscovidcsse.csv";
        String fecha ="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";

        //Restando 1 DIA A LA FECHA ACTUAL
        int calMinusOne = (cal.get(Calendar.DAY_OF_MONTH)- 1);
        int calMonth = (cal.get(Calendar.MONTH)+1);
        int calYear = (cal.get(Calendar.YEAR));
        if(calMonth<10){
            fecha+="0"+calMonth+"-";
            url+="0"+calMonth+"-";
            if(calMinusOne<10) {
                url += "0" + calMinusOne;
                fecha += "0" + calMinusOne;
            }else{
                url+= calMinusOne;
                fecha += calMinusOne;
            }
        }else{
            if(calMinusOne<10) {
                url += calMonth+"-"+"0" + calMinusOne;
                fecha += calMonth+"-"+"0" + calMinusOne;
            }else{
                url+=calMonth+"-"+calMinusOne;
                fecha +=calMonth+"-"+calMinusOne;
            }
        }
        url+="-"+calYear+".csv";
        fecha+="-"+calYear;

        if (creatingEmptyFile(filePath,url)){
            //readingFileDataCSSE(filePath);
        }else{
            System.out.println("El archivo no se creó correctamente");
        }
    }

    @Scheduled(fixedRate = 3000000L)
    @GetMapping(value="/ReadCSv")
    public void readCSV() throws IOException {
        List<LocationResponse> countries=countryDao.countries();
        List<String> countriesFound = new ArrayList<>();
        String url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
        String fecha ="";
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        int calMinusOne = cal1.get(Calendar.DAY_OF_MONTH)- 2;
        int calMonth = (cal1.get(Calendar.MONTH)+1);
        int calYear = (cal1.get(Calendar.YEAR));
        if(calMonth<10){
            fecha+="0"+calMonth+"-";
            url+="0"+calMonth+"-";
            if(calMinusOne<10) {
                url += "0" + calMinusOne;
                fecha += "0" + calMinusOne;
            }else{
                url+= calMinusOne;
                fecha += calMinusOne;
            }
        }else{
            if(calMinusOne<10) {
                url += calMonth+"-"+"0" + calMinusOne;
                fecha += calMonth+"-"+"0" + calMinusOne;
            }else{
                url+=calMonth+"-"+calMinusOne;
                fecha +=calMonth+"-"+calMinusOne;
            }
        }
        url+="-"+calYear+".csv";
        fecha+="-"+calYear;

        URL content = new URL(url);

        InputStream stream = content.openStream();
        Scanner inputStream = new Scanner(stream);
        List<CountryCovidData> covidCountries= new ArrayList<>();
        List<CovidData> covidDataList=new ArrayList<>();
        Date date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();
        int lastId = 0;
        int c2 = 0;
        String currentCountry = null, currentCountry2=null, auxString = null;
        boolean primero=false;
        int c = 0, aux=0;
        boolean reset=true, firstRound = true, flag = true, flag2 = true, repeated = false;
        while (inputStream.hasNext()) {
            String data = inputStream.nextLine();
            if (flag) {
                flag = false;
            } else {
                String[] values = data.split(",");
                currentCountry = values[3];

                String data2 = inputStream.nextLine();
                String[] values2 = data2.split(",");
                currentCountry2 = values2[3];

                if(reset) {
                    if(values[3].equals(auxString)){
                        c = aux;
                        flag2=false;
                        if(!values[3].equals(values2[3])){
                            c2+=Integer.parseInt(values[7]);

                        }
                    }else{
                        c = Integer.parseInt(values[7]);
                    }
                }


                if (currentCountry.equals(currentCountry2)) {
                    repeated = true;
                    if(flag2==false){
                        //System.out.println("values[7] flag: " + values[7]);
                        c += Integer.parseInt(values[7]);
                        //System.out.println("values2[7] flag: " + values2[7]);
                        c += Integer.parseInt(values2[7]);
                        firstRound = false;

                }else{
                        if (firstRound == true) {
                            //System.out.println("values2[7] first Round: " + values2[7]);
                            c += Integer.parseInt(values2[7]);
                            firstRound = false;
                        } else {
                            //System.out.println("values[7]: " + values[7]);
                            c += Integer.parseInt(values[7]);
                            //System.out.println("values2[7]: " + values2[7]);
                            c += Integer.parseInt(values2[7]);
                        }
                    }
                    reset = false;

                }else{
                    if(countryDao.countryId(currentCountry)!=null){
                        Transaction transaction = new Transaction();
                        transaction.setTxDate(date);

                        InetAddress direction = InetAddress.getLocalHost();
                        String IP_local = direction.getHostAddress();//ip como String

                        transaction.setTxHost(IP_local);
                        transaction.setTxId(1);
                        transaction.setTxUpdate(date);

                        CovidData covidData = new CovidData();

                        covidData.setVaccinated(-1);
                        covidData.setRecuperated(-1);
                        covidData.setConfirmedCases(-1);
                        covidData.setCumulativeCases(c);
                        covidData.setDeathCases(-1);
                        covidData.setVaccinated(-1);
                        covidData.setIdPageUrl(4);
                        covidData.setDate(convertDateb(fecha));
                        covidData.setStatus(1);
                        covidData.setTransaction(transaction);

                        covidDao.insertData(covidData);

                        lastId = covidDao.getCovidDataIdMax();

                        CountryCovidData countryCovidData=new CountryCovidData();
                        countryCovidData.setIdCountry(countryDao.countryId(currentCountry));
                        countryCovidData.setIdCovidData(lastId);
                        countryCovidData.setTransaction(transaction);
                        countryCovidDataDao.insertCountryCovidData(countryCovidData);
                    }
                    c2 = c;
                    reset = true;
                    firstRound=true;
                    flag2=true;
                }

                auxString = currentCountry2;
                aux = Integer.parseInt(values2[7]);
            }

        }

    }



    //FUNCIONES

    private void readingFileDataDatosGob(String filePath) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] datos = null;
        CovidData covidData;
        Transaction transaction;
        String city = null, municipality=null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date date;
        Municipality municipalityOb;
        int cityId, municipalityId, lastId;
        MunicipalityCovidData municipalityCovidData;
        //Restando 4 horas a la hora actual
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();
        int cont = 0;

        try {
            boolean flag = true;
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null && (cont<93)) {
                if(flag){
                    flag=false;
                }else {
                    municipalityOb = new Municipality();
                    municipalityCovidData =new MunicipalityCovidData();
                    covidData = new CovidData();

                    cont++;
                    datos = line.split(cvsSplitBy); // todos los datos
                    for (int i = 0; i < datos.length; i++) {

                        if ((i == 1)) {
                            city = datos[i];
                        }

                        if ((i == 3) ) {
                            municipality = datos [i];
                            municipalityOb.setMunicipality(municipality);
                        }

                        if ((i == 5)) {
                            covidData.setConfirmedCases(Integer.parseInt(datos[i]));
                        }

                        if ((i == 7)) {
                            covidData.setDeathCases(Integer.parseInt(datos[i]));
                        }

                        if ((i == 8)) {
                            covidData.setRecuperated(Integer.parseInt(datos[i]));
                        }
                    }

                    transaction = new Transaction();
                    transaction.setTxDate(date);

                    InetAddress direction = InetAddress.getLocalHost();
                    String IP_local = direction.getHostAddress();//ip como String

                    transaction.setTxHost(IP_local);
                    transaction.setTxId(1);
                    transaction.setTxUpdate(date);


                    covidData.setCumulativeCases(-1);
                    covidData.setVaccinated(-1);
                    covidData.setIdPageUrl(4);
                    covidData.setDate(convertDate("2020-12-03"));
                    covidData.setStatus(1);
                    covidData.setTransaction(transaction);
                    municipalityOb.setTransaction(transaction);

                    covidDao.insertData(covidData);

                    cityId = cityDao.getCityId(city);
                    municipalityOb.setIdCity(cityId);
                    //System.out.println("ID ciudad "+cityId);
                    municipalityDao.insertMunicipalityData(municipalityOb);

                    lastId = covidDao.getCovidDataIdMax();
                    municipalityId = municipalityDao.getMunicipalityId(municipality);

                    municipalityCovidData.setIdCovidData(lastId);
                    municipalityCovidData.setIdMunicipality(municipalityId);
                    municipalityCovidData.setTransaction(transaction);

                    municipalityCovidDataDao.insertMunicipalityCovidData(municipalityCovidData);

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

    public boolean creatingEmptyFile(String filePath, String url) throws ParseException {
        try {
            File file = new File(filePath);
            file.createNewFile();
            System.out.println("Se crea archivo csv vacio " + file.length());
        } catch (IOException e) {
            System.out.println("Path no identificado");
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
                return true;
            } catch (IOException ioExObj) {
                System.out.println("Error al descargar el archivo: " + ioExObj.getMessage());
                return false;
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
                    return false;
                }
            }
        } else {
            System.out.println("El archivo no se encuentra");
            return false;
        }
    }

    public void readingFileDataCSSE(String filePath) throws ParseException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] datos = null;
        String datoString = null;
        CovidData covidData;
        String country = "Bolivia";
        Transaction transaction;
        //String country = "Bolivia (Plurinational State of)";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date date;
        boolean flag=false;

        //Restando 4 horas a la hora actual
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                datos = line.split(cvsSplitBy); // todos los datos
                for (int i=0; i< datos.length; i++) {
                    datoString = datos[i];
                    if (datoString.equals(country)&&(flag==false)) {
                        flag=true;
                        //System.out.println("Encuentra Bolivia");
                        transaction = new Transaction();
                        transaction.setTxDate(date);

                        InetAddress direccion = InetAddress.getLocalHost();
                        String IP_local = direccion.getHostAddress();//ip como String

                        transaction.setTxHost(IP_local);
                        transaction.setTxId(1);
                        transaction.setTxUpdate(date);


                        covidData = new CovidData();
                        covidData.setConfirmedCases(-1);
                        covidData.setCumulativeCases(Integer.parseInt(datos[7]));
                        covidData.setDeathCases(-1);
                        covidData.setRecuperated(Integer.parseInt(datos[9]));
                        covidData.setVaccinated(-1);
                        covidData.setIdPageUrl(3);
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
                        covidData.setIdPageUrl(1);
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
                        covidData.setIdPageUrl(2);
                        covidData.setDate(date);
                        covidData.setStatus(1);
                        covidData.setTransaction(transaction);

                        covidDao.insertData(covidData);


                        city = datos[6];
                        if(!city.equals("Unassigned")) {
                            //System.out.println(city);
                            cityId = cityDao.getCityId(datos[6]);
                            //System.out.println(cityId);
                            lastId = covidDao.getCovidDataIdMax();
                            //System.out.println(lastId);

                        cityCovidData = new CityCovidData();
                        cityCovidData.setIdCity(cityId);
                        cityCovidData.setIdCovidData(lastId);
                        cityCovidData.setTransaction(transaction);

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

    public static Date convertDate(String fecha)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date convertDateb(String fecha)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;

        try {
            date = formatter.parse(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
