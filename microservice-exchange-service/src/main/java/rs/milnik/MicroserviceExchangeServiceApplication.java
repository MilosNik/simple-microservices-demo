package rs.milnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("rs.milnik")
public class MicroserviceExchangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceExchangeServiceApplication.class, args);
	}

}
