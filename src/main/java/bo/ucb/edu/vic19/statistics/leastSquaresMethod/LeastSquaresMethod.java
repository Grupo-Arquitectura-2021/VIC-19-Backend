package bo.ucb.edu.vic19.statistics.leastSquaresMethod;

import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestLeastSquares;
import bo.ucb.edu.vic19.model.PeriodQuantity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeastSquaresMethod {
    public String blName;
    public CountryDao countryDao;
    public String dateCovid;
    public Integer countryId;
    public Integer period;
    public String forecastDate;
    public List<CovidDataRequest> covidDataList;
    public ArrayList<PeriodQuantity> periodQuantities = new ArrayList<PeriodQuantity>();
    public float n,dif,dayAux;
    public boolean flagFirstVac = true, flagFirstConf =true, flagSaveVac=false;
    public CovidDataRequestLeastSquares covidDataRequestLeastSquares = new CovidDataRequestLeastSquares();


    public LeastSquaresMethod(CountryDao countryDao, String forecastDate, String countryDaoName, int countryId, String dateCovid) {
        this.countryDao = countryDao;
        this.blName = countryDaoName;
        this.dateCovid = dateCovid;
        this.countryId = countryId;
        this.forecastDate = forecastDate;
    }

    public String getBlName(){
        return blName;
    }

    public CovidDataRequestLeastSquares assignCovidDataAccordingToBlName(){
        switch (blName){
            case "CountryBl":
                covidDataList=countryDao.covidDataListCountryAllInfo(countryId, dateCovid);
                covidDataRequestLeastSquares.setNameLocationCovid(countryDao.countryName(countryId));
                determinePeriod(covidDataList);
                break;
        }

        return covidDataRequestLeastSquares;
    }

    private void getVariables(List<CovidDataRequest> covidDataList) {
        n = covidDataList.size();
        for(int i=0; i<n; i++){
            if(flagFirstVac) {
                if (covidDataList.get(i).getVaccinated() >= 0) {
                    Date datePeriod;
                    datePeriod = parseFecha(covidDataList.get(i).getDateLocationCovid());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePeriod);
                    dayAux = cal.get(Calendar.DAY_OF_MONTH);
                    PeriodQuantity periodQuantity = new PeriodQuantity(1, covidDataList.get(i).getVaccinated());
                    periodQuantities.add(periodQuantity);
                    flagFirstVac = false;
                }
            }else{
                if (covidDataList.get(i).getVaccinated() >= 0) {
                    Date datePeriod;
                    datePeriod = parseFecha(covidDataList.get(i).getDateLocationCovid());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePeriod);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    dif = day - dayAux;
                    dayAux = day;
                    //System.out.println("fecha: "+covidDataList.get(i).getDateLocationCovid());
                    //System.out.println("dif "+dif);
                    int period = (int) ((periodQuantities.get(periodQuantities.size()-1).getPeriod())+1);
                    PeriodQuantity periodQuantity = new PeriodQuantity(period, covidDataList.get(i).getVaccinated());
                    periodQuantities.add(periodQuantity);
                }
            }

            leastSquaresMethod(periodQuantities,"Vac");

            //leastSquaresMethod(periodQuantities,"Conf");
        }

    }

    private void determinePeriod(List<CovidDataRequest> covidDataList) {
        int dayForecast = 0, lastDay =0;
        Date datePeriod, datePeriodf;
        datePeriod = parseFecha(covidDataList.get(0).getDateLocationCovid());
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePeriod);
        lastDay = cal.get(Calendar.DAY_OF_MONTH);

        datePeriodf = parseFecha(forecastDate);
        Calendar calf = Calendar.getInstance();
        calf.setTime(datePeriodf);
        dayForecast = calf.get(Calendar.DAY_OF_MONTH);
        System.out.println("dayFo "+dayForecast);
        System.out.println("dayLast "+lastDay);
        period = dayForecast - lastDay;
        if(period<=0){
            covidDataRequestLeastSquares.setDateLocationCovid("No se pueden realizar proyecciones del dia actual o dias anteriores");
        }else{
            covidDataRequestLeastSquares.setDateLocationCovid(forecastDate);
            getVariables(covidDataList);
        }
    }

    private void leastSquaresMethod(ArrayList<PeriodQuantity> periodQuantities, String name) {
        float x = 0,y = 0,x2 = 0,y2 = 0,xy = 0,b=0,a=0,growthPercentage=0, forecast =0;
        float n = periodQuantities.size();
        for(int i=-0; i<periodQuantities.size(); i++){
            x+=periodQuantities.get(i).getPeriod();
            y+=periodQuantities.get(i).getQuantity();
            x2+= (float) Math.pow(periodQuantities.get(i).getPeriod(),2);
            y2+= (float) Math.pow(periodQuantities.get(i).getQuantity(),2);
            xy+=periodQuantities.get(i).getPeriod()*periodQuantities.get(i).getQuantity();
        }

        b = (float) (((n*xy)-(x*y))/((n*x2)-(Math.pow(x,2))));
        a = (y-(b*x)) /n;
        growthPercentage = ((b*n)/y)*100;
        forecast = a + (b*period);

        switch (name){
            case "Vac":
                covidDataRequestLeastSquares.setVacForecast(forecast);
                covidDataRequestLeastSquares.setVacPercentage(growthPercentage);
                break;
            case "Conf":
                covidDataRequestLeastSquares.setConfForecast(forecast);
                covidDataRequestLeastSquares.setConfPercentage(growthPercentage);
                break;
        }
    }

    private Date parseFecha(String dateLocationCovid) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(dateLocationCovid);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
}
