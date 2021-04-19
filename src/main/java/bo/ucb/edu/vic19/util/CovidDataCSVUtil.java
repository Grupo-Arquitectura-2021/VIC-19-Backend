package bo.ucb.edu.vic19.util;

import bo.ucb.edu.vic19.dao.*;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.*;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


@Component
public class CovidDataCSVUtil {

    private CovidDataDao covidDataDao;
    private CityDao cityDao;
    private MunicipalityDao municipalityDao;
    private CountryDao countryDao;


    @Autowired
    public void ScheduledTasks(CovidDataDao covidDataDao, CityDao cityDao, MunicipalityDao municipalityDao, CountryDao countryDao) {

        this.covidDataDao = covidDataDao;
        this.cityDao = cityDao;
        this.municipalityDao = municipalityDao;
        this.countryDao = countryDao;
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

    @Scheduled(fixedRate = 3000000L)
    @GetMapping(value="/ReadCSv")
    public void readCSV() throws IOException, ParseException {
        List<LocationResponse> cities=cityDao.cities();
        String url="https://raw.githubusercontent.com/mauforonda/covid19-bolivia/opsoms/confirmados.csv";
        String url2="https://raw.githubusercontent.com/mauforonda/covid19-bolivia/opsoms/decesos.csv";

        URL content = new URL(url);
        URL content2 = new URL(url2);
        InputStream stream = content.openStream();
        InputStream stream2 = content2.openStream();
        Scanner inputStream = new Scanner(stream);
        Scanner inputStream2 = new Scanner(stream2);
        Date date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)- 4);
        date = cal.getTime();
        List<CovidData> covidDataList=new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTxDate(date);

        InetAddress direction = InetAddress.getLocalHost();
        String IP_local = direction.getHostAddress();//ip como String

        transaction.setTxHost(IP_local);
        transaction.setTxId(1);
        transaction.setTxUpdate(date);
        cities.forEach((co)->{
            CovidData covidData=new CovidData();
            covidData.setConfirmedCases(0);
            covidData.setDeathCases(0);
            covidData.setRecuperated(-1);
            covidData.setStatus(1);
            covidData.setIdPageUrl(3);
            covidData.setVaccinated(-1);
            covidData.setCumulativeCases(0);
            covidData.setTransaction(transaction);
            covidDataList.add(covidData);
        });
        inputStream.nextLine();
        inputStream2.nextLine();
        int i=0;
        Integer idCovidData;
        CityCovidData cityCovidData;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Integer verifyCities=1;
       while (inputStream.hasNext()) {
            String data = inputStream.nextLine();
           String data2 = inputStream.nextLine();

           String death = inputStream2.nextLine();
           String death2 = inputStream2.nextLine();
            String[] values = data.split(",");
           String[] values2 = data2.split(",");
           String[] deathV = death.split(",");
           String[] deathV2 = death2.split(",");
            covidDataList.get(0).setConfirmedCases(Integer.parseInt(values[1])-Integer.parseInt(values2[1]));
           covidDataList.get(0).setDeathCases(Integer.parseInt(deathV[1])-Integer.parseInt(deathV2[1]));
            covidDataList.get(0).setCumulativeCases(Integer.parseInt(values[1]));

           covidDataList.get(1).setConfirmedCases(Integer.parseInt(values[3])-Integer.parseInt(values2[3]));
           covidDataList.get(1).setDeathCases(Integer.parseInt(deathV[3])-Integer.parseInt(deathV2[3]));
           covidDataList.get(1).setCumulativeCases(Integer.parseInt(values[3]));

           covidDataList.get(2).setConfirmedCases(Integer.parseInt(values[2])-Integer.parseInt(values2[2]));
           covidDataList.get(2).setDeathCases(Integer.parseInt(deathV[2])-Integer.parseInt(deathV2[2]));
           covidDataList.get(2).setCumulativeCases(Integer.parseInt(values[2]));

           covidDataList.get(3).setConfirmedCases(Integer.parseInt(values[6])-Integer.parseInt(values2[6]));
           covidDataList.get(3).setDeathCases(Integer.parseInt(deathV[6])-Integer.parseInt(deathV2[6]));
           covidDataList.get(3).setCumulativeCases(Integer.parseInt(values[6]));

           covidDataList.get(4).setConfirmedCases(Integer.parseInt(values[7])-Integer.parseInt(values2[7]));
           covidDataList.get(4).setDeathCases(Integer.parseInt(deathV[7])-Integer.parseInt(deathV2[7]));
           covidDataList.get(4).setCumulativeCases(Integer.parseInt(values[7]));

           covidDataList.get(5).setConfirmedCases(Integer.parseInt(values[4])-Integer.parseInt(values2[4]));
           covidDataList.get(5).setDeathCases(Integer.parseInt(deathV[4])-Integer.parseInt(deathV2[4]));
           covidDataList.get(5).setCumulativeCases(Integer.parseInt(values[4]));

           covidDataList.get(6).setConfirmedCases(Integer.parseInt(values[9])-Integer.parseInt(values2[9]));
           covidDataList.get(6).setDeathCases(Integer.parseInt(deathV[9])-Integer.parseInt(deathV2[9]));
           covidDataList.get(6).setCumulativeCases(Integer.parseInt(values[9]));

           covidDataList.get(7).setConfirmedCases(Integer.parseInt(values[8])-Integer.parseInt(values2[8]));
           covidDataList.get(7).setDeathCases(Integer.parseInt(deathV[8])-Integer.parseInt(deathV2[8]));
           covidDataList.get(7).setCumulativeCases(Integer.parseInt(values[8]));

           covidDataList.get(8).setConfirmedCases(Integer.parseInt(values[5])-Integer.parseInt(values2[5]));
           covidDataList.get(8).setDeathCases(Integer.parseInt(deathV[5])-Integer.parseInt(deathV2[5]));
           covidDataList.get(8).setCumulativeCases(Integer.parseInt(values[5]));

           for(int j=0;j<covidDataList.size();j++){
               covidDataList.get(j).setDate((fmt.parse(values[0])));
               verifyCities=covidDataDao.verifyCityCovidData(values[0],cities.get(j).getIdLocation());

               if(verifyCities==0){
                   covidDataDao.insertCovidData(covidDataList.get(j));
                   idCovidData=covidDataDao.getLastIdCovidData();
                   cityCovidData=new CityCovidData();
                   cityCovidData.setIdCity(cities.get(j).getIdLocation());
                   cityCovidData.setIdCovidData(idCovidData);
                   cityCovidData.setStatus(1);
                   cityCovidData.setTransaction(transaction);
                   covidDataDao.insertCityCovidData(cityCovidData);
               }
           }
            i+=2;
            if(i>100){
                break;
            }
        }
       /*
        Integer lastId=1;
       Integer verify=1;
        for(int i=0;i<covidDataList.size();i++){
            if(covidDataList.get(i).getConfirmedCases()!=0&&covidDataList.get(i).getDeathCases()!=0&&covidDataList.get(i).getRecuperated()!=0){
                verify=covidDataDao.verifyCountryCovidData(date.toString(),countries.get(i).getIdLocation());
                if(verify==0){
                    covidDataDao.insertCovidData(covidDataList.get(i));
                    lastId= covidDataDao.getLastIdCovidData();
                    CountryCovidData countryCovidData=new CountryCovidData();
                    countryCovidData.setIdCountry(countries.get(i).getIdLocation());
                    countryCovidData.setIdCovidData(lastId);
                    countryCovidData.setTransaction(transaction);
                    covidDataDao.insertCountryCovidData(countryCovidData);
                }
            }
        }*/
    }
}
        /*while (inputStream.hasNext()) {
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

    }*/



    //FUNCIONES
/*
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

                        covidDataDao.insertCityCovidData(cityCovidData);
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
    }*/

