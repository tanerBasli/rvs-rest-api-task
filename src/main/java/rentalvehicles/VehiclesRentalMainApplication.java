package rentalvehicles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry(proxyTargetClass = true)
public class VehiclesRentalMainApplication {
	public static void main(String[] args) {
		SpringApplication.run(VehiclesRentalMainApplication.class, args);
	}
}