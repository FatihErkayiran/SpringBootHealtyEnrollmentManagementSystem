package com.cognixia.jump.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis( RequestHandlerSelectors.withClassAnnotation(RestController.class) )
				.paths( PathSelectors.any() )
				.build()
				.apiInfo( apiDetails() );
		
	}
	
	public ApiInfo apiDetails() {
	       return new ApiInfo(
	    		   "Enrollee with Dependents Info", 
	    		   "Open source API for obtaining/updating info on enrollees and their dependents", 
	    		   "1.0", 
	    		   "Free to use", 
	    		   new Contact("fatih", "https://github.com/FatihErkayiran", "fatiherkayiran@gmail.org"), 
	    		   "Api Licence", 
	    		   "https://github.com/FatihErkayiran",
	    		    Collections.emptyList());
		}
	
	
}
