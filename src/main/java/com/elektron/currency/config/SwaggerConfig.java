package com.elektron.currency.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
	
    @Bean
    public Docket configApi() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(info());
    }

	private ApiInfo info() {
	    ApiInfo apiInfo = new ApiInfo(
	            "Currency Converter",
	            "API for currency conversion",
	            "1.0",
	            null,
	            new Contact("Edmilson Pontes", null, "edmilsonspontes@gmail.com"),
	            null,
	            null, 
	            new ArrayList<>());
	    return apiInfo;
	}
	
}
