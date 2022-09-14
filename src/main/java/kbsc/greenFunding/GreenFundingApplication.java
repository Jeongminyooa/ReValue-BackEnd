package kbsc.greenFunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class GreenFundingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenFundingApplication.class, args);
	}

}
