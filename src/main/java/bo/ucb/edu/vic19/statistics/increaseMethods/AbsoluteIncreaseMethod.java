package bo.ucb.edu.vic19.statistics.increaseMethods;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestIncreaseMethod;
import bo.ucb.edu.vic19.dto.CovidDataRequestLeastSquares;
import bo.ucb.edu.vic19.model.PeriodQuantity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AbsoluteIncreaseMethod {
    public String blName;
    public String dateCovid;
    public String locationName;
    public Integer period;
    public String forecastDate;
    public List<CovidDataRequest> covidDataList;
    public List<CovidDataRequest> covidDataListDESC;
    public ArrayList<PeriodQuantity> periodQuantitiesVac = new ArrayList<PeriodQuantity>(),periodQuantitiesConf = new ArrayList<PeriodQuantity>(),periodQuantitiesDeath = new ArrayList<PeriodQuantity>(),periodQuantitiesRec = new ArrayList<PeriodQuantity>();
    public float n,dif,dayAux;
    public boolean flagFirst = true;
    public CovidDataRequestIncreaseMethod covidDataRequestIncreaseMethod = new CovidDataRequestIncreaseMethod();


    public AbsoluteIncreaseMethod(List<CovidDataRequest> covidDataList, List<CovidDataRequest> covidDataListDESC, String forecastDate, String locationName) {
        this.covidDataList=covidDataList;
        this.covidDataListDESC=covidDataListDESC;
        this.locationName=locationName;
        this.forecastDate = forecastDate;
    }

    public CovidDataRequestIncreaseMethod getCovidDataRequestAbsoluteIncrease() {
        determinePeriod();
        return covidDataRequestIncreaseMethod;
    }

    private void getVariables() {
        n = covidDataList.size();
        System.out.println("get variables size "+covidDataList.size());
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
                    //System.out.println("fecha covid: "+covidDataList.get(i).getDateLocationCovid());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePeriod);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    dif = Math.abs(day - dayAux);
                    dayAux = day;
                    //System.out.println("fecha: "+covidDataList.get(i).getDateLocationCovid());
                    //System.out.println("dif "+dif);
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

            absoluteIncreaseMethod();
        }

    }

    private void determinePeriod() {
        covidDataRequestIncreaseMethod.setDateLocationCovid(forecastDate);

        Date datePeriod, forecastPeriod;
        datePeriod = parseFecha(covidDataListDESC.get(0).getDateLocationCovid());
        System.out.println("primer dato fecha "+covidDataListDESC.get(0).getDateLocationCovid());
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePeriod);

        forecastPeriod =parseFecha(forecastDate);
        Calendar calf = Calendar.getInstance();
        calf.setTime(forecastPeriod);

        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        forecastDate = dateFormat.format(date);

        long startTime =  cal.getTimeInMillis();
        long endTime = calf.getTimeInMillis();
        long diffTime = Math.abs(endTime - startTime);
        period = (int) diffTime/(1000*60*60*24);

        System.out.println("periodo difftime : "+period);

        getVariables();
    }

    private void absoluteIncreaseMethod() {
        float x = 0, xsum=0;
        float xc = 0,xcsum=0;
        float xd = 0,xdsum=0;
        float xr = 0,xrsum=0;
        float mediax=0, mediaxc=0, mediaxd=0, mediaxr=0;
        float forecastx=0, forecastxc=0, forecastxd=0, forecastxr=0;
        float n = periodQuantitiesVac.size();
        for(int i=1; i<periodQuantitiesVac.size(); i++){
            System.out.println("get 0 vac PQ "+periodQuantitiesVac.get(i).getQuantity());

            System.out.println("periodo "+periodQuantitiesVac.get(i).getPeriod());

            x = (periodQuantitiesVac.get(i).getQuantity()-periodQuantitiesVac.get(i-1).getQuantity());
            xsum+=x;

            System.out.println("valor de x "+x);
            System.out.println("suma vac "+xsum);

            xr = (periodQuantitiesRec.get(i).getQuantity()-periodQuantitiesRec.get(i-1).getQuantity());
            xrsum+=xr;

            xc = (periodQuantitiesConf.get(i).getQuantity()-periodQuantitiesConf.get(i-1).getQuantity());
            xcsum+=xc;

            xd= (periodQuantitiesDeath.get(i).getQuantity()-periodQuantitiesDeath.get(i-1).getQuantity());
            xdsum+=xd;
        }

            mediax = xsum/(periodQuantitiesVac.size()-1);
            mediaxc = xcsum/(periodQuantitiesVac.size()-1);
            mediaxd = xdsum/(periodQuantitiesVac.size()-1);
            mediaxr = xrsum/(periodQuantitiesVac.size()-1);

            for(int p=0; p<period;p++) {
                if(p==0) {
                    forecastx = covidDataListDESC.get(0).getVaccinated() + mediax;
                    forecastxc = covidDataListDESC.get(0).getConfirmedCases() + mediaxc;
                    forecastxd = covidDataListDESC.get(0).getDeathCases() + mediaxd;
                    forecastxr = covidDataListDESC.get(0).getRecuperated() + mediaxr;
                }else{
                    forecastx+=mediax;
                    forecastxc+=mediaxc;
                    forecastxd+=mediaxd;
                    forecastxr+=mediaxr;
                }
                System.out.println("forecast vac "+forecastx);
            }

        covidDataRequestIncreaseMethod.setNameLocationCovid(locationName);

        covidDataRequestIncreaseMethod.setVacForecast(forecastx);

        covidDataRequestIncreaseMethod.setConfForecast(forecastxc);

        covidDataRequestIncreaseMethod.setDeathForecast(forecastxd);

        covidDataRequestIncreaseMethod.setRecForecast(forecastxr);


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
