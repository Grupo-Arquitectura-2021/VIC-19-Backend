package bo.ucb.edu.ingsoft;

import bo.ucb.edu.ingsoft.bl.DataBl;
import bo.ucb.edu.ingsoft.util.CovidDataUrlUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IngsoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngsoftApplication.class, args);
		CovidDataUrlUtil.getJson();
		CovidDataUrlUtil.getJsonHistorico();
		//DataBl dataBl = new DataBl(null,null);
		//dataBl.createData(null);

	}

}
