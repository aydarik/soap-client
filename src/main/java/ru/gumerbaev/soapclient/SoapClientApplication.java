package ru.gumerbaev.soapclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:filereader.properties")
public class SoapClientApplication {

	public static void main(String[] args) {
		System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl"); // warning recommendation
		SpringApplication.run(SoapClientApplication.class, args);
	}

}
