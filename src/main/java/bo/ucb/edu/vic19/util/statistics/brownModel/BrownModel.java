package bo.ucb.edu.vic19.util.statistics.brownModel;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataRequestBrownModel;
import bo.ucb.edu.vic19.dto.CovidDataRequestLeastSquares;
import bo.ucb.edu.vic19.model.QuantitiesBrown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BrownModel {
    public String blName;
    public String dateCovid;
    public String locationName;
    public Integer period;
    public float totalPeriod;
    public String forecastDate;
    public float alpha=0.2f;
    public List<CovidDataRequest> covidDataList;
    public List<CovidDataRequest> covidDataListDESC;
    public ArrayList<QuantitiesBrown> periodQuantitiesVac = new ArrayList<QuantitiesBrown>(),periodQuantitiesConf = new ArrayList<QuantitiesBrown>(),periodQuantitiesDeath = new ArrayList<QuantitiesBrown>(),periodQuantitiesRec = new ArrayList<QuantitiesBrown>();
    public float n,dif,dayAux;
    public boolean flagFirst =true;
    private CovidDataRequestBrownModel covidDataRequestBrownModel = new CovidDataRequestBrownModel();


    public BrownModel(List<CovidDataRequest> covidDataList, List<CovidDataRequest> covidDataListDESC, String forecastDate, String locationName) {
        this.covidDataList=covidDataList;
        this.covidDataListDESC=covidDataListDESC;
        this.locationName = locationName;
        this.forecastDate = forecastDate;
    }

    public CovidDataRequestBrownModel getCovidDataRequestBrownModel() {
        determinePeriod();
        return covidDataRequestBrownModel;
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
                    periodQuantitiesVac.add(new QuantitiesBrown(covidDataList.get(i).getVaccinated()));
                    periodQuantitiesConf.add(new QuantitiesBrown(covidDataList.get(i).getConfirmedCases()));
                    periodQuantitiesRec.add(new QuantitiesBrown( covidDataList.get(i).getRecuperated()));
                    periodQuantitiesDeath.add(new QuantitiesBrown( covidDataList.get(i).getDeathCases()));
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

                    periodQuantitiesVac.add(new QuantitiesBrown(covidDataList.get(i).getVaccinated()));
                    periodQuantitiesConf.add(new QuantitiesBrown(covidDataList.get(i).getConfirmedCases()));
                    periodQuantitiesRec.add(new QuantitiesBrown(covidDataList.get(i).getRecuperated()));
                    periodQuantitiesDeath.add(new QuantitiesBrown(covidDataList.get(i).getDeathCases()));
                }
            }

            brownModel();

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
            covidDataRequestBrownModel.setDateLocationCovid("No se pueden realizar proyecciones del dia actual o dias anteriores");
        }else{
            covidDataRequestBrownModel.setDateLocationCovid(forecastDate);
            getVariables();
        }
    }

    private void brownModel() {
        float DAM = 0, sum =0;
        for(int i=0; i<periodQuantitiesVac.size(); i++){
            //at
            if(i==0){
                periodQuantitiesVac.get(i).setAt(periodQuantitiesVac.get(i).getYt());
                periodQuantitiesConf.get(i).setAt(periodQuantitiesConf.get(i).getYt());
                periodQuantitiesRec.get(i).setAt(periodQuantitiesRec.get(i).getYt());
                periodQuantitiesDeath.get(i).setAt(periodQuantitiesDeath.get(i).getYt());
            }else{
                periodQuantitiesVac.get(i).setAt((alpha*periodQuantitiesVac.get(i).getYt())+(1-alpha)*periodQuantitiesVac.get(i-1).getAt());
                periodQuantitiesRec.get(i).setAt((alpha*periodQuantitiesRec.get(i).getYt())+(1-alpha)*periodQuantitiesRec.get(i-1).getAt());
                periodQuantitiesConf.get(i).setAt((alpha*periodQuantitiesConf.get(i).getYt())+(1-alpha)*periodQuantitiesConf.get(i-1).getAt());
                periodQuantitiesDeath.get(i).setAt((alpha*periodQuantitiesDeath.get(i).getYt())+(1-alpha)*periodQuantitiesDeath.get(i-1).getAt());

            }
        }

        for(int i=0; i<periodQuantitiesVac.size(); i++){
            //atp
            if(i==0){
                periodQuantitiesVac.get(i).setAtp(periodQuantitiesVac.get(i).getAt());
                periodQuantitiesConf.get(i).setAtp(periodQuantitiesConf.get(i).getAt());
                periodQuantitiesRec.get(i).setAtp(periodQuantitiesRec.get(i).getAt());
                periodQuantitiesDeath.get(i).setAtp(periodQuantitiesDeath.get(i).getAt());
            }else{
                periodQuantitiesVac.get(i).setAtp((alpha*periodQuantitiesVac.get(i).getAt())+(1-alpha)*periodQuantitiesVac.get(i-1).getAtp());
                periodQuantitiesRec.get(i).setAtp((alpha*periodQuantitiesRec.get(i).getAt())+(1-alpha)*periodQuantitiesRec.get(i-1).getAtp());
                periodQuantitiesConf.get(i).setAtp((alpha*periodQuantitiesConf.get(i).getAt())+(1-alpha)*periodQuantitiesConf.get(i-1).getAtp());
                periodQuantitiesDeath.get(i).setAtp((alpha*periodQuantitiesDeath.get(i).getAt())+(1-alpha)*periodQuantitiesDeath.get(i-1).getAtp());

            }
        }

        for(int i=0; i<periodQuantitiesVac.size(); i++){
            //atpp
                periodQuantitiesVac.get(i).setAtpp(2*periodQuantitiesVac.get(i).getAt()-periodQuantitiesVac.get(i).getAtp());
                periodQuantitiesRec.get(i).setAtpp(2*periodQuantitiesRec.get(i).getAt()-periodQuantitiesRec.get(i).getAtp());
                periodQuantitiesConf.get(i).setAtpp(2*periodQuantitiesConf.get(i).getAt()-periodQuantitiesConf.get(i).getAtp());
                periodQuantitiesDeath.get(i).setAtpp(2*periodQuantitiesDeath.get(i).getAt()-periodQuantitiesDeath.get(i).getAtp());
        }

        for(int i=0; i<periodQuantitiesVac.size(); i++){
            //bt
            periodQuantitiesVac.get(i).setBt(alpha*(periodQuantitiesVac.get(i).getAt()-periodQuantitiesVac.get(i).getAtp())/(1-alpha));
            periodQuantitiesRec.get(i).setBt(alpha*(periodQuantitiesRec.get(i).getAt()-periodQuantitiesRec.get(i).getAtp())/(1-alpha));
            periodQuantitiesConf.get(i).setBt(alpha*(periodQuantitiesConf.get(i).getAt()-periodQuantitiesConf.get(i).getAtp())/(1-alpha));
            periodQuantitiesDeath.get(i).setBt(alpha*(periodQuantitiesDeath.get(i).getAt()-periodQuantitiesDeath.get(i).getAtp())/(1-alpha));
        }

        for(int i=1; i<periodQuantitiesVac.size(); i++){
            //ytp
            periodQuantitiesVac.get(i).setYtp(periodQuantitiesVac.get(i).getAtpp()+periodQuantitiesVac.get(i).getBt()*period);
            periodQuantitiesRec.get(i).setYtp(periodQuantitiesRec.get(i).getAtpp()+periodQuantitiesRec.get(i).getBt()*period);
            periodQuantitiesConf.get(i).setYtp(periodQuantitiesConf.get(i).getAtpp()+periodQuantitiesConf.get(i).getBt()*period);
            periodQuantitiesDeath.get(i).setYtp(periodQuantitiesDeath.get(i).getAtpp()+periodQuantitiesDeath.get(i).getBt()*period);
        }

        for(int i=1; i<periodQuantitiesVac.size(); i++){
            //et
            periodQuantitiesVac.get(i).setEt(Math.abs(periodQuantitiesVac.get(i).getYt()-periodQuantitiesVac.get(i).getYtp()));
            periodQuantitiesRec.get(i).setEt(Math.abs(periodQuantitiesRec.get(i).getYt()-periodQuantitiesRec.get(i).getYtp()));
            periodQuantitiesConf.get(i).setEt(Math.abs(periodQuantitiesConf.get(i).getYt()-periodQuantitiesConf.get(i).getYtp()));
            periodQuantitiesDeath.get(i).setEt(Math.abs(periodQuantitiesDeath.get(i).getYt()-periodQuantitiesDeath.get(i).getYtp()));
        }

        for(int i=1; i<periodQuantitiesVac.size(); i++){
            //dam
            sum+=periodQuantitiesVac.get(i).getEt();
        }

        DAM = sum/periodQuantitiesVac.size()-1;

        covidDataRequestBrownModel.setDAM(DAM);

        covidDataRequestBrownModel.setNameLocationCovid(locationName);

        covidDataRequestBrownModel.setVacForecast(periodQuantitiesVac.get(periodQuantitiesVac.size()-1).getYtp());

        covidDataRequestBrownModel.setConfForecast(periodQuantitiesConf.get(periodQuantitiesConf.size()-1).getYtp());

        covidDataRequestBrownModel.setDeathForecast(periodQuantitiesDeath.get(periodQuantitiesDeath.size()-1).getYtp());

        covidDataRequestBrownModel.setRecForecast(periodQuantitiesRec.get(periodQuantitiesRec.size()-1).getYtp());

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
