package com.biohack.z5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EmbeddedServletContainerAutoConfiguration.EmbeddedTomcat.class)
@Configuration
public class BioHackApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BioHackApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BioHackApp.class);
	}
}
