package br.com.zup.CouchZupper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CouchZupperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouchZupperApplication.class, args);
	}

}
