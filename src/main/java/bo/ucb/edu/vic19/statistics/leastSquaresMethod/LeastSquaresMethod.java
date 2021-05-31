package bo.ucb.edu.vic19.statistics.leastSquaresMethod;

import bo.ucb.edu.vic19.dao.CityDao;
import bo.ucb.edu.vic19.dao.CountryDao;
import bo.ucb.edu.vic19.dao.MunicipalityDao;
import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestLeastSquares;
import bo.ucb.edu.vic19.model.PeriodQuantity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeastSquaresMethod {
    public String blName;
    public String dateCovid;
    public String locationName;
    public Integer period;
    public float totalPeriod;
    public String forecastDate;
    public List<CovidDataRequest> covidDataList;
    public List<CovidDataRequest> covidDataListDESC;
    public ArrayList<PeriodQuantity> periodQuantitiesVac = new ArrayList<PeriodQuantity>(),periodQuantitiesConf = new ArrayList<PeriodQuantity>(),periodQuantitiesDeath = new ArrayList<PeriodQuantity>(),periodQuantitiesRec = new ArrayList<PeriodQuantity>();
    public float n,dif,dayAux;
    public boolean flagFirst=true;
    private CovidDataRequestLeastSquares covidDataRequestLeastSquares = new CovidDataRequestLeastSquares();


    public LeastSquaresMethod(List<CovidDataRequest> covidDataList, List<CovidDataRequest> covidDataListDESC, String forecastDate, String locationName) {
        this.covidDataList=covidDataList;
        this.covidDataListDESC=covidDataListDESC;
        this.locationName = locationName;
        this.forecastDate = forecastDate;
    }

    public CovidDataRequestLeastSquares getCovidDataRequestLeastSquares() {
        determinePeriod();
        return covidDataRequestLeastSquares;
    }

    private void getVariables() {
        n = covidDataList.size();
        for(int i=0; i<n; i++){
            if(flagFirst) {
                if (covidDataList.get(i).getVaccinated() >= 0) {
                    Date datePeriod;
                    datePeriod = parseFecha(covidDataList.get(i).getDateLocationCovid());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePeriod);
                    dayAux = cal.get(Calendar.DAY_OF_MONTH);
                    periodQuantitiesVac.add(new PeriodQuantity(1, covidDataList.get(i).getVaccinated()));
                    periodQuantitiesConf.add(new PeriodQuantity(1, covidDataList.get(i).getConfirmedCases()));
                    periodQuantitiesRec.add(new PeriodQuantity(1, covidDataList.get(i).getRecuperated()));
                    periodQuantitiesDeath.add(new PeriodQuantity(1, covidDataList.get(i).getDeathCases()));
                    flagFirst = false;
                }
            }else{
                if (covidDataList.get(i).getVaccinated() >= 0) {
                    Date datePeriod;
                    datePeriod = parseFecha(covidDataList.get(i).getDateLocationCovid());
                    System.out.println("fecha covid: "+covidDataList.get(i).getDateLocationCovid());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePeriod);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    dif = Math.abs(day - dayAux);
                    dayAux = day;
                    //System.out.println("fecha: "+covidDataList.get(i).getDateLocationCovid());
                    System.out.println("dif "+dif);
                    int periodq = (int) ((periodQuantitiesVac.get(periodQuantitiesVac.size()-1).getPeriod())+dif);
                    if(periodq == 1){
                        periodq = (int) ((periodQuantitiesVac.get(periodQuantitiesVac.size()-1).getPeriod())+1);
                    }
                    periodQuantitiesVac.add(new PeriodQuantity(periodq, covidDataList.get(i).getVaccinated()));
                    periodQuantitiesConf.add(new PeriodQuantity(periodq, covidDataList.get(i).getConfirmedCases()));
                    periodQuantitiesRec.add(new PeriodQuantity(periodq, covidDataList.get(i).getRecuperated()));
                    periodQuantitiesDeath.add(new PeriodQuantity(periodq, covidDataList.get(i).getDeathCases()));
                }
            }

            leastSquaresMethod();

            //leastSquaresMethod(periodQuantities,"Conf");
        }

    }

    private void determinePeriod() {
        int dayForecast = 0, lastDay =0;

        Date datePeriod, datePeriodf, dateTotal;
        datePeriod = parseFecha(covidDataListDESC.get(0).getDateLocationCovid());
        System.out.println("primer dato fecha "+covidDataListDESC.get(0).getDateLocationCovid());
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePeriod);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        //lastDay = cal.get(Calendar.DAY_OF_MONTH);

        datePeriodf = parseFecha(forecastDate);
        Calendar calf = Calendar.getInstance();
        calf.setTime(datePeriodf);
        //dayForecast = calf.get(Calendar.DAY_OF_MONTH);
        calf.set(Calendar.HOUR,0);
        calf.set(Calendar.HOUR_OF_DAY,0);
        calf.set(Calendar.MINUTE,0);
        calf.set(Calendar.SECOND,0);

        long startTime =  cal.getTimeInMillis();
        long endTime = calf.getTimeInMillis();
        long diffTime = Math.abs(endTime - startTime);
        period = (int) diffTime/(1000*60*60*24);

        cal.add(Calendar.DATE, period);
        System.out.println("CAL AFTER ADD: "+cal);

        //period =(int) ((datePeriodf.getTime()-datePeriod.getTime())/86400000);
        System.out.println("Hay "+period+" dias de diferencia");

        if(period<=0){
            covidDataRequestLeastSquares.setDateLocationCovid("No se pueden realizar proyecciones del dia actual o dias anteriores");
        }else{
            covidDataRequestLeastSquares.setDateLocationCovid(forecastDate);
            getVariables();
        }
    }

    private void leastSquaresMethod() {
        float x = 0,y = 0,x2 = 0,y2 = 0,xy = 0,b=0,a=0,growthPercentage=0, forecast =0;
        float xc = 0,yc = 0,x2c = 0,y2c = 0,xyc = 0,bc=0,ac=0,growthPercentagec=0, forecastc =0;
        float xd = 0,yd = 0,x2d = 0,y2d = 0,xyd = 0,bd=0,ad=0,growthPercentaged=0, forecastd =0;
        float xr = 0,yr = 0,x2r = 0,y2r = 0,xyr = 0,br=0,ar=0,growthPercentager=0, forecastr =0;
        float n = periodQuantitiesVac.size();
        for(int i=-0; i<periodQuantitiesVac.size(); i++){
            x+=periodQuantitiesVac.get(i).getPeriod();
            x2+= (float) Math.pow(periodQuantitiesVac.get(i).getPeriod(),2);
            y+=periodQuantitiesVac.get(i).getQuantity();
            y2+= (float) Math.pow(periodQuantitiesVac.get(i).getQuantity(),2);
            xy+=periodQuantitiesVac.get(i).getPeriod()*periodQuantitiesVac.get(i).getQuantity();

            xr+=periodQuantitiesRec.get(i).getPeriod();
            x2r+= (float) Math.pow(periodQuantitiesRec.get(i).getPeriod(),2);
            yr+=periodQuantitiesRec.get(i).getQuantity();
            y2r+= (float) Math.pow(periodQuantitiesRec.get(i).getQuantity(),2);
            xyr+=periodQuantitiesRec.get(i).getPeriod()*periodQuantitiesRec.get(i).getQuantity();

            xc+=periodQuantitiesConf.get(i).getPeriod();
            x2c+= (float) Math.pow(periodQuantitiesConf.get(i).getPeriod(),2);
            yc+=periodQuantitiesConf.get(i).getQuantity();
            y2c+= (float) Math.pow(periodQuantitiesConf.get(i).getQuantity(),2);
            xyc+=periodQuantitiesConf.get(i).getPeriod()*periodQuantitiesConf.get(i).getQuantity();

            xd+=periodQuantitiesDeath.get(i).getPeriod();
            x2d+= (float) Math.pow(periodQuantitiesDeath.get(i).getPeriod(),2);
            yd+=periodQuantitiesDeath.get(i).getQuantity();
            y2d+= (float) Math.pow(periodQuantitiesDeath.get(i).getQuantity(),2);
            xyd+=periodQuantitiesDeath.get(i).getPeriod()*periodQuantitiesDeath.get(i).getQuantity();
        }

        totalPeriod = periodQuantitiesVac.get(periodQuantitiesVac.size()-1).getPeriod() + period;

        b = (float) (((n*xy)-(x*y))/((n*x2)-(Math.pow(x,2))));
        a = (y-(b*x)) /n;
        growthPercentage = ((b*n)/y)*100;
        forecast = a + (b*totalPeriod);

        bc = (float) (((n*xyc)-(xc*yc))/((n*x2c)-(Math.pow(xc,2))));
        ac = (yc-(bc*xc)) /n;
        growthPercentagec = ((bc*n)/yc)*100;
        forecastc = ac + (bc*totalPeriod);

        br = (float) (((n*xyr)-(xr*yr))/((n*x2r)-(Math.pow(xr,2))));
        ar = (yr-(br*xr)) /n;
        growthPercentager = ((br*n)/yr)*100;
        forecastr = ar + (br*totalPeriod);

        bd = (float) (((n*xyd)-(xd*yd))/((n*x2d)-(Math.pow(xd,2))));
        ad = (yd-(bd*xd)) /n;
        growthPercentaged = ((bd*n)/yd)*100;
        forecastd = ad + (bd*totalPeriod);

        //Guardando datos del metodo de minimos cuadrados en una variable
        covidDataRequestLeastSquares.setNameLocationCovid(locationName);

        covidDataRequestLeastSquares.setVacForecast(forecast);
        covidDataRequestLeastSquares.setVacPercentage(growthPercentage);

        covidDataRequestLeastSquares.setConfForecast(forecastc);
        covidDataRequestLeastSquares.setConfPercentage(growthPercentagec);

        covidDataRequestLeastSquares.setDeathForecast(forecastd);
        covidDataRequestLeastSquares.setDeathpercetage(growthPercentaged);

        covidDataRequestLeastSquares.setRecForecast(forecastr);
        covidDataRequestLeastSquares.setRecPercentage(growthPercentager);

    }

    private Date parseFecha(String dateLocationCovid) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date fechaDate = null;
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));
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
