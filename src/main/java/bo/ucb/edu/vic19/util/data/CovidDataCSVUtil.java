package bo.ucb.edu.vic19.util.data;

import bo.ucb.edu.vic19.dao.*;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.LocationResponse;
import bo.ucb.edu.vic19.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

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

import org.apache.commons.csv.*;

@Component
public class CovidDataCSVUtil {

    private CovidDataDao covidDataDao;
    private CityDao cityDao;
    private MunicipalityDao municipalityDao;
    private CountryDao countryDao;

    public static String TYPE = "text/csv";

    @Autowired
    public void ScheduledTasks(CovidDataDao covidDataDao, CityDao cityDao, MunicipalityDao municipalityDao, CountryDao countryDao) {

        this.covidDataDao = covidDataDao;
        this.cityDao = cityDao;
        this.municipalityDao = municipalityDao;
        this.countryDao = countryDao;
    }



    @Scheduled(fixedRate = 3000000L)
    @GetMapping(value="/ReadCSv")
    public void readCSV() throws IOException, ParseException {
        List<LocationResponse> cities=cityDao.citiesLocation();
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
        String varAux=inputStream.nextLine();
        String varAux2=inputStream2.nextLine(); //2?
       while (inputStream.hasNext()) {
            String data = varAux;
           String data2 = inputStream.nextLine();

           String death = varAux2;
           String death2 = inputStream2.nextLine();
            String[] values = data.split(",");
           String[] values2 = data2.split(",");
           String[] deathV = death.split(",");
           String[] deathV2 = death2.split(",");
           varAux=data2;
           varAux2=death;
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
            i++;
            if(i>300){
                break;
            }
        }
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        System.out.println(file.getContentType());
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("text/csv")||file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }

    public static List<CovidDataRequest> csvToDataCsvRequest(InputStream is, boolean municipio) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<CovidDataRequest> dataCountryCsvRequestList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CovidDataRequest dataCountryCsvRequest = new CovidDataRequest();
                dataCountryCsvRequest.setDateLocationCovid(csvRecord.get(0));
                dataCountryCsvRequest.setNameLocationCovid(municipio?csvRecord.get(1)+"-"+csvRecord.get(2):csvRecord.get(1));
                dataCountryCsvRequest.setDeathCases(Integer.valueOf(csvRecord.get(municipio?3:2)));
                dataCountryCsvRequest.setConfirmedCases(Integer.valueOf(csvRecord.get(municipio?4:3)));
                dataCountryCsvRequest.setRecuperated(Integer.valueOf(csvRecord.get(municipio?5:4)));
                dataCountryCsvRequest.setVaccinated(Integer.valueOf(csvRecord.get(municipio?6:5)));
                dataCountryCsvRequest.setCumulativeCases(Integer.valueOf(csvRecord.get(municipio?7:6)));
                dataCountryCsvRequestList.add(dataCountryCsvRequest);
            }
            return dataCountryCsvRequestList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }




}

