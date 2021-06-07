package bo.ucb.edu.vic19.util.statistics.leastSquaresMethod;

import bo.ucb.edu.vic19.dto.CovidDataRequest;
import bo.ucb.edu.vic19.dto.CovidDataStatistics;
import bo.ucb.edu.vic19.dto.LeastSquaresFunction;
import bo.ucb.edu.vic19.dto.LeastSquaresVariables;
import bo.ucb.edu.vic19.model.CovidData;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeastSquaresLib {
    public LeastSquaresFunction getFunction(List<CovidDataRequest> list) throws ParseException {
        // v=[r,c,d,v]
        long[] sumXY= new long[]{0, 0, 0, 0};
        long sumX2=0;
        long sumX=0;
        double[] mList= new double[]{0.0, 0.0, 0.0, 0.0};
        double[] bList= new double[]{0.0, 0.0, 0.0, 0.0};
        long[] sumY= new long[]{0, 0, 0, 0};
        Long primDate= new SimpleDateFormat("yyyy-MM-dd").parse(list.get(0).getDateLocationCovid()).getTime();
        for(CovidDataRequest cov:list){
            System.out.println(cov.getRecuperated());
            sumY[0]+=cov.getRecuperated();
            cov.setRecuperated((int)sumY[0]);
            sumY[1]+=cov.getConfirmedCases();
            cov.setConfirmedCases((int)sumY[1]);
            sumY[2]+=cov.getDeathCases();
            cov.setDeathCases((int)sumY[2]);
            sumY[3]+=cov.getVaccinated();
            cov.setVaccinated((int)sumY[3]);

        }
        sumY= new long[]{0, 0, 0, 0};
        for(CovidDataRequest cov:list){
            Long date= new SimpleDateFormat("yyyy-MM-dd").parse(cov.getDateLocationCovid()).getTime();
            double x=(date-primDate)/86400000.0;
            sumX+=x;
            sumXY[0]+=(long)cov.getRecuperated()*x;
            sumXY[1]+=(long)cov.getConfirmedCases()*x;
            sumXY[2]+=(long)cov.getDeathCases()*x;
            sumXY[3]+=(long)cov.getVaccinated()*x;

            sumX2+=x*x;
            sumY[0]+=(long)cov.getRecuperated();
            sumY[1]+=(long)cov.getConfirmedCases();
            sumY[2]+=(long)cov.getDeathCases();
            sumY[3]+=(long)cov.getVaccinated();

        }
        int n=list.size();
        for(int i=0;i<4;i++){
            double den=(n*sumX2-Math.abs(sumX)*Math.abs(sumX));
            mList[i]=(n*sumXY[i]-sumX*sumY[i])/den;
            bList[i]=(sumY[i]*sumX2-sumX*sumXY[i])/den;
        }
        LeastSquaresVariables rVariables=new LeastSquaresVariables(mList[0],bList[0]);
        LeastSquaresVariables cVariables=new LeastSquaresVariables(mList[1],bList[1]);
        LeastSquaresVariables dVariables=new LeastSquaresVariables(mList[2],bList[2]);
        LeastSquaresVariables vVariables=new LeastSquaresVariables(mList[3],bList[3]);
        LeastSquaresFunction function=new LeastSquaresFunction(rVariables,cVariables,dVariables,vVariables);
        function.setFunction("y=mx+b");
        System.out.println(function);
        return function;
    }
}
