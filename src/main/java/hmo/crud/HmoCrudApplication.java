package hmo.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmoCrudApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(HmoCrudApplication.class, args);
		} catch (Throwable t) {
			new Exception(t).printStackTrace();
		}
	}

}
