package com.rodrigodev.payment_sytem;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class PaymentSytemApplication {

	public static void main(String[] args) throws IOException {
		// Carregar propriedades do arquivo env.properties
        Properties properties = new Properties();
        properties.load(PaymentSytemApplication.class.getClassLoader().getResourceAsStream("env.properties"));

        // Adicionar propriedades ao Spring Application
        SpringApplication app = new SpringApplication(PaymentSytemApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
	}

}
