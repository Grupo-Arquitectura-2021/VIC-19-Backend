package bo.ucb.edu.ingsoft;


import bo.ucb.edu.ingsoft.util.CovidDataJsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.io.*;



@EnableScheduling
@SpringBootApplication
public class IngsoftApplication{

	public static void main(String[] args) throws IOException {
		SpringApplication.run(IngsoftApplication.class, args);
		//CovidDataJsonUtil.getJson();
		CovidDataJsonUtil.getJsonMunicipios();

	}
}

@Configuration
class SchedulerConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.initialize();

		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	}
}
